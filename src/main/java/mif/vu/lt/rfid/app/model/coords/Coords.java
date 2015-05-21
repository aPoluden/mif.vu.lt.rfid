package mif.vu.lt.rfid.app.model.coords;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

	@XmlAttribute
	private int x;
	
	@XmlAttribute
	private int y;
	
	@XmlAttribute
	private int z;
}
