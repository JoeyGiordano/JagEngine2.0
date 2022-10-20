package com.giordano.game;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class Player extends GameObject {

	public PhysicalObject ball;
	public double ballVel = 2.0;
	public boolean transitioning = false;
	public int gameOver = -1;
	public int score = 0;
	
	
	public Player(String tag, double posX, double posY) {
		super(tag, posX, posY);
		visualize = true;
		this.setVisualizeColor(Color.green);
		
		ball = new PhysicalObject("playerBall", posX-2, posY+20, 5, 5, false, false, false);
		ball.visualize = true;
		ball.setVisualizeColor(Color.blue);
		ball.velX = ballVel;
		
	}
	
	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		
		if (ball.collisionObjects.length != 1) gameOver += 1;
		if (gameOver == 60) gm.stopGame();
		if (gameOver > 0) return;
		
		if (gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
			posX = (gc.getInput().getMouseX() + Constants.camera.getOffX());
			posY = (gc.getInput().getMouseY() + Constants.camera.getOffY());
			transitioning = true;
		}
		
		if (transitioning) {
			double[] vectorToNewPos = new double[] {posX - ball.posX, posY - ball.posY};
			if (Math.abs(Constants.dotProduct(ball.velX, ball.velY, vectorToNewPos[0], vectorToNewPos[1])) < 1.5 * Math.sqrt(ball.getVelocityMagnitude())) {
				transitioning = false;
			} else return;
		}
		
		double dirToCenter = ball.directionTo(this);
		double vSquaredOverR = Math.pow(ballVel,2) / distanceTo(ball);
		ball.accelerate(vSquaredOverR, dirToCenter, 1);
		
		//missile killing
		if (!transitioning) {
			for (GameObject g : gm.getObjects()) {
				if (!g.tag.contains("missile")) continue;
				Missile m = (Missile)g;
				
				if (distanceTo(m) < distanceTo(ball) && Math.abs(Constants.crossProduct2D(ball.posX-posX, ball.posY-posY, m.posX-posX, m.posY-posY)) < distanceTo(m)
						&& 0 < Constants.dotProduct(ball.posX-posX, ball.posY-posY, m.posX-posX, m.posY-posY)) {
					gm.destroyObject(m);
					score++;
					if (gm.highScore < score) gm.highScore = score;
				}
			}
		} else {
			for (GameObject g : gm.getObjects()) {
				if (!g.tag.contains("missile")) continue;
				((Missile)g).touchedByString = false;
			}
		}
		
		
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawRect((int)Math.round(ball.posX+1), (int)Math.round(ball.posY+1), ball.width-3, ball.height-3, Color.green.getRGB());
		
		r.drawText("Score: " + score, 5+(int)camera.getOffX(), 5+(int)camera.getOffY(), new Color(90,0,240).getRGB());
		
		if (gameOver < 1) {
			if (!transitioning) r.drawLine(getCenterX(), getCenterY(), ball.getCenterX(), ball.getCenterY(), new Color(190, 50, 50).getRGB());
			r.drawCircle((int)Math.round(posX), (int)Math.round(posY), 2, new Color(0,150,0).getRGB());
		}
		
		if (gameOver > 0) r.drawText("Game Over", SCREEN_WIDTH/2 + (int)camera.getOffX() - 20, SCREEN_HEIGHT/2 + (int)camera.getOffY() - 5, Color.RED.getRGB());
	}
	
}
