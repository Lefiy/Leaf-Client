package com.leafclient.font;

import java.awt.Font;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CustomFont {
	
    private static FontRender render;
    private static Font font;

    private static Font getFont(String fontname, int size) {
    	
    	Font font = null;

    	try(
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(
            		new ResourceLocation("leafclient/font/" + fontname)).getInputStream();
    	) {
    		
    		font = Font.createFont(0, is);
        	font = font.deriveFont(Font.PLAIN, size);
        	return font;
        	
    	} catch(Exception e) {
    		
    		font = new Font("default", Font.PLAIN, size);
    		e.printStackTrace();
    		
    	} return font;
    }
    
    public static FontRender getRender() {
    	return render;
    }

    public static void StartSetup() {
    	font = getFont("arial.ttf", 20);
    	render = new FontRender(font);
    }
}