package com.gvendas.gestaovendas.dto.venda;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Itens da venda requisição DTO")
public class ItemVendaRequestDTO {
	
	@ApiModelProperty(value = "Código produto")
	private Long codigoProduto;
	
	@ApiModelProperty(value = "Quantidade")
	private Integer quantidade;
	
	@ApiModelProperty(value = "Preço Vendido")
	private BigDecimal precoVendido;

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoVendido() {
		return precoVendido;
	}

	public void setPrecoVendido(BigDecimal precoVendido) {
		this.precoVendido = precoVendido;
	}

}
