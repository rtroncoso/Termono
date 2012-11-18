package com.quest.entities;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.text.Text;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

import com.quest.entities.interfaces.IEntityCallbacks;
import com.quest.entities.objects.Attack;
import com.quest.entities.objects.Item;
import com.quest.game.Game;
import com.quest.helpers.interfaces.IBaseEntityActions;
import com.quest.triggers.Trigger;
import com.quest.util.constants.IGameConstants;
import com.quest.util.constants.IMeasureConstants;

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
	protected Attack tmpAttack;
	protected AnimatedSprite mBodySprite;
	protected TMXTile mTMXTileAt;
	protected ArrayList<Attack> mAttackLayer;
	protected boolean isWalking;
	protected float mSpeedFactor;
	protected int mBodyColumns;
	protected int mBodyRows;
	protected int mBodyExtraCols;
	protected int mCurrentMap;
	protected byte mFacingDirection;
	protected Entity mEquippedWeaponsLayer = new Entity();
	private int[] mCoords;
	
	//a ordenar
	protected int mLevel;
	protected float currHP,currMana;
	protected int mModEndurance,mModIntelligence,mModPower,mModDefense,mModHP,mModMana = 0;
	protected int mEndurance,mIntelligence,mPower,mDefense;
	protected int mMoney;
	protected String mName;
	protected float mExperience;
	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseEntity(String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		
		// Init class objects
		this.mEntityType = "BaseEntity";
		this.mSpeedFactor = 1.0f;
		this.mBodyColumns = pCols;
		this.mBodyRows = pRows;
		this.mBodyExtraCols = 0;
		this.mFacingDirection = DIRECTION_SOUTH;
		
		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		// Craete Texture Objects
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), pFrameWidth, pFrameHeight);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, Game.getInstance().getApplicationContext(), pTextureName, pFramePosX, pFramePosY, this.mBodyColumns, this.mBodyRows);

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
		
		// Attach the Weapons layer
		this.mEquippedWeaponsLayer.setPosition(10, 30);
		this.attachChild(this.mEquippedWeaponsLayer);
		
		this.mAttackLayer = new ArrayList<Attack>();
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public BaseEntity setAnimationDirection(byte pFacingDirection, boolean restartAnimation, boolean pLoop) {
		
		// Check if not already animating
		if(restartAnimation && !this.mBodySprite.isAnimationRunning()) return this;

		// Calculate frame durations
		long[] bodyFrameDurations = new long[this.mBodyColumns - this.mBodyExtraCols];
		for(int i = 0; i < this.mBodyColumns - this.mBodyExtraCols; i++) {
			bodyFrameDurations[i] = 100;
		}
		
		for(int a = this.mEquippedWeaponsLayer.getChildCount() - 1; a >= 0; a--){
		Item item = (Item)(this.mEquippedWeaponsLayer.getChildByIndex(a));
			// Check if not already animating
			if(!restartAnimation && item.getItemAnimation().isAnimationRunning()){
	
				// Calculate frame durations
				long[] frameDurations = new long[item.getCols() - item.getExtraCols()];
				for(int i = 0; i < item.getCols() - item.getExtraCols(); i++) {
					frameDurations[i] = 50;
				}
				
				// Animate it
				switch(pFacingDirection) {
				case DIRECTION_SOUTH:
					item.getItemAnimation().animate(frameDurations, 0, (item.getCols()) - (item.getExtraCols()) - 1, false);
					break;
				case DIRECTION_NORTH:
					item.getItemAnimation().animate(frameDurations, (item.getCols() * 3), (item.getCols() * 4) - (item.getExtraCols()) - 1, false);
					break;	
				case DIRECTION_EAST:
					item.getItemAnimation().animate(frameDurations, (item.getCols() * 2), (item.getCols() * 3) - (item.getExtraCols()) - 1, false);
					break;
				case DIRECTION_WEST:
					item.getItemAnimation().animate(frameDurations, item.getCols(), (item.getCols() * 2) - (item.getExtraCols()) - 1, false);
					break;	
				}
				
			}
		}
		
		// Animate it
		switch(pFacingDirection) {
		case DIRECTION_SOUTH:
			this.mBodySprite.animate(bodyFrameDurations, 0, (this.mBodyColumns) - (this.mBodyExtraCols) - 1, pLoop);
			break;
		case DIRECTION_NORTH:
			this.mBodySprite.animate(bodyFrameDurations, (this.mBodyColumns * 3), (this.mBodyColumns * 4) - (this.mBodyExtraCols) - 1, pLoop);
			break;	
		case DIRECTION_EAST:
			this.mBodySprite.animate(bodyFrameDurations, (this.mBodyColumns * 2), (this.mBodyColumns * 3) - (this.mBodyExtraCols) - 1, pLoop);
			break;
		case DIRECTION_WEST:
			this.mBodySprite.animate(bodyFrameDurations, this.mBodyColumns, (this.mBodyColumns * 2) - (this.mBodyExtraCols) - 1, pLoop);
			break;	
		}
		
		this.mFacingDirection = pFacingDirection;
		return this;
	}
	
	public void setAttackAnimation() {
		
		// Check if it has extra columns
		if(this.mBodyExtraCols == 0) return;
		
		// Check if not already animating
		if(this.mBodySprite.isAnimationRunning()) return;

		// Calculate frame durations
		long[] frameDurations = new long[this.mBodyExtraCols];
		for(int i = 0; i < this.mBodyExtraCols; i++) {
			frameDurations[i] = 200;
		}
		
		IAnimationListener tmpAnimationListener = new IAnimationListener() {
			
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
				BaseEntity.this.setAnimationDirection(BaseEntity.this.mFacingDirection, false, false);
			}
		};
		
		// Animate it
		switch(this.mFacingDirection) {
		case DIRECTION_SOUTH:
			this.mBodySprite.animate(frameDurations, (this.mBodyColumns) - (this.mBodyExtraCols), (this.mBodyColumns) - 1, false, tmpAnimationListener);
			break;
		case DIRECTION_NORTH:
			this.mBodySprite.animate(frameDurations, (this.mBodyColumns * 4) - (this.mBodyExtraCols), (this.mBodyColumns * 4) - 1, false, tmpAnimationListener);
			break;	
		case DIRECTION_EAST:
			this.mBodySprite.animate(frameDurations, (this.mBodyColumns * 3) - (this.mBodyExtraCols), (this.mBodyColumns * 3) - 1, false, tmpAnimationListener);
			break;
		case DIRECTION_WEST:
			this.mBodySprite.animate(frameDurations, (this.mBodyColumns * 2) - (this.mBodyExtraCols), (this.mBodyColumns * 2) - 1, false, tmpAnimationListener);
			break;	
		}
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
	
	public TMXTile moveInDirection(byte pDirection) {
		
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
		if(!Game.getMapManager().isLegalPosition(mCurrentMap,(int) moveToXTile, (int) moveToYTile)) return null;

		// Get the new Tile
		final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(moveToXTile, moveToYTile);

		// Animate the Character
		this.setAnimationDirection(this.getFacingDirectionToTile(tmxTileTo), false, false);
		
		// Check Tiles
		Trigger tmpTrigger = Game.getMapManager().checkTrigger(tmxTileTo);
		if(tmpTrigger != null) { tmpTrigger.onHandleTriggerAction(); return null; } // Hacer cambio de mapa
		
		return tmxTileTo;
	}
	
	public BaseEntity moveToTile(final TMXTile pTileTo) {
		
		// null Tile?
		if(pTileTo == null)	return this;
		
		// Unblock our current Tile and block our new one
		Game.getMapManager().unregisterCollisionTile(this.mTMXTileAt);
		Game.getMapManager().registerCollisionTile(pTileTo);
		
		// Animate the Character
		this.setAnimationDirection(this.getFacingDirectionToTile(pTileTo), false, false);

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

	public void cleanUp() {

		Game.getMapManager().unregisterCollisionTile(this.mTMXTileAt);
	}
	
	public void popOverHead(Text pText,float pScale){
		final Text tmpText = pText;
		tmpText.setScale(pScale);
		this.attachChild(tmpText);
		tmpText.registerEntityModifier(new MoveModifier(1f,BaseEntity.this.getBodySprite().getX(),BaseEntity.this.getBodySprite().getX()+5,BaseEntity.this.getBodySprite().getY(),BaseEntity.this.getBodySprite().getY()-5,new IEntityModifierListener() {		
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				tmpText.registerEntityModifier(new AlphaModifier(1f,1f,0.2f));
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				BaseEntity.this.detachChild(tmpText);
				Game.getTextHelper().deleteText(tmpText);
			}
		}));
	}
	
	public void popOverHead(final Item item){
		this.attachChild(item.getItemIcon());
		item.getItemIcon().setScale(0.8f);
		item.getItemIcon().registerEntityModifier(new MoveModifier(1f,BaseEntity.this.getBodySprite().getX(),BaseEntity.this.getBodySprite().getX()+5,BaseEntity.this.getBodySprite().getY(),BaseEntity.this.getBodySprite().getY()-5,new IEntityModifierListener() {		
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				item.getItemIcon().registerEntityModifier(new AlphaModifier(1f,1f,0.2f));
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				BaseEntity.this.detachChild(item.getItemIcon());
				Game.getItemHelper().recycleItem(item);
			}
		}));
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

	/**
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		mName = name;
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

	public float getCurrHP() {
		return currHP;
	}

	public void setCurrHP(float pcurrHP) {
		this.currHP = pcurrHP;			
	}
	
	public float getCurrMana() {
		return currMana;
	}

	public void setCurrMana(float currMana) {
		this.currMana = currMana;
	}
	
	
	public int[] getAttributes(){
		return new int[]{getPower(),getIntelligence(),getDefense(),getEndurance()};
	}
	
	public void setAttributes(int[] pAttributes){
		this.setAttributes(pAttributes[0], pAttributes[1], pAttributes[2], pAttributes[3]);
	}
	
	public void setAttributes(int pPower,int pIntelligence,int pDefense,int pEndurance){
		this.setPower(pPower);
		this.setIntelligence(pIntelligence);
		this.setDefense(pDefense);
		this.setEndurance(pEndurance);
		updateModHPMP();
	}
	
	public void updateModHPMP(){
		this.mModHP = ((this.getEndurance() + this.getModEndurance()) * 10);
		this.mModMana = ((this.getIntelligence()+this.getModIntelligence())*10);
	}
	
	public int getCurrentMap() {
		return mCurrentMap;
	}

	public void setCurrentMap(int mCurrentMap) {
		this.mCurrentMap = mCurrentMap;
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
		updateModHPMP();
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
	
	public float[] getCurrHPMP(){
		return new float[]{this.currHP,this.currMana};
	}
	
	public void Heal(){
		setCurrHP(this.mModEndurance*10);
		setCurrMana(this.mModIntelligence*10);
	}
	
	public int[] getCoords() {
		return mCoords;
	}

	public void setCoords(int[] mPosition) {
		this.mCoords = mPosition;
	}

	public void setCoords(int tileX, int tileY) {
		this.mCoords = new int[]{tileX,tileY};
	}
	
	public int getMoney() {
		return mMoney;
	}

	public void setMoney(int mMoney) {
		this.mMoney = mMoney;
	}

	public float getExperience() {
		return mExperience;
	}

	public void setExperience(float mExperience) {
		this.mExperience = mExperience;
	}
	
	/**
	 * @return the mAttackLayer
	 */
	public ArrayList<Attack> getAttackLayer() {
		return mAttackLayer;
	}

	/**
	 * @param mAttackLayer the mAttacksLayer to set
	 */
	public void setAttackLayer(ArrayList<Attack> mAttacksLayer) {
		this.mAttackLayer = mAttacksLayer;
	}

	/**
	 * @return the mEquippedWeaponsLayer
	 */
	public Entity getEquippedWeaponsLayer() {
		return mEquippedWeaponsLayer;
	}

	/**
	 * @param mEquippedWeaponsLayer the mEquippedWeaponsLayer to set
	 */
	public void setEquippedWeaponsLayer(Entity mEquippedWeaponsLayer) {
		this.mEquippedWeaponsLayer = mEquippedWeaponsLayer;
	}

	public boolean decreaseHP(int damage){
		setCurrHP(currHP-damage);
		if(currHP<1)return true;
		return false;
	}
	
	public boolean decreaseMP(int mana){
		setCurrMana(currMana-mana);
		if(currMana<1)return true;
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
		
		while(this.mAttackLayer.size()>0){
			for(int i = this.mAttackLayer.size()-1;i>=0; i--){
				final Attack mAttackToDraw = this.mAttackLayer.get(i);
					mAttackToDraw.setAnimationStatus(1);
					this.attachChild(mAttackToDraw.getAttackAnimation());
					mAttackToDraw.getAttackAnimation().animate(100,false,new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
								int pInitialLoopCount) {
							mAttackToDraw.setAnimationStatus(1);			
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
							mAttackToDraw.setAnimationStatus(2);
							BaseEntity.this.detachChild(mAttackToDraw.getAttackAnimation());
							Game.getAttacksHelper().recycleAttack(mAttackToDraw);
							}
					
					});
					this.mAttackLayer.remove(mAttackToDraw);
			}
		}
		
		super.onManagedUpdate(pSecondsElapsed);
	}

	@Override
	public void onDeathAction(BaseEntity pKillerEntity) {
		// TODO Auto-generated method stub
		this.cleanUp();
	}

	@Override
	public void onAttackedAction(BaseEntity pAttackingEntity, int pDamage,int pAttackID) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAttackAction(BaseEntity pAttackedEntity, int pAttackID) {
		// TODO Auto-generated method stub
		
	}

	public byte getFacingDirection() {
		return mFacingDirection;
	}

	public void setFacingDirection(byte mFacingDirection) {
		this.mFacingDirection = mFacingDirection;
	}

	@Override
	public void onDisplayAttackingAction() {
		this.setAttackAnimation();		
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
