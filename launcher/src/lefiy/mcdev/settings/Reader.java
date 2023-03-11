package lefiy.mcdev.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lefiy.mcdev.Main;
import lefiy.mcdev.utils.PathUtils;

public class Reader {
	
	private String settings_json;
	
	public Reader() {
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		
		File settings = new File(util.getConfig() + File.separator + "Settings.json");
		
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonNode json = objectMapper.readTree(settings);
			this.settings_json = json.toString();
			
			Main.getMain().loading = false;
			Main.getMain().launched = false;
			Main.getMain().setup = json.get("setup").asBoolean();
			Main.getMain().lang = json.get("language").asText();
			Main.getMain().ver = json.get("version").asText();
			Main.getMain().m_1_7 = json.get("1.7-module").asText();
			Main.getMain().m_1_8 = json.get("1.8-module").asText();
			Main.getMain().f_1_7 = json.get("1.7-forge-mod").asText();
			Main.getMain().f_1_8 = json.get("1.8-forge-mod").asText();
			Main.getMain().ram = json.get("ram").asText();
			
		} catch (IOException e) {e.printStackTrace();}
		
		System.out.println("Current Lang: " + Main.getMain().lang);
		this.reload();
	}
	
	public void reload() {
		String lang = Main.getMain().lang;
		Main.getMain().sences = new ArrayList<>();
		Main.getMain().sences.add(reader(lang + "-lang-done"));
		Main.getMain().sences.add(reader(lang + "-lang-select"));
		Main.getMain().sences.add(reader(lang + "-lang-select-1"));
		Main.getMain().sences.add(reader(lang + "-lang-select-2"));
		Main.getMain().sences.add(reader(lang + "-lang-select-3"));
		Main.getMain().sences.add(reader(lang + "-pass"));
		Main.getMain().sences.add(reader(lang + "-pass-1"));
		Main.getMain().sences.add(reader(lang + "-pass-2"));
		Main.getMain().sences.add(reader(lang + "-pass-3"));
		Main.getMain().sences.add("LAUNCH");
		Main.getMain().sences.add("LOADING");
		Main.getMain().sences.add(reader(lang + "-menu-1"));
		Main.getMain().sences.add(reader(lang + "-menu-2"));
		Main.getMain().sences.add(reader(lang + "-menu-3"));
		Main.getMain().sences.add(reader(lang + "-version"));
		Main.getMain().sences.add(reader(lang + "-version-1"));
		Main.getMain().sences.add(reader(lang + "-version-2"));
		Main.getMain().sences.add(reader(lang + "-version-3"));
		Main.getMain().sences.add(reader(lang + "-ram"));
		for(String str : Main.getMain().sences) {
			System.out.println("Registered Word: " + str);
		}
	}
	
	public void writer(String key, Object value) {
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		
		File settings = new File(util.getConfig() + File.separator + "Settings.json");
		
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode json = objectMapper.readTree(settings_json);
			
			if(value instanceof Boolean) {
				boolean b_value = (Boolean) value;
				((ObjectNode) json).put(key, b_value);
			} else {
				((ObjectNode) json).put(key, value.toString());
			}
			
			settings_json = json.toString();
			
			BufferedWriter JWriter = new BufferedWriter(new FileWriter(settings));
			
			JWriter.write(settings_json);
			
			JWriter.close();
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private String reader(String path) {
		
		try (InputStream input = getClass().getResourceAsStream("/lefiy/mcdev/settings/lang/Lang.txt")) {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			
			String line;
			
			while((line = reader.readLine()) != null) {
				if(line.startsWith(path)) {
					reader.close();
					input.close();
					return line.split(":")[1];}}
			
		} catch (IOException e) {e.printStackTrace();}
		
		return null;
	}
}