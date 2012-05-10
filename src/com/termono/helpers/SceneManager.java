package com.termono.helpers;

import org.andengine.entity.scene.Scene;

import com.termono.game.Game;
import com.termono.scenes.GameScene;
import com.termono.scenes.InventoryScene;
import com.termono.scenes.MainMenuScene;

public class SceneManager {
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
	public SceneManager(Game pGame){
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
