package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repositorio.ClienteRepositorio;

@Service
public class ClienteServico {

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	public List<Cliente> listarTodos() {
		return clienteRepositorio.findAll();
	}

	public Optional<Cliente> buscarPorCodigo(Long codigo) {
		return clienteRepositorio.findById(codigo);
	}

	public Cliente salvar(Cliente cliente) {
		validarClienteDuplicado(cliente);
		return clienteRepositorio.save(cliente);
	}

	private void validarClienteDuplicado(Cliente cliente) {
		Cliente clientePorNome = clienteRepositorio.findByNome(cliente.getNome());
		if (clientePorNome != null && clientePorNome.getCodigo() != cliente.getCodigo()) {
			throw new RegraNegocioException(
					String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
		}
	}
}
