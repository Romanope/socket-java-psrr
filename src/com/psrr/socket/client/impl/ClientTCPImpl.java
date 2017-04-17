package com.psrr.socket.client.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.psrr.socket.contrato.ISocket;
import com.socket.gui.Index;

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
			Index.addLogClientTCP("Enviando mensagem para o servidor...");

			out = new ObjectOutputStream(client.getOutputStream());
			Index.addLogClientTCP("Preparando dados para transmissão...");
			
			out.writeObject(msgs);
			out.flush();
			Index.addLogClientTCP("Mensagem enviada para o servidor...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		
		try {
			Index.addLogClientTCP("Aguardando resposta do servidor");
			in = new ObjectInputStream(client.getInputStream());
			byte[]response = (byte[]) in.readObject();
			Index.addLogClientTCP("Resposta recebida do servidor...");
			
			client.close();
			out.close();
			in.close();
			Index.addLogClientTCP("Fechando conexão...");
			Index.addLogClientTCP("Passando para o DHCP a mensagem recebida...");
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
