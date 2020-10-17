package main;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPConnection extends Thread{
	
	private DatagramSocket socket;
	OnMessageListener observer;
	
	
	public void setObserver(OnMessageListener observer) {
		this.observer = observer;
	
	}
	
	
	
	
	@Override
	public void run() {
		
		try {
			//1. Escucha
			socket = new DatagramSocket(5000);
			
			
			//2. Esperar mensajes: Datagramas
			while(true) {
				
				byte[] buffer = new byte[100];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				//System.out.println(packet.getSocketAddress());
				System.out.println("Esperando datagrama");
			//3. Esperando datagrama
				socket.receive(packet);
				String mensaje = new String(packet.getData()).trim();
				
				observer.recibiendo(mensaje);
				
				System.out.println("Datagrama recibido:"+mensaje);
				
			}
			 
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void sendMessage(String mensaje) { 
		
		new Thread(
				()-> {
					
					try {
						//4. datagrama de envio
						InetAddress ip = InetAddress.getByName("192.168.0.106");
						DatagramPacket packet = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, ip, 6000);
						socket.send(packet);
						
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			).start();
		
		
	}
}
