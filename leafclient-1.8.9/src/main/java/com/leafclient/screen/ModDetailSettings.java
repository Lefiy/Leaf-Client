package com.leafclient.screen;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Mouse;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.mods.Mod;
import com.leafclient.screen.ui.Bar;
import com.leafclient.screen.ui.ColorChart;
import com.leafclient.screen.ui.ScrollBar;
import com.leafclient.screen.ui.SelectButton;
import com.leafclient.screen.ui.SystemButton;
import com.leafclient.screen.ui.TextBox;
import com.leafclient.screen.ui.ToggleButton;
import com.leafclient.screen.ui.UIBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ModDetailSettings extends GuiScreen {
	
	private Mod mods;
	
	private SystemButton home;
	private ArrayList<UIBase> base;
	private ArrayList<ColorChart> charts;
	private ScrollBar scroll;
	
	public ModDetailSettings(Mod ll) {
		this.mods = ll;
	}
	
	@Override
	public void initGui() {
		ScaledResolution sr = new ScaledResolution(mc);
		int width = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
		int height = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
		double displayW = ((width / sr.getScaleFactor()) > sr.getScaledWidth()) ? (double)sr.getScaledWidth() / ((double)width / (double)sr.getScaleFactor()) : 1.0;
		double displayH = ((height / sr.getScaleFactor()) > sr.getScaledHeight()) ? (double)sr.getScaledHeight() / ((double)height / (double)sr.getScaleFactor()) : 1.0;
		double abjustW = (double)width / 1920.0; double abjustH = (double)height / 1080.0;
		ScaleFixer.setup(sr.getScaleFactor(), displayW, displayH, abjustW, abjustH);
		CustomFont.getRender().setSize(ScaleFixer.disHB(50));
		String name = mods.getName();
		home = new SystemButton("home", 640, 220, 80, 80);
		if(name.equals("FreeLook") || name.equals("Old Animation")) {return;}
		if(name.equals("NickHider")) {
			TextBox textbox = null;
			base.add(textbox = new TextBox("search", 770, 310, 400, 67, false) {
				@Override
				public void doThings() {
					if(select) {
						String username = this.getPassward();
						if(Client.getInstance().modmanager.nickhider.setProfile(username)) {
							Client.getInstance().setting.writer("NickHider", "username", username);
							Client.getInstance().modmanager.nickhider.username = username;
							if(Client.getInstance().modmanager.nickhider.skin) {
								Client.getInstance().modmanager.nickhider.update = true;
							}
						}
					}
				}
			}); textbox.setText(Client.getInstance().modmanager.nickhider.username);
			base.add(new ToggleButton("Enable Skin Changes", 1120, 420, 100, 60, Client.getInstance().modmanager.nickhider.skin) {
				@Override
				public void doThings() {
					if(select) {
						Client.getInstance().modmanager.nickhider.update = true;
						Client.getInstance().setting.writer("NickHider", "skin", String.valueOf(select));
						Client.getInstance().modmanager.nickhider.skin = select;
					} else {
						Client.getInstance().modmanager.nickhider.reset = true;
						Client.getInstance().modmanager.nickhider.update = true;
					}
				}
			});
			return;
		} else if(name.equals("ToggleSprint")) {
			base.add(new ToggleButton("Enable Toggle Sneak", 1120, 520, 100, 60, Client.getInstance().modmanager.sprint.sneak) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("ToggleSprint", "sneak", String.valueOf(select));
					Client.getInstance().modmanager.sprint.sneak = select;}
			});
			base.add(new ToggleButton("Enable Flying Boost", 1120, 620, 100, 60, Client.getInstance().modmanager.sprint.flyspeed) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("ToggleSprint", "speedup", String.valueOf(select));
					Client.getInstance().modmanager.sprint.flyspeed = select;}
			});
			base.add(new ToggleButton("Show Background", 1120, 720, 100, 60, Client.getInstance().modmanager.sprint.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("ToggleSprint", "background", String.valueOf(select));
					Client.getInstance().modmanager.sprint.back = select;}
			});
		} else if(name.equals("CPS")) {
			base.add(new ToggleButton("Enable Right Click CPS", 1120, 520, 100, 60, Client.getInstance().modmanager.cps.showRight) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("CPS", "right_click", String.valueOf(select));
					Client.getInstance().modmanager.cps.showRight = select;}
			});
			base.add(new ToggleButton("Show Background", 1120, 620, 100, 60, Client.getInstance().modmanager.cps.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("CPS", "background", String.valueOf(select));
					Client.getInstance().modmanager.cps.back = select;}
			});
		} else if(name.equals("KillEffect")) {
			base.add(new SelectButton("Type of Effects", 920, 310, 300, 90, Arrays.asList("NONE", "BLOOD", "LIGHTNING", "EXPLOSION", "METAL", "FIREWORK", "BURNING", "NATURE"), Client.getInstance().modmanager.killeffect.effect) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("KillEffect", "effect", list.get(index));
					Client.getInstance().modmanager.killeffect.effect = list.get(index);}
			});
			base.add(new ToggleButton("Animation of Hiding Corpse", 1120, 440, 100, 60, Client.getInstance().modmanager.killeffect.hide) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("KillEffect", "hide_cadaver", String.valueOf(select));
					Client.getInstance().modmanager.killeffect.hide = select;}
			});
			base.add(new ToggleButton("Enable Hypixel Mode", 1120, 540, 100, 60, Client.getInstance().modmanager.killeffect.bypass) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("KillEffect", "bypass", String.valueOf(select));
					Client.getInstance().modmanager.killeffect.bypass = select;}
			});
			base.add(new ToggleButton("Add Recent Colors to Firework", 1120, 640, 100, 60, Client.getInstance().modmanager.killeffect.recent) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("KillEffect", "recentcolor", String.valueOf(select));
					Client.getInstance().modmanager.killeffect.recent = select;}
			});
			return;
		} else if(name.equals("PotionStatus")) {
			base.add(new ToggleButton("Show Background", 1120, 520, 100, 60, Client.getInstance().modmanager.potion.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("PotionStatus", "background", String.valueOf(select));
					Client.getInstance().modmanager.potion.back = select;}
			});
		} else if(name.equals("ArmorStatus")) {
			base.add(new ToggleButton("Show Background", 1120, 520, 100, 60, Client.getInstance().modmanager.armor.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("ArmorStatus", "background", String.valueOf(select));
					Client.getInstance().modmanager.armor.back = select;}
			});
		} else if(name.equals("Ping")) {
			base.add(new ToggleButton("Show Background", 1120, 520, 100, 60, Client.getInstance().modmanager.ping.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Ping", "background", String.valueOf(select));
					Client.getInstance().modmanager.ping.back = select;}
			});
		} else if(name.equals("Saturation")) {
			base.add(new ToggleButton("Show Background", 1120, 520, 100, 60, Client.getInstance().modmanager.saturation.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Saturation", "background", String.valueOf(select));
					Client.getInstance().modmanager.saturation.back = select;}
			});
		} else if(name.equals("FPS")) {
			base.add(new ToggleButton("Show Background", 1120, 520, 100, 60, Client.getInstance().modmanager.fps.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("FPS", "background", String.valueOf(select));
					Client.getInstance().modmanager.fps.back = select;}
			});
		} else if(name.equals("HitBox")) {
			
			charts.add(new ColorChart("Color of HUD", 960, 310, 255, 40, mods.getImageX(), mods.getImageY(), mods.getColorValue()) {
				@Override
				public void doThings() {

					Client.getInstance().setting.writer(mods.getName(), "red", String.valueOf(r));
					Client.getInstance().setting.writer(mods.getName(), "green", String.valueOf(g));
					Client.getInstance().setting.writer(mods.getName(), "blue", String.valueOf(b));
					
					Client.getInstance().setting.writer(mods.getName(), "imagex", String.valueOf(image_x));
					Client.getInstance().setting.writer(mods.getName(), "imagey", String.valueOf(image_y));
					
					mods.setColor(r, g, b); mods.setImageX(image_x); mods.setImageY(image_y);
					
					String colorlist = Client.getInstance().modmanager.addColor(code);
					
					if(colorlist != null) Client.getInstance().setting.writer("Setting", "colors", colorlist);}
			});
			
			base.add(new ToggleButton("Show Player's Direction", 1120, 410, 100, 60, Client.getInstance().modmanager.hitbox.showDirection) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("HitBox", "look_direction", String.valueOf(select));
					Client.getInstance().modmanager.hitbox.showDirection = select;}
			});
			base.addAll(charts);
			return;
		} else if(name.equals("ScoreBoard")) {
			base.add(new Bar("Size of HUD", 960, 310, 255, 90, mods.getSizeValue(), 0, 255) {
				@Override
				public void doThings() {
					int custom = ScaleFixer.disWB(x_point);
					Client.getInstance().setting.writer(mods.getName(), "size", String.valueOf(custom));
					mods.setSize(custom);}
			});
			base.add(new ToggleButton("Hide Number of Lines", 1120, 430, 100, 60, Client.getInstance().modmanager.scoreboard.number) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("ScoreBoard", "number", String.valueOf(select));
					Client.getInstance().modmanager.scoreboard.number = select;}
			});
			base.add(new ToggleButton("Hide ScoreBoard Background", 1120, 530, 100, 60, Client.getInstance().modmanager.scoreboard.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("ScoreBoard", "background", String.valueOf(select));
					Client.getInstance().modmanager.scoreboard.back = select;}
			});
			base.add(new ToggleButton("Add Shadow Effects", 1120, 630, 100, 60, Client.getInstance().modmanager.scoreboard.shadow) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("ScoreBoard", "shadow", String.valueOf(select));
					Client.getInstance().modmanager.scoreboard.shadow = select;}
			});
			return;
		} else if(name.equals("NameTag")) {
			base.add(new ToggleButton("Add Shadow Effects", 1120, 310, 100, 60, Client.getInstance().modmanager.nametag.isShadow) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("NameTag", "shadow", String.valueOf(select));
					Client.getInstance().modmanager.nametag.isShadow = select;}
			});
			base.add(new ToggleButton("Hide NameTag Background", 1120, 410, 100, 60, Client.getInstance().modmanager.nametag.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("NameTag", "background", String.valueOf(select));
					Client.getInstance().modmanager.nametag.back = select;}
			});
			return;
		} else if(name.equals("Chat")) {
			
			charts.add(new ColorChart("Color of HUD", 960, 310, 255, 40, mods.getImageX(), mods.getImageY(), mods.getColorValue()) {
				@Override
				public void doThings() {

					Client.getInstance().setting.writer(mods.getName(), "red", String.valueOf(r));
					Client.getInstance().setting.writer(mods.getName(), "green", String.valueOf(g));
					Client.getInstance().setting.writer(mods.getName(), "blue", String.valueOf(b));
					
					Client.getInstance().setting.writer(mods.getName(), "imagex", String.valueOf(image_x));
					Client.getInstance().setting.writer(mods.getName(), "imagey", String.valueOf(image_y));
					
					mods.setColor(r, g, b); mods.setImageX(image_x); mods.setImageY(image_y);
					
					String colorlist = Client.getInstance().modmanager.addColor(code);
					
					if(colorlist != null) Client.getInstance().setting.writer("Setting", "colors", colorlist);}
			});
			
			base.add(new ToggleButton("Detect Your Name", 1120, 410, 100, 60, Client.getInstance().modmanager.chat.detect) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Chat", "detect", String.valueOf(select));
					Client.getInstance().modmanager.chat.detect = select;}
			});
			base.add(new ToggleButton("Hide Chat Background", 1120, 510, 100, 60, Client.getInstance().modmanager.chat.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Chat", "background", String.valueOf(select));
					Client.getInstance().modmanager.chat.back = select;}
			});
			base.add(new ToggleButton("Block Access to Chat", 1120, 610, 100, 60, Client.getInstance().modmanager.chat.access) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Chat", "access", String.valueOf(select));
					Client.getInstance().modmanager.chat.access = select;}
			});
			base.addAll(charts);
			return;
		} else if(name.equals("Coordinate")) {
			base.add(new ToggleButton("Hide the Biome", 1120, 520, 100, 60, Client.getInstance().modmanager.coordinate.showBiome) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Coordinate", "biome", String.valueOf(select));
					Client.getInstance().modmanager.coordinate.showBiome = select;}
			});
			base.add(new ToggleButton("Enable Decimal Format", 1120, 620, 100, 60, Client.getInstance().modmanager.coordinate.setDecimal) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Coordinate", "decimal", String.valueOf(select));
					Client.getInstance().modmanager.coordinate.setDecimal = select;}
			});
			base.add(new ToggleButton("Show Background", 1120, 720, 100, 60, Client.getInstance().modmanager.coordinate.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Coordinate", "background", String.valueOf(select));
					Client.getInstance().modmanager.coordinate.back = select;}
			});
		} else if(name.equals("BossBar")) {
			base.add(new Bar("Size of HUD", 960, 310, 255, 90, mods.getSizeValue(), 0, 255) {
				@Override
				public void doThings() {
					int custom = ScaleFixer.disWB(x_point);
					Client.getInstance().setting.writer(mods.getName(), "size", String.valueOf(custom));
					mods.setSize(custom);}
			});
			base.add(new ToggleButton("Hide Health Bar", 1120, 430, 100, 60, Client.getInstance().modmanager.bossbar.showHealth) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("BossBar", "health", String.valueOf(select));
					Client.getInstance().modmanager.bossbar.showHealth = select;}
			});
			return;
		} else if(name.equals("Hypixel")) {
			base.add(new SelectButton("Status", 920, 310, 300, 90, Arrays.asList("LEVEL", "KARMA", "RANK", "SKYWARS", "BEDWARS", "UHC", "SKYWARS WINS", "BEDWARS WINS", "UHC WINS", "DUELS WINS"), Client.getInstance().modmanager.hypixel.show) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Hypixel", "show", list.get(index));
					Client.getInstance().modmanager.hypixel.show = list.get(index);}
			});
			charts.add(new ColorChart("Color of HUD", 960, 450, 255, 40, mods.getImageX(), mods.getImageY(), mods.getColorValue()) {
				@Override
				public void doThings() {

					Client.getInstance().setting.writer(mods.getName(), "red", String.valueOf(r));
					Client.getInstance().setting.writer(mods.getName(), "green", String.valueOf(g));
					Client.getInstance().setting.writer(mods.getName(), "blue", String.valueOf(b));
					
					Client.getInstance().setting.writer(mods.getName(), "imagex", String.valueOf(image_x));
					Client.getInstance().setting.writer(mods.getName(), "imagey", String.valueOf(image_y));
					
					mods.setColor(r, g, b); mods.setImageX(image_x); mods.setImageY(image_y);
					
					String colorlist = Client.getInstance().modmanager.addColor(code);
					
					if(colorlist != null) Client.getInstance().setting.writer("Setting", "colors", colorlist);}
			});
			base.addAll(charts);
			return;
		} else if(name.equals("Time")) {
			base.add(new SelectButton("Time", 920, 310, 300, 90, Arrays.asList("MORNING", "NOON", "EVENING", "NIGHT"), Client.getInstance().modmanager.timechanger.time) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Time", "time", list.get(index));
					Client.getInstance().modmanager.timechanger.time = list.get(index);}
			});
			return;
		} else if(name.equals("Weather")) {
			base.add(new SelectButton("Weather", 920, 310, 300, 90, Arrays.asList("CLEAN", "RAIN", "SNOW"), Client.getInstance().modmanager.weatherchanger.weather) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Weather", "weather", list.get(index));
					Client.getInstance().modmanager.weatherchanger.weather = list.get(index);
					Client.getInstance().modmanager.weatherchanger.setWeather();}
			});
			return;
		} else if(name.equals("MotionBlur")) {
			base.add(new Bar("Blur Amount", 960, 310, 255, 90, Client.getInstance().modmanager.motionblur.amount, 0, 10) {
				@Override
				public void doThings() {
					int custom = ScaleFixer.disWB(x_point);
					Client.getInstance().setting.writer("MotionBlur", "amount", String.valueOf(custom));
					Client.getInstance().modmanager.motionblur.amount = custom;
					Client.getInstance().modmanager.motionblur.updateAmount(custom);}
			});
			return;
		} else if(name.equals("Overlay")) {
			base.add(new ToggleButton("Gold Ore", 1090, 310, 100, 60, Client.getInstance().modmanager.overlay.gold_ore) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "gold_ore", String.valueOf(select));
					Client.getInstance().modmanager.overlay.gold_ore = select;}
			});
			base.add(new ToggleButton("Gold Nugget", 1090, 410, 100, 60, Client.getInstance().modmanager.overlay.gold_nugget) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "gold_nugget", String.valueOf(select));
					Client.getInstance().modmanager.overlay.gold_nugget = select;}
			});
			base.add(new ToggleButton("Gold Ingot", 1090, 510, 100, 60, Client.getInstance().modmanager.overlay.gold_ingot) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "gold_ingot", String.valueOf(select));
					Client.getInstance().modmanager.overlay.gold_ingot = select;}
			});
			base.add(new ToggleButton("Diamond", 1090, 610, 100, 60, Client.getInstance().modmanager.overlay.diamond) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "diamond", String.valueOf(select));
					Client.getInstance().modmanager.overlay.diamond = select;}
			});
			base.add(new ToggleButton("Emerald", 1090, 710, 100, 60, Client.getInstance().modmanager.overlay.emerald) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "emerald", String.valueOf(select));
					Client.getInstance().modmanager.overlay.emerald = select;}
			});
			base.add(new ToggleButton("Apple", 1090, 310, 100, 60, Client.getInstance().modmanager.overlay.apple) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "apple", String.valueOf(select));
					Client.getInstance().modmanager.overlay.apple = select;}
			});
			base.add(new ToggleButton("Gold Apple", 1090, 410, 100, 60, Client.getInstance().modmanager.overlay.gold_apple) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "gold_apple", String.valueOf(select));
					Client.getInstance().modmanager.overlay.gold_apple = select;}
			});
			base.add(new ToggleButton("Potion", 1090, 510, 100, 60, Client.getInstance().modmanager.overlay.potion) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "potion", String.valueOf(select));
					Client.getInstance().modmanager.overlay.potion = select;}
			});
			base.add(new ToggleButton("Player Head", 1090, 610, 100, 60, Client.getInstance().modmanager.overlay.head) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "head", String.valueOf(select));
					Client.getInstance().modmanager.overlay.head = select;}
			});
			base.add(new ToggleButton("Ender Pearl", 1090, 710, 100, 60, Client.getInstance().modmanager.overlay.pearl) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Overlay", "pearl", String.valueOf(select));
					Client.getInstance().modmanager.overlay.pearl = select;}
			});
			scroll = new ScrollBar(base, 1230, 310, 32, 460, 5);
			return;
		} else if(name.equals("Keystrokes")) {
			base.add(new ToggleButton("Change Arrow Style", 1120, 520, 100, 60, Client.getInstance().modmanager.keystrokes.arrow) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Keystrokes", "arrow", String.valueOf(select));
					Client.getInstance().modmanager.keystrokes.arrow = select;
					Client.getInstance().modmanager.keystrokes.onChangeArrow();}
			});
			base.add(new ToggleButton("Show Clicks", 1120, 620, 100, 60, Client.getInstance().modmanager.keystrokes.click) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Keystrokes", "click", String.valueOf(select));
					Client.getInstance().modmanager.keystrokes.click = select;
					Client.getInstance().modmanager.keystrokes.addKeySpace();}
			});
			base.add(new ToggleButton("Show Space Key", 1120, 720, 100, 60, Client.getInstance().modmanager.keystrokes.space) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Keystrokes", "space", String.valueOf(select));
					Client.getInstance().modmanager.keystrokes.space = select;
					Client.getInstance().modmanager.keystrokes.addKeySpace();}
			});
		} else if(name.equals("Music")) {
			base.add(new Bar("Music Volume", 960, 520, 255, 90, Client.getInstance().modmanager.music.volume_amount, 0, 5) {
				@Override
				public void doThings() {
					int custom = ScaleFixer.disWB(x_point);
					Client.getInstance().setting.writer("Music", "volume", String.valueOf(custom));
					Client.getInstance().modmanager.music.volume_amount = custom;
					Client.getInstance().modmanager.music.updateVolume(custom);}
			});
			base.add(new ToggleButton("Automatic Music Switching", 1120, 650, 100, 60, Client.getInstance().modmanager.music.switching) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Music", "switch", String.valueOf(select));
					Client.getInstance().modmanager.music.switching = select;}
			});
			base.add(new ToggleButton("Show Background", 1120, 750, 100, 60, Client.getInstance().modmanager.music.back) {
				@Override
				public void doThings() {
					Client.getInstance().setting.writer("Music", "background", String.valueOf(select));
					Client.getInstance().modmanager.music.back = select;}
			});
		}
		
		charts.add(new ColorChart("Color of HUD", 960, 310, 255, 40, mods.getImageX(), mods.getImageY(), mods.getColorValue()) {
			@Override
			public void doThings() {

				Client.getInstance().setting.writer(mods.getName(), "red", String.valueOf(r));
				Client.getInstance().setting.writer(mods.getName(), "green", String.valueOf(g));
				Client.getInstance().setting.writer(mods.getName(), "blue", String.valueOf(b));
				
				Client.getInstance().setting.writer(mods.getName(), "imagex", String.valueOf(image_x));
				Client.getInstance().setting.writer(mods.getName(), "imagey", String.valueOf(image_y));
				
				mods.setColor(r, g, b); mods.setImageX(image_x); mods.setImageY(image_y);
				
				String colorlist = Client.getInstance().modmanager.addColor(code);
				
				if(colorlist != null) Client.getInstance().setting.writer("Setting", "colors", colorlist);}
		});
		
		base.add(new Bar("Size of HUD", 960, 400, 255, 90, mods.getSizeValue(), 0, 255) {
			@Override
			public void doThings() {
				int custom = ScaleFixer.disWB(x_point);
				Client.getInstance().setting.writer(mods.getName(), "size", String.valueOf(custom));
				mods.setSize(custom);}
		});
		
		base.addAll(charts);
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		ScaledResolution sr = new ScaledResolution(mc);
		int width = sr.getScaledWidth();
		int height = sr.getScaledHeight();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/main_mod.png"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, width, height);
		if(scroll != null) {
			for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
				base.get(i).render();}
			scroll.render();
		} else {
			for(UIBase button : base) {
				button.render();
			}
		}
		home.render();
		mouseMove(p_73863_1_, p_73863_2_);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		base = new ArrayList<>();
		charts = new ArrayList<>();
		home = null;
		scroll = null;
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		boolean isBlock = false;
		for(ColorChart chart : charts) {
			if(chart.isPanelOpen()) isBlock = true;
		}
		if(scroll != null) {
			for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
				if(base.get(i) instanceof ColorChart || !isBlock)
				base.get(i).onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
			}
		} else {
			for(UIBase button : base) {
				if(button instanceof ColorChart || !isBlock)
				button.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
			}
		}
		home.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
	}
	
	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
		for(UIBase ui : base) {
			if(ui instanceof TextBox) {
				((TextBox)ui).keyTyped(p_73869_1_, p_73869_2_);
			}
		}
		super.keyTyped(p_73869_1_, p_73869_2_);
    }
	
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		boolean isBlock = false;
		for(ColorChart chart : charts) {
			if(chart.isPanelOpen()) isBlock = true;
		}
		int event = Mouse.getEventDWheel();
		if(event < 0 && !isBlock && scroll != null) {scroll.onScroll();}
		else if(event > 0 && !isBlock && scroll != null) {scroll.onUnScroll();}
	}

	void mouseMove(int mouseX, int mouseY) {
		boolean isBlock = false;
		for(ColorChart chart : charts) {
			if(chart.isPanelOpen()) isBlock = true;
		}
		if(scroll != null) {
			for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
				if(base.get(i) instanceof ColorChart || !isBlock)
				base.get(i).onMouseMove(mouseX, mouseY);
			}
		} else {
			for(UIBase button : base) {
				if(button instanceof ColorChart || !isBlock)
				button.onMouseMove(mouseX, mouseY);
			}
		}
		home.onMouseMove(mouseX, mouseY);
	}
	
	@Override
	public void onGuiClosed() {
		this.mc.currentScreen = null;
	}
}