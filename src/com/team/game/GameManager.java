/**
 * 
 */
package com.team.game;

import java.util.ArrayList;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();

	public GameManager() {

		objects.add(new Player(1, 1));
	}
	
	@Override
	public void init(GameContainer gc) {
		gc.getRenderer().setAmbientColor(-1);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).update(gc, dt);
			if(objects.get(i).isDead()) {
				objects.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public void render(GameContainer gc, Renderer renderer) {
		
		for(GameObject object : objects) {
			object.render(gc, renderer);
		}
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f); 
		gc.start();
	}
}
