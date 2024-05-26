package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Timer;

import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Coin extends GameObj {

	private Texture tex = Game.getTexture();
	private BufferedImage coin;
	
	public Coin(int x, int y, int width, int height, int scale,Handler handler) {
		super(x, y, ObjID.Coin, width, height, scale,handler);
		this.coin = tex.coin;
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
		gf.drawImage(coin, (int) get_x(), (int) get_y(), (int) get_width(), (int)get_height(), null);
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
