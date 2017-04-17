package com.psrr.socket.server.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.psrr.socket.contrato.ISocket;

public class ServerTCPImpl implements ISocket {

	private ServerSocket serverTCP;	
	
	private ObjectOutputStream out;
	
	private ObjectInputStream in;
	
	private Socket conn;
	
	public ServerTCPImpl(int port) {
		try {
			this.serverTCP =  new ServerSocket(port);
			System.out.println("Servidor rodando na porta " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(byte[] msgs) {
		try {
			out = new ObjectOutputStream(conn.getOutputStream());
			
			out.writeObject(msgs);
			out.flush();
			
			conn.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		
		try {
			System.out.println("Servidor aguardando conexão...");
			conn = serverTCP.accept();
			System.out.println("Conexão com o cliente realizada...");
			in = new ObjectInputStream(conn.getInputStream());
			byte[] msg = (byte[]) in.readObject();
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
