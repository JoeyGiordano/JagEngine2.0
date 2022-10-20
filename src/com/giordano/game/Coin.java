package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.GameContainer;

public class Coin extends ImageObject {

	boolean blue = true;
	int updateCount = 0;
	
	public Coin(double posX, double posY) {
		super("coin", posX, posY, 2, 2);
		visualize = true;
		visualizeColor = Color.blue;
		
		if (Math.abs(posX) > 400 || Math.abs(posX) > 255) {
			gm.destroyObject(this);
		}
	}
	
	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		super.update(gc, gm, dt);
		if (updateCount == 8) {
			if (blue) {visualizeColor = Color.green; blue = false;}
			else {visualizeColor = Color.blue; blue = true;}
			updateCount = 0;
		}
		updateCount++;
		
		if (collisionObjects.length > 1) {
			gm.destroyObject(this);
		}
		
	}

}
