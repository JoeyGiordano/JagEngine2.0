package com.giordano.game;

import com.giordano.engine.AbstractGame;
import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class GameManager extends AbstractGame implements Constants {
	//if using eclipse make sure to add the resources folder to the build path (right-click res folder -> Build Path -> Use as source folder
	Player p;
	ImageObject background;
	
	@Override
	public void init(GameContainer gc) {
		
		background = new ImageObject("bkgd",-400,-300,"/bkgd.jpg");
		p = new Player("p", 0, 0);
		camera.setTarget(p.tag);
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		if (p.transitioning) {
			camera.setTarget(p.ball.tag);
		} else {
			camera.setTarget(p.tag);
		}
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		
	}
	
}

