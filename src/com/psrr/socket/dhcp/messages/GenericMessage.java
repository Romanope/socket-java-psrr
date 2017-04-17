package com.psrr.socket.dhcp.messages;

import java.io.Serializable;

public class GenericMessage implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long identificador;

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}
}
