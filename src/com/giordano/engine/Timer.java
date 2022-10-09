package com.giordano.engine;

public class Timer {
	
	GameContainer gc;
	long startTime = System.nanoTime();
	double rTime;	//real time
	double sTime;	//short time
	
	public Timer(GameContainer gc) {
		this.gc = gc;
	}
	
	public void update() {
		
		rTime = System.nanoTime() - startTime;
		rTime *= (double)Math.pow(10, -9);
		sTime = (double)((int)(rTime * 1000)) / 1000;
		
	}

	public double getsTime(int digitsAfterDecimal) {
		
		double time;
		
		if (digitsAfterDecimal > 8) return rTime;
		if (digitsAfterDecimal < 0) return sTime;
		
		int pow10 = (int)Math.pow(10, digitsAfterDecimal);
		time = (double)((int)(rTime * pow10)) / pow10;
		
		return time;
		
	}
	
	public double getrTime() {
		return rTime;
	}

	public double getsTime() {
		return sTime;
	}
	
	public double getSecs() {
		return (double)((int)((sTime % 60) * 1000)) / 1000;
	}
	
	public int getMins() {
		return (int)(sTime / 60);
	}
	
	public int getHours() {
		return (int)(getMins() / 60);
	}
	
	public String getMinsSecs() {
		return Integer.toString(getMins()) + ":" + Double.toString(getSecs());
	}
	
	public String getHoursMinsSecs() {
		return Integer.toString(getHours()) + ":" + Integer.toString(getMins()) + ":" + Double.toString(getSecs());
	}
	
}
