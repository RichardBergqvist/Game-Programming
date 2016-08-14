package net.rb.tacitumserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server {
	private int port;
	private Thread listenThread;
	private boolean listening = false;
	private DatagramSocket socket;
	
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] recievedDataBuffer = new byte[MAX_PACKET_SIZE * 10];
	
	public Server(int port) {
		this.port = port;
	}
	
	public void start() {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		listening = true;
		
		listenThread = new Thread(() -> listen());
		listenThread.start();
	}
	
	private void listen() {
		while (listening) {
			DatagramPacket packet = new DatagramPacket(recievedDataBuffer, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			process(packet);
		}
	}
	
	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		if (new String(data, 0, 4).equals("TCDB")) {
			
		}
	}
	
	public void send(byte[] data, InetAddress address, int port) {
		assert(socket.isConnected());
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}