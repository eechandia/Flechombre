/**
 * 
 */
package com.team.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.State;
import com.team.engine.audio.SoundClip;
import com.team.engine.gfx.Image;
import com.team.engine.gfx.Light;
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
	
	public static final int TILE_SIZE = 8;
	private Image levelImage;
	private Light light = new Light(150, 0xffffffff);
	private Image background;
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
	
	private boolean musicaSonando = false;
	
	private Image decoracion1;
	

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
		
		
		if(!musicaSonando) {
			sonidoLevel.setVolume(-20);
			sonidoLevel.start();
			musicaSonando = true;
		}
		
		
		
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
			switch (levelActual) {
			case 1:

				gc.getRenderer().setAmbientColor(-1);

				levelImage = new Image("/Niveles/Nivel1/lvl1.png");
				background = new Image("/background0.png");
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
				
			
			
				levelCreado = true;
				break;
				
			case 2:
				
				gc.getRenderer().setAmbientColor(-1);

				levelImage = new Image("/Niveles/Nivel2/lvl2.png");
				objects.add(new Player(8, 54));
				posPlayerX = 8;
				posPlayerY = 54;
				objects.add(new Flag(48, 54));
				
				//objects.add(new Spikes(28, 42, 54, false));
				
				objects.add(new Diana(4,2,1));
				loadLevel("/Niveles/Nivel2/new.png");
				camara.setTarget(this.getObject("player"));
				
				
				levelCreado = true;
				break;
			
			case 3:
				gc.getRenderer().setAmbientColor(0xff232323);
				levelImage = new Image("/Niveles/Nivel3/level0001.png");
				background = new Image("/Niveles/Nivel3/background.png");
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
				loadLevel("/Niveles/Nivel3/levelColision.png");
				camara.setTarget(this.getObject("player"));
				
				
				levelCreado = true;
				break;
				
			default:
				gc.setState(State.LEVELS);
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
		
		if(levelCreado)
			switch (gc.getLevelSeleccionado()) {
			case 1:
				renderer.drawImage(background, 0, 0);
				renderer.drawImage(levelImage, 0, 0);
				break;
			case 2:
				renderer.drawImage(levelImage, 0, 0);
				for(int y=0; y<levelHeight; y++) {
					for(int x=0; x<levelWidth; x++) {
						if(collision[x+y*levelWidth] == false)
							renderer.drawFillRect(x*8, y*8, 8, 8, 0xffffffff);
						else
							renderer.drawFillRect(x*8, y*8, 8, 8, 0);
					}
				}
				break;
			case 3:
				renderer.drawImage(background, 0, 0);
				renderer.drawImage(levelImage, 0, 0);
				if(playerCounter > 0)
					renderer.drawLight(light, (int) this.getObject("player").getPosX(), (int) this.getObject("player").getPosY());
				renderer.drawLight(light, 15*TILE_SIZE, 20*TILE_SIZE);
				break;
			default:
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
