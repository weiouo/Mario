package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Coin extends GameObj {

	public Coin(int x, int y, int width, int height, int scale,Handler handler) {
		super(x, y, ObjID.Block, width, height, scale,handler);
	}

	@Override
	public void tick() {
		set_y(get_vy()+get_y());
		applyGravity();
		collision();
	}
	
	public void die() {
		handler.removeCoin(this);
	}

	@Override
	public void render(Graphics gf) {
		gf.setColor(Color.green);
		gf.fillRect((int)get_x(),(int)get_y(),(int)get_width(),(int)get_height());
	}
	
	public void collision() {
		for (int i=0;i<handler.getObj().size();i++) {
			GameObj temp = handler.getObj().get(i);
			
			if (temp.get_ID() == ObjID.Block || temp.get_ID() == ObjID.Pipe) {
				if (getBounds().intersects(temp.getBounds())) {
					set_vy(0);
				}
			}
		}
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) (get_x()+get_width()/4),
				(int) (get_y()+get_height()/2),
				(int) get_width()/2,
				(int) get_height()/2);
	}
}
