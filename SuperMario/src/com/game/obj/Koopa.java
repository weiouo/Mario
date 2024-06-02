package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import com.game.graph.Animation;
import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Koopa extends GameObj {
	
	private static final float KOOPA_W = 32;
	private static final float KOOPA_H = 32;
	
	private static float rangeL;
	private static float rangeR;

	private Texture tex = Game.getTexture();
	private int index;
	private BufferedImage[] sprite;
	private Animation koopaWalk;
	private BufferedImage squashedImage;
	
	private boolean hasCollide = false;
	private boolean state = true;//walk
	private Timer timer;
	private int v_origin;
	
	public Koopa(float x, float y, float rangeL, float rangeR, int scale, Handler handler, int index) {
		super(x, y, ObjID.Enemy, KOOPA_W, KOOPA_H, scale,handler);
		this.index = index;
		this.rangeL = rangeL;
		this.rangeR = rangeR;
		sprite = tex.getKoopa();
		koopaWalk = new Animation(5, sprite[0], sprite[1]);
		squashedImage = sprite[4];
		timer = new Timer(10000, listener);
		set_vx(2);
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
		set_vx(0);
		timer.start();
	}
	
	public boolean get_state() {
		return state;
	}

	@Override
	public void tick() {
		if (get_y()>600) this.die();
		set_x(get_vx()+get_x());
		set_y(get_vy()+get_y());
		applyGravity();
		collision();
		koopaWalk.runAnimation();
		if (get_x()==rangeL) set_vx(2);
		else if (get_x()==rangeR)set_vx(-2);
	}
	
	public void die() {
		handler.removeKoopa(this);
	}

	@Override
	public void render(Graphics gf) {
		if (get_vx() > 0) {
			koopaWalk.drawAnimation(gf, (int) get_x(), (int) get_y(), (int) -get_width(), (int) get_height());
		} else if (get_vx() < 0) {
			koopaWalk.drawAnimation(gf, (int) (get_x() + get_width()), (int) get_y(), (int) get_width(), (int) get_height());
		} else {
			gf.drawImage(squashedImage, (int) get_x(), (int) get_y(), (int) get_width(), (int) get_height(), null);
		}
	}
	
	public void collision() {
		for (int i=0;i<handler.getObj().size();i++) {
			GameObj temp = handler.getObj().get(i);
			
			if (temp.get_ID() == ObjID.Block || temp.get_ID() == ObjID.Pipe) {
				if (getBounds().intersects(temp.getBounds())) {
					set_vy(0);
				}
				
				if (getBoundsRight().intersects(temp.getBounds())&&state) {
					set_vx(-2);
					v_origin=-2;
				}
				if (getBoundsLeft().intersects(temp.getBounds())&&state) {
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
