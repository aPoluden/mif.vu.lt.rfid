package mif.vu.lt.rfid.app.model.coords;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Coords {

	@XmlElement
	private int x;
	
	@XmlElement
	private int y;
	
	@XmlElement
	private int z;
}
