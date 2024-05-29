package com.game.obj.util;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import com.game.obj.Block;
import com.game.obj.Coin;
import com.game.obj.GameObj;
import com.game.obj.Goomba;
import com.game.obj.Koopa;
import com.game.obj.Player;

public class Handler {
	private List<GameObj> gameObj;
	private List<Goomba> goomba;
	private List<Koopa> koopa;
	private List<Coin> coin;
	private Player player;
	
	public Handler()
	{
		gameObj = new LinkedList<GameObj>();
		goomba = new LinkedList<Goomba>();
		koopa = new LinkedList<Koopa>();
		coin = new LinkedList<Coin>();
	}
	
	public void tick()
	{
		for(GameObj obj : gameObj)
		{
			obj.tick();
		}
		//remove blocks
		LinkedList<Block> removeBlocks = player.getAndResetRemoveBlock();
		for(Block removeBlock : removeBlocks)
		{
			removeObj(removeBlock);
		}
		for(Goomba goomba : goomba)
		{
			goomba.tick();
		}
		for(Koopa koopa : koopa)
		{
			koopa.tick();
		}
		for(Coin coin : coin)
		{
			coin.tick();
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
		for(Koopa koopa : koopa)
		{
			koopa.render(g);
		}
		for(Coin coin : coin)
		{
			coin.render(g);
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
	public List<Koopa> getKoopa()
	{
		return koopa;
	}
	public List<Coin> getCoin()
	{
		return coin;
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
	
	public void addKoopa(Koopa koopa) {
		this.koopa.add(koopa);
	}
	
	public void removeKoopa(Koopa koopa) {
		this.koopa.remove(koopa);
	}
	public void addCoin(Coin coin) {
		this.coin.add(coin);
	}
	
	public void removeCoin(Coin coin) {
		this.coin.remove(coin);
	}
	
	public void reset() {
		coin.clear();
		goomba.clear();
	
	}
	
}
