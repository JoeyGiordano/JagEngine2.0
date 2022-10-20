package com.giordano.game;

public interface Constants {
	
	public final GameManager gm = new GameManager();
	public final Camera camera = new Camera();
	
	//Panel Settings
	public final int SCREEN_WIDTH = 320;
	public final int SCREEN_HEIGHT = 240;
	public final float scale = 3;
	public final String title = "SwingIT";
	public final boolean displayFrameInfo = false;
	
	//Game Settings
	public final double CAMERA_SPEED = 2;
	public final double GRAVITY = 9.81;
	public final double WARNING_SPEED = 10;
	public final double MIN_BOUNCE_VEL = 0.05;
	
	
	
	//resource methods
	//vector resources
	public static double magnitude(double x, double y) {
		return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
	}
	public static double[] unit(double x, double y) {
		double mag = magnitude(x,y);
		return new double[] {x/mag, y/mag};
	}
	public static double[] normal(double x, double y) {
		return new double[] {-y,x};
	}
	public static double[] unitNormal(double x, double y) {
		double mag = magnitude(x,y);
		return new double[] {-y/mag, x/mag};
	}
	public static double dotProduct(double ax, double ay, double bx, double by) {
		return ax*bx+ay*by;
	}
	public static double crossProduct2D(double ax, double ay, double bx, double by) {
		return ax*by-ay*bx;
	}
	public static double[] vectorProjection(double ax, double ay, double bx, double by) {
		//a onto b
		double d = dotProduct(ax,ay,bx,by);
		double magB = magnitude(bx, by);
		double magAontoB = d/magB;
		double unitBx = bx/magB;
		double unitBy = by/magB;
		
		return new double[] {unitBx * magAontoB, unitBy * magAontoB};
	}
	public static double directionTo(double ax, double ay, double bx, double by) {
		//from a to b in degrees
		return Math.toDegrees(Math.atan2(by-ay, bx-ax));
	}
	
	
}
