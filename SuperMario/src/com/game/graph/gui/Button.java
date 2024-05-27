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
	
	public Button(int x, int y, int width, int height, String label) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
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
			Game.play("bgm");
			//Game.setPlaying(true);
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
