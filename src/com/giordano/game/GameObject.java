package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public abstract class GameObject implements Constants {
	
	//the superclass for all things that will exist in the game
	//the update and render are defined empty here, but subclasses can override them to add functionalities
	//subclasses of subclasses that override the update and render methods should call super.update() so functionalities are not lost
	
	protected String tag;
	protected double posX, posY;
	protected GameObject[] collisionObjects;
	private boolean destroyed = false;
	protected boolean visualize = false;
	protected Color visualizeColor = Color.magenta;
	private int zDepth = 1;
	
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
		r.setzDepth(zDepth);
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
	public void setVisualize(boolean visualize) {
		this.visualize = visualize;
	}
	public void setVisualizeColor(Color visualizeColor) {
		this.visualizeColor = visualizeColor;
	}
	public double getZdepth() {
		return zDepth;
	}
	public void setZdepth(int zDepth) {
		this.zDepth = zDepth;
	}
	
	public double xDistanceTo(GameObject go) {
		return go.getCenterX() - getCenterX();
	}
	public double yDistanceTo(GameObject go) {
		return go.getCenterY() - getCenterY();
	}
	public double squareDistanceTo(GameObject go) {
		return Math.pow(xDistanceTo(go),2) + Math.pow(yDistanceTo(go),2); 
	}
	public double distanceTo(GameObject go) {
		return Math.sqrt(squareDistanceTo(go));
	}
	public double directionTo(GameObject go) {
		//in degrees
		return Math.toDegrees(Math.atan2(yDistanceTo(go), xDistanceTo(go)));
	}
	
}
