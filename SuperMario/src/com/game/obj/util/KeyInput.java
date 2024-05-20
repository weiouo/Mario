package com.game.obj.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		Player player = handler.getPlayer();
		//jump
		if (key == KeyEvent.VK_W) {
			if (!player.hasJumped()) {
				player.set_vy(-15);
				keyDown[0] = true;
				player.setJumped(true);
			}
		}
		//move left
		if (key == KeyEvent.VK_A) {
			player.set_vx(-8);
			keyDown[1] = true;
		}
		//move right
		if (key == KeyEvent.VK_D) {
			player.set_vx(8);
			keyDown[2] = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		Player player = handler.getPlayer();
		if (key == KeyEvent.VK_W) {
			keyDown[0] = false;
			player.setJumped(false);
		}
		if (key == KeyEvent.VK_A) {
			keyDown[1] = false;
		}
		if (key == KeyEvent.VK_D) {
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
