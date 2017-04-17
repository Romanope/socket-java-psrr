package com.socket.psrr.dhcp;

import java.io.IOException;

import com.psrr.socket.contrato.ISocket;
import com.psrr.socket.dhcp.messages.ACKMessage;
import com.psrr.socket.dhcp.messages.DiscoveryMessage;
import com.psrr.socket.dhcp.messages.OfferMessage;
import com.psrr.socket.dhcp.messages.RequestMessage;
import com.psrr.socket.util.Constantes;
import com.psrr.socket.util.ControlInstanceClient;
import com.psrr.socket.util.Util;
import com.socket.gui.Index;

public class DHCPClient {

	private ISocket clientSocket;
	
	private String host;
	
	private int port;
	
	private char ipClass;
	
	public DHCPClient(String host, int port, boolean clientTCP, char ipClass) {
		this.host = host;
		this.port = port;
		this.ipClass = ipClass;
		clientSocket = ControlInstanceClient.getInstanceClient(host, port, clientTCP);
		try {
			startConfig();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void startConfig() throws IOException, ClassNotFoundException {
		sendDiscovery();
		OfferMessage offer = responseDiscovery();
		sendRequest(offer);
		responseRequest();
	}
	
	private void sendDiscovery() throws IOException {
		DiscoveryMessage discovery = new DiscoveryMessage(Constantes.HOST, ipClass, Constantes.IP_SERVER);
		byte[] dArray = Util.serialize(discovery);
		clientSocket.send(dArray);
		Index.addLogDHCPClient("Mensagem DISCOVERY Enviada para o servidor");
		Index.addLogDHCPClient(discovery.toString());
	}
	
	private OfferMessage responseDiscovery() throws ClassNotFoundException, IOException {
		
		byte[] response = clientSocket.receive();
		OfferMessage offer = (OfferMessage) Util.deserialize(response);
		
		Index.addLogDHCPClient("Resposta OFFER recebida");
		Index.addLogDHCPClient(offer.toString());
		
		return offer;
	}
	
	private void sendRequest(OfferMessage offer) throws IOException {
		
		RequestMessage request = new RequestMessage("localhost", offer.getIpOffer(), offer.getIpClass(), offer.getIpServer());
		request.setIdentificador(offer.getIdentificador());
		byte[] rArray = Util.serialize(request);
		clientSocket.send(rArray);
		
		Index.addLogDHCPClient("Mensagem REQUEST Enviada para o servidor");
		Index.addLogDHCPClient(request.toString());
	}
	
	private void responseRequest() throws ClassNotFoundException, IOException {
		
		byte[] response = clientSocket.receive();
		ACKMessage ack = (ACKMessage) Util.deserialize(response);

		Index.addLogDHCPClient("MENSAGEM ACK RECEBIDA");
		Index.addLogDHCPClient(ack.toString());
		Index.addLogDHCPClient("CLIENT CONFIGURADO COM SUCESSO!");
	}
}
