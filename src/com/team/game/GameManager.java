/**
 * 
 */
package com.team.game;

import java.util.ArrayList;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.Image;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {
	
	public static final int TILE_SIZE = 16;
	
	private boolean[] collision;
	private int levelWidth, levelHeight;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();

	public GameManager() {

		objects.add(new Player(1, 5));
		loadLevel("/level0.png");
	}
	
	@Override
	public void init(GameContainer gc) {
		gc.getRenderer().setAmbientColor(-1);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).update(gc, this, dt);
			if(objects.get(i).isDead()) {
				objects.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public void render(GameContainer gc, Renderer renderer) {
		
		for(int y=0; y<levelHeight; y++) {
			for(int x=0; x<levelWidth; x++) {
				if(collision[x+y*levelWidth]) {
					renderer.drawFillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, 0xff0f0f0f);
				}else {
					renderer.drawFillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE, 0xfff9f9f9);
				}
			}
		}
		
		for(GameObject object : objects) {
			object.render(gc, renderer);
		}
	}
	
	public void loadLevel(String path) {
		
		Image levelImage = new Image(path);
		levelWidth = levelImage.getWidth();
		levelHeight = levelImage.getHeight();
		collision = new boolean[levelWidth * levelHeight];
		
		for(int y=0; y<levelImage.getHeight(); y++) {
			for(int x=0; x<levelImage.getWidth(); x++) {
				
				if(levelImage.getPixel()[x+y*levelImage.getWidth()] == 0xff000000) {
					collision[x+y*levelImage.getWidth()] = true;
				}else {
					collision[x+y*levelImage.getWidth()] = false;
				}
			}
		}
	}
	
	public boolean getCollision(int x, int y) {
		if(x<0 || x>levelWidth || y<0 || y>levelHeight)
			return true;
		return collision[x+y*levelWidth];
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f); 
		gc.start();
	}
}
