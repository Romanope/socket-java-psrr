package com.psrr.socket.client.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.psrr.socket.contrato.ISocket;
import com.psrr.socket.util.Constantes;

public class ClientUDPImpl implements ISocket {

	private DatagramSocket client;
	
	private InetAddress serverIp;
	
	private int port;
	
	public ClientUDPImpl(int port) {
		try {
			client = new DatagramSocket();
			serverIp = InetAddress.getByName(Constantes.IP_SERVER);
			this.port = port;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(byte[] msgs) {
		DatagramPacket datagrama = new DatagramPacket(msgs, msgs.length, serverIp, port);
		try {
			client.send(datagrama);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		
		byte[] response = new byte[1024];
		
		DatagramPacket pResponse = new DatagramPacket(response, response.length);
		try {
			client.receive(pResponse);
			return pResponse.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
