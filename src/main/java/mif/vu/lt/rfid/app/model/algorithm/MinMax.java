package mif.vu.lt.rfid.app.model.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mif.vu.lt.rfid.app.controller.ElementController;
import mif.vu.lt.rfid.app.manager.SequenceManager.TagSpec;
import mif.vu.lt.rfid.app.model.coords.Coords;

public class MinMax implements Algorithm {
	
	public <T extends TagSpec> Coords compute(List<T> tagList, ElementController controller) {
		
		double[] d = new double[3];
		int[] x = new int[3];
		int[] y = new int[3];
		
		double X = 0;
		double Y = 0;
		double Z = 0; // not provided yet
		
		if (tagList.size() == 3) {
			
			int receiverNum = 3;
			
			List<Double> xmax = new ArrayList<>();
			List<Double> ymax = new ArrayList<>();
			List<Double> xmin = new ArrayList<>();
			List<Double> ymin = new ArrayList<>();
			
			for (int i = 0; i < tagList.size(); i++) {
				try {
					d[i] = convertRssi((tagList.get(i)).getRssi());
					Long oid = tagList.get(i).getReceiverOid();
					Coords receiverCoords = controller.getReceiverCoords(oid);
					x[i] = receiverCoords.getX();
					y[i] = receiverCoords.getY();
				} catch (NullPointerException e) {
					return null;
				}

				xmax.add(x[i] - d[i]);
				ymax.add(y[i] - d[i]);
				xmin.add(x[i] + d[i]);
				ymin.add(y[i] + d[i]);
			}
			
		Y = (Collections.max(ymax) + Collections.min(ymin)) / 2;
		X = (Collections.min(xmin) + Collections.max(xmax)) / 2;
		
		} else if (tagList.size() < 3) {
			return null;
		} else if (tagList.size() > 3) {
			return null;
		}
		return new Coords((int) X, (int) Y, (int) Z);
	}
}
