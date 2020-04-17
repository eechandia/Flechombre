/**
 * 
 */
package com.team.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.audio.SoundClip;
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
	private Image levelImage;
	private Image background = new Image("/background0.png");
	private SoundClip sonidoLevel = new SoundClip("/Audio/Level.wav");
	
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camara camara = new Camara("");
	
	private boolean[] collision;
	private int levelWidth, levelHeight;
	private int playerCounter = 1;
	private boolean levelCreado = false;
	private int posPlayerX, posPlayerY;
	private int levelActual;
	private boolean levelTerminado = false;
	

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
		levelActual = gc.getLevelSeleccionado();
		
		if(levelTerminado) {
			for(int i = 0; i<objects.size();i++){
				objects.get(i).removeComponent("aabb");
				objects.remove(i);
				i--;
			}
			levelCreado = false;
			levelTerminado = false;
		}
		
		if(!levelCreado) {
			switch (levelActual+1) {
			case 1:
			
				levelImage = new Image("/Niveles/Nivel1/lvl1.png");
				objects.add(new Player(5, 25));
				posPlayerX = 5;
				posPlayerY = 25;
				objects.add(new Saw(9, 17));
				objects.add(new Flag(36, 19));
				objects.add(new Flag(8,12));
				objects.add(new Diana(34,6,0));
				objects.add(new Spikes(4, 7, 17, false));
				loadLevel("/Niveles/Nivel1/colision1.png");
				camara.setTarget(this.getObject("player"));
				
				sonidoLevel.setVolume(-10);
				sonidoLevel.loop();
				
				levelCreado = true;
				break;
				
			case 2:
				levelImage = new Image("/Niveles/Nivel2/lvl2.png");
				objects.add(new Player(4, 26));
				posPlayerX = 4;
				posPlayerY = 26;
				objects.add(new Flag(24, 27));
				
				objects.add(new Spikes(14, 21, 27, false));
				
				objects.add(new Diana(2,1,1));
				loadLevel("/Niveles/Nivel2/colisionlvl2.png");
				camara.setTarget(this.getObject("player"));
				
				sonidoLevel.setVolume(-40);
				sonidoLevel.start();
				
				
				levelCreado = true;
				break;
			
			case 3:
				//levelImage = new Image("/Niveles/.png");
				objects.add(new Player(6, 22));
				posPlayerX = 6;
				posPlayerY = 22;
				objects.add(new Spikes(12, 20, 26, false));
				objects.add(new Spikes(10, 23, 5, false));
				objects.add(new Spikes(10, 23, 1, true));//hasta 25 de largo es el numero magico creo, llegue una vez
				objects.add(new Diana(35, 4,0));
				objects.add(new Saw(20, 13));
				objects.add(new Saw(17, 13));
				objects.add(new Saw(14, 13));
				objects.add(new Saw(11, 13));
				objects.add(new Flag(3, 11));
				objects.add(new Flag(7, 4));
				loadLevel("/Niveles/colisionLevel3.png");
				camara.setTarget(this.getObject("player"));
				
				sonidoLevel.setVolume(-40);
				sonidoLevel.start();
				
				
				levelCreado = true;
				break;
				
			default:
				gc.setLevelSeleccionado(1);
				levelImage = new Image("/Niveles/Nivel1/lvl1.png");
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
		if(gc.getInput().isKey(KeyEvent.VK_R) && levelCreado) {
			
			for(int i = 0; i<objects.size();i++){
				if(objects.get(i).getTag() == "player") {
					objects.remove(i);
					playerCounter-=1;
				}
			}
			if(playerCounter == 0) {
				objects.add(new Player(posPlayerX, posPlayerY));
				camara.setTarget(this.getObject("player"));
				playerCounter+=1;
			}
		}
		
		//ACTUALIZAR CHECKPOINT
		if(playerCounter>0 && levelCreado) {
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
			renderer.drawImage(background, 0, 0);
			renderer.drawImage(levelImage, 0, 0);
			break;
		case 3:
			for(int y=0; y<levelHeight; y++) {
				for(int x=0; x<levelWidth; x++) {
					if(collision[x+y*levelWidth] == false)
						renderer.drawFillRect(x*16, y*16, 16, 16, 0xffffffff);
					else
						renderer.drawFillRect(x*16, y*16, 16, 16, 0);
				}
			}
			break;
		case 4:
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

	public boolean isLevelTerminado() {
		return levelTerminado;
	}

	public void setLevelTerminado(boolean levelTerminado) {
		this.levelTerminado = levelTerminado;
	}
}
