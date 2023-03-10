package com.leafclient.data;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class Downloader {
	
	public Map<String, ResourceLocation> data = new HashMap<>();
	public Map<ResourceLocation, List<String>> subData = new HashMap<>();
	public ArrayList<String> custom = new ArrayList<>();
	public ArrayList<String> server = new ArrayList<>();
	
	public Downloader() {
		data = data(data, "ht8shx1e4rxxaws/data.txt");
		server = server(server, "g7ebgiho2vtabgk/server.txt");
	}
	
	Map<String, ResourceLocation> data(Map<String, ResourceLocation> map, String link) {
		String url = "https://www.dropbox.com/s/" + link + "?raw=1"; map.clear();
		try {
			File file = urlToStream(new URL(url));
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				if(line.split(":")[1].contains(",")) {
					if(line.split(":")[1].contains("Animated")) {
						
						String resources = line.split(":")[1];
						
						String[] links = resources.split(",");
						
						String name = line.split(":")[0];
						
						custom.add(name);
						
						int count = 0;
						
						for(String c_link : links) {
							
							String c_name = name;
							
							if(count != 0) { c_name = name + String.valueOf(count); }
							
							String load = "https://www.dropbox.com/s/" + c_link + "?raw=1";
							BufferedImage image = null;
							try { image = ImageIO.read(new URL(load));
							} catch (IOException e) {}
							ResourceLocation r = Minecraft.getMinecraft().getTextureManager()
									.getDynamicTextureLocation(c_name, new DynamicTexture(image));
		        		
							map.put(c_name, r);
							
							count++;
						}
						
					} else {
						String[] info = line.split(":")[1].split(",");
						String load = "https://www.dropbox.com/s/" + info[0] + "?raw=1";
						BufferedImage image = null;
						try { image = ImageIO.read(new URL(load));
						} catch (IOException e) {}
						ResourceLocation r = Minecraft.getMinecraft().getTextureManager()
								.getDynamicTextureLocation(line.split(":")[0], new DynamicTexture(image));
	        		
						map.put(line.split(":")[0], r);
						subData.put(r, Arrays.asList(info[1], info[2], info[3], info[4], info[5], info[6], 
								info[7], info[8], info[9], info[10], info[11], info[12], info[13], info[14], info[15]));
					}
				} else {
					String load = "https://www.dropbox.com/s/" + line.split(":")[1] + "?raw=1";
					BufferedImage image = null;
					try { image = ImageIO.read(new URL(load));
					} catch (IOException e) {}
					ResourceLocation r = Minecraft.getMinecraft().getTextureManager()
							.getDynamicTextureLocation(line.split(":")[0], new DynamicTexture(image));
	        		
					map.put(line.split(":")[0], r);
				}
			}
			reader.close();
			return map;
		} catch (Exception e) {}
		return null;
	}
	
	ArrayList<String> server(ArrayList<String> list, String link) {
		String url = "https://www.dropbox.com/s/" + link + "?raw=1"; list.clear();
		try {
			File file = urlToStream(new URL(url));
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.equals("none")) {list.add(line);}}
			reader.close();
			return list;
		} catch (Exception e) {}
		return null;
	}
	
	File urlToStream(URL url) throws Exception {
		File file = null;
        try (InputStream in = url.openStream()) {
        	file = download(in);}
        return file;
    }
	
	File download(InputStream in) throws IOException {
        final File temp = File.createTempFile("config", ".tmp");
        temp.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(temp)) {
            IOUtils.copy(in, out);}
        return temp;
    }
}