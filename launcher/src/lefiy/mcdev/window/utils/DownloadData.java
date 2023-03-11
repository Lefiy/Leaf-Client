package lefiy.mcdev.window.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lefiy.mcdev.Main;
import lefiy.mcdev.utils.PathUtils;

public class DownloadData {
	
	private List<News> news;
	private int downloads;
	
	public DownloadData() {
		news_download();
		version_download();
		info_download();
		download_count();
	}
	
	public void news_download() {
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));

		File file = new File(util.getConfig() + File.separator + "News.json");
		
		try {
			
			URL url = new URL("https://www.dropbox.com/s/wtw9hfq515q8fvf/News.json?raw=1");
			FileUtils.copyURLToFile(url, file);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode json = objectMapper.readTree(file);
			
			System.out.println("News Json: " + json.toString());
			
			ObjectMapper  mapper = new ObjectMapper();
			List<News> d_news = mapper.readValue(json.toString(), new TypeReference<List<News>>() {} );
			
			System.out.println("Loaded News: " + d_news.size());
			
			int index = 3;
			
			if(d_news.size() <= 2) index = d_news.size();
			
			this.news = d_news.subList(0, index);
			
			Files.delete(file.toPath());
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void version_download() {
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		
		Main.getMain().versions = new HashMap<>();

		File file = new File(util.getConfig() + File.separator + "Versions.json");
		
		try {
			
			URL url = new URL("https://www.dropbox.com/s/bsnn6k97n02v1ic/Versions.json?raw=1");
			FileUtils.copyURLToFile(url, file);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode json = objectMapper.readTree(file);
			
			System.out.println("Versions Json: " + json.toString());
			
			ObjectMapper  mapper = new ObjectMapper();
			List<Versions> d_ver = mapper.readValue(json.toString(), new TypeReference<List<Versions>>() {} );
			
			System.out.println("Loaded Versions: " + d_ver.size());
			
			for(Versions version : d_ver) {
				
				String ver = version.getVersion();
				
				Main.getMain().versions.put(ver, new Version(version.getMod(), version.getLink(), version.getHash()));
				
			}
			
			Files.delete(file.toPath());
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void info_download() {
		
		PathUtils util = new PathUtils(System.getProperty("user.dir"));
		
		File file = new File(util.getInformation() + File.separator + "Mods.txt");
		
		try {
			
			URL url = new URL("https://www.dropbox.com/s/w3j43ir33mvceun/Mods.txt?raw=1");
			FileUtils.copyURLToFile(url, file);
			
			System.out.println("Downloaded Mods Information.");
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private void download_count() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String url = "https://api.github.com/repos/Lefiy/Leaf-Client/releases";
		
		int download = 0;
		
		try {
			
			JsonNode node = mapper.readTree(new URL(url));
			
			JsonNode assetsNode = node.get(0);
			
			int size = assetsNode.get("assets").size();
			
			System.out.println("Loaded Assets: " + size);
			
			for(int count = 1; count <= size; count++) {
				
				int index = count - 1;
				
				JsonNode assets = assetsNode.get("assets").get(index);
				
				download += assets.get("download_count").asInt();
			}
			
			System.out.println("Download Count: " + download);
			
		} catch (IOException e) {e.printStackTrace();}
		
		this.downloads = download;
	}
	
	public List<News> getNews() {
		return this.news;
	}
	
	public int getDownloads() {
		return this.downloads;
	}
}