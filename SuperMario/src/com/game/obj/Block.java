package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Block extends GameObj{
	private Texture tex = Game.getTexture();
	private int index;
	private BufferedImage[] sprite;
	
	private boolean hit;//block is hit or not
	private Debris debris;//reference for debris in the block
	
	public Block (int x, int y, int width, int height, int scale, Handler handler, int index) {
		super(x, y, ObjID.Block, width, height, scale, handler);
		this.index = index;
		sprite = tex.getTile1();
	}

	@Override
	public void tick() 
	{
		if(hit)
		{
			debris.tick();
		}
	}
	public boolean shouldRemove()
	{
		if(debris.shouldRemove())
		{
			return true;
		}
		return false;
	}

	@Override
	public void render(Graphics g) 
	{
		if(!hit) 
		{
			g.drawImage(sprite[index], (int) get_x(), (int) get_y(), (int) get_width(), (int) get_height(), null);
		}
		else
		{
			debris.draw(g);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) get_x(),(int) get_y(),(int) get_width(),(int) get_height());
	}
	
	public void hit()
	{
		hit = true;
		debris = new Debris(get_x(), get_y(), get_width(), get_height(), getScale());
	}
}
