package com.giordano.game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.giordano.engine.AbstractGame;
import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class GameManager extends AbstractGame implements Constants {
	//if using eclipse make sure to add the resources folder to the build path (right-click res folder -> Build Path -> Use as source folder
	Player player;
	ImageObject background;
	
	int totalMissileCount = 0;
	int missileCount = 0;
	int missileTime = 0;
	int missileCountdown = 300;
	
	int coinTime = 0;
	
	int highScore = 0;
	
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
		
		if (player.transitioning) {
			camera.setTarget(player.ball.tag);
		} else {
			camera.setTarget(player.tag);
		}
		
		//missiles
		if (missileTime >= missileCountdown) {
			createMissile((int)(Math.random()*1000-500),(int)(Math.random()*700-350));
			missileTime = 0;
			if (Math.random() < 0.7) missileCountdown -= 10;
			if (missileCountdown < 4) missileCountdown = 4;
		}
		missileTime++;
		
		//coins
		if (coinTime == 150) {
			spawnCoins(2);
			coinTime = 0;
		}
		coinTime++;
		player.ball.posX =0;
		player.ball.posY = 0;
		for (GameObject go : objects) {
			if (go instanceof Missile && go.distanceTo(player.ball) < 30) {
				destroyObject(go);
			}
		}
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		if (gameOver) {
			updateBlink++;
			r.drawText("SwingIT", -20, -10, new Color(150,0,240).getRGB());
			if (updateBlink >= 30) r.drawText("Press Space To Play", -55, 5, new Color(0,120,55).getRGB());
			if (updateBlink == 65) updateBlink = 0;
			r.drawText("High Score: " + highScore, 5+(int)camera.getOffX(), 5+(int)camera.getOffY(), new Color(90,0,240).getRGB());
			return;
		}
		
		double x = (gc.getInput().getMouseX() + camera.getOffX());
		double y = (gc.getInput().getMouseY() + camera.getOffY());
		r.drawCircle((int)Math.round(x), (int)Math.round(y), 2, new Color(0,235,0).getRGB());
	}
	
	public void startGame() {
		background = new ImageObject("bkgd",-400,-255,"/bkgd.jpg");
		background.setZdepth(0);
		background.setVisualize(true);
		background.setVisualizeColor(Color.red);
		player = new Player("player", 0, 0);
		camera.setTarget(player.tag);
		
		totalMissileCount = 0;
		missileCount = 0;
		
		spawnCoins(10);
	}
	
	public void stopGame() {
		gameOver = true;
		destroyAllObjects();
	}
	
	public void createMissile(int posX, int posY) {
		new Missile(totalMissileCount, posX, posY);
		totalMissileCount++;
		missileCount++;
	}
	
	public void createCoin(int posX, int posY) {
		new Coin(posX, posY);
	}
	
	public void spawnCoins(int spawns) {
		for (int i = 0; i < spawns; i++) {
			createCoinCurve((int)(Math.random()*1000-500),(int)(Math.random()*700-350), Math.random() * 100);
		}
	}
	
	public void createCoinCurve(int posX, int posY, double radius) {
		double start = Math.random() * 2*Math.PI+0.2;
		double radiansOfCoins = Math.random() * 2*Math.PI+0.2;
		for (double i = start; i <= radiansOfCoins; i+=20/(radius+5)) {
			createCoin(posX + (int)(radius*Math.cos(i)), posY + (int)(radius*Math.sin(i)));
		}
	}
	
}

