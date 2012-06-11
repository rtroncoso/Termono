package com.quest.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.quest.display.hud.StatsHud;
import com.quest.game.Game;
import com.quest.helpers.SceneHelper;

public class EquipmentScene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Game mGame;
	private SceneHelper mSceneManager;
	
	private BitmapTextureAtlas mEquipmentTextureAtlas;
	private ITextureRegion mBackgroundTextureRegion;
	private ITextureRegion mQuitTextureRegion;
	private TiledTextureRegion mItemsTextureRegion;
	private TiledTextureRegion  mAttributesTextureRegion;
	private Sprite mBackgroundSprite;
	private Sprite mQuitSprite;
	private AnimatedSprite mItemsSprite;
	private AnimatedSprite mAttributesSprite;
	private boolean mCurrent = false;
	
	private StatsHud mStatsHud;
	// ===========================================================
	// Constructors
	// ===========================================================
	public EquipmentScene(Game pGame){
		this.mGame = pGame;
		this.mSceneManager = new SceneHelper(mGame);
		this.mStatsHud = new StatsHud(this.mGame);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Equipment/PauseMenu/");
		this.mEquipmentTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 1024,1024, TextureOptions.BILINEAR);		
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, this.mGame.getApplicationContext(), "Background.png", 0, 0);
		this.mQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, this.mGame.getApplicationContext(), "Quit.png", 200, 480);
		this.mItemsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mEquipmentTextureAtlas, this.mGame.getApplicationContext(), "Items.png", 0, 0, 1,2);
		this.mAttributesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mEquipmentTextureAtlas, this.mGame.getApplicationContext(), "Attributes.png", 0, 0, 1,2);		
		this.mEquipmentTextureAtlas.load();
		
		
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, this.mGame.getVertexBufferObjectManager());
		this.attachChild(mBackgroundSprite);
		
		this.attachChild(this.mStatsHud.getTermono());
		

		this.mItemsSprite = new AnimatedSprite(80, 200,this.mItemsTextureRegion,this.mGame.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
					this.animate(10);
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					mStatsHud.getTermono().setText(String.valueOf(this.getX())+ "  " + String.valueOf(this.getY()));
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_OUTSIDE:
				break;
			case TouchEvent.ACTION_UP:
				break;
			}
			return true;
			}					
		};
		
		
		this.mAttributesSprite = new AnimatedSprite(130, 200,this.mItemsTextureRegion,this.mGame.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
					this.animate(10);
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					mStatsHud.getTermono().setText(String.valueOf(this.getX())+ "  " + String.valueOf(this.getY()));
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_OUTSIDE:
				break;
			case TouchEvent.ACTION_UP:
				break;
			}
			return true;
			}					
		};
		
		
		
		
		
		
		
		
		
		
		
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
