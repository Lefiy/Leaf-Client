package lefiy.mcdev.window.utils;

import java.util.Timer;
import java.util.TimerTask;

public class Animation {
	
	private boolean end;
	
	private float float_value;
	private int int_value;
	
	private int time;
	
	public Animation() {
		this.end = false;
		this.float_value = 1.0F;
	}
	
	public void wait(int wait) {
		
		this.time = wait;
		
		this.startAnimation();
		
		Timer timer = new Timer(false);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(time <= 0) {timer.cancel(); endAnimation(); end = true;}
				time--;
			}
		};
		timer.schedule(task, 0, 20);
	}
	
	public void startUpFloat(float start, int wait) {
		
		this.end = false;
		this.float_value = start;
		this.time = wait;
		
		this.startAnimation();
		
		Timer timer = new Timer(false);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(float_value >= 0.9F) {timer.cancel(); float_value = 1.0F; endAnimation(); end = true;}
				else if(time == 0 && float_value < 1.0F) {float_value = float_value + 0.1F;}
				else {time--;}
			}
		};
		timer.schedule(task, 0, 20);
	}
	
	public void startDownFloat(float start, int wait) {
		
		this.end = false;
		this.float_value = start;
		this.time = wait;
		
		this.startAnimation();
		
		Timer timer = new Timer(false);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(float_value <= 0.1F) {timer.cancel(); float_value = 0.0F; endAnimation(); end = true;}
				else if(time == 0 && float_value > 0.0F) {float_value = float_value - 0.1F;}
				else {time--;}
			}
		};
		timer.schedule(task, 0, 20);
	}
	
	public void startAnimation() {}
	
	public void endAnimation() {}
	
	public boolean isEnd() {
		return this.end;
	}
	
	public float getFloat() {
		return this.float_value;
	}
	
	public int getint() {
		return this.int_value;
	}
}
