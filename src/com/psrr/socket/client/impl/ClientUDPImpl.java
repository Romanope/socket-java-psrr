package com.psrr.socket.client.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.psrr.socket.contrato.ISocket;
import com.psrr.socket.util.Constantes;
import com.socket.gui.Index;

public class ClientUDPImpl implements ISocket {

	private DatagramSocket client;
	
	private InetAddress serverIp;
	
	private int port;
	
	public ClientUDPImpl(int port) {
		try {
			Index.addLogClientUDP("Criando socket para envio de mensagem...");
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
		Index.addLogClientUDP("Mensagem para envio ao servidor recebida...");
		Index.addLogClientUDP("Configurando datagrama para transmissão...");
		DatagramPacket datagrama = new DatagramPacket(msgs, msgs.length, serverIp, port);
		
		try {
			Index.addLogClientUDP("Enviando mensagem...");
			client.send(datagrama);
			Index.addLogClientUDP("Mensagem enviada com sucesso...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] receive() {
		
		byte[] response = new byte[1024];
		DatagramPacket pResponse = new DatagramPacket(response, response.length);
		Index.addLogClientUDP("Aguardando resposta do servidor UDP...");
		try {
			client.receive(pResponse);
			Index.addLogClientUDP("Resposta recebida com sucesso...");
			Index.addLogClientUDP("Passando resposta para o DHCP...");
			return pResponse.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
