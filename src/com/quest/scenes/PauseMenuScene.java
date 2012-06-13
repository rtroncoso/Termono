package com.quest.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.display.hud.StatsHud;
import com.quest.game.Game;
import com.quest.helpers.SceneHelper;

public class PauseMenuScene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	//Texturas
	private BitmapTextureAtlas mPauseMenuTextureAtlas;
	private ITextureRegion mBackgroundTextureRegion;
	private ITextureRegion mOptionsTextureRegion;
	private ITextureRegion mResumeTextureRegion;
	private ITextureRegion mQuitTextureRegion;
	private Sprite mBackgroundSprite;
	private Sprite mResumeSprite;
	private Sprite mOptionsSprite;
	private Sprite mQuitSprite;
	// ===========================================================
	// Constructors
	// ===========================================================
	public PauseMenuScene(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/PauseMenu/");
		this.mPauseMenuTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);		
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPauseMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Background.jpg", 0, 0);
		this.mResumeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPauseMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Resume.jpg", 0, 480);
		this.mOptionsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPauseMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Options.jpg", 256, 480);
		this.mQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mPauseMenuTextureAtlas, Game.getInstance().getApplicationContext(), "QuittoMainMenu.jpg", 512, 480);
		this.mPauseMenuTextureAtlas.load();
		
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		this.attachChild(mBackgroundSprite);
				
		this.mResumeSprite = new Sprite(80, 200,this.mResumeTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mResumeSprite.setScale(1.5f);
					case TouchEvent.ACTION_CANCEL:
					case TouchEvent.ACTION_OUTSIDE:
						mResumeSprite.setScale(1.0f);
						break;
					case TouchEvent.ACTION_UP:
						mResumeSprite.setScale(1.0f);
						Game.getSceneManager().setGameScene();
						break;
					}
					return true;
					}					
				};
		
				this.mOptionsSprite = new Sprite(535, 200,this.mOptionsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mOptionsSprite.setScale(1.5f);
					case TouchEvent.ACTION_CANCEL:
					case TouchEvent.ACTION_OUTSIDE:
						mOptionsSprite.setScale(1.0f);
						break;
					case TouchEvent.ACTION_UP:
						mOptionsSprite.setScale(1.0f);
						Game.getSceneManager().setOptionsScene();
						break;
					}
					return true;
					}					
				};
				
				this.mQuitSprite = new Sprite(315, 360,this.mQuitTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mQuitSprite.setScale(1.5f);
					case TouchEvent.ACTION_CANCEL:
					case TouchEvent.ACTION_OUTSIDE:
						mQuitSprite.setScale(1.0f);
						break;
					case TouchEvent.ACTION_UP:
						mQuitSprite.setScale(1.0f);
						Game.getSceneManager().setMainMenuScene();
						break;
					}
					return true;
					}					
				};
				
				
				this.registerTouchArea(this.mResumeSprite);
				this.registerTouchArea(this.mOptionsSprite);
				this.registerTouchArea(this.mQuitSprite);
				
				this.attachChild(this.mResumeSprite);
				this.attachChild(this.mOptionsSprite);
				this.attachChild(this.mQuitSprite);
				
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
