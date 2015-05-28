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
	
	@Override
	public <T extends TagSpec> Coords compute(List<T> tagSpec, ElementController controller) {
		return null;
	}
	
	public void calibrate(ElementController controller, Long receiverOid, Element sender) {
		
		double n = PropertiesManager.n;
		double A = PropertiesManager.A;
		double E = 0.5; // user selected
		double atstumas = computeDistance(controller.getReceiverCoords(receiverOid), 
				controller.getReceiverCoords(sender.getOid()));
	    
		double dgautas = Math.pow(10, (sender.getRssi() - A) / (10 * n));
	    double error = Math.abs(atstumas - dgautas);
	    
		double tarpinePaklaida = error;
		@SuppressWarnings("unused")
		double tarpinisAtstumas;
		double nTemp = 2;
		
		if (E <= error) {
		    for (double i = 1; i <= 5; i+= 0.01) {
		    	double dTemp =  Math.pow(10, (sender.getRssi() - A) / (10 * i));
				double errorTemp = Math.abs(atstumas -  dTemp);
			    if (tarpinePaklaida > errorTemp) {
					   tarpinisAtstumas = dTemp;
					   tarpinePaklaida = errorTemp;
					   nTemp = i;
				}
			}
		}
		PropertiesManager.n = nTemp;
	}
	
	private double computeDistance(Coords rCoords, Coords rCoords2) {
		return Math.sqrt(Math.pow(rCoords.getX() - rCoords2.getX(), 2) + Math.pow(rCoords.getY() - rCoords.getY(), 2));
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
	}

}
