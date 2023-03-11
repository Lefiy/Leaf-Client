package lefiy.mcdev.window.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import lefiy.mcdev.Main;

public class ScrollBar extends JScrollBar implements AdjustmentListener {
	
	private static final long serialVersionUID = 1L;

	public ScrollBar(int x, int y, int w, int h, String ram) {
		setOrientation(JScrollBar.HORIZONTAL);
		setUI(new CustomUI());
		setPreferredSize(new Dimension(8, 8));
		setBackground(new Color(40, 38, 40));
		setFont(new Font(Font.SANS_SERIF, 1, 14));
		setBounds(x, y, w, h);
		int value = (Integer.parseInt(ram) - 1) * 10;
		setValue(value);
		addAdjustmentListener(this);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		this.doThings(getValue());
	}
	
	private class CustomUI extends BasicScrollBarUI {
		
		@Override
		protected JButton createIncreaseButton(int orientation) {
			return new CustomButtom();
		}
		
		@Override
		protected JButton createDecreaseButton(int orientation) {
			return new CustomButtom();
		}
		
		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			int x = 0;
			int y = trackBounds.y + (trackBounds.height / 2) - 10;
			int width = trackBounds.width;
			int height = 10;
			g2.setColor(new Color(240, 240, 240));
			g2.fillRoundRect(x, y, width, height, 10, 10);
		}
		
		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			int x = thumbBounds.x;
			int y = thumbBounds.y + (thumbBounds.height / 2) - 15;
			int width = thumbBounds.width;
			int height = 20;
			g2.setColor(new Color(240, 240, 240));
			String ram = Main.getMain().ram + " G";
			int w = g2.getFontMetrics().stringWidth(ram);
			g2.drawString(ram, x + (width / 2) - (w / 2), 15);
			g2.setColor(new Color(50, 170, 0));
			g2.fillRoundRect(x, y, width, height, 10, 10);
		}
		
		private class CustomButtom extends JButton {
			
			private static final long serialVersionUID = 1L;

			public CustomButtom() {
				setBorder(BorderFactory.createEmptyBorder());
			}
			
			@Override
			public void paint(Graphics g) {
			}
		}
	}
	
	public void doThings(int value) {}
}