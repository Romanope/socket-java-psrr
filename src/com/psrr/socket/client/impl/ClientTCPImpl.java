package com.psrr.socket.client.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.psrr.socket.contrato.ISocket;

public class ClientTCPImpl implements ISocket {

	private String host;
	
	private int port;
	
	private Socket client;
	
	private ObjectOutputStream out;
	
	private ObjectInputStream in;
	
	public ClientTCPImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void send(byte[] msgs) {
		
		try {
			this.client = new Socket(host, port);
			
			System.out.println("Client enviando mensagem para o servidor");
			out = new ObjectOutputStream(client.getOutputStream());
			
			out.writeObject(msgs);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		try {
			
			System.out.println("Cliente aguardando resposta do servidor");
			in = new ObjectInputStream(client.getInputStream());
			byte[]response = (byte[]) in.readObject();
			System.out.println("Resposta recebida do servidor");
			client.close();
			out.close();
			in.close();
			
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
