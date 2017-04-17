package com.psrr.socket.dhcp.messages;

import java.io.Serializable;

public class DiscoveryMessage extends GenericMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ipSource;
	
	private char ipClass;
	
	private String ipDestiny;
	
	public DiscoveryMessage(String ipSource, char ipClass, String ipDestiny) {
		this.ipSource = ipSource;
		this.ipClass = ipClass;
		this.ipDestiny = ipDestiny;
	}

	public String getIpSource() {
		return ipSource;
	}

	public void setIpSource(String ipSource) {
		this.ipSource = ipSource;
	}

	public char getIpClass() {
		return ipClass;
	}

	public void setIpClass(char ipClass) {
		this.ipClass = ipClass;
	}

	public String getIpDestiny() {
		return ipDestiny;
	}

	public void setIpDestiny(String ipDestiny) {
		this.ipDestiny = ipDestiny;
	}
	
	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder("\nDISCOVERY MESSAGE: ");
		
		retorno.append("\nIP HOST: ").append(getIpSource())
		.append("\nIP CLASS: ").append(getIpClass())
		.append("\nIP DESTINY: ").append(getIpDestiny()).append("\n");
		
		return retorno.toString();
	}
}
