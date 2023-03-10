package com.leafclient.utils;

import java.util.concurrent.CompletableFuture;

import hypixelapi.HypixelAPI;
import hypixelapi.entities.player.HypixelPlayer;
import hypixelapi.util.LevelingUtil;

public class HypixelUtil extends Thread {
	
	private String uuid;
	
	public HypixelUtil(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public void run() {
		
		final HypixelAPI API = ApiKey.API;
		
		int level = 0, karma = 0, sky = 0, bed = 0, uhc = 0,
		skywin = 0, bedwin = 0, uhcwin = 0, duelwin = 0;
		
		String rank = "Normal";
		
		CompletableFuture<HypixelPlayer> run = API.retrievePlayerByUuid(uuid);
		try {
			HypixelPlayer player = run.get();
			long exp = player.getNetworkExp();
			level = (int) LevelingUtil.getLevel(exp);
			karma = (int) player.getKarma();
			rank = player.getRank().getLocalizedName();
			sky = player.getAchievements().getSkyWarsStar();
			bed = player.getAchievements().getBedWarsStar();
			uhc = player.getStats().getUHC().getTitle().getStar();
			skywin = player.getAchievements().getSkyWarsWin();
			bedwin = player.getAchievements().getBedWarsWin();
			uhcwin = player.getStats().getUHC().getSoloWins();
			duelwin = player.getStats().getDuels().getWin();
		} catch (Exception e) { return; }
		
		result(level, karma, rank, sky, bed, uhc, skywin, bedwin, uhcwin, duelwin);
	}
	
	public void result(int level, int karma, String rank, int sky, int bed, int uhc, int skywin, int bedwin, int uhcwin, int duelwin) {}
}