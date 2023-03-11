package lefiy.mcdev.window.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
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

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import lefiy.mcdev.window.utils.FadeUtil;

public class TextBox extends JPasswordField {

	private static final long serialVersionUID = 1L;
	
	private boolean hide;
	
	private float image_alpha;
	
	private FadeUtil fade;
	
	public TextBox(int x, int y, int w, int h) {
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setBorder(new EmptyBorder(10, 10, 10, 50));
		setFont(new Font("sansserif", 0, 14));
		setSelectionColor(new Color(45, 200, 55));
		setBounds(x, y, w, h);
		setEchoChar('\u2022');
		this.hide = true;
		this.image_alpha = 0.0F;
		this.fade = new FadeUtil() {
			@Override
			public void doThings() {
				repaint();
			}
		};
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				if(mouseX >= getWidth() - getHeight() - 2
				&& mouseX <= getWidth() - getHeight() - 2 + getHeight() - 2
				&& mouseY >= 2
				&& mouseY <= 2 + getHeight() - 4) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				} else {
					setCursor(new Cursor(Cursor.TEXT_CURSOR));
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
				repaint();
				super.mouseExited(e);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				if(mouseX >= getWidth() - getHeight() - 2
				&& mouseX <= getWidth() - getHeight() - 2 + getHeight() - 2
				&& mouseY >= 2
				&& mouseY <= 2 + getHeight() - 4) {
					hide = !hide;
					if(hide) {
						setEchoChar('\u2022');
					} else {
						setEchoChar((char)0);
					}
				}
				super.mouseClicked(e);
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
		g2.setColor(new Color(255, 255, 255, alpha));
		g2.fillRoundRect(0, 0, width, height, height, height);
		//if(cover) {repaint();}
		super.paintComponent(g);
		int circle_x = width - height - 2;
		GradientPaint gra = new GradientPaint(circle_x, 0, new Color(50, 170, 0, alpha), width, 0, new Color(0, 170, 90, alpha));
		g2.setPaint(gra);
		g2.fillOval(circle_x, 2, height - 2, height - 4);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image_alpha));
		g2.drawImage(getIcon(), circle_x + 5, 5, 28, 28, this);
		g2.setColor(new Color(0, 0, 0, fade.getValue()));
		g2.fillRoundRect(0, 0, width, height, height, height);
	}
	
	Image getIcon() {
		if(hide) {
			return Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/hide.png"));
		} else {
			return Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/show.png"));
		}
	}
	
	public void setImageAlpha(float image_alpha) {
		this.image_alpha = image_alpha;
	}
}