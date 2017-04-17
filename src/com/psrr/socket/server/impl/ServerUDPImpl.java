package com.psrr.socket.server.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.psrr.socket.contrato.ISocket;
import com.psrr.socket.util.Constantes;

public class ServerUDPImpl implements ISocket {

	private DatagramSocket server;
	
	private byte[] req;
	
	private DatagramPacket request;
	
	private int port;
	
	private int currentPort;
	
	private InetAddress source;
	
	public ServerUDPImpl(int port) {
		try {
			server = new DatagramSocket(port);
			this.port = port;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(byte[] msgs) {
		
		DatagramPacket retMSG = new DatagramPacket(msgs, msgs.length, source, currentPort);
		try {
			server.send(retMSG);
			System.out.println("UDP SERVER: Finish");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		
		req = new byte[1024];
		request = new DatagramPacket(req, req.length);
		System.out.println("UDP SERVER waiting for connection in the port: " + port);
		try {
			server.receive(request);
			source = request.getAddress();
			currentPort = request.getPort();
			return request.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
