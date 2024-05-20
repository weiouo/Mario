package com.game.obj.util;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import com.game.obj.GameObj;
import com.game.obj.Player;

public class Handler {
	private List<GameObj> gameObj;
	private Player player;
	
	public Handler()
	{
		gameObj = new LinkedList<GameObj>();
	}
	
	public void tick()
	{
		for(GameObj obj : gameObj)
		{
			obj.tick();
		}
	}
	public void render(Graphics g)
	{
		for(GameObj obj : gameObj)
		{
			obj.render(g);
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
	
}
