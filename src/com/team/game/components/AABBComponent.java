/**
 * 
 */
package com.team.game.components;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.game.GameManager;
import com.team.game.Physics;
import com.team.game.objects.GameObject;

/**
 * @author Pedro
 *
 */
public class AABBComponent extends Component{
	private GameObject parent;
	private int centerX, centerY;
	private int halfWidth, halfHeight;
	private int lastCenterX, lastCenterY;
	
	public AABBComponent(GameObject parent) {
		this.parent = parent;
		this.tag = "aabb";
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		lastCenterX = centerX;
		lastCenterY = centerY;
		
		centerX = (int) (parent.getPosX() + parent.getWidth()/2);
		centerY = (int) (parent.getPosY() + parent.getHeight()/2 + parent.getPaddingTop()/2);
		halfWidth = (parent.getWidth() - ((parent.getPaddingRight()+parent.getPaddingLeft())))/2;
		halfHeight = (parent.getHeight() - parent.getPaddingTop())/2;
		
		Physics.addAABBComponent(this);
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		//renderer.drawRect(centerX-halfWidth, centerY-halfHeight, halfWidth*2, halfHeight*2, 0xff000000);
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getHalfWidth() {
		return halfWidth;
	}

	public void setHalfWidth(int halfWidth) {
		this.halfWidth = halfWidth;
	}

	public int getHalfHeight() {
		return halfHeight;
	}

	public void setHalfHeight(int halfHeight) {
		this.halfHeight = halfHeight;
	}

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public int getLastCenterX() {
		return lastCenterX;
	}

	public void setLastCenterX(int lastCenterX) {
		this.lastCenterX = lastCenterX;
	}

	public int getLastCenterY() {
		return lastCenterY;
	}

	public void setLastCenterY(int lastCenterY) {
		this.lastCenterY = lastCenterY;
	}
	
}
