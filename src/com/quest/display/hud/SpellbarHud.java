package com.quest.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.DynamicSpriteBatch;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;

import com.quest.database.myDatabase;
import com.quest.game.Game;

public class SpellbarHud extends HUD{


	// ===========================================================
	// Constants
	// ===========================================================
	private final int CANT_SPELLS = 5;
	private final Sprite[] mSpells = new Sprite[CANT_SPELLS];
	
	// ===========================================================
	// Fields
	// ===========================================================
	private HUD mHud;
	private BitmapTextureAtlas mSpellTextureAtlas;
	private ITextureRegion mSpellTextureRegion;
	private SpriteBatch mSpellBatch;
	private Sprite mConsultarSprite;
	private Sprite mSetearSprite;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public SpellbarHud(HUD pHud) {

		// Init local Variables
		this.mHud = pHud;
	
		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// Create Texture objects
		this.mSpellTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mSpellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSpellTextureAtlas, Game.getInstance().getApplicationContext(), "Spell.png", 0, 0);

		// Load Texture into memory and on the screen
		Game.getInstance().getTextureManager().loadTexture(this.mSpellTextureAtlas);
		
		this.mConsultarSprite = new Sprite(150, 60, this.mSpellTextureRegion, Game.getInstance().getVertexBufferObjectManager()){
			private Boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						this.setScale(2.5f);
						this.mGrabbed = true;
						break;
					case TouchEvent.ACTION_UP:
						if(this.mGrabbed == true){
								Log.d("Logd", "consultar");
								//Log.d("Logd", SpellbarHud.this.isLevelUnLocked(1));
							//	Log.d("Logd", SpellbarHud.this.isLevelUnLocked(2));
							//	Log.d("Logd", SpellbarHud.this.isLevelUnLocked(3));
								Log.d("Logd", SpellbarHud.this.isLevelBeat(1));
								this.setScale(2.0f);
								this.mGrabbed= false;
						}
					}
					return true;
				}
			};
			this.mHud.attachChild(mConsultarSprite);
			this.mHud.registerTouchArea(this.mConsultarSprite);
			this.mConsultarSprite.setAlpha(0.6f);
			this.mConsultarSprite.setScale(2.0f);
		
		
			this.mSetearSprite = new Sprite(250, 60, this.mSpellTextureRegion, Game.getInstance().getVertexBufferObjectManager()){
				private Boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							this.setScale(2.5f);
							this.mGrabbed = true;
							break;
						case TouchEvent.ACTION_UP:
							if(this.mGrabbed == true){
									this.setScale(2.0f);
									this.mGrabbed= false;
									Log.d("Logd", "setear");
								//	SpellbarHud.this.unLockLevel(2, "true");
								//	SpellbarHud.this.unLockLevel(3, "false");
								//	SpellbarHud.this.unLockLevel(3, "false");
									SpellbarHud.this.BeatLevel(1, "true");
									
							}
						}
						return true;
					}
				};
				this.mHud.attachChild(mSetearSprite);
				this.mHud.registerTouchArea(this.mSetearSprite);
				this.mSetearSprite.setAlpha(0.6f);
				this.mSetearSprite.setScale(2.0f);
			
			
				
				
				
		for(int i = 0; i < this.mSpells.length; i++) {
			float posX = 190.0f;
			this.mSpells[i] = new Sprite(posX + (90 * i), Game.getInstance().getSceneManager().getDisplay().getCameraHeight() - 71, this.mSpellTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {

				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							this.setScale(2.5f);
							break;
						case TouchEvent.ACTION_MOVE:
						case TouchEvent.ACTION_CANCEL:
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							this.setScale(2.0f);
							break;
					}
					return true;
				}
			};
			
			this.mHud.registerTouchArea(this.mSpells[i]);
			this.mSpells[i].setAlpha(0.6f);
			this.mSpells[i].setScale(2.0f); 
		}
		
		this.mSpellBatch = new DynamicSpriteBatch(this.mSpellTextureAtlas, CANT_SPELLS, Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onUpdateSpriteBatch() {
				for(int i = 0; i < SpellbarHud.this.mSpells.length; i++) {
					this.draw(SpellbarHud.this.mSpells[i]);
				}

				return true;
			}
		};
		
	}

	
	private String isLevelUnLocked(int levelNum){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.isLevelUnLocked(levelNum);
        myDB.close();
        return myReturn;
    }
       
    private int unLockLevel(int levelNum, String isUnLocked){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.unLockLevel(levelNum, isUnLocked);
        myDB.close();
        return myReturn;
    }
    
    private int BeatLevel(int levelNum, String isBeat){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.BeatLevel(levelNum, isBeat);
        myDB.close();
        return myReturn;
    }
    
    private String isLevelBeat(int levelNum){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.isLevelBeat(levelNum);
        myDB.close();
        return myReturn;
    }
    
    
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public SpriteBatch getSpellBar() {
		return mSpellBatch;
	}

	public void setSpellBar(SpriteBatch pSpellBatch) {
		this.mSpellBatch = pSpellBatch;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
}
