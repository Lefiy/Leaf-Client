package com.leafclient.impl;

import net.minecraft.util.Session;

public interface IMixinSession {
	
	Session getSession();
	
	void setSession(Session session);
	
	void windowResize(int width, int height);

}
