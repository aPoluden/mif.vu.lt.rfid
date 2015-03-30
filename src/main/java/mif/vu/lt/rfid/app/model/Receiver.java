package mif.vu.lt.rfid.app.model;

import java.util.Set;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;
import mif.vu.lt.rfid.app.model.coords.Coords;

@Getter @Setter
public class Receiver extends Element {
	
	@XmlAttribute
	private String name;
	
	@XmlTransient
	private Integer proto;
	
	@XmlTransient
	private Long target;
	
	@XmlTransient
	Set<Tag> tags;
	
	@XmlElement
	Coords coords;
}
