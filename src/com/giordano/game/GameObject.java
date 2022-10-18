package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public abstract class GameObject implements Constants {
	
	//the superclass for all things that will exist in the game
	//the update and render are defined empty here, but subclasses can override them to add functionalities
	//subclasses of subclasses that override the update and render methods should call super.update() so functionalities are not lost
	
	private String tag;
	protected double posX, posY;
	protected GameObject[] collisionObjects;
	private boolean destroyed = false;
	protected boolean visualize = false;
	protected Color visualizeColor = Color.magenta;
	
	public GameObject(String tag, double posX, double posY) {
		this.tag = tag;
		this.posX = posX;
		this.posY = posY;
		gm.getObjects().add(this);
		collisionObjects = new GameObject[] {};
	}
	
	public void update(GameContainer gc, GameManager gm, float dt) {
		
	}
	
	public void render(GameContainer gc, Renderer r) {
		if (visualize) r.setPixel((int)Math.round(getCenterX()), (int)Math.round(getCenterY()), visualizeColor.getRGB());
	}
	
	public void destroy() {
		destroyed = true;
		gm.getObjects().remove(this);
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public void changePosXBy(double dx) {
		posX += dx;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public void changePosYBy(double dy) {
		posY += dy;
	}
	public double[] getPosXY() {
		return new double[] {posX, posY};
	}
	public void setPosXY(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}
	public void changePosXYBy(double dx, double dy) {
		posX += dx;
		posY += dy;
	}
	public double getCenterX() {
		return posX;
	}
	public double getCenterY() {
		return posY;
	}
	public GameObject[] getCollisionObjects() {
		return collisionObjects;
	}
	public void setCollisionObjects(GameObject[] collisionObjects) {
		this.collisionObjects = collisionObjects;
	}
	
}
