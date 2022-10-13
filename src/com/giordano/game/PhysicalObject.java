package com.giordano.game;

import com.giordano.engine.GameContainer;

public abstract class PhysicalObject extends ImageObject {
	
	protected boolean useGravity, transparent, fixed;
	protected double velX, velY;
	protected double mass;
	protected double elasticity, friction;
	
	//for the collision detector
	private int fixation;
	public boolean fixedX = false, fixedY = false;
	public PhysicalObject collidedX = null, collidedY = null;
	
	
	public PhysicalObject(String tag, double posX, double posY, String defaultImagePath, boolean useGravity, boolean transparent, boolean fixed) {
		super(tag, posX, posY, defaultImagePath);
		this.useGravity = useGravity;
		this.transparent = transparent;
		this.fixed = fixed;
		mass = 1;
		elasticity = 0.7;
		friction = 0.3;
	}
	
	public void update(GameContainer gc, GameManager gm, float dt) {
		super.update(gc, gm, dt);
		
		if (useGravity) {
			velY += dt * GRAVITY;
		}
		
		posX += velX;
		posY += velY;
	}
	
	@Override
	public void changeSize(int newWidth, int newHeight, double scaleFromX, double scaleFromY) {
		//cramping prevention
		//also I didn't test this very thoroughly so if you get a bug here then... but I'm like 90% sure it'll work
		//if anywhere you'll get a bug in the gm.cd method but you won't be able to tell where it is coming from
		//just generally try to avoid changing image sizes
		//also you might get a little positive velocity if you try to make an image bigger and it cramps 
		int oldWidth = width, oldHeight = height;
		super.changeSize(newWidth, newHeight, scaleFromX, scaleFromY);
		if (newWidth > oldWidth || newHeight > oldHeight) {
			gm.cd.fixChangeSizeCollision(this);
		}
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
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public double getElasticity() {
		return elasticity;
	}
	public void setElasticity(double elasticity) {
		this.elasticity = elasticity;
	}
	public double getFriction() {
		return friction;
	}
	public void setFriction(double friction) {
		this.friction = friction;
	}
	
}
