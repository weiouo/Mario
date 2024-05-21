package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.game.graph.Window;
import com.game.graph.gui.Launcher;
import com.game.obj.Block;
import com.game.obj.Goomba;
import com.game.obj.Pipe;
import com.game.obj.Player;
import com.game.obj.util.Handler;
import com.game.obj.util.KeyInput;
import com.game.obj.util.MouseInput;

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
	private boolean playing;
	private boolean gameOver;
	
	//Game Components
	private Thread thread;
	private Handler handler;
	private Launcher launcher;
	private MouseInput mouseInput;
	
	
	public Game()
	{
		init();
	}
	
	
	private void init()
	{
		handler = new Handler();
		launcher = new Launcher(this);
		mouseInput = new MouseInput(launcher);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
		
		//temporary code - yellow for player / blue for enemy
		handler.setPlayer(new Player(32,32,1,handler));
		handler.addGoomba(new Goomba(32*20,32*14,1,handler));
		for (int i=0;i<20;i++) {
			handler.addObj(new Block(i*32,320,32,32,1,handler));
		}
		handler.addObj(new Block(32*16,32*14,32,32,1,handler));
		handler.addObj(new Block(32*26,32*14,32,32,1,handler));
		for (int i=0;i<30;i++) {
			handler.addObj(new Pipe(i*32,32*15,32,32,1,false,handler));
		}
		
		new Window(WIN_W, WIN_H, GAME_NAME, this);
		start();
	}
	
	private synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
		playing = false;
		
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
		if(playing)handler.tick();
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
		gf.setColor(Color.BLACK);
		gf.fillRect(0, 0, WIN_W, WIN_H);
		
		if(playing)handler.render(gf);
		else if (!playing)launcher.render(gf);
		
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

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	//main function
	public static void main(String[] args)
	{
		new Game();
	}
	
	
}
