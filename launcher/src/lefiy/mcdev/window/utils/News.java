package lefiy.mcdev.window.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class News {
	
	@JsonProperty("id")
	public int id;
	
	@JsonProperty("date")
	public String date;
	
	@JsonProperty("author")
	public String author;
	
	@JsonProperty("title")
	public List<String> title;
	
	@JsonProperty("desc")
	public List<String> desc;
	
	public int getID() {
		return id;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getTitle(String lang) {
		if(lang.equalsIgnoreCase("en")) {
			return title.get(0);
		} else if(lang.equalsIgnoreCase("jp")) {
			return title.get(1);
		} else {return "null";}
	}
	
	public String getDesc(String lang) {
		if(lang.equalsIgnoreCase("en")) {
			return desc.get(0);
		} else if(lang.equalsIgnoreCase("jp")) {
			return desc.get(1);
		} else {return "null";}
	}
}