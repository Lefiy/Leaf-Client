package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

public class Chat extends Mod {
	
	public boolean detect, back, access;

	public Chat() {
		super("Chat", 0, 0, 
				Integer.parseInt(Client.getInstance().setting.reader("Chat", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("Chat", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("Chat", "blue")), 
				0, 
				Boolean.valueOf(Client.getInstance().setting.reader("Chat", "enable")), false);
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("Chat", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("Chat", "imagey")));
		this.detect = Boolean.valueOf(Client.getInstance().setting.reader("Chat", "detect"));
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("Chat", "background"));
		this.access = Boolean.valueOf(Client.getInstance().setting.reader("Chat", "access"));
	}
}
