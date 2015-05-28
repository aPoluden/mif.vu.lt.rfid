package mif.vu.lt.rfid.app.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mif.vu.lt.rfid.app.controller.ElementController;
import mif.vu.lt.rfid.app.model.Receiver;
import mif.vu.lt.rfid.app.model.Root;
import mif.vu.lt.rfid.app.model.Tag;
import mif.vu.lt.rfid.app.model.algorithm.Algorithm;
import mif.vu.lt.rfid.app.model.algorithm.Calibration;
import mif.vu.lt.rfid.app.model.coords.Coords;

import org.apache.commons.lang3.time.StopWatch;
@Getter 
public class SequenceManager {

	private int sequenceGenerator = 0;
	private Algorithm algorithm;
	private BlockingQueue<List<TagSpec>> internalQueue;
	private BlockingQueue<Receiver> socketQueue;
	private Root root;
	private StopWatch watch;
	private Calibration calibrate;
	
	public SequenceManager(Algorithm algorithm, Root root, BlockingQueue<Receiver> socketQueue) {
		calibrate = new Calibration();
		this.algorithm = algorithm;
		internalQueue = new LinkedBlockingQueue<List<TagSpec>>();
		this.socketQueue = socketQueue;
		this.root = root;
		watch = new StopWatch();
		try {
			Thread.sleep(10000);
			watch.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

/*
 * Waits messages from socket. Further sends messages to BlockingQueue
 * May filter Calibration Messages
 * 
 */
	public void resolveSeq(ElementController controller) throws InterruptedException {
		
		List<TagSpec> list = null;
		while (true) {
		    Receiver receiverJson = socketQueue.take();
		    Long receiverJsonOid = receiverJson.getOid();
		    for (Tag tag : receiverJson.getTags()) {
		    	if (controller.checkIfReceiver(tag.getOid())) {
		    		calibrate.calibrate(controller, receiverJson.getOid() ,tag);
		    		continue;
		    	}
			    Integer key = tag.getSeq();
                if (sequenceGenerator == key) {
		    	    list.add(new TagSpec(receiverJsonOid, tag.getOid(), tag.getRssi()));
		        } else if (sequenceGenerator < key) {
			        if (list == null) { 
			    	    list = new ArrayList<TagSpec>();
			        } else if (!controller.checkIfReceiver(tag.getOid())) {
			        	internalQueue.put(list);
			        	list = new ArrayList<TagSpec>();
			        }
			        list.add(new TagSpec(receiverJsonOid, tag.getOid(), tag.getRssi()));
			        sequenceGenerator = key;
		        } else {
//		            Garbage messages
		        }
		    }
		}
	}
	
	// Receives messages from BlockingQueue for processing
	public void resolveCoords(ElementController controller) throws InterruptedException {
		Long elementOid = null;
	    while (true) {
			List<TagSpec> list = internalQueue.take();
			if (!list.isEmpty()) {
			        Coords coords = algorithm.<TagSpec>compute(list, controller);
			        if (coords == null) { 
			        	continue;
			        }
			        elementOid = list.get(0).getTagOid();
                    controller.updateTagCoords(elementOid, coords);
			}
		}
	}
	
	@Getter @Setter
	@RequiredArgsConstructor
	public class TagSpec {
	    private final Long receiverOid;
		private final Long tagOid;
		private final Integer rssi;
	}
}
