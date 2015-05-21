package mif.vu.lt.rfid.app;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;
import mif.vu.lt.rfid.app.controller.ElementController;
import mif.vu.lt.rfid.app.manager.JsonManager;
import mif.vu.lt.rfid.app.manager.ParseManager;
import mif.vu.lt.rfid.app.manager.SequenceManager;
import mif.vu.lt.rfid.app.manager.SocketManager;
import mif.vu.lt.rfid.app.manager.XmlManager;
import mif.vu.lt.rfid.app.model.Receiver;
import mif.vu.lt.rfid.app.model.Root;
import mif.vu.lt.rfid.app.model.algorithm.MinMax;

public class Application {
	
	ParseManager parser;
	@Getter	
	Root root;

	public void init() throws Exception {
		String file = "src/main/resources/elements.xml";
		ParseManager parser = new XmlManager();
		root = (Root) parser.<File>parse(Root.class, new File(file));
	}
	
	public void run(int port, int buffSize) throws Exception {
		// Fill controller with elements
		ElementController controller = new ElementController();
		controller.setReceivers(root.getReceivers());
		controller.setTags(root.getTags());
		
		BlockingQueue<Receiver> SocketToSequence = new LinkedBlockingQueue<>();
		
		SocketManager socketManager = new SocketManager(new JsonManager(), port, buffSize, SocketToSequence);
		SequenceManager seqManager = new SequenceManager(new MinMax(), root, SocketToSequence);
		
		Thread socketThread = new Thread(socketManager);
		socketThread.start();
		
		// Start pull messages from queue
		new Thread(new Runnable() {
			@Override
			public void run() {
		        try {
					seqManager.resolveSeq(controller);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		// Start receiveng messages from SequnceManager internal queue
		new Thread(new Runnable() {
			@Override
			public void run() {
		        try {
					seqManager.resolveCoords(controller);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
				
	}
	
    public static void main(String[] args) {
    	
    	int bufferSize = 2048;
    	int port = 7777;
    	Application app = new Application();
    	
    	try {
    		
    		app.init();
    	    app.run(port, bufferSize);
    	    
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}

    }
	
}