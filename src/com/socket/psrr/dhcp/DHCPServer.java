package com.socket.psrr.dhcp;
import java.io.IOException;

import com.psrr.socket.contrato.ISocket;
import com.psrr.socket.dhcp.messages.ACKMessage;
import com.psrr.socket.dhcp.messages.DiscoveryMessage;
import com.psrr.socket.dhcp.messages.OfferMessage;
import com.psrr.socket.dhcp.messages.RequestMessage;
import com.psrr.socket.util.Constantes;
import com.psrr.socket.util.ControlInstanceServer;
import com.psrr.socket.util.Util;
import com.socket.gui.Index;

public class DHCPServer {
	
	private int countClassA;
	
	private int countClassB;
	
	private int countClassC;
	
	private ISocket server;
	
	private int portServer;
	
	private long identificador;
	
	public DHCPServer(int port, boolean serverTCP) {
		this.portServer = port;
		this.server = ControlInstanceServer.getInstanceServer(port, serverTCP);
		startCounters();
		try {
			start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void start() throws ClassNotFoundException, IOException {
		
		while (true) {
			
			byte[] request = server.receive();

			new Thread(new Runnable() {
				@Override
				public void run() {
					Object obj;
					try {
						obj = Util.deserialize(request);
						byte[] response = getResponse(obj);
						server.send(response);					
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	private byte[] getResponse(Object obj) throws IOException {
		
		if (obj instanceof DiscoveryMessage) {
			return offer((DiscoveryMessage) obj);
		} else if (obj instanceof RequestMessage) {
			return getAckMessage((RequestMessage) obj);
		}
		
		return null;
	}
	
	private byte[] offer(DiscoveryMessage discovery) throws IOException {
		
		System.out.println("\nDHCP SERVER: Message Discovery Received!");
		System.out.println(discovery.toString());
		Index.setText("\nDHCP SERVER: Message Discovery Received!");
		Index.setText(discovery.toString());
		OfferMessage offer = new OfferMessage(Constantes.IP_SERVER, getIp(discovery.getIpClass()), Long.valueOf("9999999999"), discovery.getIpClass());
		identificador++;
		offer.setIdentificador(identificador);
		System.out.println("\nDHCP SERVER: Enviando mensagem OFFER");
		System.out.println(offer.toString());
		Index.setText("\nDHCP SERVER: Enviando mensagem OFFER");
		Index.setText(offer.toString());
		return Util.serialize(offer);
	}
	
	private void startCounters() {
		this.countClassA = 1;
		this.countClassB = 1;
		this.countClassC = 1;
		this.identificador = 0;
	}
	
	private String getIp(char ipClass) {
		
		switch(ipClass) {
			case 'A': 
				return getIpClassA();
			case 'B': 
				return getIpClassB();
			case 'C': 
				return getIpClassC();
			default:
				return null;
		}
	}
	
	private String getIpClassA() {
		
		String ip;
		if (this.countClassA <  Constantes.NUMBER_OF_OPTIONS_IP-1) {
			ip = Constantes.BASE_CLASS_A.concat("0.0."+countClassA);
		} else {
			int result = this.countClassA / Constantes.NUMBER_OF_OPTIONS_IP-1;
			ip = Constantes.BASE_CLASS_A.concat("0." + result + "." + countClassA);
		}
		
		return ip;
	}

	private String getIpClassB() {
		
		String ip;
		if (this.countClassA <  Constantes.NUMBER_OF_OPTIONS_IP-1) {
			ip = Constantes.BASE_CLASS_B.concat("0."+countClassB);
		} else {
			int result = this.countClassA / Constantes.NUMBER_OF_OPTIONS_IP-1;
			ip = Constantes.BASE_CLASS_B.concat("" + result + "." + countClassB);
		}
		
		return ip;
	}
	
	private String getIpClassC() {
		String ip;
		if (this.countClassB <  Constantes.NUMBER_OF_OPTIONS_IP-1) {
			ip = Constantes.BASE_CLASS_B.concat(""+countClassC);
		} else {
			ip = "Todos os endereços estão em uso";
		}
		
		return ip;
	}
	
	private byte[] getAckMessage(RequestMessage request) throws IOException {
		
		System.out.println("\nDHCP SERVER: Mensagem Recebida");
		System.out.println(request.toString());
		Index.setText("\nDHCP SERVER: Mensagem Recebida");
		Index.setText(request.toString());
		ACKMessage ack = new ACKMessage(request.getIpOffer(), request.getIpServer(), request.getIpClass());
		ack.setIdentificador(request.getIdentificador());
		byte[] mByte = Util.serialize(ack);
		
		addCount(ack.getIpClass(), 1);
		
		System.out.println("\nDHCP SERVER: Enviando Mensagem: ");
		System.out.println(ack.toString());
		Index.setText("\nDHCP SERVER: Enviando Mensagem: ");
		Index.setText(ack.toString());
		
		return mByte;
	}
	
	private synchronized void addCount(char ipClass, int value) {
		
		switch(ipClass) {
			case 'A': 
				this.countClassA = this.countClassA + value;
				break;
			case 'B': 
				this.countClassB = this.countClassB + value;
				break;
			case 'C': 
				this.countClassC = this.countClassC + value;
				break;
			default:
				System.out.println("Classe não existe");
		}
	}
}
