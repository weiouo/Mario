package com.game.obj.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.game.graph.gui.Button;
import com.game.graph.gui.Launcher;
import com.game.main.Game;

public class MouseInput implements MouseListener,MouseMotionListener{

	private int x,y;
	//private Launcher launcher;
	private Button [] buttons;
	private Launcher launcher;
	
	public MouseInput(Launcher launcher) {
		this.launcher = launcher;
		this.buttons = launcher.getButton();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	
	public void mousePressed(MouseEvent e) {
		for (int i=0;i<buttons.length;i++) {
			Button button = buttons[i];
			
			//check mouse inside the area of button
			if (x>=button.getX()&& x<=button.getX()+button.getWidth() && y>=button.getY() && y<=button.getY()+button.getHeight()) {
				//button.triggerEvent();
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
