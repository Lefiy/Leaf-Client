package com.leafclient.utils;

public class HypixelUser {
	
	private String uuid, rank;
	private int level, karma, sky, bed, uhc, skywin, bedwin, uhcwin, duelwin;
	
	public HypixelUser(String uuid, int level, int karma, String rank, int sky, int bed, int uhc, int skywin, int bedwin, int uhcwin, int duelwin) {
		this.uuid = uuid;
		this.level = level;
		this.karma = karma;
		this.rank = rank;
		this.sky = sky;
		this.bed = bed;
		this.uhc = uhc;
		this.skywin = skywin;
		this.bedwin = bedwin;
		this.uhcwin = uhcwin;
		this.duelwin = duelwin;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getKarma() {
		return this.karma;
	}
	
	public String getRank() {
		return this.rank;
	}
	
	public int getSkyLevel() {
		return this.sky;
	}
	
	public int getBedLevel() {
		return this.bed;
	}
	
	public int getUHCLevel() {
		return this.uhc;
	}
	
	public int getSkyWin() {
		return this.skywin;
	}
	
	public int getBedWin() {
		return this.bedwin;
	}
	
	public int getUHCWin() {
		return this.uhcwin;
	}
	
	public int getDuelWin() {
		return this.duelwin;
	}
}