package com.game.obj;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Player extends GameObj{
	private static final float MARIO_W = 50;
	private static final float MARIO_H = 50;
	
	private Handler handler;
	
	public Player(float x, float y, int scale, Handler handler)
	{
		super(x, y, ObjID.Player, MARIO_W, MARIO_H, scale);
		this.handler = handler;
	}

	@Override
	public void tick() 
	{
		set_x(get_vx()+get_x());
		set_y(get_vy()+get_y());
		applyGravity();
	}

	@Override
	public void render(Graphics gf)//for presenting  graph
	{
		
		
	}

	@Override
	public Rectangle getBounds() 
	{
		
		return null;
	}
}
