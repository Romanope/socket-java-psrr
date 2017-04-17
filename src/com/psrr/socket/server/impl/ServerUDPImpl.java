package com.psrr.socket.server.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.psrr.socket.contrato.ISocket;
import com.socket.gui.Index;

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
			Index.addLogServerUDP("Servidor UDP executando na porta " + port);
			this.port = port;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(byte[] msgs) {
		
		Index.addLogServerUDP("Mensagem para transmitir para o cliente recebida...");
		Index.addLogServerUDP("Preparando mensagem para envio...");
		DatagramPacket retMSG = new DatagramPacket(msgs, msgs.length, source, currentPort);
		try {
			server.send(retMSG);
			Index.addLogServerUDP("Mensagem enviada para o cliente...");
			Index.addLogServerUDP("Finalizando troca de mensagens...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		
		req = new byte[1024];
		request = new DatagramPacket(req, req.length);
		Index.addLogServerUDP("aguardando conexão...");
		try {
			server.receive(request);
			Index.addLogServerUDP("Conexão obtida... Mensagem recebida...");
			source = request.getAddress();
			Index.addLogServerUDP("source " + source.toString());
			currentPort = request.getPort();
			Index.addLogServerUDP("Source port" + currentPort);
			Index.addLogServerUDP("Passando mensagem recebida para o DHCP...");
			return request.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
