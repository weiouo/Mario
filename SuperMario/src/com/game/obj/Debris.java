package com.game.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.graph.Texture;
import com.game.main.Game;

public class Debris 
{
	private Texture tex = Game.getTexture();
	private BufferedImage[] debris;
	private int scale;
	private float width, height, velX, velY;
	private float[]x, y;
	
	
	public Debris(float x, float y, float w, float h, int scale)
	{
		//init float array
		this.x = new float[4];
		this.y = new float[4];
		
		this.x[0] = (float)(x-0.5*w);
		this.x[1] = (float)(x-0.5*w);
		this.x[2] = (float)(x+0.5*w);
		this.x[3] = (float)(x+0.5*w);
		
		this.y[0] = (float)(y+0.5*h);
		this.y[1] = (float)(y-0.5*h);
		this.y[2] = (float)(y+0.5*h);
		this.y[3] = (float)(y-0.5*h);
		
		this.width = w;
		this.height = h/2;
		debris = tex.getDebris1();
		
		//brick broke in constant speed in x,y directions
		velX = 2f;
		velY = -7f;
		
	}
	
	public void applyGravity()
	{
		//apply gravity effects when debris is going downward
		velY += 0.5f;
	}
	
	public void tick()
	{
		//break into 4 pieces
		x[0] = -velX +x[0];
		x[1] = -velX +x[1];
		x[2] = -velX +x[2];
		x[3] = -velX +x[3];
		
		// pieces on top go higher
		y[0] = velY + y[0];
		y[1] = (float) (velY + y[1] -2);
		y[2] = velY - y[2];
		y[3] = (float) (velY + y[3] -2);
		applyGravity();
	}
	
	public boolean shouldRemove()
	{
		//remove pieces when they went out of window
		if(y[1] > Game.getWindow_H())
		{
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g)
	{
		for(int i = 0 ; i < 4 ; i++)
		{
			g.drawImage(debris[i], (int)x[i], (int)y[i], (int)width, (int)height, null);
		}
	}

}
