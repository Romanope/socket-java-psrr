package com.psrr.socket.contrato;

public interface ISocket {

	public void send(byte[] msgs);
	
	public byte[] receive();
}
