package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Koopa extends GameObj {
	
	private static final float KOOPA_W = 32;
	private static final float KOOPA_H = 32;
	
	private boolean hasCollide = false;
	private boolean state = true;//walk
	private Timer timer;
	private int v_origin;
	
	public Koopa(float x, float y, int scale, Handler handler) {
		super(x, y, ObjID.Enemy, KOOPA_W, KOOPA_H, scale,handler);
		timer = new Timer(10000, listener);
	}
	
	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			state=true;
			set_vx(v_origin);
			timer.stop();
		}
	};
	
	public void tranfer_state() {
		state=false;
		timer.start();
	}
	
	public boolean get_state() {
		return state;
	}

	@Override
	public void tick() {
		if (!hasCollide && state)set_vx(2);
		else if (!state) set_vx(0);
		set_x(get_vx()+get_x());
		set_y(get_vy()+get_y());
		applyGravity();
		collision();
	}
	
	public void die() {
		handler.removeKoopa(this);
	}

	@Override
	public void render(Graphics gf) {
		gf.setColor(Color.red);
		gf.fillRect((int) get_x(), (int) get_y(), (int) get_width(), (int)get_height());
		
	}
	
	public void collision() {
		for (int i=0;i<handler.getObj().size();i++) {
			GameObj temp = handler.getObj().get(i);
			
			if (temp.get_ID() == ObjID.Block || temp.get_ID() == ObjID.Pipe) {
				if (getBounds().intersects(temp.getBounds())) {
					set_vy(0);
				}
				
				if (getBoundsRight().intersects(temp.getBounds())&&state) {
					hasCollide=true;
					set_vx(-2);
					v_origin=-2;
				}
				if (getBoundsLeft().intersects(temp.getBounds())&&state) {
					hasCollide=true;
					set_vx(2);
					v_origin=2;
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
