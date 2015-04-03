package mif.vu.lt.rfid.app.model.coords;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Coords {
	
	public Coords() { 
	}
	
	public Coords(int x, int y, int z) { 
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@XmlElement
	private int x;
	
	@XmlElement
	private int y;
	
	@XmlElement
	private int z;
}
