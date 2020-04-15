/**
 * 
 */
package com.team.game;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.State;
import com.team.engine.gfx.Image;
import com.team.engine.gfx.ImageTile;

/**
 * @author Pedro
 *
 */
public class Menu {
	private Image levelImage = new Image("/level2Image.png");
	
	private int mouseX, mouseY;
	private int startX, startY;
	private int levelsX, levelsY;
	private int helpX, helpY;
	private int quitX, quitY;
	
	public void update(GameContainer gc) {
		
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		startX = gc.getWidth()/3+20;
		startY = gc.getHeight()/4+25;
		levelsX = gc.getWidth()/3+20;
		levelsY = gc.getHeight()/4+50;
		helpX = gc.getWidth()/3+20;
		helpY = gc.getHeight()/4+75;
		quitX = gc.getWidth()/3+20;
		quitY =  gc.getHeight()/4+120;

		if(gc.getInput().isButtonUp(1) && (mouseX>=startX && mouseX<=startX+48) && (mouseY>=startY && mouseY<=startY+16)) {
			gc.setState(State.GAME);
			gc.setLevelSeleccionado(1);
		}
		
		if(gc.getInput().isButtonUp(1) && ((mouseX>=levelsX && mouseX<=levelsX+48) && (mouseY>=levelsY && mouseY<=levelsY+16))) {
			gc.setState(State.LEVELS);
		}
		
		if(gc.getInput().isButtonUp(1) && (mouseX>=helpX && mouseX<=helpX+48) && (mouseY>=helpY && mouseY<=helpY+16)) {
			System.out.println("aiuda");
		}
		
		if(gc.getInput().isButtonUp(1) && (mouseX>=quitX && mouseX<=quitX+48) && (mouseY>=quitY && mouseY<=quitY+16)) {
			System.exit(0);
		}
	}
	
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImage(levelImage, 0, 0);
		
		startX = gc.getWidth()/3+20;
		startY = gc.getHeight()/4+25;
		levelsX = gc.getWidth()/3+20;
		levelsY = gc.getHeight()/4+50;
		helpX = gc.getWidth()/3+20;
		helpY = gc.getHeight()/4+75;
		quitX = gc.getWidth()/3+20;
		quitY =  gc.getHeight()/4+120;
		
		renderer.drawText("FLECHOMBRE", gc.getWidth()/3+5, gc.getHeight()/4, 0xffff0000);

		renderer.drawRect(startX,startY, 48, 16, 0xffff0000);
		renderer.drawText("Start", startX+10, startY+3, 0xffff0000);
		
		renderer.drawRect(levelsX,levelsY, 48, 16, 0xffff0000);
		renderer.drawText("Levels", levelsX+7, levelsY+3, 0xffff0000);
		
		renderer.drawRect(helpX, helpY, 48, 16, 0xffff0000);
		renderer.drawText("Help",helpX+12, helpY+3, 0xffff0000);
		
		renderer.drawRect(quitX, quitY, 48, 16, 0xffff0000);
		renderer.drawText("Quit",quitX+11, quitY+3, 0xffff0000);
	}
}
