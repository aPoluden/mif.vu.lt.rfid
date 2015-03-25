package mif.vu.lt.rfid.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tag extends Element {

	private Integer rssi;
	private Integer butt;
	private Integer stop;
	
}
