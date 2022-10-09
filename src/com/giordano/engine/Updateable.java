package com.giordano.engine;

import java.util.ArrayList;

import com.giordano.game.EmptyGameObject;
import com.giordano.game.GameObject;
import com.giordano.game.ImageObject;
import com.giordano.game.PhysicalObject;

public interface Updateable {
	//this is used for everything that needs update EXCEPT GAMEOBJECTS
	
	public static ArrayList<Updateable> updateables = new ArrayList<Updateable>();
	
	public void init();
	public void update();
	
	public static String getGOType(GameObject go) {
		String temp = go.getClass().toString();
		int lastIndex = temp.lastIndexOf('.');
		return temp.substring(lastIndex+1, temp.length());
	}
	
	public static boolean instanceOf(GameObject go, String className) {
		switch (className) {
		case "GameObject": 
			return go instanceof GameObject;
		case "EmptyObject": 
			return go instanceof EmptyGameObject;
		case "ImageObject": 
			return go instanceof ImageObject;
		case "PhysicalObject": 
			return go instanceof PhysicalObject;
		default:
			System.out.println("instanceof className not recognized");
			return false;
		}
	}
	
}
