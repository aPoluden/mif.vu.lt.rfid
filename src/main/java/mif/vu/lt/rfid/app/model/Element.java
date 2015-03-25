package mif.vu.lt.rfid.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public abstract class Element {

	private Long oid;
	private Integer batt;
	private Integer seq;

}
