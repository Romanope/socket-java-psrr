package com.psrr.socket.test;

import java.io.UnsupportedEncodingException;

import com.psrr.socket.contrato.ISocket;
import com.psrr.socket.util.ControlInstanceClient;
import com.socket.psrr.dhcp.DHCPClient;

public class IndexClient {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
//		ISocket socketClient = ControlInstanceClient.getInstanceClient("127.0.0.1", 5000, true);
//		
//		String request = "Testing conexion with TCP server!";
//		socketClient.send(request.getBytes());
//		String response = new String(socketClient.receive(), "UTF-8");
//		
//		System.out.println("Response received of the TCP server : ".concat(response));
		DHCPClient client = new DHCPClient("127.0.0.1", 5000, true, 'B');
	}
}
