/**
 * 
 */
package com.team.game.components;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.game.GameManager;
import com.team.game.objects.GameObject;

/**
 * @author Pedro
 *
 */
public abstract class Component {
	protected String tag;
	
	public abstract void update(GameContainer gc, GameManager gm, float dt);
	public abstract void render(GameContainer gc, Renderer renderer);
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
