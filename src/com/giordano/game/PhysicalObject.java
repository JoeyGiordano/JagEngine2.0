package com.giordano.game;

import com.giordano.engine.GameContainer;

public abstract class PhysicalObject extends ImageObject {
	
	private boolean useGravity, transparent, fixed;
	private double velX, velY;
	private int fixation;
	public boolean collidedX = false, collidedY = false;
	
	public PhysicalObject(String tag, double posX, double posY, String defaultImagePath, boolean useGravity, boolean transparent, boolean fixed) {
		super(tag, posX, posY, defaultImagePath);
		this.useGravity = useGravity;
		this.transparent = transparent;
		this.fixed = fixed;
	}
	
	public void update(GameContainer gc, GameManager gm, float dt) {
		super.update(gc, gm, dt);
		
		if (useGravity) {
			velY += dt * GRAVITY;
		}
		
		posX += velX;
		posY += velY;
	}
	
	public boolean isUseGravity() {
		return useGravity;
	}
	public void setUseGravity(boolean useGravity) {
		this.useGravity = useGravity;
	}
	public boolean isTransparent() {
		return transparent;
	}
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}
	public boolean isFixed() {
		return fixed;
	}
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	public int getFixation() {
		return fixation;
	}
	public void setFixation(int fixation) {
		this.fixation = fixation;
	}
	public double getVelX() {
		return velX;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public double getVelY() {
		return velY;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	public double[] getVelXY() {
		return new double[] {velX, velY};
	}
	public void setVelXY(double velX, double velY) {
		this.velX = velX;
		this.velY = velY;
	}
	public double getSquaredVelocityMagnitude() {
		return Math.pow(velX, 2) + Math.pow(velY, 2);
	}
	public double getVelocityMagnitude() {
		return Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
	}
	public double getVelocityDirection() {
		return Math.atan2(velY, velX);
	}
	public double[] getVelocityVector() {
		return new double[] {velX, velY};
	}
	
}
