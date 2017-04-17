package com.psrr.socket.dhcp.messages;

import java.io.Serializable;

public class ACKMessage extends GenericMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String ipServer;
	
	public String ipOffer;
	
	private char ipClass;
	
	public ACKMessage(String ipOffer, String ipServer, char ipClass) {
		this.ipOffer = ipOffer;
		this.ipServer = ipServer;
		this.ipClass = ipClass;
	}
	
	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	public String getIpOffer() {
		return ipOffer;
	}

	public void setIpOffer(String ipOffer) {
		this.ipOffer = ipOffer;
	}

	public char getIpClass() {
		return ipClass;
	}

	public void setIpClass(char ipClass) {
		this.ipClass = ipClass;
	}

	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder("ACK MESSAGE: ");
		
		retorno.append("\nIDENTIFICADOR: ".concat(String.valueOf(getIdentificador())))
		.append("\nIP SERVER: ").append(getIpServer())
		.append("\nIP OFFER: ").append(getIpOffer())
		.append("\nIP CLASS: ").append(getIpClass());
		
		return retorno.toString();
	}
}
