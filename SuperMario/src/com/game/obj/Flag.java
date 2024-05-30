package com.game.obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Flag extends GameObj{
	
	private Texture tex = Game.getTexture();
	private BufferedImage flag;
	//flag pole 10*500
	public Flag(float x, float y, float width, float height, int scale, Handler handler) {
		super(x, y, ObjID.Flag, width, height, scale, handler);
		// TODO Auto-generated constructor stub
		this.flag = tex.flag;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics gf) {
	
		gf.drawImage(flag, (int) get_x(), (int) get_y(), (int) get_width(), (int)get_height(), null);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int) get_x(),(int) get_y(),(int) get_width(),(int) get_height());
	}

}
