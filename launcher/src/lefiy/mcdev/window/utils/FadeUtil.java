package lefiy.mcdev.window.utils;

import java.util.Timer;
import java.util.TimerTask;

public class FadeUtil {
	
	private int value;
	
	private boolean fadeOut;
	
	public FadeUtil() {
		this.value = 0;
		this.fadeOut = false;
	}
	
	public void fadeIn() {
		
		Timer timer = new Timer(false);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(value < 60 && !fadeOut) {
					value++;
				} else {
					cancel();
					return;
				}
				doThings();
			}
		};
		timer.schedule(task, 0, 3);
	}
	
	public void fadeOut() {
		
		fadeOut = true;
		
		Timer timer = new Timer(false);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(value > 0) {
					value--;
				} else {
					fadeOut = false;
					cancel();
					return;
				}
				doThings();
			}
		};
		timer.schedule(task, 0, 3);
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void doThings() {};
}