package com.leafclient.mods.mod;

import java.util.ArrayList;

import com.leafclient.Client;
import com.leafclient.mods.Mod;
import com.leafclient.utils.HypixelUser;

public class Hypixel extends Mod {
	
	public String show;
	
	public ArrayList<HypixelUser> loaded;
	
	private final char STAR = '\u272B';
	
	public Hypixel() {
		super("Hypixel", 0, 0, 
				Integer.parseInt(Client.getInstance().setting.reader("Hypixel", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("Hypixel", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("Hypixel", "blue")),
				0,
				Boolean.valueOf(Client.getInstance().setting.reader("Hypixel", "enable")), false);
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("Hypixel", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("Hypixel", "imagey")));
		this.show = Client.getInstance().setting.reader("Hypixel", "show");
		this.loaded = new ArrayList<>();
	}
	
	public String getText(String uuid) {
		String tag = "";
		for (int i = loaded.size() - 1; i >= 0; i--) {
			HypixelUser user = loaded.get(i);
			if(user.getUUID().equals(uuid)) {
				if(show.equals("LEVEL")) {
					tag = "Level: " + user.getLevel();
				} else if(show.equals("KARMA")) {
					tag = "Karma: " + user.getKarma();
				} else if(show.equals("RANK")) {
					tag = "Rank: " + user.getRank();
				} else if(show.equals("SKYWARS")) {
					tag = "Skywars: " + user.getSkyLevel() + "" + STAR;
				} else if(show.equals("BEDWARS")) {
					tag = "Bedwars: " + user.getBedLevel() + "" + STAR;
				} else if(show.equals("UHC")) {
					tag = "UHC: " + user.getUHCLevel() + "" + STAR;
				} else if(show.equals("SKYWARS WINS")) {
					tag = "Skywars Wins: " + user.getSkyWin();
				} else if(show.equals("BEDWARS WINS")) {
					tag = "Bedwars Wins: " + user.getBedWin();
				} else if(show.equals("UHC WINS")) {
					tag = "UHC Wins: " + user.getUHCWin();
				} else if(show.equals("DUELS WINS")) {
					tag = "Duels Wins: " + user.getDuelWin();
				} return tag;
			}
		}
		return null;
	}
}