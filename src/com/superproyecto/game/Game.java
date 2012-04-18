package com.superproyecto.game;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import com.superproyecto.display.Display;
import com.superproyecto.display.hud.ControlsHud;
import com.superproyecto.display.hud.SpellbarHud;
import com.superproyecto.display.hud.StatsHud;
import com.superproyecto.methods.Point;
import com.superproyecto.player.Player;

public class Game extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	private static int CAMERA_WIDTH = 720;
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
	
	
	////////
	///////

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		// Init Objects
		this.mDisplay = new Display(CAMERA_WIDTH, CAMERA_HEIGHT, getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getHeight());

		// Return the Engine
		return new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						this.mDisplay.getDisplayWidth(),
						this.mDisplay.getDisplayHeight()),
				this.mDisplay.getCamera());
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
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(),
					this.mEngine.getTextureManager(),
					TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager());
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
		this.mHero.loadTexture("1.png", 128, 128, 0, 0, 3, 4);

		// Center the Player in the Screen
		final float centerX = (this.mDisplay.getCameraWidth() - this.mHero.getTiledTextureRegion().getWidth()) / 2;
		final float centerY = (this.mDisplay.getCameraHeight() - this.mHero.getTiledTextureRegion().getHeight()) / 2;
		this.mHero.setPosition(new Point(centerX, centerY));
		this.getDisplay().doFocusCamera(this.mHero, tmxLayer);
		
		this.mScene.attachChild(this.mHero.getAnimatedSprite());
		
		/*
		 * LAYER - HUDs
		 */
		
		this.mHud = new HUD();
		
		this.mStatsHud = new StatsHud(this);
		this.mSpellbarHud = new SpellbarHud(this);
		this.mControlsHud = new ControlsHud(this, this.mHero);
		
		this.mHud.setChildScene(this.mControlsHud.getDigitalOnScreenControl());
		this.mHud.attachChild(this.mSpellbarHud.getSpellBar());
		this.mHud.attachChild(this.mStatsHud.getTermono());
		this.mHud.attachChild(this.mControlsHud.getDigitalOnScreenControl());
		
		this.mDisplay.getCamera().setHUD(this.mHud);
		
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

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
