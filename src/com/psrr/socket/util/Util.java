package com.psrr.socket.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Util {

	private Util() {
	
	}
	
	public static byte[] serialize(Object obj) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ObjectOutputStream ous = new ObjectOutputStream(baos);
		
		ous.writeObject(obj);
		
		return baos.toByteArray();
	}
	
	public static Object deserialize(byte[] objByte) throws IOException, ClassNotFoundException {
		
		ByteArrayInputStream bais = new ByteArrayInputStream(objByte);
		
		ObjectInputStream ois = new ObjectInputStream(bais);
		
		return ois.readObject();
	}
}
