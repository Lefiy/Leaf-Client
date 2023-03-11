package lefiy.mcdev.utils;

import java.io.File;

import lefiy.mcdev.Main;

public class PathUtils {
	
	private String currentDir;
	
	public PathUtils(String currentDir) {
		this.currentDir = currentDir;
	}
	
	public String getConfig() {
		return currentDir + File.separator + "clientdata" +  File.separator + "config";
	}
	
	public String getInformation() {
		return currentDir + File.separator + "clientdata" +  File.separator + "mods_info";
	}
	
	public String getAssets() {
		return currentDir + File.separator + "clientdata" +  File.separator + "assets";
	}
	
	public String getLibraries() {
		return currentDir + File.separator + "clientdata" + File.separator + "libraries"
				+ File.separator + Main.getMain().ver;
	}
	
	public String getForgeLibraries() {
		return currentDir + File.separator + "clientdata" + File.separator + "libraries_forge"
				+ File.separator + Main.getMain().ver;
	}
	
	public String getNatives() {
		return currentDir + File.separator + "clientdata" + File.separator + "natives"
				+ File.separator + Main.getMain().ver;
	}
	
	public String getLaunchLog() {
		return currentDir + File.separator + "clientdata" + File.separator + "logs" 
				+ File.separator + "rename.log";
	}
	
	public String getJavaSystem() {
		
		String version = "zulu-8";
		
		return currentDir + File.separator + "clientdata" + File.separator + "jre" 
				+ File.separator + version + File.separator + "bin" + File.separator + "java";
	}
	
	public String getJavaLibSystem() {
		
		String version = "zulu-8";
		
		return currentDir + File.separator + "clientdata" + File.separator + "jre" 
				+ File.separator + version + File.separator + "lib" + File.separator + "ext";
	}
	
	public String getMinecraft() {
		return currentDir + File.separator + "clientdata" + File.separator + "client"
				+ File.separator + Main.getMain().ver;
	}
	
	public String getMinecraftData() {
		return System.getProperty("user.home") + File.separator + OSHelper.getOS().getDirectory();
	}
	
	public String getModData() {
		return System.getProperty("user.home") + File.separator + OSHelper.getOS().getDirectory() + File.separator + "mods";
	}
}