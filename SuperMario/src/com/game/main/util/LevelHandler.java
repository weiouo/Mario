package com.game.main.util;

import java.awt.image.BufferedImage;

import com.game.graph.BufferedImageLoader;
import com.game.obj.Block;
import com.game.obj.Cloud;
import com.game.obj.Flag;
import com.game.obj.FlagPole;
import com.game.obj.Goomba;
import com.game.obj.Mountain;
import com.game.obj.Pipe;
import com.game.obj.Player;
import com.game.obj.util.Handler;

public class LevelHandler {
	
	private final String PARENT_FOLDER = "res/level";
	
	private BufferedImageLoader loader;
	private BufferedImage levelTiles;
	private Handler handler;

	public LevelHandler(Handler handler) {
		this.handler = handler;
		loader = new BufferedImageLoader();
	}
	
	public void start() {
		setLevel(PARENT_FOLDER + "/1_1.png");
		loadBackground(PARENT_FOLDER + "/1_1b.png");
		loadCharacters(PARENT_FOLDER + "/1_1c.png");	
	}
	public void restart() {
		loadCharacters(PARENT_FOLDER + "/1_1c.png");	
	}
	
	public void setLevel(String levelTilesPath) {
		this.levelTiles = loader.loadImage(levelTilesPath);
		
		int width = levelTiles.getWidth();
		int height = levelTiles.getHeight();
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int pixel = levelTiles.getRGB(i, j);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;
				
				if (red == 255 && green ==255 && blue == 255) {
					continue;
				}
				if (red == 100 && green == 100 && blue == 100) { //地面磚塊
					handler.addObj(new Block(i*16, j*16, 16, 16, 3, handler, 0));
				} else if (red == 127 && green == 127 && blue == 127) { //一般磚塊
					handler.addObj(new Block(i*16, j*16, 16, 16, 3, handler, 1));
				} else if (red == 195 && green == 195 && blue == 195) { //階梯磚塊
					handler.addObj(new Block(i*16, j*16, 16, 16, 3, handler, 28));
				} else if (blue == 0 && green == 0 && red == 5) {
					handler.addObj(new Pipe(i*16, j*16, 32, 16, 3, false, handler, 0));
				} else if (blue == 0 && green == 0 && red == 10) {
					handler.addObj(new Pipe(i*16, j*16, 32, 16, 3, false, handler, 1));
				} else if (blue == 0 && green == 0 && red == 15) {
					handler.addObj(new Pipe(i*16, j*16, 32, 16, 3, false, handler, 2));
				} else if (blue == 0 && green == 0 && red == 20) {
					handler.addObj(new Pipe(i*16, j*16, 32, 16, 3, false, handler, 3));
				} else if (blue == 0 && green == 0 && red == 25) {
					handler.addObj(new Pipe(i*16, j*16, 32, 16, 3, true, handler, 0));
				} else if (blue == 0 && green == 0 && red == 30) {
					handler.addObj(new Pipe(i*16, j*16, 32, 16, 3, true, handler, 2));
				} else if(red == 237 && green == 28 && blue == 36) {
					handler.addObj(new Flag(i*16, j*16, 16, 16, 3, handler));
					handler.addObj(new FlagPole(i*16, j*16, 2, 180, 3, handler));
				}
			}
		}
	}
	
	private void loadCharacters(String levelCharactersPath) {
        this.levelTiles = loader.loadImage(levelCharactersPath);
		
		int width = levelTiles.getWidth();
		int height = levelTiles.getHeight();
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int pixel = levelTiles.getRGB(i, j);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;
				
				if (red == 255 && green ==255 && blue == 255) {
					continue;
				}
				if (red == green && red == blue) {
					if (red == 0) {
						handler.setPlayer(new Player(i*16, j*16, 3, handler));
					}
				} 
			}
		}
	}

	private void loadBackground(String levelBackgroundPath) {
		this.levelTiles = loader.loadImage(levelBackgroundPath);
		
		int width = levelTiles.getWidth();
		int height = levelTiles.getHeight();
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int pixel = levelTiles.getRGB(i, j);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;
				
				if (red == 255 && green == 255 && blue == 255) {
					continue;
				}
				if (red == 50 && green == 250 && blue == 0) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 0));//左邊山
				} else if (red == 0 && green == 250 && blue == 0) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 1));//上面山
				} else if (red == 0 && green == 250 && blue == 50) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 2));//右邊山
				} else if (red == 100 && green == 250 && blue == 0) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 3));//左下山
				} else if (red == 0 && green == 200 && blue == 0) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 4));//中下山
				} else if (red == 0 && green == 250 && blue == 100) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 5));//右下山
				} else if (red == 50 && green == 255 && blue == 0) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 6));//左邊草
				} else if (red == 0 && green == 255 && blue == 0) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 7));//中間草
				} else if (red == 0 && green == 255 && blue == 50) {
					handler.addObj(new Mountain(i*16, j*16, 16, 16, 3, handler, 8));//右邊草
				} else if (red == 50 && green == 0 && blue == 255) {
					handler.addObj(new Cloud(i*16, j*16, 16, 32, 3, handler, 0));//左邊雲
				} else if (red == 0 && green == 0 && blue == 255) {
					handler.addObj(new Cloud(i*16, j*16, 16, 32, 3, handler, 1));//中間雲
				} else if (red == 0 && green == 50 && blue == 255) {
					handler.addObj(new Cloud(i*16, j*16, 16, 32, 3, handler, 2));//右邊雲
				}
			}
		}
		
	}
	
}
