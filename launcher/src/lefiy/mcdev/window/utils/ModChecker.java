package lefiy.mcdev.window.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import lefiy.mcdev.Main;
import lefiy.mcdev.utils.PathUtils;
import lefiy.mcdev.window.utils.Version.Mod;

public class ModChecker extends Thread {
	
	private Version version;
	
	public ModChecker(Version version) {
		this.version = version;
	}
	
	@Override
	public void run() {
		
		Main.getMain().launch_status = "PREPARING MOD";
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		
		File modsFolder = new File(util.getModData());
		
		if(!modsFolder.exists()) {
			try {
				Files.createDirectory(modsFolder.toPath());
				System.out.println("Created mods folder.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<String> checked = new ArrayList<>();
		
		ArrayList<String> deleteChecked = new ArrayList<>();
		
		ArrayList<String> enableMods = new ArrayList<>();
		
		if(Main.getMain().ver.equals("1.8")) {
		
			enableMods.addAll(Arrays.asList(Main.getMain().f_1_8.split(",")));
			
		} else if(Main.getMain().ver.equals("1.7")){
			
			enableMods.addAll(Arrays.asList(Main.getMain().f_1_7.split(",")));
			
		}
		
		File[] files = new File(util.getModData() + File.separator).listFiles();
		
		for(File file : files) {
			
			String hash = null;
			
			try (FileInputStream input = new FileInputStream(file)) {
				
				hash = DigestUtils.sha1Hex(input);
				
				input.close();
				
			} catch (Exception e) { e.printStackTrace(); }
			
			if(hash != null) {
			
				Mod mod = version.getModByHash(hash);
				
				System.out.println("Checking: " + file.getName());
				
				if(mod != null) {
					
					if(Main.getMain().ver.equals("1.8")) {
						
						if(!Main.getMain().f_1_8.contains(mod.getName())) {
							
							deleteChecked.add(file.getPath());
							
							System.out.println("Added to deletion list.");
						
						} else {
						
							checked.add(mod.getName());
							
							System.out.println("Added to download list.");
						
						}
						
					} else if(Main.getMain().ver.equals("1.7")) {
						
						if(!Main.getMain().f_1_7.contains(mod.getName())) {
							
							deleteChecked.add(file.getPath());
							
							System.out.println("Added to deletion list.");
						
						} else {
						
							checked.add(mod.getName());
							
							System.out.println("Added to download list.");
						
						}
					}
				
				} else {
					
					deleteChecked.add(file.getPath());
					
					System.out.println("[!] Disallowed Mods [!]");
					System.out.println("Added to deletion list.");
				
				}
			}
		}
		
		files = null;
		
		for(String deletePath : deleteChecked) {
			
			try {
				Files.delete(Paths.get(deletePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		enableMods.add("Leaf Client");
		
		for(String enableMod : enableMods) {
			
			if(!checked.contains(enableMod)) {
				
				Mod mod = version.getModByName(enableMod);
				
				if(mod != null) {
					
					File file = new File(util.getModData() + File.separator + mod.getName() + ".jar");
					
					try {
						
						URL url = new URL(mod.getLink());
						FileUtils.copyURLToFile(url, file);
						
					} catch (Exception e) { e.printStackTrace(); }
					
				}
			}
		}
		
		result();
	}
	
	public void result() {};
}