package com.giordano.game;

public class TestObject extends PhysicalObject {
	
	public TestObject(String tag, double posX, double posY, String defaultImagePath, boolean gravity, boolean transparent, boolean fixed) {
		super(tag, posX, posY, defaultImagePath, gravity, transparent, fixed);
	}
	
	public TestObject(String tag, double posX, double posY, int width, int height, boolean gravity, boolean transparent, boolean fixed) {
		super(tag, posX, posY, width, height, gravity, transparent, fixed,1,0.7,0.7);
	}
	
}
