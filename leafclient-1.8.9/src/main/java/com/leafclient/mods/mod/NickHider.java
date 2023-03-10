package com.leafclient.mods.mod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafclient.Client;
import com.leafclient.mods.Mod;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

public class NickHider extends Mod {
	
	public GameProfile profile;
	public String username;
	
	public boolean skin, update, reset;

	public NickHider() {
		super("NickHider", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("NickHider", "enable")), false);
		this.username = Client.getInstance().setting.reader("NickHider", "username");
		this.skin = Boolean.valueOf(Client.getInstance().setting.reader("NickHider", "skin"));
		this.setProfile(username); this.update = false; this.reset = false;
	}
	
	public boolean setProfile(String name) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String uuid = null;
		
		String uuidUrl = "https://api.mojang.com/users/profiles/minecraft/" + name;
		
		try {
			
			JsonNode uuidNode = mapper.readTree(new URL(uuidUrl));
			
			uuid = uuidNode.get("id").asText();
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(uuid == null) return false;
		
		String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false";
		
		try {
			
			JsonNode node = mapper.readTree(new URL(url));
			
			JsonNode properties = node.get("properties").get(0);
			
			GameProfile profile = new GameProfile(UUIDTypeAdapter.fromString(uuid), name);
			
			String key = properties.get("name").asText();
			
			String value = properties.get("value").asText();
			
			String signature = properties.get("signature").asText();
			
			profile.getProperties().put(key, new Property(key, value, signature));
			
			this.profile = profile;
			
			return true;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}