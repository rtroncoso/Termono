package com.superproyecto.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.DynamicSpriteBatch;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.superproyecto.game.Game;

public class SpellbarHud extends HUD{

	
		// ===========================================================
		// Constants
		// ===========================================================
		private final int CANT_SPELLS = 5;
		private final Sprite[] mSpells = new Sprite[CANT_SPELLS];
		
		// ===========================================================
		// Fields
		// ===========================================================
		private Game mGame;
		private BitmapTextureAtlas mSpellTextureAtlas;
		private ITextureRegion mSpellTextureRegion;
		private SpriteBatch mSpellBatch;

		// ===========================================================
		// Constructors
		// ===========================================================
		public SpellbarHud(Game pGame) {

			// Init local Variables
			this.mGame = pGame;
			
			// Set base path for Textures
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			
			// Create Texture objects
			this.mSpellTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
			this.mSpellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSpellTextureAtlas, this.mGame.getApplicationContext(), "Spell.png", 0, 0);

			// Load Texture into memory and on the screen
			this.mGame.getTextureManager().loadTexture(this.mSpellTextureAtlas);
			
			for(int i = 0; i < this.mSpells.length; i++) {
				float posX = 150.0f;//;this.mGame.getControlsHud().getDigitalOnScreenControl().getControlBase().getWidth();
				this.mSpells[i] = new Sprite(posX + (45.0f * i), this.mGame.getDisplay().getCameraHeight() - 50, this.mSpellTextureRegion, this.mGame.getVertexBufferObjectManager());
				this.mSpells[i].setAlpha(0.6f);
			}
			
			this.mSpellBatch = new DynamicSpriteBatch(this.mSpellTextureAtlas, CANT_SPELLS, this.mGame.getVertexBufferObjectManager()) {
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
