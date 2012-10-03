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

import com.quest.entities.objects.Spell;
import com.quest.game.Game;
import com.quest.util.constants.IGameConstants;
import com.quest.util.constants.IMeasureConstants;

public class BaseEntity extends Entity implements IMeasureConstants, IGameConstants, ITouchArea {
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
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseEntity(int pInitialPosX, int pInitialPosY, String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		this.mEntityType = "BaseEntity";

		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		// Craete Texture Objects
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), pFrameWidth, pFrameHeight);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, Game.getInstance().getApplicationContext(), pTextureName, pFramePosX, pFramePosY, pCols, pRows);

		// Load Texture into memory and on the screen
		this.mBitmapTextureAtlas.load();

		// Create the sprite and add it to the scene.
		this.mBodySprite = new AnimatedSprite(0, 0, this.mTiledTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				return BaseEntity.this.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		
		this.attachChild(this.mBodySprite);

		this.mSpellsLayer = new ArrayList<Spell>();
		
		this.setTileAt(pInitialPosX, pInitialPosY);
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public BaseEntity setAnimationDirection(byte pFacingDirection, long[] pFrameDuration, boolean restartAnimation) {
		if(restartAnimation && !BaseEntity.this.mBodySprite.isAnimationRunning()) return this;
		switch(pFacingDirection) {
		case DIRECTION_EAST:
			BaseEntity.this.mBodySprite.animate(pFrameDuration, 8, 11, false);
			break;
		case DIRECTION_WEST:
			BaseEntity.this.mBodySprite.animate(pFrameDuration, 4, 7, false);
			break;
		case DIRECTION_SOUTH:
			BaseEntity.this.mBodySprite.animate(pFrameDuration, 0, 3, false);
			break;
		case DIRECTION_NORTH:
			BaseEntity.this.mBodySprite.animate(pFrameDuration, 12, 15, false);
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
	
	public BaseEntity moveToTile(final TMXTile pTileTo) {
		return this.moveToTile(pTileTo, 1.0f);
	}
	
	public BaseEntity moveToTile(final TMXTile pTileTo, final float pSpeed) {
		
		// get our current tile
		final TMXTile tmxTileAt = Game.getMapManager().getTMXTileAt(this.getX(), this.getY());
		
		// Unblock our current Tile and block our new one
		Game.getMapManager().unregisterCollisionTile(tmxTileAt);
		Game.getMapManager().registerCollisionTile(pTileTo);

		this.mPath = new Path(2).to(this.getX(), this.getY()).to(pTileTo.getTileX(), pTileTo.getTileY());

		this.mPathModifier = new PathModifier(pSpeed / SPEED_MODIFIER, this.mPath, new IPathModifierListener() {

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
				BaseEntity.this.onEntityMoved(pTileTo);
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
		
		final TMXTile tmpTMXTile = Game.getMapManager().getCurrentMap().getTMXLayers().get(0).getTMXTile(tileX, tileY);

		final float pNewX = (this.mBodySprite.getWidth() > 32) ? tmpTMXTile.getTileX() - (this.mBodySprite.getWidth() - 32) : tmpTMXTile.getTileX();
		final float pNewY = (this.mBodySprite.getHeight() > 32) ? tmpTMXTile.getTileY() - (this.mBodySprite.getHeight() - 32) : tmpTMXTile.getTileY();

		this.setPosition(pNewX, pNewY);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

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
			if(!mSpellToDraw.getSpellAnimation().isAnimationRunning()) this.attachChild(mSpellToDraw.getSpellAnimation());
			
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
					BaseEntity.this.mSpellsLayer.remove(mSpellToDraw);
				}
			});
		}
		
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}