package lefiy.mcdev.window.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

import lefiy.mcdev.Main;
import lefiy.mcdev.window.utils.News;

public class NewsPanel extends JTextArea {

	private static final long serialVersionUID = 1L;
	
	private News data;
	
	private Image author;
	
	private ImageObserver base;
	
	public NewsPanel(News data, int x, int y, int width, int height, ImageObserver base) {
		this.base = base;
		this.data = data;
		this.author = getAuthorIcon(data.getAuthor());
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
		setFont(new Font("sansserif", 0, 14));
		setText(data.getDesc(Main.getMain().lang).replace("|", "\n"));
		setBounds(x, y, width, height);
		setMargin(new Insets(40, 15, 30, 15));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		int alpha = getForeground().getAlpha();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		setText(data.getDesc(Main.getMain().lang).replace("|", "\n"));
		g2.setColor(new Color(40, 38, 40, alpha));
		g2.fillRoundRect(0, 0, width, height, 20, 20);
		g2.setColor(new Color(255, 255, 255, alpha));
		g2.setFont(new Font("sansserif", 1, 18));
		g2.drawString(data.getTitle(Main.getMain().lang), 15, 30);
		g2.setFont(new Font("sansserif", 0, 14));
		String write = "Written By";
		int w_author = g2.getFontMetrics().stringWidth(write + data.getAuthor()) + 55;
		int w_image = g2.getFontMetrics().stringWidth(write) + 8;
		g2.drawString(write, width - w_author, height - 10);
		g2.drawString(data.getAuthor(), (width - w_author) + w_image + 33, height - 10);
		g2.drawImage(author, (width - w_author) + w_image, height - 30, 25, 25, base);
		super.paintComponent(g2);
	}
	
	Image getAuthorIcon(String name) {
		try {
			return ImageIO.read(new URL("https://minotar.net/avatar/" + name));
		} catch(Exception e) {e.printStackTrace();}
		return null;
	}
}