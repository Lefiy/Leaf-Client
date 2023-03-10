package com.leafclient.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ApiKey {
	
	public static MongoClient CLIENT;
	
	public static void Client(final String URI) {
		CLIENT = MongoClients.create(URI);
	}
}