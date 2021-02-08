package com.gvendas.gestaovendas.dto.categoria;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entidades.Categoria;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Categoria requisição DTO")
public class CategoriaRequestDTO {
	
	@ApiModelProperty(value = "Nome")
	@NotBlank(message = "Nome")
	@Length(min = 3, max = 50, message = "Nome")
	private String nome;
	
	public Categoria converterParaEntidade() {
		return new Categoria(nome);
	}
	
	public Categoria converterParaEntidade(Long codigo) {
		return new Categoria(codigo, nome);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
