package com.giordano.engine;

import com.giordano.game.Constants;

public class GameContainer implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	private Timer timer;

	long startTime = System.nanoTime();
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	private int width = 150, height = 93;
	private float scale = 10f;
	private String title = "GiordEngine v1.0";
	private int color = 0xff00ffff;

	public GameContainer(AbstractGame game) {
		this.game = game;
	}
	
	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		timer = new Timer(this);

		thread = new Thread(this);
		thread.run();
	}

	public void stop() {

	}

	public void run() {

		running = true;

		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;

		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		game.Init(this);
		
		lastTime = System.nanoTime() / 1000000000.0;
		
		while (running) {

			render = false;

			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				game.Update(this, Constants.gm, (float)UPDATE_CAP);

				input.update();
				
				timer.update();

				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					color += 0xff2d1f66;
				}
			}

			if (render) {
				renderer.clear();
				game.Render(this, renderer);
				renderer.process();
				renderer.setCamX(0);
				renderer.setCamY(0);
				if (Constants.displayFrameInfo) renderer.drawText("FPS:" + fps + " Time: " + timer.getMinsSecs(), 0, 0, color);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	public void dispose() {

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public Renderer getRenderer() {
		return renderer;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public double getUPDATE_CAP() {
		return UPDATE_CAP;
	}
}
