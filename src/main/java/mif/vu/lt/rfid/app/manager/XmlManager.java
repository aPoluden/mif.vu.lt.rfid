package mif.vu.lt.rfid.app.manager;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlManager implements ParseManager {
	
	
	@Override
	public <T> Object parse(Class<?> clas, T file) throws JAXBException {
		return this.<T>unmarshal(clas, file);
	}
	
	private <T> Object unmarshal(Class<?> clas, T file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clas);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal((File) file);
	}

}
