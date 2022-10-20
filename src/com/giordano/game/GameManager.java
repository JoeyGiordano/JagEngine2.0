package com.giordano.game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.giordano.engine.AbstractGame;
import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class GameManager extends AbstractGame implements Constants {
	//if using eclipse make sure to add the resources folder to the build path (right-click res folder -> Build Path -> Use as source folder
	Player p;
	ImageObject background;
	
	public boolean gameOver = true;
	int updateBlink = 0;
	
	@Override
	public void init(GameContainer gc) {
		
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		if (gameOver) {
			if (gc.getInput().isKeyDown(KeyEvent.VK_SPACE)) {
				gameOver = false;
				startGame();
			}
			return;
		}
		
		if (p.transitioning) {
			camera.setTarget(p.ball.tag);
		} else {
			camera.setTarget(p.tag);
		}
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		if (gameOver) {
			updateBlink++;
			r.drawText("SwingIT", -20, -10, new Color(150,0,240).getRGB());
			if (updateBlink >= 30) r.drawText("Press Space To Play", -55, 5, new Color(0,120,55).getRGB());
			if (updateBlink == 65) updateBlink = 0;
			return;
		}
		
		double x = (gc.getInput().getMouseX() + camera.getOffX());
		double y = (gc.getInput().getMouseY() + camera.getOffY());
		r.drawCircle((int)Math.round(x), (int)Math.round(y), 2, new Color(0,235,0).getRGB());
	}
	
	public void startGame() {
		background = new ImageObject("bkgd",-400,-300,"/bkgd.jpg");
		background.setZdepth(0);
		p = new Player("p", 0, 0);
		camera.setTarget(p.tag);
	}
	
	public void stopGame() {
		gameOver = true;
		destroyAllObjects();
	}
	
}

