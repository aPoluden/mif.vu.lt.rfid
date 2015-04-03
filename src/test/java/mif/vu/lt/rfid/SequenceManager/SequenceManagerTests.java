package mif.vu.lt.rfid.SequenceManager;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

import mif.vu.lt.rfid.app.Application;
import mif.vu.lt.rfid.app.manager.SequenceManager;
import mif.vu.lt.rfid.app.model.Receiver;
import mif.vu.lt.rfid.app.model.Tag;
import mif.vu.lt.rfid.app.model.algorithm.MinMax;

import org.junit.Assert;
import org.junit.Test;

public class SequenceManagerTests {

	Application app = new Application();
	
	@Test
	public void test() throws Exception {
		app.init();
		SequenceManager sequenceManager = new SequenceManager(new MinMax(), app.getRoot(), new LinkedBlockingQueue<Receiver>());
		
		Assert.assertEquals(5, app.getRoot().getReceivers().size());
		
		Tag tag1 = new Tag();
		tag1.setOid(10l);
		tag1.setSeq(1);
		Tag tag2 = new Tag();
		tag2.setOid(20l);
		tag2.setSeq(1);
		
		Receiver receiver = new Receiver();
		receiver.setOid(10l);
		receiver.setTags(new HashSet<Tag>());
		receiver.getTags().add(tag1);
		receiver.getTags().add(tag2);
		
		sequenceManager.getSockerQueue().add(receiver);
		sequenceManager.resolveSeq();
		
		
		tag1 = new Tag();
		tag1.setOid(10L);
		tag1.setSeq(0);
		
	    receiver = new Receiver();
		receiver.setOid(10l);
		receiver.setTags(new HashSet<Tag>());		
		receiver.getTags().add(tag1);
		
		tag1 = new Tag();
		tag1.setOid(10l);
		tag1.setSeq(2);
		tag2 = new Tag();
		tag2.setSeq(2);
		tag2.setOid(20l);
		
		
	}
	
}
