package lefiy.mcdev.window.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

import lefiy.mcdev.window.utils.FadeUtil;

public class SystemButton extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private FadeUtil fade;
	
	private ImageObserver base;
	
	private Image image;
	
	private float image_alpha;

	public SystemButton(String image, int x, int y, int w, int h, ImageObserver base) {
		this.base = base;
		this.image_alpha = 0.0F;
		this.image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/" + image));
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setBorder(new EmptyBorder(10, 10, 10, 50));
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
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setColor(new Color(0, 0, 0, fade.getValue()));
		g2.fillRoundRect(0, 0, width, height, 20, 20);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, image_alpha));
		g2.drawImage(image, 5, 5, width - 10, height - 10, base);
		super.paintComponent(g2);
	}
	
	public void onClick() {}
	
	public void setImageAlpha(float image_alpha) {
		this.image_alpha = image_alpha;
	}
}