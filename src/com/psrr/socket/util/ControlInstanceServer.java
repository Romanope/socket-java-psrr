package com.psrr.socket.util;

import com.psrr.socket.contrato.ISocket;
import com.psrr.socket.server.impl.ServerTCPImpl;
import com.psrr.socket.server.impl.ServerUDPImpl;

public final class ControlInstanceServer {

	private static ISocket currentServer;
	
	private static boolean typeCurrentServer;
	
	private ControlInstanceServer() {
		
	}
	
	public static ISocket getInstanceServer(int port, boolean tcp) {
		
		if (currentServer == null || typeCurrentServer != tcp) {
			currentServer = defineTypeServer(port, tcp);
		}
		
		return currentServer;
	}
	
	private static ISocket defineTypeServer(int port, boolean tcp) {
		
		typeCurrentServer = tcp;
		if (tcp) {
			return new ServerTCPImpl(port);
		} else {
			return new ServerUDPImpl(port);
		}
	}
}
