package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

public class Overlay extends Mod {
	
	public boolean gold_ore, gold_nugget, gold_ingot, diamond, emerald, apple, gold_apple, potion, head, pearl;

	public Overlay() {
		super("Overlay", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "enable")), false);
		this.gold_ore = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "gold_ore"));
		this.gold_nugget = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "gold_nugget"));
		this.gold_ingot = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "gold_ingot"));
		this.diamond = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "diamond"));
		this.emerald = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "emerald"));
		this.apple = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "apple"));
		this.gold_apple = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "gold_apple"));
		this.potion = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "potion"));
		this.head = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "head"));
		this.pearl = Boolean.valueOf(Client.getInstance().setting.reader("Overlay", "pearl"));
	}
	
	public boolean isTarget(int id) {
		if(id == 14 && gold_ore) return true;
		if(id == 371 && gold_nugget) return true;
		if(id == 266 && gold_ingot) return true;
		if(id == 264 && diamond) return true;
		if(id == 388 && emerald) return true;
		if(id == 260 && apple) return true;
		if(id == 322 && gold_apple) return true;
		if((id == 373 || id == 438) && potion) return true;
		if(id == 397 && head) return true;
		if(id == 368 && pearl) return true;
		return false;
	}
}