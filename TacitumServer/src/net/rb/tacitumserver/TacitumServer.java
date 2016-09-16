package net.rb.tacitumserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TacitumServer {
	public static void main(String[] args) {
		Server server = new Server(9754);
		server.start();
		
		InetAddress address = null;
		try {
			address = InetAddress.getByName("192.168.1.106");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		int port = 9750;
		server.send(new byte[] { 0, 1, 2 }, address, port);
	}
}