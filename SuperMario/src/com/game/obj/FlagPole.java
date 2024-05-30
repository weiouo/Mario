package com.game.obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class FlagPole extends GameObj{
	
	private Texture tex = Game.getTexture();
	private BufferedImage flagpole;
	//flag pole 10*500
	
	public FlagPole(float x, float y, float width, float height, int scale, Handler handler) {
		super(x, y, ObjID.FlagPole, width, height, scale, handler);
		this.flagpole = tex.flagpole;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		//collision();
	}

	@Override
	public void render(Graphics gf) {
		
		gf.drawImage(flagpole, (int) get_x(), (int) get_y(), (int) get_width(), (int)get_height(), null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) get_x(),(int) get_y(),(int) get_width(),(int) get_height());
	}
	

}
