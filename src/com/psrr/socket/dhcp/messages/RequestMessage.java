package com.psrr.socket.dhcp.messages;

import java.io.Serializable;

public class RequestMessage extends GenericMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ipSource;
	
	private String ipOffer;
	
	private char ipClass;
	
	private String ipServer;
	
	public RequestMessage(String source, String ipOffer, char ipClass, String ipServer) {
		this.ipSource = source;
		this.ipOffer = ipOffer;
		this.ipClass = ipClass;
		this.ipServer = ipServer;
	}
	
	public String getIpSource() {
		return ipSource;
	}

	public void setIpSource(String ipSource) {
		this.ipSource = ipSource;
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

	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder("\nREQUEST MESSAGE: ");
		
		retorno.append("\nIDENTIFICADOR: ".concat(String.valueOf(getIdentificador())))
		.append("\nIP SOURCE: ").append(getIpSource())
		.append("\nIP OFFER: ").append(getIpOffer())
		.append("\nIP CLASS: ").append(getIpClass())
		.append("\nIP SERVER: ").append(getIpServer()).append("\n");
		
		return retorno.toString();
	}
}
