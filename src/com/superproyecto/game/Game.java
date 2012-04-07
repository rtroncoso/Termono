package com.superproyecto.game;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.FixedStepEngine;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import com.superproyecto.display.Display;
import com.superproyecto.methods.Point;
import com.superproyecto.player.Player;

public class Game extends BaseGameActivity {

	/*
	 * PRIVATE FIELDS
	 */
	private Display mDisplay;
	private Scene mScene;
	private Player mHero;
	private TMXTiledMap mTMXTiledMap;
	private static int CAMERA_WIDTH = 480;
	private static int CAMERA_HEIGHT = 320;

	/*
	 * CONSTRUCTORS
	 */
	

	/*
	 * METHODS
	 */
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub

		// Init Objects
		this.mDisplay = new Display(CAMERA_WIDTH, CAMERA_HEIGHT, getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getHeight());

		// Return the Engine
		return new FixedStepEngine(new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						this.mDisplay.getDisplayWidth(),
						this.mDisplay.getDisplayHeight()),
				this.mDisplay.getCamera()), 60);
	}

	public void onLoadResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

	}

	public Scene onLoadScene() {
		// TODO Auto-generated method stub

		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mScene = new Scene();

		/*
		 * LAYER 0 - FLOORS
		 */
		try {
			final TMXLoader tmxLoader = new TMXLoader(this,
					this.mEngine.getTextureManager(),
					TextureOptions.BILINEAR_PREMULTIPLYALPHA, null);
			this.mTMXTiledMap = tmxLoader.loadFromAsset(this, "tmx/desert.tmx");
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}

		final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
		this.mScene.attachChild(tmxLayer);

		/*
		 * LAYER 1 - ENTITIES
		 */
		// Create the Player
		this.mHero = new Player(this, tmxLayer);
		this.mHero.loadTexture("1.png", 128, 128, 0, 0, 3, 4);

		// Center the Player in the Screen
		final int centerX = (this.mDisplay.getCameraWidth() - this.mHero.getTiledTextureRegion().getTileWidth()) / 2;
		final int centerY = (this.mDisplay.getCameraHeight() - this.mHero.getTiledTextureRegion().getTileHeight()) / 2;
		this.mHero.setPosition(new Point(centerX, centerY));
		this.getDisplay().doFocusCamera(this.mHero, tmxLayer);

		this.mScene.attachChild(this.mHero.getAnimatedSprite());

		/*
		 * LAYER 3 - CONTROLS
		 */
		Control digitalControl = new Control(this, this.mHero);
		this.mScene.setChildScene(digitalControl.getDigitalOnScreenControl());
		this.mScene.attachChild(digitalControl.getDigitalOnScreenControl());
		
		

		return this.mScene;
	}

	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

	/*
	 * GETTERS/SETTERS
	 */
	public void setDisplay(Display pDisplay) {
		this.mDisplay = pDisplay;
	}

	public Display getDisplay() {
		return this.mDisplay;
	}
	
	public Scene getScene() {
		return this.mScene;
	}
	
	public TMXTiledMap getTMXTiledMap() {
		return this.mTMXTiledMap;
	}
}
