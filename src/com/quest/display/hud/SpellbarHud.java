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

import com.quest.constants.GameFlags;
import com.quest.entities.Player;
import com.quest.entities.objects.Attack;
import com.quest.game.Game;

public class SpellbarHud extends HUD implements GameFlags{


	// ===========================================================
	// Constants
	// ===========================================================
	private final int CANT_SPELLS = 5;
	private final Sprite[] mSpells = new Sprite[CANT_SPELLS];
	private final Attack[] mSpellIcons = new Attack[6];
	// ===========================================================
	// Fields
	// ===========================================================
	private HUD mHud;
	private BitmapTextureAtlas mSpellTextureAtlas;
	private ITextureRegion mSpellTextureRegion;
	private SpriteBatch mSpellBatch;
	
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
				
		for(int i = 0; i < this.mSpells.length; i++) {
			float posX = 190.0f;
			Game.getInstance();//****??
			
			
			
			this.mSpells[i] = new Sprite(posX + (90 * i), Game.getSceneManager().getDisplay().getCameraHeight() - 71, this.mSpellTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {

				private Boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							switch ((Integer)(this.getUserData())) {
							case 0:
								//Game.getPlayerHelper().getOwnPlayer().setSpeedFactor(2.0f);
								break;
							case 1:
								
								break;
							case 2:
								
								break;
							case 3:
								
								break;
							case 4:
								
								break;
							}
							this.setScale(2.5f);
							this.mGrabbed = true;
							break;
						case TouchEvent.ACTION_UP:
							if(this.mGrabbed == true){
								this.setScale(2.0f);
								this.mGrabbed= false;
								switch ((Integer)(this.getUserData())) {
								case 0:
									//Game.getPlayerHelper().getOwnPlayer().setSpeedFactor(1.0f);
									break;
								case 1:
									/*if(Game.isServer()){
										Game.getSceneManager().getGameScene().CreateMob(FLAG_MOB_BAT,Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileColumn(),Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileRow(),1);
									}*/
									break;
								case 2:
									//Game.getSceneManager().getGameScene().DeleteMob(0);//la key no se usa por ahora
									break;
								case 3:
									//if(Game.isServer()){
									//	Game.getSceneManager().getGameScene().CreateMob(FLAG_MOB_BAT,Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileColumn(),Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileRow(),1);
									//}
									break;
								case 4:
									if(Game.isServer()){
										for(int i = Game.getPlayerHelper().getEntities().size()-1;i>=0;i--){
											Player tmpPlayer = Game.getPlayerHelper().getPlayerbyIndex(i);
											Game.getDataHandler().setPlayerCurrentMap(tmpPlayer.getCurrentMap(), tmpPlayer.getPlayerID());
											Game.getDataHandler().setPlayerPosition(tmpPlayer.getTMXTileAt(), tmpPlayer.getPlayerID());
										}
									}
									//Game.getSceneManager().getGameScene().DeleteMobs(0);
									break;
								}
							}
							break;
					}
					return true;
				}
			};
			
			this.mSpells[i].setAlpha(0.6f);
			this.mSpells[i].setUserData(i);
			this.mSpells[i].setScale(2.0f);
			
			if(i>0 && i<5){
			mSpellIcons[i] = new Attack(i);
			mSpellIcons[i].getAttackIcon().setPosition(this.mSpells[i-1].getX()+6, this.mSpells[i-1].getY()+6);
			}
		
			if(i==4){
				this.mHud.registerTouchArea(mSpells[i]);
			}
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

	public Sprite getSpells(int spell){
		return mSpellIcons[spell].getAttackIcon();
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
}
