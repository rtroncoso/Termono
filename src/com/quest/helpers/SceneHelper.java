package com.quest.helpers;

import org.andengine.entity.scene.Scene;

import com.quest.display.Display;
import com.quest.game.Game;
import com.quest.scenes.GameScene;
import com.quest.scenes.InventoryScene;
import com.quest.scenes.MainMenuScene;
import com.quest.scenes.OptionsScene;
import com.quest.scenes.PauseMenuScene;

public class SceneHelper {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Scene mScene;
	private Game mGame;
	private Display mDisplay;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public SceneHelper(Game pGame){
		this.mGame = pGame;
		
		this.mDisplay = new Display(this.mGame.getWindowManager().getDefaultDisplay().getWidth(),
				this.mGame.getWindowManager().getDefaultDisplay().getHeight(), 1.0f);
    }

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void setDisplay(Display pDisplay) {
		this.mDisplay = pDisplay;
	}

	public Display getDisplay() {
		return mDisplay;
	}
	
    public void setMainMenuScene(){
    	this.mDisplay.getCamera().setZoomFactor(1.0f);
    	this.mScene = new MainMenuScene(this.mGame);
        this.mGame.getEngine().setScene(this.mScene);
    }

    public void setPauseMenuScene(){
    	this.mDisplay.getCamera().setZoomFactor(1.0f);
    	this.mScene = new PauseMenuScene(this.mGame);
        this.mGame.getEngine().setScene(this.mScene);    	
    }
    
    public void setGameScene(){
    	this.mDisplay.getCamera().setZoomFactor(1.7f);
    	final GameScene gs = new GameScene(this.mGame);
    	this.mGame.getEngine().setScene(gs);
    	this.mDisplay.doFocusCamera(gs.getHero());
    	this.mScene = gs;
    }

    public void setInventoryScene(){
    	this.mDisplay.getCamera().setZoomFactor(1.7f);
    	this.mDisplay.getCamera().setCenter(400, 240);
    	this.mScene = new InventoryScene(this.mGame);
    	this.mGame.getEngine().setScene(this.mScene);
    }
        
    public void setOptionsScene(){
    	this.mScene = new OptionsScene(this.mGame);
        this.mGame.getEngine().setScene(this.mScene);
    }
    
    public Scene getCurrScene(){
        return this.mScene;
    }
    
    

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
