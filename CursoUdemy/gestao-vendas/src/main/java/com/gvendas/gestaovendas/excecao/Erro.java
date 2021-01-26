package com.gvendas.gestaovendas.excecao;

public class Erro {

	private String msgUsuario;
	private String msgDesenvolvedor;

	public Erro(String msgUsuario, String msgDesenvolvedor) {
		this.msgUsuario = msgUsuario;
		this.msgDesenvolvedor = msgDesenvolvedor;
	}

	public String getMsgUsuario() {
		return msgUsuario;
	}

	public String getMsgDesenvolvedor() {
		return msgDesenvolvedor;
	}
}
