package mif.vu.lt.rfid.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@XmlRootElement(name = "ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
	
	@XmlElementWrapper
	@XmlElement(name="receiver")
	Set<Receiver> receivers = new HashSet<>();
	
}
