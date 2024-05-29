package com.game.obj.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.main.Game;
import com.game.obj.Player;

//KeyEvent Listener - KeyPress, KeyRelease
public class KeyInput extends KeyAdapter{
	
	private boolean [] keyDown  = new boolean [4];
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//Debug
		System.out.println(Game.getGameOver());
		if (key == KeyEvent.VK_ENTER) {
			Game.setPlaying(true);
			Game.play("bgm");
			Game.startTimer();
		}
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		Player player = handler.getPlayer();
		//jump
		if (key == KeyEvent.VK_UP && !player.hasJumped()) {
			if (!player.hasJumped()) {
				player.set_vy(-15);
				keyDown[0] = true;
				Game.play("jump");
				player.setJumped(true);
			}
		}
		//move left
		if (key == KeyEvent.VK_LEFT) {
			player.set_vx(-8);
			keyDown[1] = true;
		}
		//move right
		if (key == KeyEvent.VK_RIGHT) {
			player.set_vx(8);
			keyDown[2] = true;
		}
		
		
		if (key == KeyEvent.VK_SPACE && Game.getGameOver()) {
			//Debug -> no output -> not reacting
			System.out.println("Space key pressed and game is over. Restarting game...");
			Game.reset();
			Game.setPlaying(true);
			Game.startTimer();
			//Game.reset();
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		Player player = handler.getPlayer();
		if (key == KeyEvent.VK_UP) {
			keyDown[0] = false;
		}
		if (key == KeyEvent.VK_LEFT) {
			keyDown[1] = false;
		}
		if (key == KeyEvent.VK_RIGHT) {
			keyDown[2] = false;
		}
		
		if (!keyDown[1] && !keyDown[2]) {
			player.set_vx(0);
		}
		if (!keyDown[0]) {
			player.set_vy(0);
		}
		
	}
}
