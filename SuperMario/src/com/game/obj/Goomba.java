package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Goomba extends GameObj{
	
	private static final float GOOMBA_W = 32;
	private static final float GOOMBA_H = 32;

	private Texture tex = Game.getTexture();
	private int index;
	private BufferedImage[] sprite;
	
	private boolean hasCollide = false;
	
	public Goomba (float x, float y, int scale,Handler handler, int index) {
		super(x, y, ObjID.Enemy, GOOMBA_W, GOOMBA_H, scale,handler);
		this.index = index;
		sprite = tex.getGoomba();
	}

	@Override
	public void tick() {
		if (!hasCollide)set_vx(2);
		set_x(get_vx()+get_x());
		set_y(get_vy()+get_y());
		applyGravity();
		collision();
	}
	
	public void die() {
		handler.removeGoomba(this);
	}
	
	@Override
	public void render(Graphics gf) {
		gf.drawImage(sprite[index], (int) get_x(), (int) get_y(), (int) get_width(), (int)get_height(), null);
	}
	
	public void collision() {
		for (int i=0;i<handler.getObj().size();i++) {
			GameObj temp = handler.getObj().get(i);
			
			if (temp.get_ID() == ObjID.Block || temp.get_ID() == ObjID.Pipe) {
				if (getBounds().intersects(temp.getBounds())) {
					set_vy(0);
				}
				
				if (getBoundsRight().intersects(temp.getBounds())) {
					hasCollide=true;
					set_vx(-2);
				}
				if (getBoundsLeft().intersects(temp.getBounds())) {
					hasCollide=true;
					set_vx(2);
				}
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) get_x(),(int) get_y(),(int) get_width(),(int) get_height());
	}
	
	public Rectangle getBoundsTop() 
	{
		return new Rectangle((int) (get_x()+get_width()/4),
				(int) get_y(),
				(int) get_width()/2,
				(int) get_height()/2);
	}
	
	public Rectangle getBoundsRight() 
	{
		return new Rectangle((int) (get_x()+get_width()-5),
				(int) get_y(),
				5,
				(int) get_height()-10);
	}
	
	public Rectangle getBoundsLeft() 
	{
		return new Rectangle((int) get_x(),
				(int) get_y(),
				5,
				(int) get_height()-10);
	}

}
