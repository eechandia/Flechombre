/**
 * 
 */
package com.pedro.game;

import com.pedro.engine.AbstractGame;
import com.pedro.engine.GameContainer;
import com.pedro.engine.Renderer;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {
	
	public GameManager() {
		
	}

	@Override
	public void update(GameContainer gc, float dt) {

	}

	@Override
	public void render(GameContainer gc, Renderer r) {

	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
