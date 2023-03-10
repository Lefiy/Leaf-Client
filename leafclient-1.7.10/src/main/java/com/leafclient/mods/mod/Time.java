package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

public class Time extends Mod {
	
	public String time;

	public Time() {
		super("Time", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("Time", "enable")), false);
		this.time = Client.getInstance().setting.reader("Time", "time");
	}
	
	public long getTime() {
		if(time.equals("MORNING")) {
			return 1000;
		} else if(time.equals("NOON")) {
			return 6000;
		} else if(time.equals("EVENING")) {
			return 12786;
		} else if(time.equals("NIGHT")) {
			return 18000;}
		return 1000;
	}
}
