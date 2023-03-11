package lefiy.mcdev.window.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import lefiy.mcdev.Main;

public class SelectBar extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private int per_w, hover_x, cover, selected;
	
	private List<Integer> list;
	
	public SelectBar(List<Integer> list, int x, int y, int w, int h, int select) {
		this.list = list;
		this.per_w = w / list.size();
		this.selected = select;
		this.cover = 0;
		this.hover_x = 0;
		setBounds(x, y, w, h);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int mouseX = e.getX();
				for(int num : list) {
					int width = list.indexOf(num) * per_w;
					if(mouseX >= width && mouseX <= width + per_w) {
						cover = num;
						break;
					}
				}
				super.mouseMoved(e);
			}
		});
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				repaint();
				super.mouseEntered(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				cover = 0;
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
				int mouseX = e.getX();
				for(int num : list) {
					int width = list.indexOf(num) * per_w;
					if(mouseX >= width && mouseX <= width + per_w) {
						selected = num;
						onClick();
						break;
					}
				}
				repaint();
				super.mouseReleased(e);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int height = getHeight();
		int alpha = getForeground().getAlpha();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		for(int num : list) {
			int width = list.indexOf(num) * per_w;
			if(selected == num) {
				GradientPaint gra = new GradientPaint(0, 0, new Color(50, 170, 0, alpha), width * 2, 0, new Color(0, 170, 90, alpha));
				g2.setPaint(gra);
				g2.fillRoundRect(hover_x, 0, per_w, height, 20, 20);
			}
			if(cover == num) {
				g2.setColor(new Color(0, 0, 0, 50));
				g2.fillRoundRect(width, 0, per_w, height, 20, 20);
			}
		}
		g2.setColor(new Color(0, 0, 0, alpha));
		g2.setFont(new Font(Font.SANS_SERIF, 1, 20));
		for(int num : list) {
			String str = getText(num);
			int width = list.indexOf(num) * per_w;
			int f_width = g2.getFontMetrics().stringWidth(getText(num)) + 10;
			g2.drawString(str, width + ((per_w / 2) - (f_width / 2)), 32);
		}
		if(cover != 0) {repaint();}
		//if(!cover.equals("")) {repaint();}
		//super.paintComponent(g);
	}
	
	public void start(int s, int e) {
		int start = list.indexOf(s) * per_w;
		int end = list.indexOf(e) * per_w;
		
		boolean isPlus = start <= end;
		int limit = Math.abs(start - end);
		if(isPlus) {hover_x = start + limit;}
		else {hover_x = start - limit;}
	}
	
	public void onClick() {};
	
	public void doThings(int start, int end) {
		this.start(start, end);
	}
	
	public int getSelected() {
		return this.selected;
	}
	
	public String getText(int num) {
		if(Main.getMain().sences.isEmpty()) return "Loading";
		return Main.getMain().sences.get(num);
	}
}