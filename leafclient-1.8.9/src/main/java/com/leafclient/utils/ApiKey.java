package com.leafclient.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import hypixelapi.HypixelAPI;

public class ApiKey {
	
	public static HypixelAPI API;
	public static MongoClient CLIENT;
	
	public static void Load(final String KEY) {
		API = new HypixelAPI(KEY);
	}
	
	public static void Client(final String URI) {
		CLIENT = MongoClients.create(URI);
	}
}