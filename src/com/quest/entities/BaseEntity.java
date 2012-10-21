package com.quest.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

import com.quest.entities.interfaces.IEntityCallbacks;
import com.quest.entities.objects.Spell;
import com.quest.game.Game;
import com.quest.helpers.interfaces.IBaseEntityActions;
import com.quest.util.constants.IGameConstants;
import com.quest.util.constants.IMeasureConstants;
import com.quest.triggers.Trigger;

public class BaseEntity extends Entity implements IMeasureConstants, IGameConstants, ITouchArea, IEntityCallbacks,IBaseEntityActions {
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mTiledTextureRegion;
	private PathModifier mPathModifier;
	private Path mPath;
	
	protected String mEntityType;
	protected Spell tmpSpell;
	protected AnimatedSprite mBodySprite;
	protected TMXTile mTMXTileAt;
	protected ArrayList<Spell> mSpellsLayer;
	protected boolean isWalking;
	protected float mSpeedFactor;
	protected int mBodyColumns;
	protected int mBodyRows;
	
	
	
	//a ordenar
	protected int mLevel;
	protected int currHP,currMana;
	protected int mModEndurance,mModIntelligence,mModPower,mModDefense,mModHP,mModMana = 0;
	protected int mEndurance,mIntelligence,mPower,mDefense;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseEntity(String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		
		// Init class objects
		this.mEntityType = "BaseEntity";
		this.mSpeedFactor = 1.0f;
		this.mBodyColumns = pCols;
		this.mBodyRows = pRows;
		
		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		// Craete Texture Objects
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), pFrameWidth, pFrameHeight);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, Game.getInstance().getApplicationContext(), pTextureName, pFramePosX, pFramePosY, pCols, pRows);

		// Load Texture into memory and on the screen
		this.mBitmapTextureAtlas.load();

		// Create the sprite and add it to the entity
		this.mBodySprite = new AnimatedSprite(0, 0 - (this.mTiledTextureRegion.getHeight() - 32), this.mTiledTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				return BaseEntity.this.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		this.attachChild(this.mBodySprite);
		this.mBodySprite.setCullingEnabled(true);
		this.mSpellsLayer = new ArrayList<Spell>();
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public BaseEntity setAnimationDirection(byte pFacingDirection, boolean restartAnimation) {
		
		// Check if not already animating
		if(restartAnimation && !this.mBodySprite.isAnimationRunning()) return this;

		// Calculate frame durations
		long[] frameDurations = new long[this.mBodyColumns];
		for(int i = 0; i < this.mBodyColumns; i++) {
			frameDurations[i] = 50;
		}
		
		// Animate it
		switch(pFacingDirection) {
		case DIRECTION_SOUTH:
			this.mBodySprite.animate(frameDurations, 0, (this.mBodyColumns - 1), false);
			break;
		case DIRECTION_NORTH:
			this.mBodySprite.animate(frameDurations, (this.mBodyColumns * 3), (this.mBodyColumns * 4) - 1, false);
			break;	
		case DIRECTION_EAST:
			this.mBodySprite.animate(frameDurations, (this.mBodyColumns * 2), (this.mBodyColumns * 3) - 1, false);
			break;
		case DIRECTION_WEST:
			this.mBodySprite.animate(frameDurations, this.mBodyColumns, (this.mBodyColumns * 2) - 1, false);
			break;	
		}
		return this;
	}
	
	public byte getFacingDirectionToTile(final TMXTile pTileTo) {
		// RIGHT
		if(BaseEntity.this.getX() - pTileTo.getTileX() < 0)
			return DIRECTION_EAST;
		// LEFT
		if(BaseEntity.this.getX() - pTileTo.getTileX() > 0)
			return DIRECTION_WEST;
		// DOWN
		if(BaseEntity.this.getY() - pTileTo.getTileY() < 0)
			return DIRECTION_SOUTH;
		// UP
		if(BaseEntity.this.getY() - pTileTo.getTileY() > 0)
			return DIRECTION_NORTH;
		return 0;
	}
	
	public BaseEntity moveInDirection(byte pDirection) {
		
		float moveToXTile = this.getX();
		float moveToYTile = this.getY();
		
		switch(pDirection) {
		case DIRECTION_NORTH:
			moveToYTile = this.getY() + (TILE_SIZE);
			break;
		case DIRECTION_SOUTH:
			moveToYTile = this.getY() - (TILE_SIZE);
			break;
		case DIRECTION_EAST:
			moveToXTile = this.getX() + (TILE_SIZE);
			break;
		case DIRECTION_WEST:
			moveToXTile = this.getX() - (TILE_SIZE);
			break;
		}
		
		// Is it a legal position?
		if(!Game.getMapManager().isLegalPosition((int) moveToXTile, (int) moveToYTile)) return this;

		// Get the new Tile
		final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(moveToXTile, moveToYTile);
		
		// Animate the Character
		this.setAnimationDirection(this.getFacingDirectionToTile(tmxTileTo), false);
		
		// Check Tiles
		Trigger tmpTrigger = Game.getMapManager().checkTrigger(tmxTileTo);
		if(tmpTrigger != null) { tmpTrigger.onHandleTriggerAction(); return this; } // Hacer cambio de mapa
		
		// Perform Move
		this.moveToTile(tmxTileTo);
		
		return this;
	}
	
	public BaseEntity moveToTile(final TMXTile pTileTo) {
		
		// null Tile?
		if(pTileTo == null)	return this;
		
		// Unblock our current Tile and block our new one
		Game.getMapManager().unregisterCollisionTile(this.mTMXTileAt);
		Game.getMapManager().registerCollisionTile(pTileTo);

		this.mPath = new Path(2).to(this.getX(), this.getY()).to(pTileTo.getTileX(), pTileTo.getTileY());

		this.mPathModifier = new PathModifier(SPEED_MODIFIER / this.mSpeedFactor, this.mPath, new IPathModifierListener() {

			@Override
			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
				BaseEntity.this.isWalking = true;
			}

			@Override
			public void onPathWaypointStarted(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPathWaypointFinished(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPathFinished(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub

				BaseEntity.this.isWalking = false;
				//BaseEntity.this.mBodySprite.stopAnimation();

			}	
		}, EaseLinear.getInstance());

		this.registerEntityModifier(this.mPathModifier);
		
		return this;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public BitmapTextureAtlas getBitmapTextureAtlas() {
		return this.mBitmapTextureAtlas;
	}

	public TiledTextureRegion getTiledTextureRegion() {
		return this.mTiledTextureRegion;
	}

	public AnimatedSprite getBodySprite() {
		return this.mBodySprite;
	}

	public void setBodySprite(AnimatedSprite pBodySprite) {
		this.mBodySprite = pBodySprite;
	}

	public String getEntityType() {
		// TODO Auto-generated method stub
		return this.mEntityType;
	}
	public void setEntityType(String pEntityType) {
		// TODO Auto-generated method stub
		this.mEntityType = pEntityType;
	}
	
	public void setTileAt(int tileX, int tileY) {
		
		// Find out which Tile is it
		final TMXTile tmpTMXTile = Game.getMapManager().getCurrentMap().getTMXLayers().get(0).getTMXTile(tileX, tileY);

		// Set the position
		this.setPosition(tmpTMXTile.getTileX(), tmpTMXTile.getTileY());
	}

	/**
	 * @return the mTMXTileAt
	 */
	public TMXTile getTMXTileAt() {
		return mTMXTileAt;
	}

	/**
	 * @param mTMXTileAt the mTMXTileAt to set
	 */
	public void setTMXTileAt(TMXTile mTMXTileAt) {
		this.mTMXTileAt = mTMXTileAt;
	}

	/**
	 * @return the mSpeedFactor
	 */
	public float getSpeedFactor() {
		return mSpeedFactor;
	}

	/**
	 * @param mSpeedFactor the mSpeedFactor to set
	 */
	public void setSpeedFactor(float mSpeedFactor) {
		this.mSpeedFactor = mSpeedFactor;
	}

	//a ordenar

	public int getLevel() {
		return mLevel;
	}

	public void setLevel(int mLevel) {
		this.mLevel = mLevel;
	}

	public int getEndurance() {
		return mEndurance;
	}

	public void setEndurance(int mEndurance) {
		this.mEndurance = mEndurance;
	}

	public int getIntelligence() {
		return mIntelligence;
	}

	public void setIntelligence(int mIntelligence) {
		this.mIntelligence = mIntelligence;
	}

	public int getPower() {
		return mPower;
	}

	public void setPower(int mPower) {
		this.mPower = mPower;
	}

	public int getDefense() {
		return mDefense;
	}

	public void setDefense(int mDefense) {
		this.mDefense = mDefense;
	}

	public int getModEndurance() {
		return mModEndurance;
	}

	public void setModEndurance(int mModEndurance) {
		this.mModEndurance = mModEndurance;
	}

	public int getModIntelligence() {
		return mModIntelligence;
	}

	public void setModIntelligence(int mModIntelligence) {
		this.mModIntelligence = mModIntelligence;
	}

	public int getModPower() {
		return mModPower;
	}

	public void setModPower(int mModPower) {
		this.mModPower = mModPower;
	}

	public int getModDefense() {
		return mModDefense;
	}

	public void setModDefense(int mModDefense) {
		this.mModDefense = mModDefense;
	}

	public int getModHP() {
		return mModHP;
	}

	public void setModHP(int mModHP) {
		this.mModHP = mModHP;
	}

	public int getModMana() {
		return mModMana;
	}

	public void setModMana(int mModMana) {
		this.mModMana = mModMana;
	}

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int pcurrHP) {
		this.currHP = pcurrHP;			
	}
	
	public int getCurrMana() {
		return currMana;
	}

	public void setCurrMana(int currMana) {
		this.currMana = currMana;
	}
	
	
	public int[] getAttributes(){
		return new int[]{getPower(),getIntelligence(),getDefense(),getEndurance()};
	}
	
	public void setAttributes(int pPower,int pIntelligence,int pDefense,int pEndurance){
		this.setPower(pPower);
		this.setIntelligence(pIntelligence);
		this.setDefense(pDefense);
		this.setEndurance(pEndurance);
	}
	
	public void setAttributes(int[] pAttributes){
		this.setPower(pAttributes[0]);
		this.setIntelligence(pAttributes[1]);
		this.setDefense(pAttributes[2]);
		this.setEndurance(pAttributes[3]);
	}
	
	public int[] getModifiers(){
		return new int[]{getModPower(),getModIntelligence(),getModDefense(),getModEndurance(),getModHP(),getModMana()};
	}
	
	public void setModifiers(int[] Modifiers){
		setModifiers(Modifiers[0], Modifiers[1], Modifiers[2], Modifiers[3]);
	}
	
	public void setModifiers(int pPower,int pIntelligence,int pDefense,int pEndurance){
		this.setModEndurance(pEndurance);
		this.setModIntelligence(pIntelligence);
		this.setModPower(pPower);
		this.setModDefense(pDefense);
		this.setModHP(pEndurance*10);
		this.setModMana(pIntelligence*10);
	}
	
	public void addModifiers(int[] pModifiers){
		int[] mods = this.getModifiers();
		this.setModPower(mods[0]+pModifiers[0]);
		this.setModIntelligence(mods[1]+pModifiers[1]);
		this.setModDefense(mods[2]+pModifiers[2]);
		this.setModEndurance(mods[3]+pModifiers[3]);
		this.setModHP(mods[4]+(pModifiers[3]*10));
		this.setModMana(mods[5]+(pModifiers[1]*10));
	}
	
	public void substractModifiers(int[] pModifiers){
		int[] mods = this.getModifiers();
		this.setModPower(mods[0]-pModifiers[0]);
		this.setModIntelligence(mods[1]-pModifiers[1]);
		this.setModDefense(mods[2]-pModifiers[2]);
		this.setModEndurance(mods[3]-pModifiers[3]);
		this.setModHP(mods[4]-(pModifiers[3]*10));
		this.setModMana(mods[5]-(pModifiers[1]*10));
	}
	
	public void updateHPMana(int currHP,int currMana){
		this.setCurrHP(currHP);
		this.setCurrMana(currMana);
	}
	
	public void updateHPMana(int[] currHPMP){
		this.setCurrHP(currHPMP[0]);
		this.setCurrMana(currHPMP[1]);
	}
	
	public int[] getCurrHPMP(){
		return new int[]{this.currHP,this.currMana};
	}
	
	public void Heal(){
		setCurrHP(this.mModEndurance*10);
		setCurrMana(this.mModIntelligence*10);
	}
	
	/**
	 * @return the mSpellsLayer
	 */
	public ArrayList<Spell> getSpellsLayer() {
		return mSpellsLayer;
	}

	/**
	 * @param mSpellsLayer the mSpellsLayer to set
	 */
	public void setSpellsLayer(ArrayList<Spell> mSpellsLayer) {
		this.mSpellsLayer = mSpellsLayer;
	}

	public boolean decreaseHP(int damage){
		setCurrHP(currHP-damage);
		if(currHP<1)return true;
		return false;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void setPosition(float pX, float pY) {
		super.setPosition(pX, pY);
		
		this.mTMXTileAt = Game.getMapManager().getTMXTileAt(pX, pY);
	};
	
	@Override
	public void onEntityMoved(TMXTile pNewTile) {
		this.mTMXTileAt = pNewTile;
		return;
	}

	@Override
	public boolean contains(float pX, float pY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		return false;
	}
	  
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		
		Iterator<Spell> it = this.mSpellsLayer.iterator();
		while(it.hasNext()) {
			final Spell mSpellToDraw = it.next();
			if(!mSpellToDraw.getSpellAnimation().isAnimationRunning()){
			this.attachChild(mSpellToDraw.getSpellAnimation());
			
			mSpellToDraw.getSpellAnimation().animate(100, false, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					BaseEntity.this.detachChild(mSpellToDraw.getSpellAnimation());
					BaseEntity.this.mSpellsLayer.remove(mSpellToDraw);
					}
			
			});
		
			
		  }
			
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	@Override
	public void onDeathAction(BaseEntity pKillerEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttackedAction(BaseEntity pAttackingEntity, int pDamage,int pAttackID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttackAction(BaseEntity pAttackedEntity, int pAttackID) {
		// TODO Auto-generated method stub
		
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
