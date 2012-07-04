package com.quest.game;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.KeyEvent;

import com.quest.helpers.MapHelper;
import com.quest.helpers.SceneHelper;
import com.quest.helpers.TextureHelper;

public class Game extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private static SceneHelper mSceneManager;
	private static MapHelper mMapManager;
	private static TextureHelper mTextureManager;
	private static Game mInstance;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Game.setInstance(this);
		
		// Init Objects
		Game.mSceneManager = new SceneHelper();
		Game.mTextureManager = new TextureHelper();
		
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth(), 
						Game.getInstance().getWindowManager().getDefaultDisplay().getHeight()), 
				Game.mSceneManager.getDisplay().getCamera());
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		
		// Return the Engine Options
		return engineOptions;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		// TODO Auto-generated method stub
		return new FixedStepEngine(pEngineOptions, 60);
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		Game.mTextureManager.loadSpritesheet("Players");
		Game.mTextureManager.loadSpritesheet("Mobs");
	}

		
	
	@Override
	protected Scene onCreateScene() {

		Game.mMapManager = new MapHelper();
		
		//###################################################
		//	this.mSceneManager.setGameScene();
		//this.mSceneManager.setInventoryScene();
		Game.mSceneManager.setMainMenuScene();
		//this.mSceneManager.setPauseMenuScene();
		//###################################################

		return Game.mSceneManager.getCurrScene();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public static SceneHelper getSceneManager() {
		return Game.mSceneManager;
	}

	public static void setSceneManager(SceneHelper pSceneManager) {
		Game.mSceneManager = pSceneManager;
	}

	/**
	 * @return the mMapManager
	 */
	public static TextureHelper getTextureHelper() {
		return Game.mTextureManager;
	}

	/**
	 * @param mMapManager the mMapManager to set
	 */
	public static void setTextureManager(TextureHelper pTextureManager) {
		Game.mTextureManager = pTextureManager;
	}
	
	/**
	 * @return the mMapManager
	 */
	public static MapHelper getMapManager() {
		return Game.mMapManager;
	}

	/**
	 * @param mMapManager the mMapManager to set
	 */
	public static void setMapManager(MapHelper pMapManager) {
		Game.mMapManager = pMapManager;
	}
	
	/**
	 * @return the mInstance
	 */
	public static Game getInstance() {
		if(mInstance == null) {
			mInstance = new Game();
		}
		return mInstance;
	}

	/**
	 * @param mInstance the mInstance to set
	 */
	public static void setInstance(Game mInstance) {
		Game.mInstance = mInstance;
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
