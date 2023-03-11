package lefiy.mcdev;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import lefiy.mcdev.security.SecureReader;
import lefiy.mcdev.settings.Reader;
import lefiy.mcdev.utils.PathUtils;
import lefiy.mcdev.window.Window;
import lefiy.mcdev.window.utils.ClientChecker;
import lefiy.mcdev.window.utils.DownloadData;
import lefiy.mcdev.window.utils.ModChecker;
import lefiy.mcdev.window.utils.Version;

public class Main {
	
	private static Main main = new Main();
	
	public Map<String, Version> versions;
	
	public String lang, ver, ram, m_1_7, m_1_8, f_1_7, f_1_8, launch_status;
	public boolean setup, loading, launched;
	
	public List<String> sences;
	
	public static void main(String[] args) {
		
		setupMinecraft();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Window(new Reader(), new SecureReader(), new DownloadData());
			}
		});
	}
	
	public static Main getMain() {
		return main;
	}
	
	public void launch() {
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		
		LocalDateTime date = LocalDateTime.now();
		
	    String formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(date);
	    
	    String fileName = util.getLaunchLog().replace("rename", formatted);
		
		ArrayList<String> args = new ArrayList<>(); boolean isForge = false;
		
		String user = "User"; String token = "0"; String uuid = "0";
		
		switch(ver) {
		
			case "1.7":
				if(m_1_7.equals("forge")) {
					isForge = true;
				}
				break;
			case "1.8":
				if(m_1_8.equals("forge")) {
					isForge = true;
				}
				break;
		}
		
		if(isForge) {
			
			new ModChecker(versions.get(ver)) {
				
				@Override
				public void result() {
			
					launch_status = "COMPLETE";
					
					args.add(util.getJavaSystem());
					
					args.add("-Xms" + ram + "G");
					
					args.add("-Xmx" + ram + "G");
					
					args.add("-Djava.library.path=" + util.getNatives());
					
					args.add("-cp");
					args.add(util.getJavaLibSystem() + File.separator + "*" + ";" + util.getForgeLibraries() + File.separator + "*" + ";" + util.getMinecraft() + File.separator + "*");
					
					args.add("net.minecraft.launchwrapper.Launch");
					
					args.add("--username");
					args.add(user);
					
					String version = "";
					
					switch(ver) {
						case "1.7":
							version = "1.7.10";
							break;
							
						case "1.8":
							version = "1.8.9";
							break;
					}
						
					args.add("--version");
					args.add(version);
					
					args.add("--gameDir");
					args.add(util.getMinecraftData());
					
					args.add("--assetsDir");
					args.add(util.getAssets());
					
					String index = "";
					
					switch(ver) {
						case "1.7":
							index = "1.7.10";
							break;
					
						case "1.8":
							index = "1.8";
							break;
					}
						
					args.add("--assetIndex");
					args.add(index);
					
					args.add("--uuid");
					args.add(uuid);
					
					args.add("--accessToken");
					args.add(token);
							
					if(ver.equals("1.7")) {
						
						args.add("--userProperties");
						args.add("{}");
						
					}
					
					if(ver.equals("1.7")) {
					
						args.add("--tweakClass");
						args.add("cpw.mods.fml.common.launcher.FMLTweaker");
						
					} else {
						
						args.add("--tweakClass");
						args.add("net.minecraftforge.fml.common.launcher.FMLTweaker");
						
					}
					
					loading = false;
					
					Window.close();
						
					ProcessBuilder builder =  new ProcessBuilder(args);
						
					builder.redirectErrorStream();
							
					try {
							
						Process process = builder.start();
							
						InputStream input = process.getInputStream();
							
						BufferedReader reader = new BufferedReader(new InputStreamReader(input));
							
						FileWriter writer = new FileWriter(new File(fileName));
							
						String log = null;
							
						while((log = reader.readLine()) != null) {
							writer.write(log + "\n");
						}
							
						writer.close();
						
						input.close();
							
						process.waitFor();
						
						process.destroy();
							
						Window.open();
							
						launched = false;
							
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}.start();
			
		} else {
			
			new ClientChecker() {
				
				@Override
				public void result() {
			
					launch_status = "COMPLETE";
					
					args.add(util.getJavaSystem());
					
					args.add("-Xms" + ram + "G");
				
					args.add("-Xmx" + ram + "G");
				
					args.add("-Djava.library.path=" + util.getNatives());
					
					args.add("-cp");
					args.add(util.getJavaLibSystem() + File.separator + "*" + ";" + util.getLibraries() + File.separator + "*" + ";" + util.getMinecraft() + File.separator + "*");
				
					args.add("net.minecraft.launchwrapper.Launch");
					
					args.add("--username");
					args.add(user);
					
					String version = "";
					
					switch(ver) {
						case "1.7":
							version = "1.7.10";
							break;
					
						case "1.8":
							version = "1.8.9";
							break;
					}
				
					args.add("--version");
					args.add(version);
					
					args.add("--gameDir");
					args.add(util.getMinecraftData());
					
					args.add("--assetsDir");
					args.add(util.getAssets());
					
					String index = "";
					
					switch(ver) {
						case "1.7":
							index = "1.7.10";
							break;
					
						case "1.8":
							index = "1.8";
							break;
					}
				
					args.add("--assetIndex");
					args.add(index);
					
					args.add("--uuid");
					args.add(uuid);
					
					args.add("--accessToken");
					args.add(token);
					
					if(ver.equals("1.7")) {
					
						args.add("--userProperties");
						args.add("{}");
						
					}
					
					args.add("--tweakClass");
					args.add("com.leafclient.mixins.LeafClientTweaker");
					
					loading = false;
				
					Window.close();
				
					ProcessBuilder builder =  new ProcessBuilder(args);
				
					builder.redirectErrorStream();
					
					try {
					
						Process process = builder.start();
					
						InputStream input = process.getInputStream();
					
						BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					
						FileWriter writer = new FileWriter(new File(fileName));
					
						String log = null;
					
						while((log = reader.readLine()) != null) {
							writer.write(log + "\n");
						}
					
						writer.close();
						
						input.close();
					
						process.waitFor();
						
						process.destroy();
					
						Window.open();
					
						launched = false;
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}.start();
		}
	}
	
	private static void setupMinecraft() {
		
		try {
		
			PathUtils util = new PathUtils(System.getProperty("user.dir"));
			
			File folders = new File(util.getMinecraftData());
			
			for(File folder : folders.listFiles()) {
				if(folder.isDirectory() && folder.getName().contains("LeafClient")) {
					Files.delete(folder.toPath());
					System.out.println("Deleted old configuration file.");
				}
			}
			
			String dataPath = util.getMinecraftData() + File.separator + "datakey";
			
			File datakey = new File(dataPath);
			File file = new File(dataPath + File.separator + "value");
			
			if(!datakey.exists()) {
				Files.createDirectory(datakey.toPath());
				Files.createFile(file.toPath());
				System.out.println("Created datakey folder.");
			}
			
			String musicPath = util.getMinecraftData() + File.separator + "music";
			
			File music = new File(musicPath);
			
			if(!music.exists()) {
				Files.createDirectory(music.toPath());
				File test = new File(musicPath + File.separator + "Test Audio.mp3");
				URL url = new URL("https://www.dropbox.com/s/1cq8zrpham6ilt4/Test%20Audio.mp3?raw=1");
				FileUtils.copyURLToFile(url, test);
				System.out.println("Created music folder.");
			}
			
		} catch (Exception e) {e.printStackTrace();}
	}
}