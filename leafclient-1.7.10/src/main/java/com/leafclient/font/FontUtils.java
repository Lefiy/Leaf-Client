package com.leafclient.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.texture.DynamicTexture;

public class FontUtils {
	
	private final float IMAGE_SIZE = 512;
	
    protected CharData[] charData = new CharData[256];
    protected Font font; int fontHeight = -1;
    protected DynamicTexture tex;

    public FontUtils(Font font) {
        this.font = font;
        this.tex = this.getTexture(font, this.charData);
    }

    protected DynamicTexture getTexture(Font font, CharData[] chars) {
    	
        BufferedImage img = this.createImage(font, chars);
        
        try {return new DynamicTexture(img);
        } catch (Exception e) {return null;}
    }

    protected BufferedImage createImage(Font font, CharData[] chars) {
    	
        int imgSize = (int) this.IMAGE_SIZE;
        BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, 2);
        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        g2.setFont(font);
        g2.setColor(new Color(255, 255, 255, 0));
        g2.fillRect(0, 0, imgSize, imgSize);
        g2.setColor(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fontMetrics = g2.getFontMetrics();
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;
        int index = 0;

        while (index < chars.length) {
        	
            char c = (char) index;
            CharData charData = new CharData();
            Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(c), g2);
            charData.width = dimensions.getBounds().width + 8;
            charData.height = dimensions.getBounds().height;

            if (positionX + charData.width >= imgSize) {
                positionX = 0;
                positionY += charHeight;
                charHeight = 0;
            }

            if (charData.height > charHeight) {
                charHeight = charData.height;
            }

            charData.storedX = positionX;
            charData.storedY = positionY;

            if (charData.height > this.fontHeight) {
                this.fontHeight = charData.height;
            }

            chars[index] = charData;
            g2.drawString(String.valueOf(c), positionX + 2, positionY + fontMetrics.getAscent());
            positionX += charData.width;
            ++index;
        }
        
        return bufferedImage;
    }

    protected void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
    	
        try {this.drawQuad(x, y, chars[c].width, chars[c].height, chars[c].storedX, chars[c].storedY, chars[c].width, chars[c].height);
        } catch (Exception e) { e.printStackTrace(); }
    }

    protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
    	
        float renderSRCX = srcX / this.IMAGE_SIZE;
        float renderSRCY = srcY / this.IMAGE_SIZE;
        float renderSRCWidth = srcWidth / this.IMAGE_SIZE;
        float renderSRCHeight = srcHeight / this.IMAGE_SIZE;
        
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, y);
    }
    
    public void setSize(int size) {
    	this.font = font.deriveFont(Font.PLAIN, size);
    	this.tex = this.getTexture(font, this.charData);
    }

    protected static class CharData {
        public int width, height, storedX, storedY;
    }
}