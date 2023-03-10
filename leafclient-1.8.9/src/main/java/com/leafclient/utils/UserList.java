package com.leafclient.utils;

import java.util.ArrayList;

public class UserList extends ArrayList<LeafUser> {
	
	private static final long serialVersionUID = 1L;

	public void setData(LeafUser user) {
		String uuid = user.getUUID();
		for(LeafUser leaf : this) {
			if(leaf.getUUID().equals(uuid)) {
				int index = this.indexOf(leaf);
				this.set(index, user);
				return;
			}
		} this.add(user);
	}
	
	public LeafUser isExistUUID(String uuid) {
		for(LeafUser user : this) {
			if(user.getUUID().equals(uuid)) {
				return user;
			}
		}
		return null;
	}
}