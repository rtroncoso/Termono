package com.quest.helpers;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;

import com.quest.display.Display;
import com.quest.game.Game;
import com.quest.scenes.GameScene;
import com.quest.scenes.InventoryScene;
import com.quest.scenes.MainMenuScene;

public class SceneHelper {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Scene mScene;
	private Game mGame;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public SceneHelper(Game pGame){
		this.mGame = pGame;
    }
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
    public void setMainMenuScene(){
    	this.mScene = new MainMenuScene();
        this.mGame.getEngine().setScene(this.mScene);
    }

    public void setGameScene(){
    	final GameScene gs = new GameScene(this.mGame);
    	this.mGame.getEngine().setScene(gs);
    	this.mGame.getDisplay().doFocusCamera(gs.getHero());
    	this.mScene = gs;
    }

    public void setInventoryScene(){
    	this.mGame.getDisplay().setCamera(new SmoothCamera(0, 0, this.mGame.getDisplay().getCameraWidth(), this.mGame.getDisplay().getCameraHeight(), 170, 170, 1.7f));
    	this.mScene = new InventoryScene(this.mGame);
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
