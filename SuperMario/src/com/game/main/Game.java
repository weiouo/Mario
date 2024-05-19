package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.game.graph.Window;
import com.game.obj.util.Handler;

public class Game extends Canvas implements Runnable {
	
	//Game Constants
	private static final int M_SEC = 1000;
	private static final int NANO_SEC = 1000000000;
	private static final double SEC_PER_MIN = 60.0;
	
	
	//declare constants for windows
	private static final String GAME_NAME = "Super Mario Game";
	private static final int WIN_H = 700;
	private static final int WIN_W = 1000;
	//Game Variables
	
	private boolean running;
	
	//Game Components
	private Thread thread;
	private Handler handler;
	
	
	public Game()
	{
		init();
	}
	
	
	private void init()
	{
		handler = new Handler();
		new Window(WIN_W, WIN_H, GAME_NAME, this);
		start();
	}
	
	private synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
		
	}
	
	private synchronized void stop()
	{
		try 
		{
			thread.join();
			running = false;
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	
	@Override
	public void run() 
	{
		double ns = NANO_SEC/SEC_PER_MIN;
		
		long lastT = System.nanoTime();
		long timer = System.currentTimeMillis();//current time in milli secs 
		double delta = 0; // time tracker
		int frame = 0; // number of frames
		int update = 0; //counter for number of frames
		
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastT) / ns; // variable, delta = (0,1)
			lastT = now;
			
			while(delta >= 1)
			{
				tick(); // time function for updating the game
				update++;
				delta--;
			}
			if(running)
			{
				render();
				frame++;
			}
			if(System.currentTimeMillis() - timer > M_SEC)
			{
				timer += M_SEC;
				//print per second and tick per second
				System.out.println("FPS : "+frame+" TPS : "+update);
				update = 0;
				frame = 0;
			}
		}
		stop();
		
	}
	private void tick()
	{
		handler.tick();
	}
	private void render()
	{
		BufferStrategy buf = this.getBufferStrategy();
		if(buf == null)
		{
			this.createBufferStrategy(5); // use buffer to generate 3 frames
			return;
		}
		
		//draw bkground, bricks, pipes......
		Graphics gf = buf.getDrawGraphics();
		gf.setColor(Color.BLUE);
		gf.fillRect(0, 0, WIN_W, WIN_H);
		
		handler.render(gf);
		
		gf.dispose();
		buf.show();
	}
	
	public static int getWindow_W()
	{
		return WIN_W;
	}
	
	public static int getWindow_H()
	{
		return WIN_H;
	}
	
	//main function
	public static void main(String[] args)
	{
		new Game();
	}
	
	
}
