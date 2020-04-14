/**
 * 
 */
package com.team.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.Image;
import com.team.game.objects.Diana;
import com.team.game.objects.Flag;
import com.team.game.objects.GameObject;
import com.team.game.objects.Platform;
import com.team.game.objects.Player;
import com.team.game.objects.Saw;
import com.team.game.objects.Spikes;

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
	private int playerCounter = 1;
	private boolean levelCreado = false;
	private int posPlayerX, posPlayerY;
	
	

	public GameManager() {
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
		if(!levelCreado) {
			switch (gc.getLevelSeleccionado()) {
			case 1:
				objects.add(new Player(1, 5));
				objects.add(new Platform(26, 7));
				objects.add(new Platform(29, 7));
				objects.add(new Platform(32, 7));
				objects.add(new Platform(35, 7));
				objects.add(new Saw(1, 26));
				objects.add(new Spikes(18, 19, 13, false));
				objects.add(new Spikes(1, 5, 15, true));
				objects.add(new Flag(20, 19));
				objects.add(new Flag(3, 17));
				objects.add(new Diana(32,28));
				
				loadLevel("/level2.png");
				camara = new Camara("player");
				
				levelCreado = true;
				break;
			case 2:
				//cargamos lvl2;
				break;
			default:
				objects.add(new Player(1, 5));
				objects.add(new Platform(26, 7));
				objects.add(new Platform(29, 7));
				objects.add(new Platform(32, 7));
				objects.add(new Platform(35, 7));
				objects.add(new Saw(1, 26));
				objects.add(new Spikes(18, 19, 13, false));
				objects.add(new Spikes(1, 5, 15, true));
				objects.add(new Flag(20, 19));
				objects.add(new Flag(3, 17));
				loadLevel("/level2.png");
				camara = new Camara("player");
				
				levelCreado = true;
				break;
			};
		}
		
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).update(gc, this, dt);
			if(objects.get(i).isDead()) {
				if(objects.get(i).getTag()=="player") {
					playerCounter-=1;
				}
				objects.remove(i);
				i--;
			}
		}
	
		
		//RESETEO DEL PERSONAJE
		if(gc.getInput().isKey(KeyEvent.VK_R)) {
			
			for(int i = 0; i<objects.size();i++){
				if(objects.get(i).getTag() == "player") {
					objects.remove(i);
					playerCounter-=1;
				}
			}
			if(playerCounter == 0) {
				objects.add(new Player(posPlayerX, posPlayerY));
				camara = new Camara("player");
				playerCounter+=1;
			}
		}
		
		//ACTUALIZAR CHECKPOINT
		if(playerCounter>0) {
			if(((Player) this.getObject("player")).isReachedCheckpoint()) {
				posPlayerX = ((Player) this.getObject("player")).getRevivirX();
				posPlayerY = ((Player) this.getObject("player")).getRevivirY();
				((Player) this.getObject("player")).setReachedCheckpoint(false);
			}
		}
		
		Physics.update();
		
		camara.update(gc, this, dt);
	}
	
	@Override
	public void render(GameContainer gc, Renderer renderer) {
		camara.render(renderer);
		
		switch (gc.getLevelSeleccionado()) {
		case 1:
			renderer.drawImage(background, 0, 0);
			renderer.drawImage(levelImage, 0, 0);
			break;
		case 2:
			//cargamos lvl2;
			break;
		default:
			renderer.drawImage(background, 0, 0);
			renderer.drawImage(levelImage, 0, 0);
			break;
		};
		
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
