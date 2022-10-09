package com.giordano.engine.gfx;

public class ImageTile extends Image {
	
	private int tileW, tileH;
	
	public ImageTile(String path, int tileW, int tileH) {
		super(path);
		this.tileW = tileW;
		this.tileH = tileH;
	}

	public Image getTileImage(int tileX, int tileY) {
		
		int[] p = new int[tileW * tileH];
		
		for (int y = 0; y < tileH; y++) {
			for (int x = 0; x < tileW; x++) {
				p[x + y * tileW] = this.getP()[(x + tileX * tileW) + (y + tileY * tileH) * this.getW()];
			}
		}
		
		return new Image(p, tileW, tileH);
	}
	
	public static ImageTile mirror(ImageTile image) {
		ImageTile temp = new ImageTile(image.getPath(), image.tileW, image.tileH);
		Image[][] tiles = new Image[image.getH()/image.getTileH()][image.getW()/image.getTileW()];
		
		for (int i = 0; i < image.getH()/image.getTileH(); i++) {
			for (int j = 0; j < image.getW()/image.getTileW(); j++) {
				tiles[i][j] = Image.mirror(image.getTileImage(j, i));
				
				for (int k = 0; k < image.getTileH(); k++) {
					for (int l = 0; l < image.getTileW(); l++) {
						temp.getP()[i * image.getTileH() * image.getW() + j * image.getTileW() + k * image.getW() + l] =
								tiles[i][j].getP()[k * image.getTileW() + l];
					}
				}
			}
		}
		
		return temp;
	}
	
	public int getTileW() {
		return tileW;
	}

	public void setTileW(int tileW) {
		this.tileW = tileW;
	}

	public int getTileH() {
		return tileH;
	}

	public void setTileH(int tileH) {
		this.tileH = tileH;
	}
}
