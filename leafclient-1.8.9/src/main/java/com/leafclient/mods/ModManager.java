package com.leafclient.mods;

import java.util.ArrayList;

import com.leafclient.Client;
import com.leafclient.mods.mod.Animation;
import com.leafclient.mods.mod.ArmorStatus;
import com.leafclient.mods.mod.BossBar;
import com.leafclient.mods.mod.CPS;
import com.leafclient.mods.mod.Chat;
import com.leafclient.mods.mod.Coordinate;
import com.leafclient.mods.mod.FPS;
import com.leafclient.mods.mod.FreeLook;
import com.leafclient.mods.mod.HitBox;
import com.leafclient.mods.mod.Hypixel;
import com.leafclient.mods.mod.Keystrokes;
import com.leafclient.mods.mod.KillEffect;
import com.leafclient.mods.mod.MotionBlur;
import com.leafclient.mods.mod.Music;
import com.leafclient.mods.mod.NameTag;
import com.leafclient.mods.mod.NickHider;
import com.leafclient.mods.mod.Overlay;
import com.leafclient.mods.mod.Ping;
import com.leafclient.mods.mod.PotionStatus;
import com.leafclient.mods.mod.Saturation;
import com.leafclient.mods.mod.ScoreBoard;
import com.leafclient.mods.mod.Time;
import com.leafclient.mods.mod.ToggleSprint;
import com.leafclient.mods.mod.Weather;

public class ModManager {
	
	public ArrayList<Mod> mods;
	public ArrayList<Integer> recent_color;
	
	public ToggleSprint sprint;
	public CPS cps;
	public FPS fps;
	public KillEffect killeffect;
	public ArmorStatus armor;
	public PotionStatus potion;
	public NickHider nickhider;
	public FreeLook freelook;
	public HitBox hitbox;
	public Ping ping;
	public ScoreBoard scoreboard;
	public NameTag nametag;
	public Chat chat;
	public Saturation saturation;
	public Animation animation;
	public Coordinate coordinate;
	public BossBar bossbar;
	public Hypixel hypixel;
	public Time timechanger;
	public Weather weatherchanger;
	public MotionBlur motionblur;
	public Overlay overlay;
	public Keystrokes keystrokes;
	public Music music;
	
	public String rank, cape, wing, hat;
	public boolean abjust, hideIcon, hideCape, hideWing, hideHat, hideEncha, fullbright, hideFire, clearGlass, customItem, borderless;
	
	public ModManager() {
		
		mods = new ArrayList<>();

		mods.add(sprint = new ToggleSprint());
		mods.add(fps = new FPS());
		mods.add(potion = new PotionStatus());
		mods.add(cps = new CPS());
		mods.add(armor = new ArmorStatus());
		mods.add(saturation = new Saturation());
		mods.add(ping = new Ping());
		mods.add(scoreboard = new ScoreBoard());
		mods.add(bossbar = new BossBar());
		mods.add(coordinate = new Coordinate());
		mods.add(hypixel = new Hypixel());
		mods.add(keystrokes = new Keystrokes());
		mods.add(music = new Music());
		
		killeffect = new KillEffect();
		freelook = new FreeLook();
		nickhider = new NickHider();
		hitbox = new HitBox();
		nametag = new NameTag();
		chat = new Chat();
		animation = new Animation();
		motionblur = new MotionBlur();
		overlay = new Overlay();
		timechanger = new Time();
		weatherchanger = new Weather();
		
		recent_color = new ArrayList<>();
		
		String colors = Client.getInstance().setting.reader("Setting", "colors");
		if(!colors.equals("none")) {
			if(colors.contains(",")) {
				String[] colorStr = colors.split(",");
				for(String c : colorStr) {
					int color = Integer.parseInt(c);
					recent_color.add(color);
				}
			} else {
				int color = Integer.parseInt(colors);
				recent_color.add(color);
			}
		}
		
		cape = Client.getInstance().setting.reader("Setting", "cape");
		wing = Client.getInstance().setting.reader("Setting", "wing");
		hat = Client.getInstance().setting.reader("Setting", "hat");
		
		abjust = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "abjust"));
		hideIcon = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "hide_icon"));
		hideCape = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "hide_cape"));
		hideWing = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "hide_wing"));
		hideHat = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "hide_hat"));
		fullbright = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "fullbright"));
		hideEncha = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "hide_enchant"));
		hideFire = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "hide_fire"));
		clearGlass = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "clear_glass"));
		borderless = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "borderless"));
		customItem = Boolean.valueOf(Client.getInstance().setting.reader("Setting", "item"));
	}
	
	public String addColor(int newColor) {
		String colorStr = ""; int index = 1;
		ArrayList<Integer> new_color = new ArrayList<>();
		colorStr += newColor; new_color.add(newColor);
		for(int code : recent_color) {
			if(code == newColor) {
				return null;
			}
			if(index <= 9) {
				colorStr += "," + code;
				new_color.add(code);
			} index++;
		}
		recent_color = new_color;
		return colorStr;
	}
	
	public void renderMods() {
		for(Mod m : mods) {
			if(m.isEnable()) m.render();
		}
	}
}