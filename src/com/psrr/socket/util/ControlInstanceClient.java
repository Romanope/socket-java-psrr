package com.psrr.socket.util;

import com.psrr.socket.client.impl.ClientTCPImpl;
import com.psrr.socket.client.impl.ClientUDPImpl;
import com.psrr.socket.contrato.ISocket;

public final class ControlInstanceClient {

	private static ISocket socketClient;
	
	private static boolean typeCurrentClient;
	
	private ControlInstanceClient() {
		
	}
	
	public static ISocket getInstanceClient(String host, int port, boolean tcp) {
		if (socketClient == null || typeCurrentClient != tcp) {
			socketClient = defineTypeClient(host, port, tcp);
		}
		
		return socketClient;
	}
	
	private static ISocket defineTypeClient(String host, int port, boolean tcp) {
		
		typeCurrentClient = tcp;
		if (tcp) {
			return new ClientTCPImpl(host, port);
		} else {
			return new ClientUDPImpl(port);
		}
	}
}
