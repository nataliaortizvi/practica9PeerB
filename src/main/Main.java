package main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Comida;
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet implements OnMessageListener{

	UDPConnection udp;
	PImage perro, papa, qbano, jugo;
	ArrayList <Comida> food;
	int x, y, num;
	String fecha;
	Comida comi;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.Main");

	}
	
	public void settings() {
		size(500,700);
	}
	
	public void setup() {
		udp = new UDPConnection();
		udp.setObserver(this);
		udp.start();
		
		food = new ArrayList<>();
	
		
		//imagenes
		perro = loadImage("img/perro.jpg");
		qbano = loadImage("img/qbano.jpg");
		jugo = loadImage("img/sandia.jpg");
		papa = loadImage("img/papas.jpg");
		
		//saber cual es mi ip para colocarla en el socket del cliente
		 try {
	            InetAddress n = InetAddress.getLocalHost();
	            String ip = n.getHostAddress();
	            System.out.println(ip);

	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }
		
	}
	
	public void draw() {
		background(135,173,294);
		
		fill(105,40,20);
		textSize(12);
		text("Haga click sobre la imagen del pedido para informale al cliente que ya está listo" ,15,685);
		
		for(int i=0; i<food.size(); i++) {
			comi = food.get(i);
			fill(0);
			text("Pedido "+comi.getNum()+"\n"+ "Hora: "+ comi.getFecha(), comi.getX(), comi.getY());
			
			if(comi.getType().equalsIgnoreCase("sandia")) {
				image(jugo, comi.getX()-190, comi.getY()-50,100,140);
			}
			if(comi.getType().equalsIgnoreCase("perro")) {
				image(perro, comi.getX()-190, comi.getY()-50,160,120);
			}
			if(comi.getType().equalsIgnoreCase("papas")) {
				image(papa, comi.getX()-190, comi.getY()-50,160,120);
			}
			if(comi.getType().equalsIgnoreCase("qbano")) {
				image(qbano, comi.getX()-190, comi.getY()-50,170,120);
			}
		}
		

		fill(355,0,0);
		text("x:"+mouseX+"y:"+mouseY, mouseX, mouseY);
	
		
		
	}
	
	public void recibiendo(String msg) {
		Gson gson = new Gson();
		comi = gson.fromJson(msg, Comida.class);
		
		if(comi.getNum()<=4) {
			food.add(comi);
		}
		
	}
	
	public void mousePressed() {
		if(mouseX > 50 && mouseX < 220 && mouseY > 17 && mouseY < 150) {
			if(comi.getType().equalsIgnoreCase("sandia")) {
				udp.sendMessage("Pedido de jugo de sandia listo");
			}
			if(comi.getType().equalsIgnoreCase("perro")) {
				udp.sendMessage("Pedido de perro caliente listo");
			}
			if(comi.getType().equalsIgnoreCase("papas")) {
				udp.sendMessage("Pedido de papas listo");
			}
			if(comi.getType().equalsIgnoreCase("qbano")) {
				udp.sendMessage("Pedido de cubanolisto");
			}
			
		}
		if(mouseX > 50 && mouseX < 220 && mouseY > 17 && mouseY < 150) {
			udp.sendMessage("Pedido listo");
		}
		if(mouseX > 50 && mouseX < 220 && mouseY > 17 && mouseY < 150) {
			udp.sendMessage("Pedido listo");
		}
		if(mouseX > 50 && mouseX < 220 && mouseY > 17 && mouseY < 150) {
			udp.sendMessage("Pedido listo");
		}
		
		
	
	
		
	}

}
