package lefiy.mcdev.window.components;

import javax.swing.JComponent;

public class ButtonGroup extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private SelectButton[] group;
	
	public ButtonGroup(SelectButton... group) {
		this.group = group;
	}
	
	public void reset(SelectButton enable) {
		for(SelectButton button : group) {
			if(button.equals(enable)) {
				button.setToggle(true);
			} else {
				button.setToggle(false);
			}
		}
	}
	
	public SelectButton[] getGroup() {
		return this.group;
	}
}