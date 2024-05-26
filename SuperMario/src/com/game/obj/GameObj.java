package com.game.obj;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.obj.util.Handler;
import com.game.obj.util.ObjID;

public abstract class GameObj //abstract class can define both abstract & concrete method
{
	
	private float x;
	private float y;
	private ObjID id;
	
	private float v_x, v_y;//velocity - x
	private float width, height;
	private int scale;
	public Handler handler;
	
	public GameObj(float x, float y, ObjID id, float width, float height, int scale,Handler handler)
	{
		this.scale = scale;
		this.x = x*scale;
		this.y = y*scale;
		this.id = id;
		this.width = width*scale;
		this.height = height*scale;
		this.handler = handler;
	}
	public abstract void tick();
	public abstract void render(Graphics gf);
	public abstract Rectangle getBounds();
	
	//gravity effect when jumping
	public void applyGravity()
	{
		v_y += 0.5f;
	}
	
	//setter - set value
	public void set_x(float x)
	{
		this.x = x;
	}
	public void set_y(float y)
	{
		this.y = y;
	}
	public void set_ID(ObjID id)
	{
		this.id = id;
	}
	public void set_vx(float vx)
	{
		this.v_x = vx;
	}
	public void set_vy(float vy)
	{
		this.v_y = vy;
	}
	public void set_width(float w)
	{
		this.width = w;
	}
	public void set_height(float h)
	{
		this.height = h;
	}
	public void setScale()
	{
		this.scale = scale;
	}
	//getter - get value
	
	public float get_x()
	{
		return x;
	}
	public float get_y()
	{
		return y;
	}
	public ObjID get_ID()
	{
		return id;
	}
	
	public float get_vx()
	{
		return v_x;
	}
	public float get_vy()
	{
		return v_y;
	}
	
	public float get_width()
	{
		return width;
	}
	public float get_height()
	{
		return height;
	}
	
	public void die() {
		handler.removeObj(this);
	}//tutorial deleted?
	public int getScale()
	{
		return scale;
	}
	
	
}
