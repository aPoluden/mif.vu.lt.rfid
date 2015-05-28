package mif.vu.lt.rfid.app.model.algorithm;

import java.util.List;

import mif.vu.lt.rfid.app.controller.ElementController;
import mif.vu.lt.rfid.app.manager.PropertiesManager;
import mif.vu.lt.rfid.app.manager.SequenceManager.TagSpec;
import mif.vu.lt.rfid.app.model.coords.Coords;

public interface Algorithm {
   
    default Double convertRssi(Integer rs) {
	   
	    double n = PropertiesManager.n;
	    double A = PropertiesManager.A;
	    
		if (rs == null) {
			return null;
		}
		
		if (rs < 55) {
			return 1.0;
		}
		return (double) Math.pow(10, (rs - A) / (10 * n));
	}
   
   public <T extends TagSpec> Coords compute(List<T> tagSpec, ElementController controller);

}
