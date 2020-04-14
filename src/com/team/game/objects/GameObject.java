/**
 * 
 */
package com.team.game.objects;

import java.util.ArrayList;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.game.GameManager;
import com.team.game.components.Component;

/**
 * @author Pedro
 *
 */
public abstract class GameObject {
	protected String tag;
	protected float posX, posY;
	protected int width, height;
	protected int paddingLeft, paddingRight, paddingTop, paddingBot;
	protected boolean dead = false;
	
	protected ArrayList<Component> components = new ArrayList<Component>();

	public abstract void update(GameContainer gc, GameManager gm, float dt);
	public abstract void render(GameContainer gc, Renderer renderer);
	public abstract void collision(GameObject other);
	
	public void updateComponents(GameContainer gc, GameManager gm, float dt) {
		for(Component component: components) {
			component.update(gc, gm, dt);
		}
	}
	
	public void renderComponents(GameContainer gc, Renderer renderer) {
		for(Component component: components) {
			component.render(gc, renderer);
		}
	}
	
	public void addComponent(Component component) {
		components.add(component);
	}
	
	public void removeComponent(String tag) {
		for(Component component: components) {
			if(component.getTag().equalsIgnoreCase(tag)) {
				components.remove(component);
			}
		}
//		for(int i=0; i<components.size(); i++) { lo mismo q lo de arriba en teoria
//			if(components.get(i).getTag().equalsIgnoreCase(tag)) {
//				components.remove(i);
//			}
//		}
	}
	
	public Component findComponent(String tag) {
		for(Component component: components) {
			if(component.getTag().equalsIgnoreCase(tag)) {
				return component;
			}
		}
		return null;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
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
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public int getPaddingLeft() {
		return paddingLeft;
	}
	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}
	public int getPaddingRight() {
		return paddingRight;
	}
	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}
	public int getPaddingTop() {
		return paddingTop;
	}
	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}
	public int getPaddingBot() {
		return paddingBot;
	}
	public void setPaddingBot(int paddingBot) {
		this.paddingBot = paddingBot;
	}
}
