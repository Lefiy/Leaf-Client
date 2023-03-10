package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S2BPacketChangeGameState;

public class Weather extends Mod {
	
	public String weather;

	public Weather() {
		super("Weather", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("Weather", "enable")), false);
		this.weather = Client.getInstance().setting.reader("Weather", "weather");
	}
	
	public void setWeather() {
		
		if(!isEnable()) return;
		
		if(weather.equals("CLEAN")) {
			Minecraft.getMinecraft().getNetHandler().handleChangeGameState(new S2BPacketChangeGameState(1, 0.0F));
		} else if(weather.equals("RAIN")) {
			Minecraft.getMinecraft().getNetHandler().handleChangeGameState(new S2BPacketChangeGameState(2, 0.0F));
		} else if(weather.equals("SNOW")) {
			Minecraft.getMinecraft().getNetHandler().handleChangeGameState(new S2BPacketChangeGameState(1, 0.0F));
			Minecraft.getMinecraft().getNetHandler().handleChangeGameState(new S2BPacketChangeGameState(7, 1.0F));
		}
	}
}