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
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import com.termono.display.Display;
import com.termono.display.hud.ControlsHud;
import com.termono.display.hud.MenuHud;
import com.termono.display.hud.SpellbarHud;
import com.termono.display.hud.StatsHud;
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
	private Scene mScene;
	private Player mHero;
	private TMXTiledMap mTMXTiledMap;
	private ControlsHud mControlsHud;
	private SpellbarHud mSpellbarHud;
	private StatsHud mStatsHud;
	private HUD mHud;
	private boolean pZoomedIn;
	private Enemy mEnemy;
	private Timers mTimers;
	private MenuHud mMenuHud;
	
	private Enemy mMob2;
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
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());		
		this.mScene = new Scene();

		/*
		 * LAYER - MAP
		 */
		try {
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager());
			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/desert.tmx");
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}

		final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
		this.mScene.attachChild(tmxLayer);

		/*
		 * LAYER - ENTITIES
		 */
		// Create the Player
		this.mHero = new Player(this);
		this.mHero.loadTexture("Mage.png", 128, 256, 0, 0, 4, 4);

		// Center the Player in the Screen
		this.mHero.getAnimatedSprite().setPosition(0, 0 - (this.mHero.getTiledTextureRegion().getHeight() - 32));
		this.getDisplay().doFocusCamera(this.mHero);
		
		// Attach it
		this.mScene.attachChild(this.mHero.getAnimatedSprite());
		
		
		
		//Enemy
		
		this.mEnemy = new Enemy(this);
		this.mEnemy.loadTexture("Mob.png", 128, 256, 0, 0, 4, 4);
		this.mEnemy.getAnimatedSprite().setPosition(64, 64);
		this.mScene.attachChild(this.mEnemy.getAnimatedSprite());
		
		this.mMob2 = new Enemy(this);
		this.mMob2.loadTexture("Mob2.png", 128, 256, 0, 0, 4, 4);
		this.mMob2.getAnimatedSprite().setPosition(96, 96);
		this.mScene.attachChild(this.mMob2.getAnimatedSprite());
		
		//Timer
		this.mTimers = new Timers(this, mEnemy, mMob2);
		this.mTimers.createMobMovementTimeHandler();
		
		
		/*
		 * LAYER - HUDs
		 */
		this.mHud = new HUD();
		this.mStatsHud = new StatsHud(this, mEnemy);
		this.mSpellbarHud = new SpellbarHud(this, this.mHud);
		this.mControlsHud = new ControlsHud(this, this.mHero);
		this.mMenuHud = new MenuHud(this, mHud);
		
		this.mHud.setChildScene(this.mControlsHud.getDigitalOnScreenControl());
		this.mHud.registerTouchArea(this.mSpellbarHud.getSpellBar());
		this.mHud.attachChild(this.mSpellbarHud.getSpellBar());
		this.mHud.attachChild(this.mStatsHud.getTermono());
		this.mHud.attachChild(this.mControlsHud.getDigitalOnScreenControl());
		this.mHud.attachChild(this.mMenuHud.getMenuEntity());
		
		this.mDisplay.getCamera().setHUD(this.mHud);
		
		this.mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
				if(Game.this.mDisplay.getCamera().getZoomFactor() == 1.7f) { pZoomedIn = true; } 
				else if(Game.this.mDisplay.getCamera().getZoomFactor() == 1.0f) { pZoomedIn = false; }
				Game.this.mDisplay.getCamera().setZoomFactor((pZoomedIn) ? 1.0f : 1.7f);
				return true;
			}
		});
		
		return this.mScene;
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

	public Scene getScene() {
		return mScene;
	}

	public void setScene(Scene pScene) {
		this.mScene = pScene;
	}

	public TMXTiledMap getTMXTiledMap() {
		return mTMXTiledMap;
	}

	public void setTMXTiledMap(TMXTiledMap pTMXTiledMap) {
		this.mTMXTiledMap = pTMXTiledMap;
	}

	public ControlsHud getControlsHud() {
		return mControlsHud;
	}

	public void setControlsHud(ControlsHud pControlsHud) {
		this.mControlsHud = pControlsHud;
	}

	public StatsHud getStatsHud() {
		return mStatsHud;
	}

	public void setStatsHud(StatsHud pStatsHud) {
		this.mStatsHud = pStatsHud;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
