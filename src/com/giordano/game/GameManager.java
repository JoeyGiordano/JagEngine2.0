package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.AbstractGame;
import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class GameManager extends AbstractGame implements Constants {
	//if using eclipse make sure to add the resources folder to the build path (right-click res folder -> Build Path -> Use as source folder
	TestObject object, object2, object3, object4, platform, red;
	
	@Override
	public void init(GameContainer gc) {
		
		//all
		//*
		object = new TestObject("test", 0, 0, 9,9, true, false, false);
		object2 = new TestObject("test1", 110, 90, "/testStageBlock2.png", true, false, false);
		object3 = new TestObject("test2", -90, 90, "/testStageBlock2.png", true, false, false);
		platform = new TestObject("platform", -100, 100, "/fonts/zapfino.png", false, false, true);
		object.setVelXY(1, -3);
		object2.setVelXY(-1, -1);
		object3.setVelXY(2, -2);
		platform.visualize = true;
		object.visualize = true;
		object.setVisualizeColor(Color.GREEN);
		//object2.render = false;
		//*/
		
		//bounce into slide into collide
		/*
		object = new TestObject("test", -90, 90, "/testStageBlock.png", true, false, false);
		object2 = new TestObject("test1", 90, 90, "/testStageBlock2.png", true, false, false);
		//object3 = new TestObject("test2", -90, 90, "/testStageBlock2.png", true, false, false);
		platform = new TestObject("platform", -100, 100, "/fonts/zapfino.png", false, false, true);
		object.setVelXY(2, 0);
		object2.setVelXY(-1, -3);
		//object3.setVelXY(2, -2);
		//*/
		
		//sliding left right collision
		/*
		object = new TestObject("test", 0, 90, "/testStageBlock.png", true, false, false);
		object2 = new TestObject("test1", 90, 80, "/testStageBlock2.png", true, false, false);
		//object3 = new TestObject("test2", -10, 95, "/testStageBlock2.png", false, false, true);
		platform = new TestObject("platform", -100, 100, "/fonts/zapfino.png", false, false, true);
		object.setVelX(1);
		object2.setVelX(-2);
		//object3.elasticity = 0;
		platform.elasticity = 0.7;
		//*/
		
		//vertical squish different speeds
		/*
		object = new TestObject("test", -30, 0, "/testStageBlock.png", true, false, false);
		object2 = new TestObject("test1", -35, 90, "/testStageBlock2.png", true, false, false);
		platform = new TestObject("platform", -100, 100, "/fonts/zapfino.png", false, false, true);
		object.setVelX(0.1);
		object2.setVelX(0.2);
		//*/
		
		//moving vertical squish
		/*
		object = new TestObject("test", 0, 0, "/testStageBlock.png", true, false, false);
		object2 = new TestObject("test1", 2, 90, "/testStageBlock2.png", true, false, false);
		platform = new TestObject("platform", -100, 100, "/fonts/zapfino.png", false, false, true);
		int v = 1;
		object.setVelX(v);
		object2.setVelX(v);
		//*/
		
		//vertical squish
		/*
		object = new TestObject("test", 0, 0, "/testStageBlock.png", true, false, false);
		object2 = new TestObject("test1", 0, 90, "/testStageBlock2.png", true, false, false);
		object3 = new TestObject("test2", 0, -10, "/testStageBlock2.png", true, false, false);
		object4 = new TestObject("test3", 0, -50, "/testStageBlock2.png", true, false, false);
		platform = new TestObject("platform", -100, 100, "/fonts/zapfino.png", false, false, true);
		//*/
		
		//falling on platform
		/*
		object = new TestObject("test", 0, 0, "/testStageBlock.png", true, false, false);
		platform = new TestObject("platform", -100, 100, "/fonts/zapfino.png", false, false, true);
		object.setVelY(-3);
		object.setVelX(1);
		//*/
		
		//left right collision
		/*
		object = new TestObject("test", 0, 90, "/testStageBlock.png", false, false, false);
		object2 = new TestObject("test1", 90, 90, "/testStageBlock2.png", false, false, false);
		object.setVelX(1);
		object2.setVelX(-1);
		//*/
		
		//up down collision
		/*
		object = new TestObject("test", 90, 0, "/testStageBlock.png", false, false, false);
		object2 = new TestObject("test1", 90, 90, "/testStageBlock2.png", false, false, false);
		object.setVelY(1);
		object2.setVelY(-1);
		//*/
		
		//corner collision
		/*
		object = new TestObject("test", 0, 0, "/testStageBlock.png", false, false, false);
		object2 = new TestObject("test1", 90, 90, "/testStageBlock2.png", false, false, false);
		object.setVelY(1);
		object2.setVelY(-1);
		object.setVelX(1);
		object2.setVelX(-1);
		//*/
		
		//camera.setTarget("test1");
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		
	}
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		
	}
	
}

