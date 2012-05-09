package com.termono.game;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.termono.display.Display;
import com.termono.display.hud.ControlsHud;
import com.termono.display.hud.MenuHud;
import com.termono.display.hud.SpellbarHud;
import com.termono.display.hud.StatsHud;
import com.termono.helpers.SceneManager;
import com.termono.map.Map;
import com.termono.methods.Timers;
import com.termono.player.Enemy;
import com.termono.player.Player;

public class Game extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================
	private Display mDisplay;
	private SceneManager mSceneManager;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		//sdf
		// Init Objects
		this.mDisplay = new Display(CAMERA_WIDTH, CAMERA_HEIGHT, getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getHeight());
		
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mDisplay.getCamera());;
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
		}

	
	@Override
	protected Scene onCreateScene() {
		this.mSceneManager = new SceneManager(this);
		this.mSceneManager.setGameScene();
		return this.mSceneManager.getCurrScene();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void setDisplay(Display pDisplay) {
		this.mDisplay = pDisplay;
	}

	public Display getDisplay() {
		return mDisplay;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
