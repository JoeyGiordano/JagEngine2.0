package com.giordano.game;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class EmptyGameObject extends GameObject {
	
	public EmptyGameObject(String tag, double posX, double posY) {
		super(tag, posX, posY);
	}
	
	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
	}

}
