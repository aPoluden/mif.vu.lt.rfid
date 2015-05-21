package mif.vu.lt.rfid.app.model.algorithm;

import java.util.List;

import mif.vu.lt.rfid.app.controller.ElementController;
import mif.vu.lt.rfid.app.manager.PropertiesManager;
import mif.vu.lt.rfid.app.manager.SequenceManager.TagSpec;
import mif.vu.lt.rfid.app.model.Element;
import mif.vu.lt.rfid.app.model.coords.Coords;

/*
 * 
 */
public class Calibration implements Algorithm {

	private boolean firstRun = true;
	
	@Override
	public <T extends TagSpec> Coords compute(List<T> tagSpec, ElementController controller) {
		return null;
	}
	
	public boolean error(ElementController controller, Long receiverOid, Element sender) {
		Coords coordsReceiver = controller.getReceiverCoords(receiverOid);
		Coords coordsSender = controller.getReceiverCoords(sender.getOid());
		
		if (coordsReceiver != null && coordsSender != null) { 
			double distanceByCoords = Math.sqrt(Math.pow((coordsReceiver.getX() - coordsSender.getX()), 2) 
					                       + Math.pow((coordsReceiver.getY() - coordsSender.getY()), 2));
		    double distanceByRssi = convertRssi(sender.getRssi());		    
		    double error = Math.abs(distanceByCoords - distanceByRssi);
		    
		    if (error != 0 && error > 1) {
		    	calibratePathLoss(controller, receiverOid, sender);
		    }
		}
		return true;
	}
	
	private void calibratePathLoss(ElementController controller, Long receiverOid, Element sender) {
		while (error(controller, receiverOid, sender)) {
			if (firstRun) {
				PropertiesManager.n = 1;
			}
			PropertiesManager.n = PropertiesManager.n + 0.1;
		}
	}
	
	@SuppressWarnings(value = { "unused" })
	private double localCalibration(Integer rssi) {
		double n = 2.0;
		double A = 55;
		double E = 0.5;
		double atstumas = 10;
	    double dgautas = Math.pow(10, (rssi - A) / (10 * n));
	    double error = Math.abs(atstumas - dgautas);
	    
		double tarpinePaklaida = error;
		double tarpinisAtstumas;
		double nTemp = 2;
		
		if (E <= error) {
		    for (double i = 1; i <= 5; i+= 0.01) {
		    	double dTemp =  Math.pow(10, (rssi - A) / (10 * i));
				double errorTemp = Math.abs(atstumas -  dTemp);
			    if (tarpinePaklaida > errorTemp) {
					   tarpinisAtstumas = dTemp;
					   tarpinePaklaida = errorTemp;
					   nTemp = i;
				}
			}
		    return nTemp;
		} else
			return nTemp;
	}
	
	private Double convertRssi(Integer rs, double pathloss) {
	    double n = pathloss;
	    double A = 55;
	    
		if (rs == null) {
			return null;
		}
		
		if (rs < 55) {
			return 1.0;
		}
		return (double) Math.pow(10, (rs - A) / (10 * n));
	}

	public static void main(String[] args) {
		Calibration calibration = new Calibration();
		for (int i = 55; i < 86; i++) { 
			System.out.printf("RSS %d n %f ", i, calibration.localCalibration(i));
			System.out.println();
		}
		
		System.out.println(calibration.convertRssi(85, 3));
		
/*		for (int i = 0; i < 255; i++) {
			System.out.printf("RSS %d Distance %f ", i, calibration.convertRssi(i, 2));
			System.out.println();
		}*/
	}

}
