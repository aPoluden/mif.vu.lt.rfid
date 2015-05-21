package mif.vu.lt.rfid.app.controller;

import java.util.Observable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import mif.vu.lt.rfid.app.model.Receiver;
import mif.vu.lt.rfid.app.model.Tag;
import mif.vu.lt.rfid.app.model.coords.Coords;

@Getter @Setter
public class ElementController extends Observable {

	private Set<Receiver> receivers;
	private Set<Tag> tags;
	
	public void updateTagCoords(Long oid, Coords coords) {
		System.out.println(coords);
		for (Tag tag : tags) {
			if (tag.getOid().equals(oid)) { 
				tag.setCoords(coords);
			}
		}
	}
	
	public boolean checkIfReceiver(Long oid) {
		if (oid != null) 
		for (Receiver receiver : receivers) {
			if (receiver.getOid().equals(oid)) return true;
		}
		return false;
	}
	
    public Coords getReceiverCoords(Long oid) {
		for (Receiver receiver : receivers) { 
			if (receiver.getOid().equals(oid)) {
				return receiver.getCoords();
			}
	     }
		 return null;
	}
	
}
