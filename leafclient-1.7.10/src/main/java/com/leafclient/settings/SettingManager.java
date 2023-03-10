package com.leafclient.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import net.minecraft.client.Minecraft;

public class SettingManager {
	
	private File leafDataDir;
	
	public SettingManager() {
		this.leafDataDir = new File(Minecraft.getMinecraft().mcDataDir, "leafclient-1.7.10");
		if(!leafDataDir.exists()) leafDataDir.mkdirs();
		this.checkFiles();
	}
	
	private void checkFiles() {
		for(String name : Arrays.asList(
				"Setting", 
				"FPS", 
				"KillEffect",
				"ToggleSprint",
				"PotionStatus",
				"CPS",
				"ArmorStatus",
				"NickHider",
				"Saturation",
				"FreeLook",
				"HitBox",
				"Ping",
				"ScoreBoard",
				"NameTag",
				"Chat",
				"Coordinate",
				"BossBar",
				"Time",
				"Weather",
				"MotionBlur",
				"Overlay",
				"Keystrokes",
				"Music")) {
			File file = new File(leafDataDir, name + ".txt");
			if(!file.exists()) {
				try {file.createNewFile();
				} catch (IOException e) {}
				this.writeSettings(file, name);
			}file = null;
		}
	}
	
	private void writeSettings(File file, String name) {
		try {
		FileWriter writer = new FileWriter(file, false);
		switch (name) {
		case "Setting":
			writer.write("index:1\n");
			writer.write("colors:none\n");
			writer.write("abjust:false\n");
			writer.write("cape:None\n");
			writer.write("wing:None\n");
			writer.write("hat:None\n");
			writer.write("hide_icon:false\n");
			writer.write("hide_cape:false\n");
			writer.write("hide_wing:false\n");
			writer.write("hide_hat:false\n");
			writer.write("fullbright:false\n");
			writer.write("clear_glass:false\n");
			writer.write("hide_enchant:false\n");
			writer.write("hide_fire:false\n");
			writer.write("borderless:false");
			writer.close();
			break;
		case "KillEffect":
			writer.write("enable:false\n");
			writer.write("effect:NONE\n");
			writer.write("hide_cadaver:false\n");
			writer.write("float_cadaver:false\n");
			writer.write("recentcolor:false\n");
			writer.write("bypass:false");
			writer.close();
			break;
		case "PotionStatus":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("size:127\n");
			writer.write("background:false");
			writer.close();
			break;
		case "HitBox":
			writer.write("enable:false\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("look_direction:false");
			writer.close();
			break;
		case "NickHider":
			writer.write("enable:false\n");
			writer.write("skin:false\n");
			writer.write("username:Player");
			writer.close();
			break;
		case "CPS":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("size:127\n");
			writer.write("right_click:false\n");
			writer.write("background:false");
			writer.close();
			break;
		case "ToggleSprint":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("size:127\n");
			writer.write("speedup:false\n");
			writer.write("sneak:false\n");
			writer.write("background:false");
			writer.close();
			break;
		case "ScoreBoard":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("size:127\n");
			writer.write("number:false\n");
			writer.write("background:false\n");
			writer.write("shadow:false\n");
			writer.write("side:false");
			writer.close();
			break;
		case "NameTag":
			writer.write("enable:false\n");
			writer.write("shadow:false\n");
			writer.write("background:false");
			writer.close();
			break;
		case "Chat":
			writer.write("enable:false\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("detect:false\n");
			writer.write("background:false\n");
			writer.write("access:false");
			writer.close();
			break;
		case "FreeLook":
			writer.write("enable:false");
			writer.close();
			break;
		case "Coordinate":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("size:127\n");
			writer.write("biome:false\n");
			writer.write("decimal:false\n");
			writer.write("background:false");
			writer.close();
			break;
		case "BossBar":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("size:127\n");
			writer.write("health:false");
			writer.close();
			break;
		case "Time":
			writer.write("enable:false\n");
			writer.write("time:MORNING");
			writer.close();
			break;
		case "Weather":
			writer.write("enable:false\n");
			writer.write("weather:CLEAN");
			writer.close();
			break;
		case "MotionBlur":
			writer.write("enable:false\n");
			writer.write("amount:127");
			writer.close();
			break;
		case "Overlay":
			writer.write("enable:false\n");
			writer.write("gold_ore:false\n");
			writer.write("gold_nugget:false\n");
			writer.write("gold_ingot:false\n");
			writer.write("diamond:false\n");
			writer.write("emerald:false\n");
			writer.write("apple:false\n");
			writer.write("gold_apple:false\n");
			writer.write("potion:false\n");
			writer.write("head:false\n");
			writer.write("pearl:false");
			writer.close();
			break;
		case "Keystrokes":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("size:127\n");
			writer.write("arrow:false\n");
			writer.write("click:false\n");
			writer.write("space:false");
			writer.close();
			break;
		case "Music":
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("size:127\n");
			writer.write("switch:false\n");
			writer.write("volume:127\n");
			writer.write("background:false");
			writer.close();
			break;
		default:
			writer.write("enable:false\n");
			writer.write("x:5\n");
			writer.write("y:5\n");
			writer.write("red:254\n");
			writer.write("green:254\n");
			writer.write("blue:254\n");
			writer.write("imagex:1\n");
			writer.write("imagey:1\n");
			writer.write("size:127\n");
			writer.write("background:false");
			writer.close();
			break;}
		writer = null;
		} catch (IOException e) {}
	}
	
	public void writer(String name, String path, String value) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(leafDataDir, name + ".txt")));
			String line;
			String result = "";
			while((line = reader.readLine()) != null) {
				if(line.startsWith(path)) {
					result += line.replace(line.split(":")[1], value) + "\n";
				}else{result += line + "\n";}}
			reader.close();
			FileWriter writer = new FileWriter(new File(leafDataDir, name + ".txt"), false);
			writer.write(result);
			writer.close();
		} catch (IOException e) {}
	}
	
	public String reader(String name, String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(leafDataDir, name + ".txt")));
			String line;
			while((line = reader.readLine()) != null) {
				if(line.startsWith(path)) {
					reader.close();
					return line.split(":")[1];}}
		} catch (IOException e) {}
		return null;
	}
}