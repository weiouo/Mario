package com.game.graph.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.game.main.Game;

public class Launcher {
	
	private Game game;
	private Button[] buttons;

	public Launcher(Game game) {
		buttons = new Button [2];
		buttons[0] = new Button(440,200,100,100,"START  GAME",game);
		buttons[1] = new Button(440,350,100,100,"EXIT  GAME",game);
		this.game = game;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.getWindow_W(), Game.getWindow_H());
		
		for (int i=0;i<buttons.length;i++) {
			buttons[i].render(g);
		}
	} 
	public Button[] getButton() {
		return buttons;
	}
}
