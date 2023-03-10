package com.leafclient.screen.custom;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;
import com.leafclient.screen.ui.SystemButton;
import com.leafclient.settings.SecureReader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiSession extends GuiScreen
{
    
    private SystemButton mi_login, close;
    
    private final SecureReader READER;
    
    private boolean closeable;

    public GuiSession(final SecureReader READER, boolean closeable) {
    	this.READER = READER;
    	this.closeable = closeable;
    }

    public void initGui()
    {
    	ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
		int height = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
		double displayW = ((width / sr.getScaleFactor()) > sr.getScaledWidth()) ? (double)sr.getScaledWidth() / ((double)width / (double)sr.getScaleFactor()) : 1.0;
		double displayH = ((height / sr.getScaleFactor()) > sr.getScaledHeight()) ? (double)sr.getScaledHeight() / ((double)height / (double)sr.getScaleFactor()) : 1.0;
		double abjustW = (double)width / 1920.0; double abjustH = (double)height / 1080.0;
		ScaleFixer.setup(sr.getScaleFactor(), displayW, displayH, abjustW, abjustH);
		CustomFont.getRender().setSize(ScaleFixer.disHB(70));
    	
    	this.close = new SystemButton("close", 1820, 20, 80, 80) {
			@Override
			public void doThings() {
				if(closeable) {
					Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
				}
			}
		};
    	
    	this.mi_login = new SystemButton("login", 823, 850, 274, 80) {
    		@Override
    		public void doThings() {
    			if(Minecraft.getMinecraft().isFullScreen()) {
    				Minecraft.getMinecraft().toggleFullscreen();
    			}
    			final String token = Client.getInstance().getToken();
    			if(token != null && !token.equals("")) {
    				Client.getInstance().getKeys().add(token);
    				READER.writer(token);
    				if(Client.getInstance().getKeys().size() > 1) {
    					int index = Client.getInstance().index + 1;
    					Client.getInstance().index = index;
    					Client.getInstance().setting.writer("Setting", "index", String.valueOf(index));
    				}
    				Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
    			}
    		}
    	};
    }
    
    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
    	mi_login = null; close = null;
    	super.setWorldAndResolution(mc, width, height);
    }
    
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
    	super.keyTyped(p_73869_1_, p_73869_2_);
    }
    
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
    	mi_login.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
    	if(closeable) {
    		close.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
    	}
    	super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    	int width = sr.getScaledWidth();
    	int height = sr.getScaledHeight();
    	mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/login_back.png"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, width, height);
    	this.mouseMove(p_73863_1_, p_73863_2_);
    	
    	GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_BLEND);
    	
    	mi_login.render();
    	
    	if(closeable) {
    		close.render();
    	}
    	
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    public void mouseMove(int mouseX, int mouseY) {
    	this.mi_login.onMouseMove(mouseX, mouseY);
    	if(closeable) {
    		this.close.onMouseMove(mouseX, mouseY);
    	}
    }
}