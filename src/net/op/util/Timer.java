package net.op.util;

public class Timer {

	/**
	 * The fps cap.
	 * 
	 * Unlimited: 0
	 */
	private int fpsCap = 0;
	
	private Runnable onTick;
	
	public Timer() {
	}
	
	public Timer(int fpsCap) {
		this.fpsCap = fpsCap;
	}
	
	public void setFPSCap(int fpsCap) {
		this.fpsCap = fpsCap;
	}
	
	public void onTick(Runnable lambda) {
		this.onTick = lambda;
	}
	
	public void tick() {
		if (fpsCap <= 0) {
			onTick.run();
			return;
		}
		
		// TODO: Implement code
	}
	
	
}
