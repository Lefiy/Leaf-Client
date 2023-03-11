package lefiy.mcdev.window.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.NumberFormat;

import javax.swing.JComponent;

public class CountText extends JComponent {
	
private static final long serialVersionUID = 1L;
	
	private int number, max, size;
	
	public CountText(int number, int x, int y, int width, int height, int size) {
		this.max = number;
		this.number = 0;
		this.size = size;
		setBounds(x, y, width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int alpha = getForeground().getAlpha();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setFont(new Font(Font.SANS_SERIF, 1, size));
		int f_width = width - (g2.getFontMetrics().stringWidth(getText()) + 5);
		GradientPaint gra = new GradientPaint(f_width, 0, new Color(80, 245, 30, alpha), width, 0, new Color(30, 245, 160, alpha));
		g2.setPaint(gra);
		g2.drawString(getText(), f_width, 32);
		super.paintComponent(g);
	}
	
	private String getText() {
		if(number > 100000000) {return "100,000,000+";}
		NumberFormat num = NumberFormat.getNumberInstance();
		return num.format(number);
	}
	
	public void doThings() {
		number = max;
	}
}