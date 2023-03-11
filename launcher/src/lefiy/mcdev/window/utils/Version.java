package lefiy.mcdev.window.utils;

import java.util.ArrayList;
import java.util.List;

public class Version {
	
	public ArrayList<Mod> mods;
	
	public Version(List<String> mod, List<String> link, List<String> hash) {
		this.mods = new ArrayList<>(); int size = mod.size();
		for(int index = 0; index < size; index++) {
			mods.add(new Mod(mod.get(index), link.get(index), hash.get(index)));
		}
	}
	
	public ArrayList<Mod> getMods() {
		return this.mods;
	}
	
	public Mod getModByName(String name) {
		for(Mod mod : mods) {
			if(mod.getName().equals(name)) {
				return mod;
			}
		}
		return null;
	}
	
	public Mod getModByHash(String hash) {
		for(Mod mod : mods) {
			if(mod.getHash().equals(hash)) {
				return mod;
			}
		}
		return null;
	}
	
	public class Mod {
		
		private String name, link, hash;
		
		public Mod(String name, String link, String hash) {
			this.name = name;
			this.link = link;
			this.hash = hash;
		}
		
		public String getName() {
			return this.name;
		}
		
		public String getLink() {
			return this.link;
		}
		
		public String getHash() {
			return this.hash;
		}
	}
}