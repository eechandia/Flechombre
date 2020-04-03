/**
 * 
 */
package com.team.engine;

public abstract class AbstractGame {
	
	public abstract void init(GameContainer gc);
	public abstract void update(GameContainer cs, float dt);
	public abstract void render(GameContainer cs, Renderer r);
}
