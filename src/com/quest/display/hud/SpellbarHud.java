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

import com.quest.database.DataHandler;
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
	private DataHandler mDataHandler;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public SpellbarHud(HUD pHud) {

		// Init local Variables
		this.mHud = pHud;
		this.mDataHandler = new DataHandler();
		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// Create Texture objects
		this.mSpellTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mSpellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSpellTextureAtlas, Game.getInstance().getApplicationContext(), "Spell.png", 0, 0);

		// Load Texture into memory and on the screen
		Game.getInstance().getTextureManager().loadTexture(this.mSpellTextureAtlas);
				
		for(int i = 0; i < this.mSpells.length; i++) {
			float posX = 190.0f;
			Game.getInstance();
			this.mSpells[i] = new Sprite(posX + (90 * i), Game.getSceneManager().getDisplay().getCameraHeight() - 71, this.mSpellTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {

				private Boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							this.setScale(2.5f);
							Game.getPlayerHelper().getPlayer("Player").setSpeedFactor(2.0f);
							this.mGrabbed = true;
							break;
						case TouchEvent.ACTION_UP:
							if(this.mGrabbed == true){
								this.setScale(2.0f);
								Game.getPlayerHelper().getPlayer("Player").setSpeedFactor(1.0f);
								this.mGrabbed= false;
							}
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
