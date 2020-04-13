/**
 * 
 */
package com.team;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.Image;
import com.team.engine.gfx.ImageTile;

/**
 * @author Pedro
 *
 */
public class Menu {
	private Image levelImage = new Image("/level2Image.png");
	
	private int mouseX, mouseY;
	
	public ImageTile startButton = new ImageTile("/Menu/startButton.png", 48, 16);
	private int startX, startY;
	private int startClickeado = 0;
	public ImageTile levelsButton;
	private int levelsX, levelsY;
	public ImageTile helpButton;
	private int helpX, helpY;
	public ImageTile quitButton;
	private int quitX, quitY;
	
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImage(levelImage, 0, 0);
		
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
		
		renderer.drawText("FLECHOMBRE", gc.getWidth()/3+5, gc.getHeight()/4, 0xffff0000);
		
		if(gc.getInput().isButton(1) && (mouseX>=startX && mouseX<=startX+48) && (mouseY>=startY && mouseY<=startY+16)) {
			startClickeado = 1;
			gc.setState(GameContainer.STATE.GAME);
		}else
			startClickeado =0;
		renderer.drawImageTile(startButton, startX, startY, 0, startClickeado);
		
		renderer.drawRect(levelsX,levelsY, 48, 16, 0xffff0000);
		renderer.drawText("Levels", levelsX+7, levelsY+3, 0xffff0000);
		if(gc.getInput().isButton(1) && (mouseX>=levelsX && mouseX<=levelsX+48) && (mouseY>=levelsY && mouseY<=levelsY+16)) {
			System.out.println("levels");
		}
		
		renderer.drawRect(helpX, helpY, 48, 16, 0xffff0000);
		renderer.drawText("Help",helpX+12, helpY+3, 0xffff0000);
		if(gc.getInput().isButton(1) && (mouseX>=helpX && mouseX<=helpX+48) && (mouseY>=helpY && mouseY<=helpY+16)) {
			System.out.println("aiuda");
		}
		
		renderer.drawRect(quitX, quitY, 48, 16, 0xffff0000);
		renderer.drawText("Quit",quitX+11, quitY+3, 0xffff0000);
		if(gc.getInput().isButton(1) && (mouseX>=quitX && mouseX<=quitX+48) && (mouseY>=quitY && mouseY<=quitY+16)) {
			System.exit(0);
		}
	}
}
