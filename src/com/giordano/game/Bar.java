package com.giordano.game;

import java.awt.Color;

import com.giordano.engine.GameContainer;
import com.giordano.engine.Renderer;

public class Bar implements Constants {
	
	int posX, posY;
	double filledness = 0;
	double max;
	int width, height;
	Color fillColor, borderColor;
	
	public Bar(int posX, int posY, int width, int height, int max, Color fillColor, Color borderColor) {
		this.posX = posX;
		this.posY = posY;
		this.max = max;
		this.width = width;
		this.height = height;
		this.fillColor = fillColor;
		this.borderColor = borderColor;
	}
	
	public void render(GameContainer gc, Renderer r) {
		
		r.drawfillRect(posX+1 + (int)(camera.getOffX()), posY+1 + (int)(camera.getOffY()), (int)((width-1)*filledness), height-1, fillColor.getRGB(), false);
		r.drawRect(posX + (int)(camera.getOffX()), posY + (int)(camera.getOffY()), width, height, borderColor.getRGB());
		
		
	}

	public void fill(double amount) {
		filledness += amount/max;
		if (filledness >= 1) filledness=1;
	}
	public void setFill(double amount) {
		filledness = amount/max;
	}
	
	public double getFilledness() {
		return filledness;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
}
