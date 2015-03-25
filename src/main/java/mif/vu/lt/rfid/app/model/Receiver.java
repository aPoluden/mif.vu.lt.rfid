package mif.vu.lt.rfid.app.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Receiver extends Element {
	
	private Integer proto;
	private Long target;
	Set<Tag> tag = new HashSet<>();
	
}
