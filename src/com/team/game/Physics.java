/**
 * 
 */
package com.team.game;

import java.util.ArrayList;

import com.team.game.components.AABBComponent;

/**
 * @author Pedro
 *
 */
public class Physics {
	private static ArrayList<AABBComponent> aabblist = new ArrayList<AABBComponent>();
	
	public static void addAABBComponent(AABBComponent aabb) {
		aabblist.add(aabb);
	}
	
	public static void update() {
		for(int i=0; i<aabblist.size(); i++) {
			for(int j=i+1; j<aabblist.size(); j++) {
				AABBComponent component0 = aabblist.get(i);
				AABBComponent component1 = aabblist.get(j);
				
				if(Math.abs(component0.getCenterX()-component1.getCenterX()) < component0.getHalfWidth()+component1.getHalfWidth()) {
					if(Math.abs(component0.getCenterY()-component1.getCenterY()) < component0.getHalfHeight()+component1.getHalfHeight()) {
						component0.getParent().collision(component1.getParent());
						component1.getParent().collision(component0.getParent());
					}
				}
			}
		}
		
		aabblist.clear();
	}
}
