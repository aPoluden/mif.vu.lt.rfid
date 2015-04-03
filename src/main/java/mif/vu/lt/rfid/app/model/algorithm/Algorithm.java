package mif.vu.lt.rfid.app.model.algorithm;

import java.util.List;

import mif.vu.lt.rfid.app.manager.SequenceManager.TagSpec;
import mif.vu.lt.rfid.app.model.Receiver;
import mif.vu.lt.rfid.app.model.Root;
import mif.vu.lt.rfid.app.model.coords.Coords;

public interface Algorithm {
	
   default Double convertRssi(Integer rs) {
	    double n = 2;
	    double A = 55.0;
		if (rs == null) {
			return null;
		}
		if (rs < 55) {
			return 1.0;
		}
		return (double) Math.pow(10, (rs - A) / (10 * n));
	}
   
   default Coords getReceiverCoords(Long oid, Root root) {
	   for (Receiver receiver : root.getReceivers()) { 
		   if (receiver.getOid().equals(oid)) {
			   return receiver.getCoords();
		   }
	   }
	   return null;
   }
   
   public <T extends TagSpec> Coords compute(List<T> tagSpec, Root root);

}
