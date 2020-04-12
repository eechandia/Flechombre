/**
 * 
 */
package com.team.game;

import java.util.ArrayList;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.Image;
import com.team.game.objects.GameObject;
import com.team.game.objects.Platform;
import com.team.game.objects.Player;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {
	
	public static final int TILE_SIZE = 16;
	private Image levelImage = new Image("/level2Image.png");
	private Image background = new Image("/background0.png");
	
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camara camara;
	
	private boolean[] collision;
	private int levelWidth, levelHeight;
	

	public GameManager() {

		objects.add(new Player(1, 5));
		objects.add(new Platform());
		loadLevel("/level2.png");
		camara = new Camara("player");
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
	
	public GameObject getObject(String tag) {
		for(GameObject object: objects) {
			if(object.getTag().equals(tag)) {
				return object;
			}
		}
		return null;
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
		
		Physics.update();
		
		camara.update(gc, this, dt);
	}
	
	@Override
	public void render(GameContainer gc, Renderer renderer) {
		camara.render(renderer);
		
		renderer.drawImage(background, 0, 0);
		renderer.drawImage(levelImage, 0, 0);
		
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

	public int getLevelWidth() {
		return levelWidth;
	}

	public int getLevelHeight() {
		return levelHeight;
	}
}
