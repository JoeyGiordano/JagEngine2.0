package com.giordano.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Image {
	private int w,h;
	private int[] p;
	private String path;
	private boolean alpha = false;
	private int lightBlock = Light.NONE;
	
	public Image(String path) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		w = image.getWidth();
		h = image.getHeight();
		p = image.getRGB(0, 0, w, h, null, 0, w);
		this.path = path;
		
		image.flush();
	}

	public Image(int[] p, int w, int h) {
		this.p = p;
		this.w = w;
		this.h = h;
	}

	public static int scanForColor(Image image, int color) {
		for (int i = 0; i < image.getP().length; i++) {
			if (image.getP()[i] == color) {
				return i;
			}
		}
		return -1;
	}
	
	public static Image mirror(Image image) {
		Image temp = new Image(new int[image.getW() * image.getH()], image.getW(), image.getH());
		
		for (int i = 0; i < image.getP().length; i++) {
			temp.getP()[i] = image.getP()[((i/image.getW()) + 1) * image.getW() - Math.floorMod(i, image.getW()) - 1];
		}
		
		return temp;
	}
	
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
	}

	public String getPath() {
		return path;
	}
	
	public boolean isAlpha() {
		return alpha;
	}

	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}

	public int getLightBlock() {
		return lightBlock;
	}

	public void setLightBlock(int lightBlock) {
		this.lightBlock = lightBlock;
	}
}