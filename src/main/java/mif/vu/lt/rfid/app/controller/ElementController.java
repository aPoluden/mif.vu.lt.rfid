package mif.vu.lt.rfid.app.controller;

import java.util.Observable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import mif.vu.lt.rfid.app.model.Element;
import mif.vu.lt.rfid.app.model.Receiver;
import mif.vu.lt.rfid.app.model.Tag;
import mif.vu.lt.rfid.app.model.coords.Coords;

@Getter @Setter
public class ElementController extends Observable {

	private Set<Receiver> receivers;
	private Set<Tag> tags;
	
//	Should be View Controll
	
	public void addObserverToReceiver() {
		for (Receiver receiver : receivers) {
			receiver.setObservable(this);
			this.addObserver(receiver);
		}
	}
	
	public void addObserverToTags() { 
		for (Tag tag : tags) {
			tag.setObservable(this);
			this.addObserver(tag);
		}
	}
	
	public void updateReceiverState(Receiver receiverJson) {
        for (Receiver receiver : receivers) {
        	if (receiver.getOid().equals(receiverJson.getOid())) {
        		this.<Receiver>notifyObserver(receiver, receiverJson);
        	}
        }
	}
	
	public <T extends Element> void notifyObserver(T element, T elementJson) {
		setChanged();
		element.update(this, elementJson);
	}
	
	public void updateTagCoords(Long oid, Coords coords) {
		for (Tag tag : tags) {
			if (tag.getOid().equals(oid)) { 
				tag.setCoords(coords);
			}
		    System.out.println("updtd");
		}
	}
	
}
