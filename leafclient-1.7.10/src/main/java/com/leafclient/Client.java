package com.leafclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.input.Keyboard;

import com.leafclient.cosmetics.Hat;
import com.leafclient.cosmetics.Wing;
import com.leafclient.data.Downloader;
import com.leafclient.font.CustomFont;
import com.leafclient.impl.IMixinSession;
import com.leafclient.mods.ModManager;
import com.leafclient.settings.SecureReader;
import com.leafclient.settings.SettingManager;
import com.leafclient.utils.ApiKey;
import com.leafclient.utils.DBUtil;
import com.leafclient.utils.UserList;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.Session;

public class Client {
	
	private static Client instance = new Client();
	
	public final SecureReader secure = new SecureReader();
	
	private ArrayList<String> keys;
	public int index;
	
	public SettingManager setting;
	public ModManager modmanager;
	public Downloader downloader;
	
	public UserList users;
	public Map<String, String> cosmes;
	public int cos_number;
	
	private long time;
	
	public KeyBinding settingKey = new KeyBinding("leafclient.gui", Keyboard.KEY_RSHIFT, "leafclient.keys");
	public KeyBinding sprintKey = new KeyBinding("leafclient.sprint", Keyboard.KEY_LCONTROL, "leafclient.keys");
	public KeyBinding freeKey = new KeyBinding("leafclient.freelook", Keyboard.KEY_RETURN, "leafclient.keys");
	
	public Wing wing;
	public Hat hat;
	
	public void setup() {
		keys = new ArrayList<>();
		users = new UserList();
		cosmes = new HashMap<>();
		setting = new SettingManager();
		modmanager = new ModManager();
		downloader = new Downloader();
		index = Integer.parseInt(setting.reader("Setting", "index"));
		CustomFont.StartSetup();
		ApiKey.Client("mongodb+srv://User:leafuser@leafclient.vdb0n.mongodb.net/?retryWrites=true&w=majority");
		this.start();
	}
	
	public void shutdown() {
		DiscordRPC.discordShutdown();
	}
	
	public ArrayList<String> getKeys() {
		return this.keys;
	}
	
	public static Client getInstance() {
		return instance;
	}
	
	public String getToken() {
		MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = null;
        try {
        	result = authenticator.loginWithWebview();
            if(((IMixinSession)Minecraft.getMinecraft()).getSession().getProfile().getId() != null) {
            	String oldUUID = ((IMixinSession)Minecraft.getMinecraft()).getSession().getProfile().getId().toString();
            	DBUtil database = new DBUtil(oldUUID); database.offline(false);
            }
            final Session session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
            ((IMixinSession) Minecraft.getMinecraft()).setSession(session);
            DBUtil database2 = new DBUtil(session.getProfile().getId().toString());
            database2.data(); return result.getRefreshToken();
        } catch (MicrosoftAuthenticationException e) {}
        return null;
	}
	
	public String tokenSession(final String token) {
		MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result = null;
        try {
            result = authenticator.loginWithRefreshToken(token);
            if(((IMixinSession)Minecraft.getMinecraft()).getSession().getProfile().getId() != null) {
            	String oldUUID = ((IMixinSession)Minecraft.getMinecraft()).getSession().getProfile().getId().toString();
            	DBUtil database = new DBUtil(oldUUID); database.offline(false);
            }
            final Session session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
            ((IMixinSession) Minecraft.getMinecraft()).setSession(session);
            DBUtil database2 = new DBUtil(session.getProfile().getId().toString());
            database2.data(); return result.getProfile().getName();
        } catch (MicrosoftAuthenticationException e) {}
        return null;
	}
	
	public void applyStatus() {
		time = System.currentTimeMillis();
		DiscordEventHandlers handle = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			@Override
			public void apply(DiscordUser user) {
				setStatus();
			}
		}).build();
		DiscordRPC.discordInitialize("954367073415471106", handle, true);
		new Thread("Discord Callback") {
			@Override
			public void run() {
				while(true) {
					DiscordRPC.discordRunCallbacks();
				}
			}
		}.start();
	}
	
	public void setStatus() {
		DiscordRichPresence.Builder build = new DiscordRichPresence.Builder("Playing Minecraft 1.7.10");
		build.setBigImage("icon", "Leaf Client");
		build.setSmallImage("check", "Version 5.0");
		build.setStartTimestamps(time);
		DiscordRPC.discordUpdatePresence(build.build());
	}
	
	private void start() {
		
		this.cos_number = 0;
		
		final Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(cos_number >= 9) {
					cos_number = 0;
				} else {
					cos_number += 1;
				}
			}
		}, 0, 400);
	}
}