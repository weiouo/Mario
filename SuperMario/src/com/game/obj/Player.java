package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.graph.Texture;
import com.game.main.Game;
import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public class Player extends GameObj{
	private static final float MARIO_W = 16;
	private static final float MARIO_H = 32;
	private static int lives = 5;
	private static int coins =0;

	private Handler handler;
	private Texture tex;
	private BufferedImage[] spriteL, spriteS;
	
	private boolean jumped = false;
	private int health = 2;
	
	public Player(float x, float y, int scale, Handler handler)
	{
		super(x, y, ObjID.Player, MARIO_W, MARIO_H, scale,handler);
		this.handler = handler;
		tex = Game.getTexture();
		spriteL = tex.getMarioL();
		spriteS = tex.getMarioS();
	}

	@Override
	public void tick() 
	{
		if (lives == 0) Game.setGameOver(true);
		if (get_y()>700) {
			set_x(32);set_y(32);
			lives-=1;
		}
		set_x(get_vx()+get_x());
		set_y(get_vy()+get_y());
		applyGravity();
		collision();
	}

	@Override
	public void render(Graphics gf)//for presenting  graph
	{
		//gf.setColor(Color.yellow);
		//gf.fillRect((int)get_x(),(int)get_y(),(int)MARIO_W,(int)MARIO_H);
		if (health == 1) {
			gf.drawImage(spriteS[0], (int) get_x(), (int) get_y(), (int) get_width(), (int) get_height()/2, null);
		} else if (health == 2) {
			gf.drawImage(spriteL[0], (int) get_x(), (int) get_y(), (int) get_width(), (int) get_height(), null);
		}
		//showBounds(gf);
		
	}
	
	public void collision() {
		for (int i=0;i<handler.getObj().size();i++) {
			GameObj temp = handler.getObj().get(i);
			
			if (temp.get_ID() == ObjID.Block || temp.get_ID() == ObjID.Pipe) {
				
				if (getBounds().intersects(temp.getBounds())) {
					set_y( temp.get_y()- get_height());
					set_vy(0);
					setJumped(false);
				}
				if (getBoundsTop().intersects(temp.getBounds())) {
					set_y( temp.get_y()+ get_height());
					set_vy(0);
				}
				if (getBoundsRight().intersects(temp.getBounds())) {
					set_x( temp.get_x()- get_width());
				}
				if (getBoundsLeft().intersects(temp.getBounds())) {
					set_x( temp.get_x()+ get_width());
				}
			}
		}
		for (int i=0;i<handler.getGoomba().size();i++) {
			Goomba goomba = handler.getGoomba().get(i);
				if (getBoundsRight().intersects(goomba.getBoundsLeft())){
					//go back to origin
					set_x(32);set_y(32);
					lives-=1;
				}
				else if (getBoundsLeft().intersects(goomba.getBoundsRight())){
					//go back to origin
					set_x(32);set_y(32);
					lives-=1;
				}
				else if (getBoundsTop().intersects(goomba.getBounds())) {
					//go back to origin
					set_x(32);set_y(32);
					lives-=1;
				}
				else if (getBounds().intersects(goomba.getBoundsTop())) {
					goomba.die();
				} 
		}
		for (int i=0;i<handler.getCoin().size();i++) {
			Coin coin = handler.getCoin().get(i);
			if (getBounds().intersects(coin.getBounds())||getBoundsTop().intersects(coin.getBounds())
					||getBoundsRight().intersects(coin.getBounds())||getBoundsLeft().intersects(coin.getBounds())) {
				coin.die();
				coins+=1;
			} 
		}
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle((int) (get_x()+get_width()/4),
				(int) (get_y()+get_height()/2),
				(int) get_width()/2,
				(int) get_height()/2);
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
				(int) (get_y()+5),
				5,
				(int) (get_height()-10));
	}
	
	public Rectangle getBoundsLeft() 
	{
		return new Rectangle((int) get_x(),
				(int) (get_y()+5),
				5,
				(int) (get_height()-10));
	}
	
	private void showBounds(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D)g;
		
		g.setColor(Color.red);
		g2d.draw(getBounds());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsTop());
	}
	
	public boolean hasJumped() {
		return jumped;
	}
	
	public void setJumped(boolean hasJumped) {
		jumped = hasJumped;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getCoins() {
		return coins;
	}
}
