package mif.vu.lt.rfid.app.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.beanutils.BeanUtilsBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mif.vu.lt.rfid.app.model.coords.Coords;

@Getter @Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Element implements Observer {
	
	@XmlTransient
	Observable observable;
	
	@XmlAttribute
	private Long oid;
	
	@XmlTransient
	private Integer batt;
	
	@XmlTransient
	private Integer seq;
	
	@XmlTransient
	private Integer rssi;
	
	@XmlElement
	Coords coords;

	public Element(Observable observable) { 
		this.observable = observable;
		this.observable.addObserver(this);
	}
	
	public Element() { }
	
	public <V> void setValue(Receiver data, V value, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanUtilsBean.getInstance().getPropertyUtils().setProperty(data, name, value);
	}

	public <T> Object getValue(T data, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return BeanUtilsBean.getInstance().getPropertyUtils().getProperty(data, name);
	}
	
}
