package mif.vu.lt.rfid.app.manager;

// Singelton
public class PropertiesManager {
	
	public static double n = 2;
	public static double A = 55;
	
	
	private static PropertiesManager propertiesManager = null;
	
	protected PropertiesManager() {
	}
	
	public static PropertiesManager getInstanceI() {
		if (propertiesManager != null) {
			propertiesManager = new PropertiesManager();
		}
		return null;
	}
}
