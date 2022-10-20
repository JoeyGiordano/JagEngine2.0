package com.giordano.engine;

import java.util.ArrayList;

import com.giordano.game.CollisionDetector;
import com.giordano.game.Constants;
import com.giordano.game.GameManager;
import com.giordano.game.GameObject;

public abstract class AbstractGame implements Constants {
	
	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<GameObject> destroyObjects = new ArrayList<GameObject>();
	public CollisionDetector cd = new CollisionDetector();
	
	public AbstractGame() {
		
	}
	
	public abstract void update(GameContainer gc, float dt);
	public abstract void render(GameContainer gc, Renderer r);
	public abstract void init(GameContainer gc);
	
	public void Init(GameContainer gc) {
		init(gc);
		
		for (Updateable u : Updateable.updateables) {
			u.init();
		}
	}
	
	public void Update(GameContainer gc, GameManager gm, float dt) {
		update(gc,dt);
		
		camera.update(gm, gc, dt);
		for (GameObject obj : objects) {
			obj.update(gc, gm, dt);
		}
		for (GameObject obj : destroyObjects) {
			objects.remove(obj);
		}
		destroyObjects.clear();
		for (Updateable u : Updateable.updateables) {
			u.update();
		}
	}
	
	public void Render(GameContainer gc, Renderer r) {
		
		camera.render(r);
		for (GameObject obj : objects) {
			obj.render(gc, r);
		}
		
		render(gc,r);
	}
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(gm);
		gc.setWidth(SCREEN_WIDTH);
		gc.setHeight(SCREEN_HEIGHT);
		gc.setScale(scale);
		gc.setTitle(title);
		gc.start();
	}
	
	public GameObject getObject(String tag) {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).getTag().equals(tag)) {
				return objects.get(i);
			}
		}
		System.out.println("getObject could not find a GameObject with tag " + tag);
		return null;
	}
	
	public ArrayList<GameObject> getObjects() {
		return objects;
	}
	
	public void destroyObject(String tag) {
		destroyObject(getObject(tag));
	}
	
	public void destroyObject(GameObject g) {
		destroyObjects.add(g);
		if (camera.getTarget() == g) {
			camera.resetTarget();
		}
	}
	
	public void destroyAllObjects() {
		for (GameObject obj : objects) {
			destroyObject(obj);
		}
	}
	
}
