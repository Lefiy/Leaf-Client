package com.leafclient.screen.custom;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.impl.IMixinSession;
import com.leafclient.screen.ScaleFixer;
import com.leafclient.screen.ui.SystemButton;
import com.leafclient.screen.ui.TextBox;
import com.leafclient.settings.SecureReader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiPassward extends GuiScreen
{
    
    private TextBox textbox;
    private SystemButton button;
    
    private final SecureReader READER;

    public GuiPassward(final SecureReader READER) {
    	this.READER = READER;
    }

    public void initGui()
    {
    	ScaledResolution sr = new ScaledResolution(mc);
		int width = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
		int height = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
		double displayW = ((width / sr.getScaleFactor()) > sr.getScaledWidth()) ? (double)sr.getScaledWidth() / ((double)width / (double)sr.getScaleFactor()) : 1.0;
		double displayH = ((height / sr.getScaleFactor()) > sr.getScaledHeight()) ? (double)sr.getScaledHeight() / ((double)height / (double)sr.getScaleFactor()) : 1.0;
		double abjustW = (double)width / 1920.0; double abjustH = (double)height / 1080.0;
		ScaleFixer.setup(sr.getScaleFactor(), displayW, displayH, abjustW, abjustH);
		CustomFont.getRender().setSize(ScaleFixer.disHB(70));
		
    	this.textbox = new TextBox("pass", 550, 500, 821, 80, true);
    	
    	this.button = new SystemButton("login", 823, 850, 274, 80) {
    		@Override
    		public void doThings() {
    			String info = READER.reader(textbox.getPassward(), Client.getInstance().index);
            	if(info != null) {
            		if(((IMixinSession)Minecraft.getMinecraft()).getSession().getProfile().getId() != null) {
            			Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
            		} else {
            			Minecraft.getMinecraft().displayGuiScreen(new GuiSession(READER, false));
            		}
            	}
    		}
    	};
    }
    
    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
    	textbox = null;
    	button = null;
    	super.setWorldAndResolution(mc, width, height);
    }
    
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
    	this.textbox.keyTyped(p_73869_1_, p_73869_2_);
    	super.keyTyped(p_73869_1_, p_73869_2_);
    }
    
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
    	this.textbox.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
    	this.button.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
    	super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	ScaledResolution sr = new ScaledResolution(mc);
    	int width = sr.getScaledWidth();
    	int height = sr.getScaledHeight();
    	mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/background.png"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, width, height);
    	this.mouseMove(p_73863_1_, p_73863_2_);
        this.textbox.render();
        this.button.render();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    public void mouseMove(int mouseX, int mouseY) {
    	this.button.onMouseMove(mouseX, mouseY);
    }
}