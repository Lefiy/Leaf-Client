package com.leafclient.utils;

public class LeafUser {
	
	private String uuid, rank, cape, wing, hat;
	
	public LeafUser(String uuid, String rank, String cape, String wing, String hat) {
		this.uuid = uuid;
		this.rank = rank;
		this.cape = cape;
		this.wing = wing;
		this.hat = hat;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getRank() {
		return this.rank;
	}
	
	public String getCape() {
		return this.cape;
	}
	
	public String getWing() {
		return this.wing;
	}
	
	public String getHat() {
		return this.hat;
	}
}