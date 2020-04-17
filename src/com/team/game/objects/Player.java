/**
 * 
 */
package com.team.game.objects;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.audio.SoundClip;
import com.team.engine.gfx.ImageTile;
import com.team.game.GameManager;
import com.team.game.components.AABBComponent;

/**a
 * @author Pedro
 *
 */
public class Player extends GameObject{
	
	private ImageTile playerImage = new ImageTile("/player4.png", 16, 16);
	private SoundClip sonidoDanio = new SoundClip("/Audio/ouch.wav");;
	
	private int direction = 0;
	private float animation = 0;
	private int tileX, tileY;
	private float offsetX, offsetY;
	private boolean reachedCheckpoint = false;
	private int revivirX, revivirY;
	
	//megaSalto
	float posX2 = 0, posY2 = 0;
	float pressX = 0, pressY = 0;
	float distX = 0, distY = 0;
	float distX2 = 0, distY2 = 0;
	float fuerza = 0, fuerza2 = 0;
	float angulo = 0, angulo2 = 0;
	boolean megaSaltando = false;
	float tiempo = 0;
	//
	
	private float speed = 100;
	private float fallSpeed = 10;
	private float jump = -4f;
	private boolean ground = false;
	private boolean groundLast = false;
	
	private float fallDistance = 0;
	
