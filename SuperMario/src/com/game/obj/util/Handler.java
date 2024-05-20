package com.game.obj.util;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import com.game.obj.GameObj;
import com.game.obj.Goomba;
import com.game.obj.Player;

public class Handler {
	private List<GameObj> gameObj;
	private List<Goomba> goomba;
	private Player player;
	
	public Handler()
	{
		gameObj = new LinkedList<GameObj>();
		goomba = new LinkedList<Goomba>();
	}
	
	public void tick()
	{
		for(GameObj obj : gameObj)
		{
			obj.tick();
		}
		for(Goomba goomba : goomba)
		{
			goomba.tick();
		}
	}
	public void render(Graphics g)
	{
		for(GameObj obj : gameObj)
		{
			obj.render(g);
		}
		for(Goomba goomba : goomba)
		{
			goomba.render(g);
		}
	}
	public void addObj(GameObj obj)
	{
		gameObj.add(obj);
	}
	public void removeObj(GameObj obj)
	{
		gameObj.remove(obj);
	}
	public List<GameObj> getObj()
	{
		return gameObj;
	}
	public List<Goomba> getGoomba()
	{
		return goomba;
	}
	
	//set player - return 0 or 1 to check if player is set
	public int setPlayer(Player player)
	{
		if(this.player != null)
		{
			return -1;
		}
		addObj(player);
		this.player = player;
		return 0;
	}
	
	
	//remove player
	public int removePlayer()
	{
		if(this.player == null) return -1;
		
		removeObj(player);
		this.player = null;
		return 0;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void addGoomba(Goomba goomba) {
		this.goomba.add(goomba);
	}
	
	public void removeGoomba(Goomba goomba) {
		this.goomba.remove(goomba);
	}
	
}
