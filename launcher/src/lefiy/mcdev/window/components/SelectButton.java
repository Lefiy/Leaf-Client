package lefiy.mcdev.window.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import lefiy.mcdev.window.utils.FadeUtil;

public class SelectButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private int space;
	
	private ButtonGroup group;
	
	private boolean select;
	
	private float image_alpha;
	
	private FadeUtil fade;
	
	private Image icon;
	
	private ImageObserver base;

	public SelectButton(String image, int x, int y, int w, int h, int space, ImageObserver base, boolean select) {
		icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/" + image));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setBorder(new EmptyBorder(10, 10, 10, 50));
		this.space = space;
		this.base = base;
		this.image_alpha = 0.0F;
		this.fade = new FadeUtil() {
			@Override
			public void doThings() {
				repaint();
			}
		};
		this.select = select;
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
				resetAll();
				onClick();
				super.mouseClicked(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
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
		if(select) {g2.setColor(new Color(70, 245, 65, alpha)); g2.fillRoundRect(0, 0, width, height, 20, 20);};
		g2.setPaint(gra);
		g2.fillRoundRect(5, 5, width - 10, height - 10, 20, 20);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image_alpha));
		if(icon.getWidth(base) >= width) {
			int i_width = width - space; int i_height = height - space;
			g2.drawImage(icon, (width / 2) - (i_width / 2), (height / 2) - (i_height / 2), i_width, i_height, base);
		} else {
			g2.drawImage(icon, (width / 2) - (icon.getWidth(base) / 2), (height / 2) - (icon.getHeight(base) / 2), base);
		}
		//if(cover) {repaint();}
		g2.setColor(new Color(0, 0, 0, fade.getValue()));
		g2.fillRoundRect(5, 5, width - 10, height - 10, 20, 20);
		super.paintComponent(g);
	}
	
	public void onClick() {}
	
	public void setGroup(ButtonGroup group) {
		this.group = group;
	}
	
	public void resetAll() {
		this.group.reset(this);
	}
	
	public void setToggle(boolean select) {
		this.select = select;
		repaint();
	}
	
	public void setImageAlpha(float image_alpha) {
		this.image_alpha = image_alpha;
	}
}