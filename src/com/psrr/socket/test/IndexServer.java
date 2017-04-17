package com.psrr.socket.test;

import com.socket.psrr.dhcp.DHCPServer;

public class IndexServer {

	public static void main(String[] args) {
		
		DHCPServer server = new DHCPServer(5000, true);
	}
}
