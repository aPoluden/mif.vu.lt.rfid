package mif.vu.lt.rfid.app;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

import mif.vu.lt.rfid.app.manager.JsonManager;
import mif.vu.lt.rfid.app.manager.ParseManager;
import mif.vu.lt.rfid.app.manager.XmlManager;
import mif.vu.lt.rfid.app.model.Receiver;
import mif.vu.lt.rfid.app.model.Root;

public class Application {
	
	ParseManager parser;
	
	private void init() throws Exception {
		String file = "src/main/resources/receivers.xml";
		ParseManager parser = new XmlManager();
		Root root = (Root) parser.<File>parse(Root.class, new File(file));
//		parser = new JsonManager();
//		Receiver receiver = (Receiver) parser.parse(Receiver.class);
	}
	
	private void run() throws Exception {
		
		try (DatagramSocket socket = new DatagramSocket(7777)) {
		    
			byte[] buf = new byte[1024];
		    DatagramPacket packet = new DatagramPacket(buf, buf.length);
		    while (true) {
	            socket.receive(packet);
		        byte[] byt = packet.getData();
		        String json = new String(byt);
		        parser = new JsonManager();
		        Receiver receiver = (Receiver) parser.<String>parse(Receiver.class, json);
		    }
		}
	}
	
    public static void main(String[] args) { 
    	Application app = new Application();
    	try { 
//    		app.init();
    	    app.run();
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}

    }
	
}