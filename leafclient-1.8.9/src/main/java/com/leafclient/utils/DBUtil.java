package com.leafclient.utils;

import org.bson.Document;

import com.leafclient.Client;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DBUtil {
	
	private String uuid;
	
	public DBUtil(String uuid) {
		this.uuid = uuid;
	}
	
	public void online() {
		
		new Thread() {
			
			@Override
			public void run() {
				
				final MongoClient client = ApiKey.CLIENT;
				
				MongoDatabase database = client.getDatabase("client");
				MongoCollection<Document> data = database.getCollection("user");
				
				Document user = data.find(Filters.eq("uuid", uuid)).first();
				
				if(user == null) {
					
					String rank = Client.getInstance().modmanager.rank;
					String cape = Client.getInstance().modmanager.cape;
					String wing = Client.getInstance().modmanager.wing;
					String hat = Client.getInstance().modmanager.hat;
				
					Document doc = new Document("uuid", uuid);
					doc.append("rank", rank);
					doc.append("cape", cape);
					doc.append("wing", wing);
					doc.append("hat", hat);
					
					data.insertOne(doc);
					
				}
			}
		}.start();
	}
	
	public void offline(boolean appEnd) {
		
		new Thread() {
			
			@Override
			public void run() {
				
				final MongoClient client = ApiKey.CLIENT;
				
				MongoDatabase database = client.getDatabase("client");
				MongoCollection<Document> data = database.getCollection("user");
				
				Document user = data.find(Filters.eq("uuid", uuid)).first();
				
				if(user != null) {
					
					data.deleteMany(user);
					
				}
				
				if(appEnd) {
					
					ApiKey.CLIENT.close();
					
				}
			}
		}.start();
	}
	
	public void data() {
		
		new Thread() {
			
			@Override
			public void run() {
				
				final MongoClient client = ApiKey.CLIENT;
				
				MongoDatabase database = client.getDatabase("client");
				MongoCollection<Document> data = database.getCollection("data");
				
				String capeTotal = "", wingTotal = "", hatTotal = "";
				
				Document free = data.find(Filters.eq("uuid", "free")).first();
				
				Client.getInstance().modmanager.rank = "user";
				
				Client.getInstance().cosmes.clear();
				
				if(free != null) {
					
					capeTotal += free.getString("cape");
					
					wingTotal += free.getString("wing");
					
					hatTotal += free.getString("hat");
					
				}
				
				Document user = data.find(Filters.eq("uuid", uuid)).first();
				
				if(user != null) {
					
					String rank = user.getString("rank");
					
					if(rank != null ) {
					
						Client.getInstance().modmanager.rank = rank;
						
					}
					
					String capes = user.getString("cape");
					
					if(capes != null) {
					
						capeTotal += "," + capes;
					}
					
					String wings = user.getString("wing");
					
					if(wings != null) {
					
						wingTotal += "," + wings;
					}
					
					String hats = user.getString("hat");
					
					if(hats != null) {
					
						hatTotal += "," + hats;
					}
				}
				
				if(!capeTotal.contains(Client.getInstance().modmanager.cape)) {
					Client.getInstance().modmanager.cape = "None";
					Client.getInstance().setting.writer("Setting", "cape", "None");
				}
				
				if(!wingTotal.contains(Client.getInstance().modmanager.wing)) {
					Client.getInstance().modmanager.wing = "None";
					Client.getInstance().setting.writer("Setting", "wing", "None");
				}
				
				if(!hatTotal.contains(Client.getInstance().modmanager.hat)) {
					Client.getInstance().modmanager.hat = "None";
					Client.getInstance().setting.writer("Setting", "hat", "None");
				}
				
				Client.getInstance().cosmes.put("cape", capeTotal);
				Client.getInstance().cosmes.put("wing", wingTotal);
				Client.getInstance().cosmes.put("hat", hatTotal);
				
			}
		}.start();
	}
	
	public void player() {
		
		new Thread() {
			
			@Override
			public void run() {
				
				final MongoClient client = ApiKey.CLIENT;
				
				MongoDatabase database = client.getDatabase("client");
				MongoCollection<Document> data = database.getCollection("user");
				
				Document user = data.find(Filters.eq("uuid", uuid)).first();
				
				if(user != null) {
					
					String rank = user.getString("rank");
					String cape = user.getString("cape");
					String wing = user.getString("wing");
					String hat = user.getString("hat");
					
					result(new LeafUser(uuid, rank, cape, wing, hat));
				}
			}
		}.start();
	}
	
	public void update(String key, String value) {
		
		new Thread() {
			
			@Override
			public void run() {
				
				final MongoClient client = ApiKey.CLIENT;
				
				MongoDatabase database = client.getDatabase("client");
				MongoCollection<Document> data = database.getCollection("user");
				
				Document user = data.find(Filters.eq("uuid", uuid)).first();
				
				if(user != null) {
					
					Document update = new Document();
			        update.append(key, value);
			        
			        Document updateObj = new Document();
			        updateObj.append("$set", update);
					
					data.updateOne(user, updateObj);
				}
			}
		}.start();
		
	}
	
	public void result(LeafUser user) {}
}