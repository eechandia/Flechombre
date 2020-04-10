/**
 * 
 */
package com.team.game;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;

/**
 * @author Pedro
 *
 */
public class Camara {
	
	private float offsetX, offsetY;
	
	private String targetTag;
	private GameObject target = null;
	
	public Camara(String tag) {
		this.targetTag = tag;
	}
	
	public void update(GameContainer gc, GameManager gm, float dt) {
		if(target == null) {
			target = gm.getObject(targetTag);
		}
		
		if(target == null)
			return;
		
		float targetX = (target.getPosX()+target.getWidth()/2) - gc.getWidth()/2;
		float targetY = (target.getPosY()+target.getHeight()/2) - gc.getHeight()/2;
			
		offsetX -= dt*(offsetX-targetX)*10;
		offsetY -= dt*(offsetY-targetY)*10;
	}
	
	public void render(Renderer renderer)  {
		renderer.setCamaraX((int)offsetX);
		renderer.setCamaraY((int)offsetY);
	}

	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public String getTargetTag() {
		return targetTag;
	}

	public void setTargetTag(String targetTag) {
		this.targetTag = targetTag;
	}

	public GameObject getTarget() {
		return target;
	}

	public void setTarget(GameObject target) {
		this.target = target;
	}
}
