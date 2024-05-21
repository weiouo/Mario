package com.game.graph.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.game.main.Game;

public class Button {
	private int x,y;
	private int width,height;
	private String label;
	
	private Game game;
	
	public Button(int x, int y, int width, int height, String label,Game game) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		this.game = game;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Century Gothic",Font.BOLD,40));
		
		FontMetrics fm = g.getFontMetrics();
		int string_x = (getWidth()-fm.stringWidth(getLabel()))/2;
		int string_y = (fm.getAscent()+(getHeight()-(fm.getAscent()+fm.getDescent())/2));
		g.drawString(getLabel(), getX()+string_x, getY()+string_y);
	}
	
	public void triggerEvent() {
		if (getLabel().toLowerCase().contains("start")) {
			game.setPlaying(true);
		}
		else if (getLabel().toLowerCase().contains("exit")) {
			System.exit(0);
		}
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
