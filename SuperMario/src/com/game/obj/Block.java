package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.obj.util.ObjID;

public class Block extends GameObj{

	public Block (int x, int y, int width, int height, int scale) {
		super(x, y, ObjID.Block, width, height, scale);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics gf) {
		//temporary code
		gf.setColor(Color.WHITE);
		gf.drawRect((int) get_x(), (int) get_y(), (int) get_width(), (int)get_height());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) get_x(),(int) get_y(),(int) get_width(),(int) get_height());
	}
}
