package com.quest.helpers;

import org.andengine.entity.scene.Scene;

import com.quest.display.Display;
import com.quest.game.Game;
import com.quest.scenes.ConectionScene;
import com.quest.scenes.EquipmentScene;
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
	private GameScene mGameScene;
	
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public SceneHelper(Game pGame){
		this.mGame = pGame;
		
		this.mDisplay = new Display(this.mGame.getWindowManager().getDefaultDisplay().getWidth(),
				this.mGame.getWindowManager().getDefaultDisplay().getHeight(), 1.7f);
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
	//#############MENUS
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
    
    public void setOptionsScene(){
    	this.mDisplay.getCamera().setZoomFactor(1.0f);
    	this.mScene = new OptionsScene(this.mGame);
        this.mGame.getEngine().setScene(this.mScene);
    }
    
    public void setConectionScene(){
    	this.mScene = new ConectionScene(this.mGame);
        this.mGame.getEngine().setScene(this.mScene);
    }
    
    //#############JUEGO
    public void setGameScene(){
    	this.mDisplay.setZoom(1.7f);
    	this.mGameScene = new GameScene(this.mGame);
    	this.mGameScene.initPhysics();
    	this.mGameScene.loadMap("desert");
    	this.mGameScene.loadEntities();
    	this.mGameScene.loadHUD();
    	this.mGame.getEngine().setScene(this.mGameScene);
    	this.mDisplay.doFocusCamera(this.mGameScene.getHero());
    	this.mScene = this.mGameScene;
    }
    
    public GameScene getGameScene(){
    	return this.mGameScene;
    }

    
    //############INTERFACES
    public void setInventoryScene(){
    	this.mDisplay.setZoom(1.0f);
    	this.mDisplay.setPos(800, 240);
    	this.mScene = new InventoryScene(this.mGame);
    	this.mGame.getEngine().setScene(this.mScene);
    }
    
    public void setEquipmentScene(){
    	//this.mDisplay.getCamera().setCenter(400, 240);
    	this.mScene = new EquipmentScene(this.mGame);
    	this.mGame.getEngine().setScene(this.mScene);
    }
    
    
    //############MISC
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
