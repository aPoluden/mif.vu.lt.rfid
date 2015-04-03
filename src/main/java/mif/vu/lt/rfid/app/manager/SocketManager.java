package mif.vu.lt.rfid.app.manager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

import lombok.RequiredArgsConstructor;
import mif.vu.lt.rfid.app.model.Receiver;

@RequiredArgsConstructor
public class SocketManager implements Runnable {
	
	private final ParseManager parser;	
	private final Integer port;
	private final Integer bufferSize;
	private final BlockingQueue<Receiver> queue;
    
	@Override
	public void run()  {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port);
			byte[] buf = new byte[bufferSize];
		    DatagramPacket packet = new DatagramPacket(buf, buf.length);
		    while (true) {
	            socket.receive(packet);
		        byte[] byt = packet.getData();
		        queue.put((Receiver) parser.<String>parse(Receiver.class,  new String(byt)));
		    }
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}

}
