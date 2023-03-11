package lefiy.mcdev.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import lefiy.mcdev.window.components.ButtonGroup;
import lefiy.mcdev.window.components.LaunchButton;
import lefiy.mcdev.window.components.NewsPanel;
import lefiy.mcdev.window.components.SelectButton;
import lefiy.mcdev.window.components.SystemButton;
import lefiy.mcdev.window.components.TextBox;
import lefiy.mcdev.window.utils.Animation;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected Animation animation;
	
	private List<Component> comps;
	private int offx, offy;
	
	private Image image;
	
	public Screen(String back, int offx, int offy, List<Component> comps) {
		this.animation = new Animation();
		this.offx = offx;
		this.offy = offy;
		if(back != null) this.image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/" + back));
		if(comps != null) {
			ArrayList<Component> list = new ArrayList<>();
			for(Component comp : comps) { if(comp instanceof ButtonGroup) {
				for(SelectButton button : ((ButtonGroup)comp).getGroup()) {
					list.add(button);
					this.add(button);
					button.setGroup((ButtonGroup)comp);
				}
			} else {
				list.add(comp);
				this.add(comp);
			}}
			this.comps = list;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		if(comps != null && !animation.isEnd()) {
			for(Component comp : comps) {
				
				if(comp instanceof NewsPanel) {
					comp.setForeground(new Color(1, 1, 1, animation.getFloat()));
				} else {
					comp.setForeground(new Color(0, 0, 0, animation.getFloat()));
				}
				
				if(comp instanceof SelectButton) {
					((SelectButton)comp).setImageAlpha(animation.getFloat());
				} else if(comp instanceof TextBox) {
					((TextBox)comp).setImageAlpha(animation.getFloat());
				} else if(comp instanceof LaunchButton) {
					((LaunchButton)comp).setImageAlpha(animation.getFloat());
				} else if(comp instanceof SystemButton) {
					((SystemButton)comp).setImageAlpha(animation.getFloat());
				}
				repaint();
			}
		}
		
		if(image != null) {
			g.drawImage(image, 0, 0, offx, offy, this);
		}
	}
	
	public void doThings() {}
	public void endScreen() {}
}