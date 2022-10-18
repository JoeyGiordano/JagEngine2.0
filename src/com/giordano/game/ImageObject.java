package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;
import com.giordano.engine.gfx.Image;

public abstract class ImageObject extends GameObject {
	
	final boolean noImage;
	
	private String defaultImagePath, imagePath;
	private Image image;
	protected int width;
	protected int height;
	
	protected boolean render = true;
	protected boolean visualize = false;
	protected Color visualizeColor = Color.magenta;
	
	
	public ImageObject(String tag, double posX, double posY, String defaultImagePath) {
		super(tag, posX, posY);
		this.defaultImagePath = defaultImagePath;
		resetImage();
		noImage = false;
	}
	
	public ImageObject(String tag, double posX, double posY, int width, int height) {
		super(tag, posX, posY);
		defaultImagePath = null;
		imagePath = null;
		this.width = width;
		this.height = height;
		noImage = true;
	}
	
	public void render(GameContainer gc, Renderer r) {
		super.render(gc, r);
		
		if (!render) return;
		
		if (noImage && visualize) {
			r.drawRect((int)Math.round(posX), (int)Math.round(posY), width-1, height-1, visualizeColor.getRGB());
			return;
		}
		
		if (image == null) {
			resetImage();
		}
		
		r.drawImage(image, (int)Math.round(posX), (int)Math.round(posY));
		
		if (visualize) r.drawRect((int)Math.round(posX), (int)Math.round(posY), width-1, height-1, visualizeColor.getRGB());	//done twice because it should render above the image
		
	}
	
	public void resetImage() {
		setImage(defaultImagePath);
	}
	
	public void setImage(String imagePath) {	//be careful if you want to create a setImage(Image i) because it will reference the same object
		image = new Image(imagePath);
		this.imagePath = imagePath;
		changeSize(image.getW(), image.getH(), 0, 0);
	}
	
	public void setImage(String imagePath, double scaleFromX, double scaleFromY) {	//be careful if you want to create a setImage(Image i) because it will reference the same object
		image = new Image(imagePath);
		this.imagePath = imagePath;
		changeSize(image.getW(), image.getH(), scaleFromX, scaleFromY);
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
	public String getImagePath() {
		if (imagePath == null) return "";
		return imagePath;
	}
	public boolean isNoImage() {
		return noImage;
	}
	public void setRender(boolean render) {
		this.render = render;
	}
	public void setVisualize(boolean visualize) {
		this.visualize = visualize;
	}
	public void setVisualizeColor(Color visualizeColor) {
		this.visualizeColor = visualizeColor;
	}

	public void changeSize(int newWidth, int newHeight, double scaleFromX, double scaleFromY) {
		//shifts the gameObject when it changes its size so that it appears to have been scaled from (scaleFromX, scaleFromY) where the
		//scaleFrom parameters are relative to the old image (i.e. to scale from top left, input (0,0) for bottom right (1,1) for center (0.5,0.5) 
		int widthDif = newWidth - width;
		int heightDif = newHeight - height;
		posX = posX - scaleFromX * widthDif;
		posY = posY - scaleFromY * heightDif;
		width = newWidth;
		height = newHeight;
	}

}
