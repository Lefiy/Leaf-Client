package lefiy.mcdev.window.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.ImageObserver;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import lefiy.mcdev.Main;
import lefiy.mcdev.window.utils.FadeUtil;

public class LaunchButton extends JButton {
	
private static final long serialVersionUID = 1L;
	
	private int sence, loading;
	
	private float image_alpha;
	
	private boolean subcover;
	
	private ImageObserver base;
	
	private FadeUtil fade;

	public LaunchButton(int sence, int loading, int x, int y, int w, int h, ImageObserver base) {
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setBorder(new EmptyBorder(10, 10, 10, 50));
		this.sence = sence;
		this.loading = loading;
		this.fade = new FadeUtil() {
			@Override
			public void doThings() {
				repaint();
			}
		};
		this.subcover = false;
		this.image_alpha = 0.0F;
		this.base = base;
		setBounds(x, y, w, h);
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				if(mouseX >= getWidth() - getHeight() - 2
				&& mouseX <= getWidth() - getHeight() - 2 + getHeight() - 2
				&& mouseY >= 2
				&& mouseY <= 2 + getHeight() - 4) {
					subcover = true;
				} else {
					subcover = false;
				}
				super.mouseMoved(e);
			}
		});
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				fade.fadeIn();
				repaint();
				super.mouseEntered(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				fade.fadeOut();
				subcover = false;
				repaint();
				super.mouseExited(e);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				repaint();
				super.mouseClicked(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(Main.getMain().launched) return;
				int mouseX = e.getX();
				int mouseY = e.getY();
				if(mouseX >= getWidth() - getHeight() - 2
				&& mouseX <= getWidth() - getHeight() - 2 + getHeight() - 2
				&& mouseY >= 2 && mouseY <= 2 + getHeight() - 4) {
					repaint();
					onSubClick();
				} else {
					Main.getMain().loading = true;
					Main.getMain().launched = true;
					Main.getMain().launch_status = "LAUNCHING CLIENT";
					repaint();
					onClick();
				}
				//super.mouseReleased(e);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		int alpha = getForeground().getAlpha();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		GradientPaint gra = new GradientPaint(0, 0, new Color(50, 170, 0, alpha), width, 0, new Color(0, 170, 90, alpha));
		g2.setPaint(gra);
		g2.fillRoundRect(0, 0, width, height, height, height);
		g2.setColor(new Color(0, 0, 0, alpha));
		String text = getLaunchText();
		g2.setFont(new Font(Font.SANS_SERIF, 1, 30));
		int f_width = g2.getFontMetrics().stringWidth(text);
		int plus = (Main.getMain().launched) ? width : width - height;
		int neg = 40;
		g2.drawString(text, (plus / 2) - (f_width / 2) + 1, neg);
		if(!Main.getMain().launched) {
			int circle_x = width - height;
			g2.setColor(new Color(70, 245, 65, alpha));
			g2.fillOval(circle_x, 0, height, height);
			GradientPaint gra2 = new GradientPaint(circle_x, 0, new Color(50, 170, 1, alpha), width, 0, new Color(1, 170, 90, alpha));
			g2.setPaint(gra2);
			g2.fillOval(circle_x + 6, 6, height - 12, height - 12);
			if(subcover) {
				g2.setColor(new Color(0, 0, 0, 20));
				g2.fillOval(circle_x + 6, 6, height - 12, height - 12);
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image_alpha));
			g2.drawImage(getImage(), circle_x + 19, 19, 40, 40, base);
		}
		
		String ver = getLaunchSubText();
		g2.setColor(new Color(0, 0, 0, alpha));
		g2.setFont(new Font(Font.SANS_SERIF, 1, 18));
		int v_width = g2.getFontMetrics().stringWidth(ver);
		int v_plus = (Main.getMain().launched) ? width : width - height;
		g2.drawString(ver, (v_plus / 2) - (v_width / 2) + 1, neg + 20);
		
		g2.setColor(new Color(0, 0, 0, fade.getValue()));
		g2.fillRoundRect(0, 0, width, height, height, height);
		
		if(Main.getMain().loading) {repaint();}
		
		super.paintComponent(g2);
	}
	
	public void onClick() {}
	
	public void onSubClick() {}
	
	public void setImageAlpha(float image_alpha) {
		this.image_alpha = image_alpha;
	}
	
	String getLaunchText() {
		if(Main.getMain().sences.isEmpty()) return "Loading";
		if(Main.getMain().launched) {
			return Main.getMain().sences.get(this.loading);
		}
		return Main.getMain().sences.get(this.sence);
	}
	
	String getLaunchSubText() {
		if(Main.getMain().launched) {
			return Main.getMain().launch_status;
		} else {
			String version = "";
			String module = "";
			switch(Main.getMain().ver) {
			case "1.7":
				version = "1.7.10";
				if(Main.getMain().m_1_7.equals("forge")) {
					module = " (Forge)";
				}
				break;
			case "1.8":
				version = "1.8.9";
				if(Main.getMain().m_1_8.equals("forge")) {
					module = " (Forge)";
				}
				break;
			}
			return "MC " + version + module;
		}
	}
	
	Image getImage() {
		return Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/gear.png"));
	}
}