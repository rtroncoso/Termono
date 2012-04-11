package com.superproyecto.game;

import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.graphics.Color;
import android.graphics.Typeface;

import com.superproyecto.display.Display;
import com.superproyecto.methods.Point;
import com.superproyecto.player.Player;

public class Game extends SimpleBaseGameActivity {

	/*
	 * PRIVATE FIELDS
	 */
	private Display mDisplay;
	private Scene mScene;
	private Player mHero;
	private TMXTiledMap mTMXTiledMap;
	private static int CAMERA_WIDTH = 480;
	private static int CAMERA_HEIGHT = 320;
	
	
	////////
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	
	///////
	
	/*
	 * CONSTRUCTORS
	 */
	

	/*
	 * METHODS
	 */

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

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		///////
		this.mFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256);
		
		this.mFont = new Font(this.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48, true, Color.BLACK);
		
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
		//////
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mScene = new Scene();

		/*
		 * LAYER 0 - FLOORS
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
		 * LAYER 1 - ENTITIES
		 */
		// Create the Player
		this.mHero = new Player(this, tmxLayer);
		this.mHero.loadTexture("1.png", 128, 128, 0, 0, 3, 4);

		// Center the Player in the Screen
		final float centerX = (this.mDisplay.getCameraWidth() - this.mHero.getTiledTextureRegion().getWidth()) / 2;
		final float centerY = (this.mDisplay.getCameraHeight() - this.mHero.getTiledTextureRegion().getHeight()) / 2;
		this.mHero.setPosition(new Point(centerX, centerY));
		this.getDisplay().doFocusCamera(this.mHero, tmxLayer);

		this.mScene.attachChild(this.mHero.getAnimatedSprite());
		///////////
		final Text elapsedText = new Text(100, 160, this.mFont, "Termono", "Tuvieja".length(), this.getVertexBufferObjectManager());
		this.mScene.attachChild(elapsedText);
 		///////////

		
		/*
		 * LAYER 3 - CONTROLS
		 */
		Control digitalControl = new Control(this, this.mHero);
		this.mScene.setChildScene(digitalControl.getDigitalOnScreenControl());
		this.mScene.attachChild(digitalControl.getDigitalOnScreenControl());
		
		return this.mScene;
	}

}
