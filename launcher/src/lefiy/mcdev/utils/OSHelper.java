package lefiy.mcdev.utils;

import java.io.File;

public enum OSHelper {
	
	WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".minecraft"),
	MACOS("Library" + File.separator + "Application Support" + File.separator + "minecraft"),
	LINUX(".minecraft");
	
	private String mcDir;
	
	private OSHelper(String mcDir) {
		this.mcDir = mcDir;
	}
	
	public String getDirectory() {
		return this.mcDir;
	}
	
	public static OSHelper getOS() {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.contains("windows")) {return WINDOWS;}
		else if(os.contains("mac")) {return MACOS;}
		else if(os.contains("linux")) {return LINUX;}
		else {return null;}
	}
}