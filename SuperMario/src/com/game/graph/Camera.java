package com.game.graph;

import com.game.main.Game;
import com.game.obj.GameObj;

public class Camera {
	
	private int x, y;
	
	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObj player) {
		x = (int) (-player.get_x() + Game.getScreenWidth()/2);
		
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
