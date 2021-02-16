package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.servico.ClienteServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {

	@Autowired
	private ClienteServico clienteServico;

	@ApiOperation(value = "Listar", nickname = "listarTodas")
	@GetMapping
	public List<Cliente> listarTodas() {
		return clienteServico.listarTodos();
	}

	@ApiOperation(value = "Listar por código", nickname = "buscarPorId")
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigo);
		return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
	}
}
