package com.giordano.game;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;
import com.giordano.engine.gfx.Image;

public abstract class ImageObject extends GameObject {
	
	private String defaultImagePath, imagePath;
	private Image image;
	protected int width, height;
	
	public ImageObject(String tag, double posX, double posY, String defaultImagePath) {
		super(tag, posX, posY);
		this.defaultImagePath = defaultImagePath;
		resetImage();
	}
	
	public void render(GameContainer gc, Renderer r) {
		if (image == null) {
			resetImage();
		}
		r.drawImage(image, (int)Math.round(posX), (int)Math.round(posY));
	}
	
	public void resetImage() {
		setImage(defaultImagePath);
	}
	
	public void setImage(String imagePath) {	//be careful if you want to create a setImage(Image i) because it will reference the same object
		image = new Image(imagePath);
		this.imagePath = imagePath;
		width = image.getW();
		height = image.getH();
	}
	
	public double getCenterX() {
		return posX + width/2;
	}
	public double getCenterY() {
		return posY + height/2;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

}
