package mif.vu.lt.rfid.app.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Element {

	@XmlAttribute
	private Long oid;
	
	@XmlTransient
	private Integer batt;
	
	@XmlTransient
	private Integer seq;

}
