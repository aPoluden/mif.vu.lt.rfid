package mif.vu.lt.rfid.app.model;

import java.util.Observable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Tag extends Element {

	
	public Tag(Observable observable) {
		super(observable);
	}
	
	public Tag() { }
	
	@XmlTransient
	private Integer butt;
	
	@XmlTransient
	private Integer stop;

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
}
