package com.psrr.socket.dhcp.messages;

import java.io.Serializable;

public class OfferMessage extends GenericMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ipServer;
	
	private String ipOffer;
	
	private long timeToLive;
	
	private char ipClass;
	
	
	public OfferMessage(String ipServer, String ipOffer, long timeToLive, char ipClass) {
		this.ipServer = ipServer;
		this.ipOffer = ipOffer;
		this.timeToLive = timeToLive;
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


	public long getTimeToLive() {
		return timeToLive;
	}


	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}


	public char getIpClass() {
		return ipClass;
	}


	public void setIpClass(char ipClass) {
		this.ipClass = ipClass;
	}
	
	@Override
	public String toString() {
		
		StringBuilder retorno = new StringBuilder("OFFER MESSAGE");
		retorno.append("\nIDENTIFICADOR: ".concat(String.valueOf(getIdentificador())))
		.append("\nIP SERVER: ").append(getIpServer())
		.append("\nIP OFFER: ").append(getIpOffer())
		.append("\nIP CLASS: ").append(getIpClass())
		.append("\nTIME TO LIVE: ").append(getTimeToLive());
		
		return retorno.toString();
	}
}
