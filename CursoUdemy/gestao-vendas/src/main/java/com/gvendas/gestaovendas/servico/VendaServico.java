package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repositorio.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repositorio.VendaRepositorio;

@Service
public class VendaServico extends AbstractVendaServico {

	private VendaRepositorio vendaRepositorio;
	private ItemVendaRepositorio itemVendaRepositorio;
	private ClienteServico clienteServico;
	private ProdutoServico produtoServico;

	@Autowired
	public VendaServico(VendaRepositorio vendaRepositorio, ClienteServico clienteServico,
			ItemVendaRepositorio itemVendaRepositorio, ProdutoServico produtoServico) {
		this.vendaRepositorio = vendaRepositorio;
		this.clienteServico = clienteServico;
		this.itemVendaRepositorio = itemVendaRepositorio;
		this.produtoServico = produtoServico;
	}

	public ClienteVendaResponseDTO listaVendaPorCliente(Long codigoCliente) {
		Cliente cliente = validarClienteVendaExiste(codigoCliente);
		List<VendaResponseDTO> vendaResponseDtoList = vendaRepositorio.findByClienteCodigo(codigoCliente).stream()
				.map(venda -> criandoVendaResponseDTO(venda, itemVendaRepositorio.findByVendaPorCodigo(venda.getCodigo())))
				.collect(Collectors.toList());
		return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);
	}

	public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
		Venda venda = validarVendaExiste(codigoVenda);
		List<ItemVenda> itensVendaList = itemVendaRepositorio.findByVendaPorCodigo(venda.getCodigo());
		return retornandoClienteVendaResponseDTO(venda, itensVendaList);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDto) {
		Cliente cliente = validarClienteVendaExiste(codigoCliente);
		validarProdutoExisteEAtualizarQuantidade(vendaDto.getItensVendaDto());
		Venda vendaSalva = salvarVenda(cliente, vendaDto);
		return retornandoClienteVendaResponseDTO(vendaSalva, itemVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo()));
	}
	
	public ClienteVendaResponseDTO atualizar(Long codigoVenda, Long codigoCliente, VendaRequestDTO vendaDto) {
		validarVendaExiste(codigoVenda);
		Cliente cliente = validarClienteVendaExiste(codigoCliente);
		List<ItemVenda> itensVendaList = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisteEDevolverEstoque(itensVendaList);
		validarProdutoExisteEAtualizarQuantidade(vendaDto.getItensVendaDto());
		itemVendaRepositorio.deleteAll(itensVendaList);
		Venda vendaAtualizada = atualizarVenda(codigoVenda, cliente, vendaDto);
		return retornandoClienteVendaResponseDTO(vendaAtualizada, itemVendaRepositorio.findByVendaPorCodigo(vendaAtualizada.getCodigo()));
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void deletar(Long codigoVenda) {
		validarVendaExiste(codigoVenda);
		List<ItemVenda> itensVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisteEDevolverEstoque(itensVenda);
		itemVendaRepositorio.deleteAll(itensVenda);
		vendaRepositorio.deleteById(codigoVenda);
	}
	
	private void validarProdutoExisteEDevolverEstoque(List<ItemVenda> itensVenda) {
		itensVenda.forEach(item -> {
			Produto produto = produtoServico.validarProdutoExiste(item.getProduto().getCodigo());
			produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
			produtoServico.atualizarQuantidadeEmEstoque(produto);
		});
	}
	
	private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDto) {
		Venda vendaSalva = vendaRepositorio.save(new Venda(vendaDto.getData(), cliente));
		vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepositorio::save);
		return vendaSalva;
	}
	
	private Venda atualizarVenda(Long codigoVenda, Cliente cliente, VendaRequestDTO vendaDto) {
		Venda vendaSalva = vendaRepositorio.save(new Venda(codigoVenda, vendaDto.getData(), cliente));
		vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepositorio::save);
		return vendaSalva;
	}

	private void validarProdutoExisteEAtualizarQuantidade(List<ItemVendaRequestDTO> itensVendaDto) {
		itensVendaDto.forEach(item -> {
			Produto produto = produtoServico.validarProdutoExiste(item.getCodigoProduto());	
			validarQuantidadeProdutoExiste(produto, item.getQuantidade());
			produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
			produtoServico.atualizarQuantidadeEmEstoque(produto);
		});
	}

	private void validarQuantidadeProdutoExiste(Produto produto, Integer qtdeVendaDto) {
		if(!(produto.getQuantidade() >= qtdeVendaDto)) {
			throw new RegraNegocioException(String.format("A quantidade %s informada para o produto %s não está disponível em estoque", 
					qtdeVendaDto, produto.getDescricao()));
		}
	}
	
	private Venda validarVendaExiste(Long codigoVenda) {
		Optional<Venda> venda = vendaRepositorio.findById(codigoVenda);
		if (venda.isEmpty()) {
			throw new RegraNegocioException(String.format("Venda de código %s não encontrada.", codigoVenda));
		}
		return venda.get();
	}

	private Cliente validarClienteVendaExiste(Long codigoCliente) {
		Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigoCliente);
		if (cliente.isEmpty()) {
			throw new RegraNegocioException(
					String.format("O Cliente de código %s informado não existe no cadastro.", codigoCliente));
		}
		return cliente.get();
	}
}
