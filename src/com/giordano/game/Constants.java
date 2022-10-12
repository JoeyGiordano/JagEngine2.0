package com.giordano.game;

public interface Constants {
	
	public final GameManager gm = new GameManager();
	public final Camera camera = new Camera();
	
	//Panel Settings
	public final int width = 320;
	public final int height = 240;
	public final float scale = 3;
	public final String title = "GiordEngine v1.0";
	public final boolean displayFrameInfo = true;
	
	//Game Settings
	public final double CAMERA_SPEED = 10;
	public final double GRAVITY = 9.81;
	public final double WARNING_SPEED = 10;
	public final double MIN_BOUNCE_VEL = 2;
	
}
