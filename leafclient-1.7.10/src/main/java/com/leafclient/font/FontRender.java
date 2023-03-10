package com.leafclient.font;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

public class FontRender extends FontUtils {
	
    private int[] colorCode = new int[32];

    public FontRender(Font font) {
        super(font);
        this.setupMinecraftColorcodes();
    }

    public int drawString(String text, double x2, float y2, int color) {
        return (int) this.drawString(text, x2, y2, color, false, 8.3F);
    }

    public float drawCenteredString(String text, float x2, float y2, int color) {
        return this.drawString(text, x2 - (float) (this.getStringWidth(text) / 2), y2, color);
    }
    
    public int drawStringWithShadow(String text, double x2, float y2, int color) {
        float shadowWidth = this.drawString(text, x2 + 0.9F, (double) y2 + 0.5F, color, true, 8.3F);
        return (int) Math.max(shadowWidth, this.drawString(text, x2, y2, color, false, 8.3F));
    }

    public float drawCenteredStringWithShadow(String text, float x2, float y2, int color) {
        return this.drawStringWithShadow(text, x2 - (float) (this.getStringWidth(text) / 2), y2, color);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow, float kerning) {
    	
        x -= 1.0;

        if (text == null) {
            return 0;
        }

        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }

        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }

        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }

        FontUtils.CharData[] currentData = this.charData;
        float alpha = (float) (color >> 24 & 255) / 255F;
        
        x *= 2;
        y = (y - 3) * 2;
        
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F, (color & 255) / 255F, alpha);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.tex.getGlTextureId());
        
        for (int index = 0; index < text.length(); index++) {
        	
            char character = text.charAt(index);
            
            if (character == '\u00a7') {
            	
                int colorIndex = 21;
                
                try {
                	colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(index + 1));
                } catch (Exception e) { e.printStackTrace(); }
                
                if (colorIndex < 16) {
                	
                	GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());
                    currentData = this.charData;

                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }

                    if (shadow) {
                        colorIndex += 16;
                    }

                    int colorcode = this.colorCode[colorIndex];
                    GL11.glColor4f((colorcode >> 16 & 255) / 255F, (colorcode >> 8 & 255) / 255F, (colorcode & 255) / 255F, alpha);
                    
                } else {
                	
                	GL11.glColor4f((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F, (color & 255) / 255F, alpha);
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.tex.getGlTextureId());
                    currentData = this.charData;
                    
                }

                ++index;
                
            } else if (character < currentData.length) {
            	
                GL11.glBegin(GL11.GL_TRIANGLES);
                this.drawChar(currentData, character, (float) x, (float) y);
                GL11.glEnd();
                x += currentData[character].width - kerning;
                
            }
        }

        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        
        return (float) x / 2.0F;
    }

    public double getStringWidth(String text) {
    	
        if (text == null) {
            return 0;
        }

        float width = 0;
        FontUtils.CharData[] currentData = charData;

        for (int index = 0; index < text.length(); index++) {
        	
            char character = text.charAt(index);
            
            if (character == '\u00a7') {
            	
                ++index;
                
            } else if (character < currentData.length) {
            	
                width += currentData[character].width - 8.3F;
                
            }
        }
        
        return width / 2;
    }

    private void setupMinecraftColorcodes() {
    	
        int index = 0;

        while (index < 32) {
        	
            int noClue = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + noClue;
            int green = (index >> 1 & 1) * 170 + noClue;
            int blue = (index & 1) * 170 + noClue;

            if (index == 6) {
                red += 85;
            }

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCode[index] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
            ++index;
        }
    }
}