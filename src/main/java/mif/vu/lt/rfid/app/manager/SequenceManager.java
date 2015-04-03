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
import mif.vu.lt.rfid.app.model.coords.Coords;

@Getter 
public class SequenceManager {

	private int sequenceGenerator = 0;
	private Algorithm algorithm;
	private BlockingQueue<List<TagSpec>> queue;
	private BlockingQueue<Receiver> sockerQueue;
	private Root root;
	
	public SequenceManager(Algorithm algorithm, Root root, BlockingQueue<Receiver> socketQueue) {
		this.algorithm = algorithm;
		queue = new LinkedBlockingQueue<List<TagSpec>>();
		this.sockerQueue = socketQueue;
		this.root = root;
	}

//	Hybrid producer - consumer
	public void resolveSeq() throws InterruptedException {
		List<TagSpec> list = null;
		while (true) {
		    Receiver receiverJson = sockerQueue.take();
		    Long receiverOid = receiverJson.getOid();
		    for (Tag tag : receiverJson.getTags()) {
			    Integer key = tag.getSeq();
                if (sequenceGenerator == key) {
		    	    list.add(new TagSpec(receiverOid, tag.getOid(), tag.getRssi()));
		        } else if (sequenceGenerator < key) {
			        if (list == null) { 
			    	    list = new ArrayList<TagSpec>();
			        } else {
			        	queue.put(list);
			        	list = new ArrayList<TagSpec>();
			        }
			        list.add(new TagSpec(receiverOid, tag.getOid(), tag.getRssi()));
			        sequenceGenerator = key;
		        }
		    }
		}
	}
	
	// consumer
	public void resolveCoords(ElementController controller) throws InterruptedException {
		Long elementOid = null;
	    while (true) {
			List<TagSpec> list = queue.take();
			if (!list.isEmpty()) {
				// Hard coded filter
				if (list.get(0).getTagOid().equals(990961860l)) {
			        Coords coords = algorithm.<TagSpec>compute(list, root);
			        if (coords == null) { 
			        	continue;
			        }
			        elementOid = list.get(0).getTagOid();
                    controller.updateTagCoords(elementOid, coords);
				}
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
