package com.quest.helpers;

import org.andengine.entity.scene.Scene;

import android.util.Log;

import com.quest.display.Display;
import com.quest.game.Game;
import com.quest.scenes.GameMenuScene;
import com.quest.scenes.GameScene;
import com.quest.scenes.LoadingScene;
import com.quest.scenes.MainMenuScene;
import com.quest.scenes.MatchScene;
import com.quest.scenes.OptionsScene;
import com.quest.scenes.PauseMenuScene;
import com.quest.util.constants.IMeasureConstants;

public class SceneHelper implements IMeasureConstants {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Scene mScene;
	private Scene mSavedSceneState;
	private Display mDisplay;
	private Display mSavedDisplayState;
	private GameScene mGameScene;
	private GameMenuScene mGameMenuScene;
	private MatchScene mMatchScene;
	private LoadingScene mLoadingScene;
	
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
    public void setMainMenuScene() {
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mScene = new MainMenuScene();
    	Game.getInstance().getEngine().setScene(this.mScene);
    }

    public void setPauseMenuScene() {
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mScene = new PauseMenuScene();
        Game.getInstance().getEngine().setScene(this.mScene);    	
    }
    
    public void setOptionsScene() {
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mScene = new OptionsScene();
    	Game.getInstance().getEngine().setScene(this.mScene);
    }
    
    public void setMatchScene() {
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mMatchScene = new MatchScene();
    	this.mScene = this.mMatchScene;
        Game.getInstance().getEngine().setScene(this.mScene);
    }
    
    public MatchScene getMatchScene() {
		return mMatchScene;
	}

	//#############JUEGO
    public void setGameScene(){
    	if(this.mGameScene == null) {
        	this.mGameScene = new GameScene();
    	}
    	this.mGameScene.initGame("1");
    }
    
    //############MISC
	public void setSpecificGameScene(GameScene pGameScene) {
		this.mGameScene = pGameScene;
		this.mScene = this.mGameScene;
		Game.getInstance().getEngine().setScene(pGameScene);
    	this.mDisplay.setZoom(1.6f);
		
	}
    
    public GameScene getGameScene() {
    	return this.mGameScene;
    }

    
    //############INTERFACES
    public void setGameMenuScene() {
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
    	this.mGameMenuScene = new GameMenuScene();
    	this.mScene = this.mGameMenuScene;
    	Game.getInstance().getEngine().setScene(this.mScene);
    }
    
    public GameMenuScene getGameMenuScene() {
    	return this.mGameMenuScene;
    }

	/**
	 * @param mLoadingScene the mLoadingScene to set
	 */
	public void setLoadingScene() {
    	this.mDisplay.getCamera().setCenter(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2, Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2);
    	this.mDisplay.setZoom(1.0f);
		if(this.mLoadingScene == null) this.mLoadingScene = new LoadingScene();
		this.mScene = this.mLoadingScene;
		Game.getInstance().getEngine().setScene(this.mScene);
	}
    
	/**
	 * @return the mLoadingScene
	 */
	public LoadingScene getLoadingScene() {
		return mLoadingScene;
	}
	
    public Scene getCurrScene() {
        return this.mScene;
    }
    
    
    
	// ===========================================================
	// Methods
	// ===========================================================
    public void saveCurrentSceneState(boolean pSaveDisplayState) {
    	this.mSavedSceneState = this.mScene;
    	if(pSaveDisplayState) this.mSavedDisplayState = this.mDisplay;
    }
    
    public void restoreSavedScene(boolean pRestoreSavedDisplay) {
    	if(this.mSavedSceneState == null) Log.e("Quest!", "SceneHelper - Error trying to restore a saved scene (NullPointerException)");
    	if(this.mSavedDisplayState == null && pRestoreSavedDisplay) Log.e("Quest!", "SceneHelper - Error trying to restore a saved display (NullPointerException)");
    	this.mScene = this.mSavedSceneState;
    	this.mDisplay = this.mSavedDisplayState;
        Game.getInstance().getEngine().setScene(this.mScene);  
    	
    }
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
