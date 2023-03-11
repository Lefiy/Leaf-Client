package lefiy.mcdev.window.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import lefiy.mcdev.Main;
import lefiy.mcdev.window.utils.FadeUtil;

public class Button extends JButton {

	private static final long serialVersionUID = 1L;
	
	private int sence;
	
	private FadeUtil fade;

	public Button(int sence, int x, int y, int w, int h) {
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setBorder(new EmptyBorder(10, 10, 10, 50));
		setFont(new Font(Font.SANS_SERIF, 1, 14));
		this.sence = sence;
		this.fade = new FadeUtil() {
			@Override
			public void doThings() {
				repaint();
			}
		};
		setBounds(x, y, w, h);
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
				repaint();
				onClick();
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
		int f_width = g2.getFontMetrics().stringWidth(getText()) + 10;
		g2.drawString(getText(), (width / 2) - (f_width / 2) + 1, 25);
		g2.setColor(new Color(0, 0, 0, fade.getValue()));
		g2.fillRoundRect(0, 0, width, height, height, height);
	}
	
	public void onClick() {}
	
	public String getText() {
		if(Main.getMain().sences.isEmpty()) return "Loading";
		return Main.getMain().sences.get(this.sence);
	}
}