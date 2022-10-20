package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class Missile extends PhysicalObject {
	
	double facing = 0;
	double speed = 1;
	double timeToEscapeBy = 0;
	boolean touchedByString = false;
	
	public Missile(int missileID, double posX, double posY) {
		super("missile" + missileID, posX, posY, 6, 6, false, false, false);
	}
	
	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		super.update(gc, gm, dt);
		facing = directionTo(gm.player.ball);
		
		velX = speed * Math.cos(Math.toRadians(facing));
		velY = speed * Math.sin(Math.toRadians(facing));
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		super.render(gc, r);
		r.drawCircle((int)Math.round(posX), (int)Math.round(posY), 3, Color.red.getRGB());
		r.setPixel((int)(3*Math.cos(Math.toRadians(facing))) + (int)Math.round(posX), (int)(3*Math.sin(Math.toRadians(facing))) + (int)Math.round(posY), Color.black.getRGB());
	}
	

}
