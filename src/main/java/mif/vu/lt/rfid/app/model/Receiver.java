package mif.vu.lt.rfid.app.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Receiver extends Element {
	
	public Receiver(Observable observable) {
		super(observable);
	}

	public Receiver() { }
	
	@XmlAttribute
	private String name;
	
	@XmlTransient
	private Integer proto;
	
	@XmlTransient
	private Long target;
	
	@XmlTransient
	Set<Tag> tags;

	@Override
	public void update(Observable o, Object arg) {
		Receiver receiverTemp = (Receiver) arg;
		
		for (Field f : Receiver.class.getDeclaredFields()) {
			if (f.getName().equals("name")) {
				continue;
			}
		    Object obj;
		    try {
				obj = getValue(receiverTemp, f.getName());
				this.setValue(this, obj, f.getName());
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
			}
				

		}
	}
}
