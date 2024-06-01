package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Mountain extends GameObj{
	private Texture tex = Game.getTexture();
	private int index;
	private BufferedImage[] sprite;
	
	
	public Mountain(int x, int y, int width, int height, int scale, Handler handler, int index) {
		super(x, y, ObjID.Mountain, width, height, scale, handler);
		this.index = index;
		sprite = tex.getMountain();
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics gf) {	
		gf.drawImage(sprite[index], (int) get_x(), (int) get_y(), (int) get_width(), (int)get_height(), null);
	}

	@Override
	public Rectangle getBounds() {
	    return new Rectangle();
	}
}
