package com.quest.helpers;

import org.andengine.entity.scene.Scene;

import com.quest.display.Display;
import com.quest.game.Game;
import com.quest.interfaces.IMeasureConstants;
import com.quest.scenes.ConectionScene;
import com.quest.scenes.GameMenuScene;
import com.quest.scenes.GameScene;
import com.quest.scenes.MainMenuScene;
import com.quest.scenes.OptionsScene;
import com.quest.scenes.PauseMenuScene;

public class SceneHelper implements IMeasureConstants {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Scene mScene;
	private Display mDisplay;
	private GameScene mGameScene;
	private GameMenuScene mGameMenuScene;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public SceneHelper() {
		
		this.mDisplay = new Display(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth(),
				Game.getInstance().getWindowManager().getDefaultDisplay().getHeight(), 1.7f);
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
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mScene = new MainMenuScene();
    	Game.getInstance().getEngine().setScene(this.mScene);
    }

    public void setPauseMenuScene(){
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mScene = new PauseMenuScene();
        Game.getInstance().getEngine().setScene(this.mScene);    	
    }
    
    public void setOptionsScene(){
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mScene = new OptionsScene();
    	Game.getInstance().getEngine().setScene(this.mScene);
    }
    
    public void setConectionScene(){
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mScene = new ConectionScene();
        Game.getInstance().getEngine().setScene(this.mScene);
    }
    
    //#############JUEGO
    public void setGameScene(){
    	if(this.mGameScene == null) {
        	this.mGameScene = new GameScene();
    	}
    	Game.getInstance().getEngine().setScene(this.mGameScene);
    	this.mScene = this.mGameScene;
    	this.mDisplay.setZoom(1.6f);
    }
    
    public GameScene getGameScene(){
    	return this.mGameScene;
    }

    
    //############INTERFACES
    public void setGameMenuScene(){
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mGameMenuScene = new GameMenuScene();
    	this.mScene = this.mGameMenuScene;
    	Game.getInstance().getEngine().setScene(this.mScene);
    }
    
    public GameMenuScene getGameMenuScene(){
    	return this.mGameMenuScene;
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
