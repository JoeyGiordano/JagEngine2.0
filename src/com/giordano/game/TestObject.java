package com.giordano.game;

import com.giordano.engine.GameContainer;

public class TestObject extends PhysicalObject {
	
	public TestObject(String tag, double posX, double posY, String defaultImagePath, boolean gravity, boolean transparent, boolean fixed) {
		super(tag, posX, posY, defaultImagePath, gravity, transparent, fixed);
	}
	
	@Override
	public void update1(GameContainer gc, GameManager gm, float dt) {
		
	}

	
}
