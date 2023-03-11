package lefiy.mcdev.window.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Versions {
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("mod")
	private List<String> mod;
	
	@JsonProperty("link")
	private List<String> link;
	
	@JsonProperty("hash")
	private List<String> hash;
	
	public String getVersion() {
		return version;
	}
	
	public List<String> getMod() {
		return mod;
	}
	
	public List<String> getLink() {
		return link;
	}
	
	public List<String> getHash() {
		return hash;
	}
}
