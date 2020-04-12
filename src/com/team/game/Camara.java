/**
 * 
 */
package com.team.game;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.game.objects.GameObject;

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
			
//		offsetX -= (int)(dt*(offsetX-targetX)*20);
//		offsetY -= (int)(dt*(offsetY-targetY)*20);
		offsetX = targetX;
		offsetY = targetY;
		
		if(offsetX<0) offsetX = 0;
		if(offsetY<0) offsetY = 0;
		if(offsetX+gc.getWidth() > gm.getLevelWidth()*GameManager.TILE_SIZE) offsetX = gm.getLevelWidth()*GameManager.TILE_SIZE-gc.getWidth();
		if(offsetY+gc.getHeight() > gm.getLevelHeight()*GameManager.TILE_SIZE) offsetY = gm.getLevelHeight()*GameManager.TILE_SIZE-gc.getHeight();
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
