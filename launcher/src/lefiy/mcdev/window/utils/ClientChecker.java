package lefiy.mcdev.window.utils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lefiy.mcdev.Main;
import lefiy.mcdev.utils.PathUtils;

public class ClientChecker extends Thread {
	
	@Override
	public void run() {
		
		Main.getMain().launch_status = "PREPARING CLIENT";
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		
		File settings = new File(util.getConfig() + File.separator + "Clients.json");
		
		try {
			
			URL url = new URL("https://www.dropbox.com/s/y3muv7qfhc5nai3/Clients.json?raw=1");
			FileUtils.copyURLToFile(url, settings);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonNode json = objectMapper.readTree(settings);
			
			System.out.println("Clients Json: " + json.toString());
			
			String download = json.get(Main.getMain().ver).asText();
			
			String fileName = download.substring(download.lastIndexOf("/") + 1, download.lastIndexOf("?"));
			
			File libs = new File(util.getLibraries() + File.separator + fileName);
			
			FileUtils.copyURLToFile(new URL(download), libs);
			
			System.out.println("Downloaded the client.");
			
			Files.delete(settings.toPath());
			
		} catch (Exception e) { e.printStackTrace(); }
		
		result();
	}
	
	public void result() {};
}