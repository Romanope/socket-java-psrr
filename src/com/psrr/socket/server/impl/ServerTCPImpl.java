package com.psrr.socket.server.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.psrr.socket.contrato.ISocket;
import com.socket.gui.Index;

public class ServerTCPImpl implements ISocket {

	private ServerSocket serverTCP;	
	
	private ObjectOutputStream out;
	
	private ObjectInputStream in;
	
	private Socket conn;
	
	public ServerTCPImpl(int port) {
		try {
			this.serverTCP =  new ServerSocket(port);
			Index.addLogServerTCP("Servidor TCP rodando na porta " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(byte[] msgs) {
		try {
			Index.addLogServerTCP("Mensagem para envio recebida...");
			out = new ObjectOutputStream(conn.getOutputStream());
			
			out.writeObject(msgs);
			out.flush();
			Index.addLogServerTCP("Mensagem enviada o cliente...");
			
			conn.close();
			out.close();
			in.close();
			Index.addLogServerTCP("Conexões encerradas...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		
		try {
			Index.addLogServerTCP("Servidor aguardando conexão...");
			conn = serverTCP.accept();
			Index.addLogServerTCP("Conexão com o cliente realizada...");
			in = new ObjectInputStream(conn.getInputStream());
			byte[] msg = (byte[]) in.readObject();
			Index.addLogServerTCP("Repassando dados recebidos para o DHCP...");
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
