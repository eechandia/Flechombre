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
import com.team.game.objects.Bat;
import com.team.game.objects.Diana;
import com.team.game.objects.Flag;
import com.team.game.objects.GameObject;
import com.team.game.objects.Player;
import com.team.game.objects.Saw;
import com.team.game.objects.Skeleton;
import com.team.game.objects.Spikes;
import com.team.game.objects.WallSpikes;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {
	
	public static final int TILE_SIZE = 8;
	private Image levelImage;
	private Light light = new Light(200, 0xffffffff);
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
			case 0:
				gc.getRenderer().setAmbientColor(-1);
				objects.add(new Player(6, 20));
				posPlayerX = 6;
				posPlayerY = 20;
				objects.add(new Spikes(53, 76, 23, false));
				objects.add(new Diana(142, 18,0));
				objects.add(new Flag(43, 18));
				
				loadLevel("/Niveles/Tutorial/levelColision.png");
				camara.setTarget(this.getObject("player"));
				levelCreado = true;
				break;
			case 1:		
				objects.add(new Player(5, 45));
				posPlayerX = 10;
				posPlayerY = 45;
				objects.add(new Saw(18, 34));
				objects.add(new Flag(72, 38));
				objects.add(new Flag(16,24));
				objects.add(new Diana(73,12,0));
				objects.add(new Spikes(8, 15, 35, false));
				objects.add(new Skeleton(25,52,25, 45));
				objects.add(new Skeleton(27,32,27, 30));
		
				levelImage = new Image("/Niveles/Nivel1/lvl1.png");
				background = new Image("/background0.png");
				loadLevel("/Niveles/Nivel1/newColision.png");
				camara.setTarget(this.getObject("player"));
				gc.getRenderer().setAmbientColor(-1);
				levelCreado = true;
				break;
				
			case 2:
				
				gc.getRenderer().setAmbientColor(-1);

				levelImage = new Image("/Niveles/Nivel2/lvl2.png");
				objects.add(new Player(4, 54));
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
				levelImage = new Image("/Niveles/Nivel3/level.png");
				background = new Image("/Niveles/Nivel3/background.png");
				objects.add(new Player(3, 44));
				posPlayerX = 3;
				posPlayerY = 44;
				objects.add(new Spikes(24, 40, 53, false));
				objects.add(new Spikes(20, 46, 11, false));
				objects.add(new Spikes(20, 46, 3, true));
				objects.add(new Diana(70, 8,0));
				objects.add(new Saw(40, 26));
				objects.add(new Saw(34, 26));
				objects.add(new Saw(28, 26));
				objects.add(new Saw(22, 26));
				objects.add(new Flag(6, 22));
				objects.add(new Flag(14, 8));
				objects.add(new WallSpikes(2, 2, 12, false));
				objects.add(new WallSpikes(2, 20, 25, false));
				objects.add(new WallSpikes(11, 12, 19, true));
				objects.add(new WallSpikes(77, 20, 43, true));
				objects.add(new Bat(18, 43));
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
		
		if(levelCreado && !levelTerminado)
			switch (gc.getLevelSeleccionado()) {
			case 0:
				for(int y=0; y<levelHeight; y++) {
					for(int x=0; x<levelWidth; x++) {
						if(collision[x+y*levelWidth] == false)
							renderer.drawFillRect(x*8, y*8, 8, 8, 0xffffffff);
						else
							renderer.drawFillRect(x*8, y*8, 8, 8, 0);
					}
				}
				break;
			case 1:
				renderer.drawImage(background, 0, 0);
				renderer.drawImage(levelImage, 0, 0);
				break;
			case 2:
				renderer.drawImage(levelImage, 0, 0);
				break;
			case 3:
				renderer.drawImage(background, 0, 0);
				renderer.drawImage(levelImage, 0, 0);
				if(playerCounter > 0)
					renderer.drawLight(light, (int) this.getObject("player").getPosX(), (int) this.getObject("player").getPosY());
				renderer.drawLight(light, 32*TILE_SIZE, 40*TILE_SIZE);
				renderer.drawLight(light, 32*TILE_SIZE, 20*TILE_SIZE);
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
