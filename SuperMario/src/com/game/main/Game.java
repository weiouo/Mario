package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;

import com.game.graph.Camera;
import com.game.graph.Texture;
import com.game.graph.Window;
import com.game.graph.gui.Button;
import com.game.graph.gui.Launcher;
import com.game.main.util.LevelHandler;
import com.game.obj.Block;
import com.game.obj.Coin;
import com.game.obj.Goomba;
import com.game.obj.Koopa;
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
	private static int gameTime = 60*5;
	private static int winlevel = 0;
	
	//Game Components
	
	private Thread thread;
	private static Handler handler;
	private Camera cam;
	private Launcher launcher;
	private static Timer timer;

	//private MouseInput mouseInput;
	private KeyInput keyInput;
	private static Texture tex;
	private LevelHandler levelHandler;
	
	//Sound
	private static Sound name_entry;
	private static Sound bgm;
	private static Sound gameover;
	private static Sound win;
	private static Sound hurry;
	private static Sound mario_die;
	private static Sound jump;
	private static Sound kick;
	private static Sound coin;
	private static Sound break_block;
	private static boolean gameoverPlayed;
	private static boolean winPlayed;
	private static boolean hurryPlayed;
	private static boolean bgmOverTime;
	
	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameTime--;	
		}
	};
	
	public Game()
	{
		init();
	}
	
	private void init()
	{
		tex = new Texture();
		handler = new Handler();
		launcher = new Launcher();
		keyInput = new KeyInput(handler);
		timer = new Timer(1000, listener);
		this.addKeyListener(keyInput);
		levelHandler = new LevelHandler(handler);
		levelHandler.start();
		
		cam = new Camera(0, SCREEN_OFFSET);
		
		name_entry = new Sound("/sound/Super Mario Bros. Deluxe Music _ 13 - Name Entry.wav");
		bgm = new Sound("/sound/01. Ground Theme.wav");
		gameover = new Sound("/sound/smb_gameover.wav");
		win = new Sound("/sound/18. Saved the Princess.wav");
		hurry = new Sound("/sound/12. Ground Theme (Hurry!).wav");
		mario_die = new Sound("/sound/smb_mariodie.wav");
		jump = new Sound("/sound/smb_jump-small.wav");
		kick = new Sound("/sound/smb_kick.wav");
		coin = new Sound("/sound/smb_coin.wav");
		break_block = new Sound("/sound/smb_breakblock.wav");
		
		new Window(WIN_W, WIN_H, GAME_NAME, this);
		this.requestFocusInWindow();
		start();
		set();
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
		else if (s=="win") {
			win.play();
		}
		else if (s=="kick") {
			kick.play();
		}
		else if (s=="coin") {
			coin.stop();
			coin.play();
		}
		else if (s=="break_block") {
			break_block.play();
		}
		else if (s=="hurry") {
			hurry.play();
		}
	}
	
	private static void set() {
		setEnemy();
		setCoin();
	}
	
	public static void reset() {
		gameTime = 60*5;
		handler.getPlayer().set_x(144);
		handler.getPlayer().set_y(576);
		gameover.stop();
		win.stop();
		bgm.play();
		handler.getPlayer().resetLives();
		handler.getPlayer().resetCoins();
		setGameOver(false);
		gameoverPlayed = false;
		winPlayed = false;
		hurryPlayed = false;
		bgmOverTime = false;
		winlevel =0;
		set();
	}
	
	private synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
		playing = false;
		gameOver = false;
		gameoverPlayed = false;
		hurryPlayed = false;
		bgmOverTime = false;
		name_entry.play();
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
		 if (playing && !gameOver) {
		        handler.tick();
		        cam.tick(handler.getPlayer());
		    } else if (gameOver) {
		        // 遊戲結束的邏輯
		        handler.reset();
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
		Color lightBlue = new Color(107, 136, 254);
		gf.setColor(lightBlue);
		gf.fillRect(0, 0, WIN_W, WIN_H);
		
		if (!gameOver) {
			Graphics ui = buf.getDrawGraphics();
			ui.setColor(Color.WHITE);
			ui.setFont(new Font("Century Gothic",Font.PLAIN,20));
			ui.drawString("Lives: "+handler.getPlayer().getLives(), 32, 50);
			ui.drawString("Coins: "+handler.getPlayer().getCoins(), 32, 80);
			int min = gameTime/60;
			int sec = gameTime%60;
			ui.drawString(String.format("%02d : %02d",min,sec),820, 50);
		}
		else {
			timer.stop();
			bgm.stop();
			hurry.stop();
			mario_die.stop();
			if (!gameoverPlayed ) {
				gameover.play();
				gameoverPlayed = true;
			}
			Graphics ui = buf.getDrawGraphics();
			ui.setColor(Color.LIGHT_GRAY);
			ui.setFont(new Font("Century Gothic",Font.BOLD,40));
			if (gameTime <=0)ui.drawString("\\>o</    TIME  OUT    \\>o</", 185, 300);
			else if(winlevel > 0) 
			{
				timer.stop();
				bgm.stop();
				hurry.stop();
				mario_die.stop();
				if (!winPlayed) {
					win.play();
					winPlayed = true;
				}
				ui.drawString(" ^^  !!  WIN  !!  ^^ ", 185, 220);
				
				ui.drawString("LEVEL : ", 185, 300);
				for(int i = 0 ; i < winlevel ; i++)
				{
					ui.drawString("* ",300+(i+1)*40, 300);
				}
				
			}
			else ui.drawString("\\>o</  LOSE ~ GAME  OVER   \\>o</", 185, 300);
			ui.setFont(new Font("Century Gothic",Font.PLAIN,25));
			ui.drawString(">>  space for restart", 185, 380);
			
			//Debug
			System.out.println(getGameOver());
			//Debug
			//handler.reset();
		}
		
		Graphics2D g2d = (Graphics2D) gf;

		if(playing && !gameOver) {
			name_entry.stop();
			g2d.translate(cam.getX(), cam.getY());
			handler.render(gf);
		    g2d.translate(-cam.getX(), -cam.getY());
		    if (gameTime==116 && !bgmOverTime) {
		    	bgm.play();
		    	bgmOverTime = true;
		    }
		    if (gameTime<=60 && !hurryPlayed) {
		    	bgm.stop();
		    	hurry.play();
		    	hurryPlayed = true;
		    }
		    if (gameTime==0) {
		    	setGameOver(true);
		    }
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
	public static void setWinLevel(int coin_cnt) {
		if(coin_cnt <= 10) winlevel = 1;
		else if(coin_cnt > 10 && coin_cnt <= 20)winlevel = 2;
		else if(coin_cnt > 20)winlevel = 3;
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
	public static void startTimer() {
		timer.start();
	}
	
	public static int get_GameTime() {
		return gameTime;
	}
	
	//main function
	public static void main(String[] args)
	{
		new Game();
	}
	
	//setting
	private static void setCoin() {
		handler.addCoin(new Coin(1464,465,30,30,1,handler));
		handler.addCoin(new Coin(1936,417,30,30,1,handler));
		handler.addCoin(new Coin(3368,585,30,30,1,handler));
		handler.addCoin(new Coin(3432,515,30,30,1,handler));
		handler.addCoin(new Coin(3496,585,30,30,1,handler));
		handler.addCoin(new Coin(5328,369,30,30,1,handler));
		for (int i=0;i<5;i++) {
			handler.addCoin(new Coin(1560+70*i,585,30,30,1,handler));
		}
		for (int i=0;i<6;i++) {
			handler.addCoin(new Coin(2430+70*i,585,30,30,1,handler));
		}
		for (int i=0;i<4;i++) {
			handler.addCoin(new Coin(3992+70*i,177,30,30,1,handler));
			handler.addCoin(new Coin(7395,585-65*i,30,30,1,handler));
			handler.addCoin(new Coin(7450,585-65*i,30,30,1,handler));
		}
		for (int i=0;i<2;i++) {
			handler.addCoin(new Coin(4488+70*i,177,30,30,1,handler));
			handler.addCoin(new Coin(5915+70*i,177,30,30,1,handler));
			handler.addCoin(new Coin(6730,585-60*(i+1),30,30,1,handler));
			handler.addCoin(new Coin(6770,585-60*(i+1),30,30,1,handler));
		}
		for (int i=0;i<3;i++) {
			handler.addCoin(new Coin(8120+70*i,369,30,30,1,handler));
			handler.addCoin(new Coin(8432+70*i,585,30,30,1,handler));
		}
	}
	private static void setEnemy() {
		handler.addKoopa(new Koopa(32*70,600,0,3000,1,handler,1));
		handler.addKoopa(new Koopa(6308,600,4376,7000,1,handler,1));
		handler.addGoomba(new Goomba(32*55,600,0,3000,1,handler,1));
		handler.addGoomba(new Goomba(32*50,600,0,3000,1,handler,1));
		handler.addGoomba(new Goomba(32*30,600,32,3000,1,handler,1));
		handler.addGoomba(new Goomba(4000,192,3960,4250,1,handler,1));
	}

	
}
