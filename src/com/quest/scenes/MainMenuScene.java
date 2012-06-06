package com.quest.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.TextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.display.Display;
import com.quest.game.Game;
import com.quest.helpers.SceneHelper;

public class MainMenuScene extends Scene {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Game mGame;
	private SceneHelper mSceneManager;
	
	private BitmapTextureAtlas mMainMenuTextureAtlas;
	private ITextureRegion mMainMenuTextureRegion;
	private ITextureRegion mOptionsTextureRegion;
	private ITextureRegion mPlayTextureRegion;
	private Sprite mMainMenuSprite;
	private Sprite mPlaySprite;
	private Sprite mOptionsSprite;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public MainMenuScene(Game pGame){
		this.mGame = pGame;
		this.mSceneManager = new SceneHelper(mGame);
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/MainMenu/");
		this.mMainMenuTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 2048,2048, TextureOptions.BILINEAR);		
		this.mMainMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMainMenuTextureAtlas, this.mGame.getApplicationContext(), "Background.jpg", 0, 0);
		this.mPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMainMenuTextureAtlas, this.mGame.getApplicationContext(), "Jugar.jpg", 0, 480);
		this.mOptionsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMainMenuTextureAtlas, this.mGame.getApplicationContext(), "Opciones.jpg", 256, 480);
		this.mMainMenuTextureAtlas.load();
		
		
		//Background
		this.mMainMenuSprite = new Sprite(0, 0, this.mMainMenuTextureRegion, this.mGame.getVertexBufferObjectManager());
		this.attachChild(mMainMenuSprite);
				
		//Play
		this.mPlaySprite = new Sprite(mMainMenuSprite.getWidth() - 300, 100,this.mPlayTextureRegion,this.mGame.getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				MainMenuScene.this.mPlaySprite.setScale(1.5f);
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_OUTSIDE:
				MainMenuScene.this.mPlaySprite.setScale(1.0f);
				break;
			case TouchEvent.ACTION_UP:
				MainMenuScene.this.mPlaySprite.setScale(1.0f);
				MainMenuScene.this.mSceneManager.setGameScene();
				break;
			}
			return true;
			}
			
		};
	
		
		//Opciones
		this.mOptionsSprite = new Sprite(mMainMenuSprite.getWidth() - 300, 300,this.mOptionsTextureRegion,this.mGame.getVertexBufferObjectManager()) {
				
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						MainMenuScene.this.mOptionsSprite.setScale(1.5f);
					case TouchEvent.ACTION_CANCEL:
					case TouchEvent.ACTION_OUTSIDE:
						MainMenuScene.this.mOptionsSprite.setScale(1.0f);
						break;
					case TouchEvent.ACTION_UP:
						MainMenuScene.this.mOptionsSprite.setScale(1.0f);
						MainMenuScene.this.mSceneManager.setOptionsScene();
						break;
					}
				return true;
			}
		};
		
		//Attach a la escena
		this.attachChild(mPlaySprite);
		this.attachChild(mOptionsSprite);

		
		//Registro Touch Areas
		this.registerTouchArea(this.mPlaySprite);
		this.registerTouchArea(this.mOptionsSprite);
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
