package com.giordano.game;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class Camera {
	
	private float offX, offY;
	
	final double CAMERA_SPEED = Constants.CAMERA_SPEED;
	
	private String targetTag;
	private GameObject target;
	private GameObject defaultTarget;
	
	public Camera(String tag) {
		setTarget(tag);
		cutToTarget();
	}
	
	public Camera(int x, int y) {
		defaultTarget = new EmptyGameObject("DefaultCameraTarget", x, y);
		setTarget(defaultTarget);
		cutToTarget();
	}
	
	public Camera() {
		defaultTarget = new EmptyGameObject("DefaultCameraTarget", 0, 0);
		setTarget(defaultTarget);
		cutToTarget();
	}
	
	public void update(GameManager gm, GameContainer gc, float dt){
		
		if (target == null) {
			target = gm.getObject(targetTag);
		}
		
		if (target == null) {
			offX = 0;
			offY = 0;
			//System.out.println("NO TARGET");
			return;
		}
		
		double targetX = target.getCenterX() - Constants.width/2;
		double targetY = target.getCenterY() - Constants.height/2;
		

		offX -= dt * (offX - targetX) * CAMERA_SPEED;
		offY -= dt * (offY - targetY) * CAMERA_SPEED;
		
	}
	
	public void render(Renderer r){
		r.setCamX((int)offX);
		r.setCamY((int)offY);
	}
	
	public void cutToTarget() {
		offX = (float)target.getCenterX() - Constants.width/2;
		offY = (float)target.getCenterY() - Constants.height/2;
	}
	
	public GameObject getTarget() {
		return target;
	}

	public void setTarget(GameObject target) {
		this.target = target;
		targetTag = target.getTag();
	}
	
	public void setTarget(String targetTag) {
		target = null;
		this.targetTag = targetTag;
	}

	public float getOffX() {
		return offX;
	}

	public float getOffY() {
		return offY;
	}
	
}
