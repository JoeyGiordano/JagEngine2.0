package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class Missile extends ImageObject {
	
	double facing = 0;
	
	public Missile(int missileID, double posX, double posY) {
		super("missile" + missileID, posX, posY, 6, 6);
	}
	
	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		facing = directionTo(gm.player.ball);
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawCircle((int)Math.round(posX), (int)Math.round(posY), 3, Color.red.getRGB());
		r.setPixel((int)(3*Math.cos(Math.toRadians(facing))) + (int)Math.round(posX), (int)(3*Math.sin(Math.toRadians(facing))) + (int)Math.round(posY), Color.black.getRGB());
	}
	

}