	public Player(int posX, int posY) {
		this.tag = "player";
		this.tileX = posX;
		this.tileY = posY;
		this.offsetX = 0;
		this.offsetY = 0;
		this.posX = posX*GameManager.TILE_SIZE;
		this.posY = posY*GameManager.TILE_SIZE;
		this.width = GameManager.TILE_SIZE;
		this.height = GameManager.TILE_SIZE;
<<<<<<< HEAD
		paddingLeft = 3;
		paddingRight = 4;
		paddingTop = 1;
=======
		paddingLeft = 2;
		paddingRight = 4;
		paddingTop = 0;
>>>>>>> baef4f0083f7b895e8a2f68501fb7178ecc07d00
		paddingBot = 0;

		sonidoDanio.setVolume(-30);
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		if(!megaSaltando)
			tiempo = 0;
		if(megaSaltando) {
			tiempo += dt*2;
			
			if((fuerza*Math.cos(angulo)) > 0) {
				if(gm.getCollision(tileX+1, tileY) || gm.getCollision(tileX+1, tileY + (int)Math.signum((int)offsetY))) {
					offsetX += (fuerza*Math.cos(angulo));
					if(offsetX > paddingRight) {
						offsetX = paddingRight;
						megaSaltando = false;
					}
				}else
					offsetX += (fuerza*Math.cos(angulo));
			}else if((fuerza*Math.cos(angulo)) < 0) {
				if(gm.getCollision(tileX-1, tileY)|| gm.getCollision(tileX-1, tileY + (int)Math.signum((int)offsetY))) {
					offsetX += (fuerza*Math.cos(angulo));
					if(offsetX < -paddingLeft) {
						offsetX = -paddingLeft;
						megaSaltando = false;
					}
				}else
					offsetX += (fuerza*Math.cos(angulo)); //offsetX += (float)(fuerza*Math.cos(angulo)*tiempo);
			}
			
			if(((fuerza*Math.sin(angulo)) + (fallSpeed*tiempo) < 0)) {
				if((gm.getCollision(tileX, tileY-1) || gm.getCollision(tileX + (int)Math.signum((int)((offsetX>paddingRight || offsetX<-paddingLeft) ? offsetX : 0)), tileY-1)) && offsetY < -paddingTop) {
					fallDistance = 0;
					offsetY = -paddingTop;
					megaSaltando = false;
				}
			}
			/*
			if(fallDistance > 0) {
				if((gm.getCollision(tileX, tileY+1) || gm.getCollision(tileX + (int)Math.signum((int)((offsetX>paddingRight || offsetX<-paddingLeft) ? offsetX : 0)), tileY+1)) && offsetY > 0) {
					fallDistance = 0;
					offsetY = 0;
					ground = true;
				}
			}
			*/
			
			if(((fuerza*Math.sin(angulo)) + (fallSpeed*tiempo) > 0)) {
				if(gm.getCollision(tileX, tileY+1) || gm.getCollision(tileX + (int)Math.signum((int)((offsetX>paddingRight || offsetX<-paddingLeft) ? offsetX : 0)), tileY+1) && offsetY > 0) {
					fallDistance = 0;
					offsetY = 0;
					ground = true;
					megaSaltando = false;
				}
			}
			offsetY += ((fuerza*Math.sin(angulo)) + (fallSpeed*tiempo));//*Math.pow(tiempo, 2)/2
		}else {
			
			if(gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
				pressX = gc.getInput().getMouseX();
				pressY = gc.getInput().getMouseY();
			}
			
			
			if(gc.getInput().isButtonUp(MouseEvent.BUTTON1) && (gc.getInput().getMouseY()-pressY) > 0) {
				distX = gc.getInput().getMouseX()-pressX;
				distY = gc.getInput().getMouseY()-pressY;
				angulo = (float) Math.atan(distY/distX);
				fuerza = (float) Math.hypot(distX, distY)/5;
				if(fuerza > 7.5f)
					fuerza = 7.5f;
				if(angulo > 0)
					angulo += Math.PI;
				megaSaltando = true;
			}
			//posX = (float)(fuerza*Math.cos(angulo)*dt);
			//posY = (float)(fuerza*Math.sin(angulo)*dt - fallSpeed*dt*dt/2);
		
		//Left and Right
		if(gc.getInput().isKey(KeyEvent.VK_D)) {
			if(gm.getCollision(tileX+1, tileY) || gm.getCollision(tileX+1, tileY + (int)Math.signum((int)offsetY))) {
				offsetX += dt*speed;
				if(offsetX > paddingRight)
					offsetX = paddingRight;
			}else {
				offsetX += dt*speed;
			}
		}
		if(gc.getInput().isKey(KeyEvent.VK_A)) {
			if(gm.getCollision(tileX-1, tileY) || gm.getCollision(tileX-1, tileY + (int)Math.signum((int)offsetY))) {
				offsetX -= dt*speed;
				if(offsetX < -paddingLeft)
					offsetX = -paddingLeft;
			}else {
				offsetX -= dt*speed;
			}
		}
		
		//Beginning of Jump and Gravity
		fallDistance += dt*fallSpeed-0.005;
		
		if(fallDistance < 0) {
			if((gm.getCollision(tileX, tileY-1) || gm.getCollision(tileX + (int)Math.signum((int)((offsetX>paddingRight || offsetX<-paddingLeft) ? offsetX : 0)), tileY-1)) && offsetY < -paddingTop) {
				fallDistance = 0;
				offsetY = -paddingTop;
			}
		}
		
		if(fallDistance > 0) {
			if((gm.getCollision(tileX, tileY+1) || gm.getCollision(tileX + (int)Math.signum((int)((offsetX>paddingRight || offsetX<-paddingLeft) ? offsetX : 0)), tileY+1)) && offsetY > 0) {
				fallDistance = 0;
				offsetY = 0;
				ground = true;
			}
		}
		
		if(gc.getInput().isKeyDown(KeyEvent.VK_W) && ground){
			fallDistance = jump;
			ground = false;
		}
		
		offsetY += fallDistance; 
		//End of Jump and Gravity 
		}
		//Final Position
		if(offsetY > GameManager.TILE_SIZE/2) {
			tileY++;
			offsetY -= GameManager.TILE_SIZE;
		}

		if(offsetY < -GameManager.TILE_SIZE/2) {
			tileY--;
			offsetY += GameManager.TILE_SIZE;
		}
		if(offsetX > GameManager.TILE_SIZE/2) {
			tileX++;
			offsetX -= GameManager.TILE_SIZE;
		}

		if(offsetX < -GameManager.TILE_SIZE/2) {
			tileX--;
			offsetX += GameManager.TILE_SIZE;
		}
		
		posX = tileX*GameManager.TILE_SIZE + offsetX;
		posY = tileY*GameManager.TILE_SIZE + offsetY;
		
		//Disparando
		if(gc.getInput().isKeyDown(KeyEvent.VK_UP)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX + width/2, offsetY + height/2, 0));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_RIGHT)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX + width/2, offsetY + height/2, 1));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_DOWN)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX+ width/2, offsetY + height/2, 2));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_LEFT)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX+ width/2, offsetY + height/2, 3));
		}
			
		if(gc.getInput().isKey(KeyEvent.VK_D)) {
			direction = 0;
			animation += dt*10;
			if(animation>=2)
				animation = 0;
		}
		else if(gc.getInput().isKey(KeyEvent.VK_A)) {
			direction = 1;
			animation += dt*10;
			if(animation>=2)
				animation = 0;
		}
		else
			animation = 0;
		
		if((int)fallDistance != 0) {
			animation = 1;
			ground = false;
		}
		
		if(ground && !groundLast) {
			animation = 0;
		} 
		groundLast = ground;
		
		this.updateComponents(gc, gm, dt);
	}
	
	@Override
	public void render(GameContainer gc, Renderer renderer) {
		//renderer.drawFillRect((int)posX, (int)posY, GameManager.TILE_SIZE, GameManager.TILE_SIZE, 0xff107a2a);
		renderer.drawImageTile(playerImage, (int)posX, (int)posY, (int)animation, direction);
		
		if(gc.getInput().isButton(MouseEvent.BUTTON1) && ((gc.getInput().getMouseY()-pressY) > 0)) {
			posX2 = posX+width/2;
			posY2 = posY+height/2;
			
			distX2 = gc.getInput().getMouseX()-pressX;
			distY2 = gc.getInput().getMouseY()-pressY;
			angulo2 = (float) Math.atan(distY2/distX2);
			fuerza2 = (float) Math.hypot(distX2, distY2)/5;
			if(fuerza2 > 7.5f)
				fuerza2 = 7.5f;
			if(angulo2 > 0)
				angulo2 += Math.PI;
			for(float T=(float) 0.5; T<1.5; T+=0.033) {
				posX2 +=  (fuerza2*Math.cos(angulo2)*1.3);
				posY2 += (fuerza2*Math.sin(angulo2)*1.5 + ((fallSpeed*T)*0.9));
				renderer.drawFillRect( (int)posX2, (int)posY2, 1, 1, 0xff939393);
			}
		}
		
		this.renderComponents(gc, renderer);
	}

	@Override
	public void collision(GameObject other) {
		if(other.getTag().equalsIgnoreCase("platform")) {
			megaSaltando = false;
			AABBComponent myComponent = (AABBComponent) this.findComponent("aabb");
			AABBComponent otherComponent = (AABBComponent) other.findComponent("aabb");
			
			if(Math.abs(myComponent.getLastCenterX()-otherComponent.getLastCenterX()) < myComponent.getHalfWidth()+otherComponent.getHalfWidth()) {
				if(myComponent.getCenterY() < otherComponent.getCenterY()) {
					int distance = (myComponent.getHalfHeight()+otherComponent.getHalfHeight()) - (otherComponent.getCenterY() - myComponent.getCenterY());
					offsetY -= distance;
					posY -= distance;
					myComponent.setCenterY(myComponent.getCenterY()-distance);
					fallDistance = 0;
					ground = true;
				}
				if(myComponent.getCenterY() > otherComponent.getCenterY()) {
					int distance = (myComponent.getHalfHeight()+otherComponent.getHalfHeight()) - (myComponent.getCenterY() - otherComponent.getCenterY());
					offsetY += distance;
					posY += distance;
					myComponent.setCenterY(myComponent.getCenterY()+distance);
					fallDistance = 0;
				}
			}else {
				if(myComponent.getCenterX() < otherComponent.getCenterX()) {
					int distance = (myComponent.getHalfWidth()+otherComponent.getHalfWidth()) - (otherComponent.getCenterX() - myComponent.getCenterX());
					offsetX -= distance;
					posX -= distance;
					myComponent.setCenterX(myComponent.getCenterX()-distance);
				}
				if(myComponent.getCenterX() > otherComponent.getCenterX()) {
					int distance = (myComponent.getHalfWidth()+otherComponent.getHalfWidth()) - (myComponent.getCenterX() - otherComponent.getCenterX());
					offsetX += distance;
					posX += distance;
					myComponent.setCenterX(myComponent.getCenterX()+distance);
				}
			}
		}
		
		//Empieza colision con sierras
		if(other.getTag().equalsIgnoreCase("saw")) {
			sonidoDanio.start();
			this.dead = true;
		}
		//Termina colision con sierras
		
		//Colision con spikes/pinches
		if(other.getTag().equalsIgnoreCase("spikes")){
			sonidoDanio.start();
			this.dead = true;
		}
		
		//Colison con flag/checkpoint
		if(other.getTag().equalsIgnoreCase("flag")) {
			reachedCheckpoint = true;
			AABBComponent otherComponent = (AABBComponent) other.findComponent("aabb");
			revivirX = otherComponent.getCenterX()/GameManager.TILE_SIZE;
			revivirY = otherComponent.getCenterY()/GameManager.TILE_SIZE;
			}
	}

	public boolean isReachedCheckpoint() {
		return reachedCheckpoint;
	}

	public void setReachedCheckpoint(boolean reachedCheckpoint) {
		this.reachedCheckpoint = reachedCheckpoint;
	}

	public int getRevivirX() {
		return revivirX;
	}

	public void setRevivirX(int revivirX) {
		this.revivirX = revivirX;
	}

	public int getRevivirY() {
		return revivirY;
	}

	public void setRevivirY(int revivirY) {
		this.revivirY = revivirY;
	}


}
