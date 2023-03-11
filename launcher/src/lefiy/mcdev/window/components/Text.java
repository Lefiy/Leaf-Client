package lefiy.mcdev.window.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import lefiy.mcdev.Main;

public class Text extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private int x, sence, size;
	
	public Text(int sence, int x, int y, int width, int height, int size) {
		this.x = x;
		this.sence = sence;
		this.size = size;
		setBounds(x, y, width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		int alpha = getForeground().getAlpha();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setFont(new Font(Font.SANS_SERIF, 1, size));
		int f_width = g2.getFontMetrics().stringWidth(getText()) + 10;
		int b_x = this.x - (f_width / 2);
		this.setBounds(b_x, getY(), f_width, height);
		GradientPaint gra = new GradientPaint(0, 0, new Color(80, 245, 30, alpha), width, 0, new Color(30, 245, 160, alpha));
		g2.setPaint(gra);
		g2.drawString(getText(), (width / 2) - (f_width / 2) + 5, 20);
		super.paintComponent(g);
	}
	
	public String getText() {
		if(Main.getMain().sences.isEmpty()) return "Loading";
		return Main.getMain().sences.get(this.sence);
	}
}