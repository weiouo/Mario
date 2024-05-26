package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.game.graph.Camera;
import com.game.graph.Texture;
import com.game.graph.Window;
import com.game.graph.gui.Button;
import com.game.graph.gui.Launcher;
import com.game.main.util.LevelHandler;
import com.game.obj.Block;
import com.game.obj.Coin;
import com.game.obj.Goomba;
import com.game.obj.Pipe;
import com.game.obj.Player;
import com.game.obj.util.Handler;
import com.game.obj.util.KeyInput;
import com.game.obj.util.MouseInput;
import com.game.obj.util.Sound;

public class Game extends Canvas implements Runnable {
	
	//Game Constants
	private static final int M_SEC = 1000;
	private static final int NANO_SEC = 1000000000;
	private static final double SEC_PER_MIN = 60.0;
	
	
	//declare constants for windows
	private static final String GAME_NAME = "Super Mario Game";
	private static final int WIN_H = 752;
	private static final int WIN_W = 960;
	private static final int SCREEN_WIDTH = WIN_W - 67;
	private static final int SCREEN_HEIGHT = WIN_H;
	private static final int SCREEN_OFFSET = 16*3; 
	
	
	//Game Variables
	private boolean running;
	private static boolean playing;
	private static boolean gameOver;
	
	//Game Components
	private Thread thread;
	private static Handler handler;
	private Camera cam;
	private Launcher launcher;
	private MouseInput mouseInput;
	private KeyInput keyInput;
	private static Texture tex;
	private LevelHandler levelHandler;
	
	//Sound
	private static Sound bgm;
	private static Sound gameover;
	private static Sound warning;
	private static Sound mario_die;
	private static Sound jump;
	private static Sound kick;
	private static Sound coin;
	private static boolean gameoverPlayed;
	
	
	public Game()
	{
		init();
	}
	
	
	private void init()
	{
		tex = new Texture();
		handler = new Handler();
		launcher = new Launcher();
		mouseInput = new MouseInput(launcher);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
		levelHandler = new LevelHandler(handler);
		levelHandler.start();
		
		//temporary code
		handler.addGoomba(new Goomba(32*55,600,1,handler,1));
		handler.addGoomba(new Goomba(32*50,600,1,handler,1));
		for (int i=0;i<10;i++) {
			handler.addCoin(new Coin(32*(15+i),600,30,30,1,handler));
		}
		
		cam = new Camera(0, SCREEN_OFFSET);
		
		bgm = new Sound("/sound/01. Ground Theme.wav");
		gameover = new Sound("/sound/smb_gameover.wav");
		warning = new Sound("/sound/smb_warning.wav");
		mario_die = new Sound("/sound/smb_mariodie.wav");
		jump = new Sound("/sound/smb_jump-small.wav");
		kick = new Sound("/sound/smb_kick.wav");
		coin = new Sound("/sound/smb_coin.wav");
		
		new Window(WIN_W, WIN_H, GAME_NAME, this);
		start();
	}
	
	public static void play(String s) {
		if (s == "bgm") {
			bgm.play();
		}
		else if (s=="jump") {
			jump.play();
		}
		else if (s=="mario_die") {
			mario_die.play();
		}
		else if (s=="gameover") {
			gameover.play();
		}
		else if (s=="kick") {
			kick.play();
		}
		else if (s=="coin") {
			coin.play();
		}
	}
	
	public static void reset() {
		gameover.stop();
		bgm.play();
		//temporary code
		handler.getPlayer().resetLives();
		handler.getPlayer().resetCoins();
		handler.addGoomba(new Goomba(32*55,600,1,handler,1));
		handler.addGoomba(new Goomba(32*50,600,1,handler,1));
		for (int i=0;i<10;i++) {
			handler.addCoin(new Coin(32*(15+i),600,30,30,1,handler));
		}
		setGameOver(false);
		gameoverPlayed = true;
	}
	
	private synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
		playing = false;
		gameOver = false;
		gameoverPlayed = false;
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
		if(playing) {
			handler.tick();
			cam.tick(handler.getPlayer());
		}
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
		
		if (!gameOver) {
			Graphics ui = buf.getDrawGraphics();
			ui.setColor(Color.WHITE);
			ui.setFont(new Font("Century Gothic",Font.PLAIN,20));
			ui.drawString("Lives: "+handler.getPlayer().getLives(), 32, 50);
			ui.drawString("Coins: "+handler.getPlayer().getCoins(), 32, 80);
		}
		else {
			bgm.stop();
			mario_die.stop();
			if (!gameoverPlayed) {
				gameover.play();
				gameoverPlayed = true;
			}
			Graphics ui = buf.getDrawGraphics();
			ui.setColor(Color.LIGHT_GRAY);
			ui.setFont(new Font("Century Gothic",Font.BOLD,40));
			ui.drawString("\\>o</   GAME  OVER   \\>o</", 185, 300);
			ui.setFont(new Font("Century Gothic",Font.PLAIN,25));
			ui.drawString(">>  space for restart", 185, 380);
			handler.reset();
		}
		
		Graphics2D g2d = (Graphics2D) gf;

		if(playing && !gameOver) {
			g2d.translate(cam.getX(), cam.getY());
			handler.render(gf);
		    g2d.translate(-cam.getX(), -cam.getY());
		}
		
		else if (!playing) launcher.render(gf);
		
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

	public static void setPlaying(boolean setPlaying) {
		playing = setPlaying;
	}
	public static void setGameOver(boolean setGameOver) {
		gameOver = setGameOver;
	}
	public static boolean getGameOver() {
		return gameOver;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static Texture getTexture() {
		return tex;
	}
	
	//main function
	public static void main(String[] args)
	{
		new Game();
	}

	
}
