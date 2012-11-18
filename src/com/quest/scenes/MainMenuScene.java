package com.quest.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.constants.GameFlags;
import com.quest.game.Game;
import com.quest.helpers.AttacksHelper;
import com.quest.helpers.BattleHelper;
import com.quest.helpers.ItemHelper;
import com.quest.helpers.MobHelper;
import com.quest.helpers.TextHelper;

public class MainMenuScene extends Scene implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mMainMenuTextureAtlas;
	private ITextureRegion mBoxTextureRegion;
	private ITextureRegion mSignsTextureRegion;
	private ITextureRegion mBackgroundTextureRegion;
	private Sprite mSignsSprite;
	private Sprite mBackgroundSprite;
	private Sprite mPlaySprite;
	private Sprite mOptionsSprite;
	private Sprite mQuitSprite;
	private Text mPlayText;
	private Text mOptionsText;
	private Text mQuitText;
	// ===========================================================
	// Constructors
	// ===========================================================
	public MainMenuScene(){
		Game.setTextHelper(new TextHelper());
		Game.setMobHelper(new MobHelper());
		Game.setBattleHelper(new BattleHelper());
		Game.setAttacksHelper(new AttacksHelper());
		Game.setItemHelper(new ItemHelper());
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/MainMenu/");
		this.mMainMenuTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);		
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMainMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Background.png", 0, 0);
		this.mBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMainMenuTextureAtlas, Game.getInstance().getApplicationContext(), "boxy.png", 0, 480);
		this.mSignsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMainMenuTextureAtlas, Game.getInstance().getApplicationContext(), "chainwood.png", 220, 480);
		this.mMainMenuTextureAtlas.load();
		
		//Background
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		this.attachChild(mBackgroundSprite);
			
		
		//Signs
		this.mSignsSprite = new Sprite(28, 0,this.mSignsTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(mSignsSprite);
			
		
		//Play
		this.mPlaySprite = new Sprite(11, 49,this.mBoxTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						Game.getTextHelper().FlushTexts("MainMenuScene");
						Game.getSceneManager().setMatchScene();
						break;
					}
				}
				return true;
			}
				
		};
		this.mPlaySprite.setScale(0.725f);
	
		
		//Opciones
		this.mOptionsSprite = new Sprite(13, 182,this.mBoxTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
						mGrabbed = true;					
						break;
					case TouchEvent.ACTION_UP:
						if(mGrabbed) {
							mGrabbed = false;
							Game.getTextHelper().FlushTexts("MainMenuScene");
							//Game.getSceneManager().setOptionsScene();
							//Game.getSceneManager().setTestScene();
							break;
						}
					}
				return true;
			}
		};
		this.mOptionsSprite.setScale(0.725f);
		
		//Quit
		this.mQuitSprite = new Sprite(13, 326,this.mBoxTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
						mGrabbed = true;					
						break;
					case TouchEvent.ACTION_UP:
						if(mGrabbed) {
							mGrabbed = false;
							Game.getInstance().finish();
							break;
						}
					}
				return true;
			}
		};
		this.mQuitSprite.setScale(0.725f);
		
		//Attach a la escena
		this.attachChild(mPlaySprite);
		this.attachChild(mOptionsSprite);
		this.attachChild(mQuitSprite);
		
		//Registro Touch Areas
		this.registerTouchArea(this.mPlaySprite);
		this.registerTouchArea(this.mOptionsSprite);
		this.registerTouchArea(this.mQuitSprite);

		//texto	
		this.mPlayText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_FANCY, 90, 87,"PLAY","MainMenuScene;Play");
		this.mOptionsText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_FANCY, 60, 217,"OPTIONS","MainMenuScene;Options");
		this.mQuitText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_FANCY, 90, 359,"QUIT","MainMenuScene;Quit");
		this.attachChild(this.mPlayText);
		this.attachChild(this.mOptionsText);
		this.attachChild(this.mQuitText);
		

		//mSignsSprite.registerEntityModifier(new MoveModifier(0.001f, 30, -450, -30, 0, EaseBackOut.getInstance()));
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
