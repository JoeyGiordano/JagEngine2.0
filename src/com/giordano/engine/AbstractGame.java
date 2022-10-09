package com.giordano.engine;

import java.util.ArrayList;

import com.giordano.game.CollisionDetector;
import com.giordano.game.Constants;
import com.giordano.game.GameManager;
import com.giordano.game.GameObject;

public abstract class AbstractGame implements Constants {
	
	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	protected CollisionDetector cd = new CollisionDetector();
	
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
			obj.Update(gc, gm, dt);
		}
		for (Updateable u : Updateable.updateables) {
			u.update();
		}
	}
	
	public void Render(GameContainer gc, Renderer r) {
		render(gc,r);
		
		camera.render(r);
		for (GameObject obj : objects) {
			obj.render(gc, r);
		}
	}
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(gm);
		gc.setWidth(width);
		gc.setHeight(height);
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
		System.out.println("getObject could not find a GameObject with given tag");
		return null;
	}
	
	public ArrayList<GameObject> getObjects() {
		return objects;
	}
	
}
