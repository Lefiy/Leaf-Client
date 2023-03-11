package lefiy.mcdev.window.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import javax.swing.JComponent;

import lefiy.mcdev.Main;
import lefiy.mcdev.window.utils.Version;
import lefiy.mcdev.window.utils.Version.Mod;

public class ToggleBox extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private String ver;
	
	private Version version;
	
	private ImageObserver base;
	
	private Image close;
	
	private boolean visible;
	
	public ToggleBox(String ver, int x, int y, int width, int height, ImageObserver base) {
		this.ver = ver;
		this.base = base;
		this.visible = false;
		this.version = Main.getMain().versions.get(ver);
		this.close = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/close.png"));
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setFont(new Font("sansserif", 0, 15));
		setBounds(x, y, width, height);
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				repaint();
				super.mouseEntered(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
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
				if(!visible) return;
				int mouseX = e.getX();
				int mouseY = e.getY();
				int height = 60;
				for(Mod mod : version.getMods()) {
					if(mod.getName().equals("Leaf Client")) continue;
					if(mouseX >= getWidth() - 35 && mouseY >= height - 15 && mouseY <= height) {
						onClick(ver, mod.getName(), !isToggle(mod.getName()));
						repaint();
					}
					height += 30;
				}
				if(mouseX >= getWidth() - 30 && mouseY >= 10 && mouseY <= 30) {
					setCompVisible(false);
				}
				//super.mouseReleased(e);
			}
		});
		
		setVisible(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setColor(new Color(190, 190, 190, 255));
		g2.fillRoundRect(0, 0, width, height, 20, 20);
		g2.setColor(new Color(0, 0, 0, 255));
		int s_height = 60;
		for(Mod mod : version.getMods()) {
			if(mod.getName().equals("Leaf Client")) continue;
			g2.drawString(mod.getName(), 10, s_height);
			g2.drawImage(getImage(mod.getName()), width - 35, s_height - 15, 25, 15, base);
			s_height += 30;
		}
		g2.drawImage(close, width - 30, 10, 20, 20, base);
		super.paintComponent(g2);
	}
	
	public void onClick(String version, String name, boolean toggle) {};
	
	boolean isToggle(String modName) {
		if(ver.equals("1.8")) {
			if(Main.getMain().f_1_8.contains(modName)) {
				return true;
			}
		} else if(ver.equals("1.7")) {
			if(Main.getMain().f_1_7.contains(modName)) {
				return true;
			}
		}
		return false;
	}
	
	public void setCompVisible(boolean visible) {
		this.visible = visible;
		setVisible(visible);
		repaint();
	}
	
	Image getImage(String modName) {
		if(isToggle(modName)) {
			return Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/true.png"));
		} else {
			return Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/false.png"));
		}
	}
}