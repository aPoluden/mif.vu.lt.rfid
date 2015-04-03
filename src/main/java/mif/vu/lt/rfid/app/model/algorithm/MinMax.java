package mif.vu.lt.rfid.app.model.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mif.vu.lt.rfid.app.manager.SequenceManager.TagSpec;
import mif.vu.lt.rfid.app.model.Root;
import mif.vu.lt.rfid.app.model.coords.Coords;

public class MinMax implements Algorithm {
	
	public <T extends TagSpec> Coords compute(List<T> receiverList, Root root) {
		
		double[] d = new double[3];
		int[] x = new int[3];
		int[] y = new int[3];
		
		double X = 0;
		double Y = 0;
		double Z = 0; // not provided yet
		
		if (receiverList.size() == 3) {
			
			int receiverNum = 3;
			
			List<Double> xmax = new ArrayList<Double>();
			List<Double> ymax = new ArrayList<>();
			List<Double> xmin = new ArrayList<>();
			List<Double> ymin = new ArrayList<>();
			
			for (int i = 0; i < receiverNum; i++) {
				
				try {
					d[i] = convertRssi((receiverList.get(i)).getRssi());
					x[i] = getReceiverCoords((receiverList.get(i)).getReceiverOid(), root).getX();
					y[i] = getReceiverCoords((receiverList.get(i)).getReceiverOid(), root).getY();
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
		
		} else if (receiverList.size() < 3) {
			return null;
		} else if (receiverList.size() > 3) {
			return null;
		}
		
		return new Coords((int) X, (int) Y, (int) Z);
	}
}
