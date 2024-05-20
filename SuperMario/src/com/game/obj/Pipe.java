package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.obj.util.ObjID;

public class Pipe extends GameObj{
	private boolean enterable;
	
	public Pipe(int x, int y, int width, int height, int scale, boolean enterable) {
		super(x, y, ObjID.Pipe, width, height, scale);
		this.enterable = enterable;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics gf) {	
		//temporary code
		gf.setColor(Color.RED);
		gf.drawRect((int) get_x(), (int) get_y(), (int) get_width(), (int)get_height());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) get_x(),(int) get_y(),(int) get_width(),(int) get_height());
	}
}
