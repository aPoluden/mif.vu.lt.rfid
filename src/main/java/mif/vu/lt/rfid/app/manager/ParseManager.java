package mif.vu.lt.rfid.app.manager;


public interface ParseManager {
	
	<T> Object parse(Class<?> clas, T obj) throws Exception;

}
