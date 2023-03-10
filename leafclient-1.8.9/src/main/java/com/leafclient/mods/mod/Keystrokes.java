package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

public class Keystrokes extends Mod {
	
	public boolean arrow, click, space;
	
	private Key w, a, s ,d, lc, rc, sp;
	
	private int sp_s;

	public Keystrokes() {
		super("Keystrokes", Integer.parseInt(Client.getInstance().setting.reader("Keystrokes", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("Keystrokes", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("Keystrokes", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("Keystrokes", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("Keystrokes", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("Keystrokes", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("Keystrokes", "enable")), true);
		this.arrow = Boolean.valueOf(Client.getInstance().setting.reader("Keystrokes", "arrow"));
		this.click = Boolean.valueOf(Client.getInstance().setting.reader("Keystrokes", "click"));
		this.space = Boolean.valueOf(Client.getInstance().setting.reader("Keystrokes", "space"));
		w = new Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 20, 0, 19, 19);
		a = new Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 0, 20, 19, 19);
		s = new Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 20, 20, 19, 19);
		d = new Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 40, 20, 19, 19);
		lc = new Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 0, 40, 29, 19);
		rc = new Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 30, 40, 29, 19);
		sp = new Key("---", Minecraft.getMinecraft().gameSettings.keyBindJump, 0, 60, 59, 10);
		this.addKeySpace(); this.onChangeArrow();
	}
	
	@Override
	public void render() {
		GL11.glPushMatrix();
        GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        w.render(hud().getHudX(), hud().getHudY());
        a.render(hud().getHudX(), hud().getHudY());
        s.render(hud().getHudX(), hud().getHudY());
        d.render(hud().getHudX(), hud().getHudY());
        if(click) {
        	lc.render(hud().getHudX(), hud().getHudY());
        	rc.render(hud().getHudX(), hud().getHudY());
        }
        if(space) {
        	sp.render(hud().getHudX(), hud().getHudY() - sp_s);
        }
		GL11.glPopMatrix();
	}
	
	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        w.render(hud().getHudX(), hud().getHudY());
        a.render(hud().getHudX(), hud().getHudY());
        s.render(hud().getHudX(), hud().getHudY());
        d.render(hud().getHudX(), hud().getHudY());
        if(click) {
        	lc.render(hud().getHudX(), hud().getHudY());
        	rc.render(hud().getHudX(), hud().getHudY());
        }
        if(space) {
        	sp.render(hud().getHudX(), hud().getHudY() - sp_s);
        }
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}
	
	@Override
	public int getWidth() {
		return 59;
	}
	
	@Override
	public int getHeight() {
		return 70 - sp_s - (space ? 0 : 11);
	}
	
	public void onChangeArrow() {
		if(arrow) {
			w.setName('\u25B2');
			a.setName('\u25C0');
			s.setName('\u25BC');
			d.setName('\u25B6');
		} else {
			w.setName('W');
			a.setName('A');
			s.setName('S');
			d.setName('D');
		}
	}
	
	public void updateKey() {
		
		if(Minecraft.getMinecraft().gameSettings != null) {
		
			w.setCode(Minecraft.getMinecraft().gameSettings.keyBindForward);
			a.setCode(Minecraft.getMinecraft().gameSettings.keyBindLeft);
			s.setCode(Minecraft.getMinecraft().gameSettings.keyBindBack);
			d.setCode(Minecraft.getMinecraft().gameSettings.keyBindRight);
		
			lc.setCode(Minecraft.getMinecraft().gameSettings.keyBindAttack);
			rc.setCode(Minecraft.getMinecraft().gameSettings.keyBindUseItem);
		
			sp.setCode(Minecraft.getMinecraft().gameSettings.keyBindJump);
			
		}
	}
	
	public void addKeySpace() {
		sp_s = 0; if((!click && space) || (!click && !space)) {sp_s = 20;}
	}
	
	private class Key {
		
		String key;
		KeyBinding code;
		int x, y, width, height;
		
		public Key(String key, KeyBinding code, int x, int y, int width, int height) {
			this.key = key;
			this.code = code;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		void setName(char key) {
			this.key = "" + key;
		}
		
		void setCode(KeyBinding code) {
			this.code = code;
		}
		
		void render(int modX, int modY) {
			Gui.drawRect(modX + x, modY + y, modX + (x + width), modY + (y + height), 1342177280);
			drawStringCenter(key, modX + x + (width / 2), modY + y + (height / 2), getColorValue());
			if(code.isKeyDown()) {
				Gui.drawRect(modX + x, modY + y, modX + (x + width), modY + (y + height), 1356651740);
			}
		}
	}
	
	int drawStringCenter(String text, int x, int y, int color) {
		FontRenderer render = Minecraft.getMinecraft().fontRendererObj;
		return render.drawStringWithShadow(text, x - (render.getStringWidth(text) / 2 - 1), y - (render.FONT_HEIGHT / 2 - 1), color);
	}
}