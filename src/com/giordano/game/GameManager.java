package com.giordano.game;

import com.giordano.engine.AbstractGame;
import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class GameManager extends AbstractGame implements Constants {
	//if using eclipse make sure to add the resources folder to the build path (right-click res folder -> Build Path -> Use as source folder
	
	
	@Override
	public void init(GameContainer gc) {
		Player p = new Player("p", 0, 0);
		
		//camera.setTarget("test1");
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		
	}
	
}

