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
	Color barColor;
	
	int blinking = 0;
	
	public Bar(int posX, int posY, int width, int height, int max, Color fillColor, Color borderColor) {
		this.posX = posX;
		this.posY = posY;
		this.max = max;
		this.width = width;
		this.height = height;
		this.fillColor = fillColor;
		this.borderColor = borderColor;
		barColor = fillColor;
	}
	
	public void render(GameContainer gc, Renderer r) {
		
		if (filledness == 1) {
			if (blinking > 7) {
				barColor = new Color(160,0,255);
			} if (blinking >= 14) {
				blinking = 0;
				barColor = new Color(255,0,160);
			}
		} else {
			barColor = fillColor;
		}
		blinking++;
		
		r.drawfillRect(posX+1 + (int)(camera.getOffX()), posY+1 + (int)(camera.getOffY()), (int)((width-1)*filledness), height-1, barColor.getRGB(), false);
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
