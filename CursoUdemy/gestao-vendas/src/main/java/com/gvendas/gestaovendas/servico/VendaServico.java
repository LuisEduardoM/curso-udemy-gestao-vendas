package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
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

	public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDto) {
		Cliente cliente = validarClienteVendaExiste(codigoCliente);
		validarProdutoExiste(vendaDto.getItensVendaDto());
		Venda vendaSalva = salvarVenda(cliente, vendaDto);
		return retornandoClienteVendaResponseDTO(vendaSalva, itemVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo()));
	}

	
	private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDto) {
		Venda vendaSalva = vendaRepositorio.save(new Venda(vendaDto.getData(), cliente));
		vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
				.forEach(itemVendaRepositorio::save);
		return vendaSalva;
	}

	private void validarProdutoExiste(List<ItemVendaRequestDTO> itensVendaDto) {
		itensVendaDto.forEach(item -> produtoServico.validarProdutoExiste(item.getCodigoProduto()));
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
