package com.quest.entities.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.quest.game.Game;
import com.quest.util.constants.IMeasureConstants;

public class Spell extends Entity implements IMeasureConstants {
	
	
	private BitmapTextureAtlas mSpellTextureAtlas;
	private TiledTextureRegion mSpellTextureRegion;
	private AnimatedSprite mSpellAnimation;
	private int animationStatus = 0;
	private Sprite mSpellIcon;
	private ITextureRegion mIconTextureRegion;
	
	private int mSpellID;
	private String mAnimatedTexturePath;
	private String mIconTexturePath;
	private int FrameHeight,FrameWidth;
	private int mCols,mRows;
	
	public Spell(int pSpellID) {;
		
		this.mSpellID = pSpellID;
		this.mAnimatedTexturePath = Game.getDataHandler().getSpellAnimationTexture(mSpellID);
		this.mIconTexturePath = Game.getDataHandler().getSpellIconTexture(mSpellID);
		this.FrameHeight = Game.getDataHandler().getSpellFrameHeight(mSpellID);
		this.setFrameWidth(Game.getDataHandler().getSpellFrameWidth(mSpellID));
		this.mCols = Game.getDataHandler().getSpellAnimationCols(mSpellID);
		this.mRows = Game.getDataHandler().getSpellAnimationRows(mSpellID);
	
	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mSpellTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024, 1024);
		this.mSpellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mSpellTextureAtlas, Game.getInstance().getApplicationContext(), mAnimatedTexturePath, 0, 0, mCols, mRows);
		this.mSpellTextureAtlas.load();
		this.mSpellAnimation = new AnimatedSprite((TILE_SIZE / 2) - (this.mSpellTextureRegion.getWidth() / 2), (TILE_SIZE / 2) - (this.mSpellTextureRegion.getWidth() / 2), this.mSpellTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		this.mSpellAnimation.setCullingEnabled(true);
		
		this.mIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSpellTextureAtlas, Game.getInstance().getApplicationContext(), mIconTexturePath, 0, FrameHeight);
		this.mSpellIcon = new Sprite(0, 0, mIconTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
					mGrabbed = true;
					this.setScale(2f);
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;
					Game.getPlayerHelper().getOwnPlayer().setSpellattackid(mSpellID);
					this.setScale(1.5f);
				}
				break;
			}
			return true;
			}
		};
		this.mSpellIcon.setScale(1.5f);
		this.mSpellIcon.setCullingEnabled(true);
	}
	
	/**
	 * @return the mSpellAnimation
	 */
	public AnimatedSprite getSpellAnimation() {
		return mSpellAnimation;
	}

	/**
	 * @param mSpellAnimation the mSpellAnimation to set
	 */
	public void setSpellAnimation(AnimatedSprite pSpellAnimation) {
		this.mSpellAnimation = pSpellAnimation;
	}
	
	public int getAnimationStatus() {
		return animationStatus;
	}

	public void setAnimationStatus(int animationStatus) {
		this.animationStatus = animationStatus;
	}

	public int getFrameWidth() {
		return FrameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		FrameWidth = frameWidth;
	}

	
	/**
	 * @return the mSpellIcon
	 */
	public Sprite getSpellIcon() {
		return mSpellIcon;
	}

	/**
	 * @param mSpellIcon the mSpellIcon to set
	 */
	public void setSpellIcon(Sprite mSpellIcon) {
		this.mSpellIcon = mSpellIcon;
	}
	/**
	 * @return the mSpellID
	 */
	public int getSpellID() {
		return mSpellID;
	}

	/**
	 * @param mSpellID the mSpellID to set
	 */
	public void setSpellID(int mSpellID) {
		this.mSpellID = mSpellID;
	}

	/**
	 * @return the mAnimatedTexturePath
	 */
	public String getAnimatedTexturePath() {
		return mAnimatedTexturePath;
	}

	/**
	 * @param mAnimatedTexturePath the mAnimatedTexturePath to set
	 */
	public void setAnimatedTexturePath(String mAnimatedTexturePath) {
		this.mAnimatedTexturePath = mAnimatedTexturePath;
	}

	/**
	 * @return the mIconTexturePath
	 */
	public String getIconTexturePath() {
		return mIconTexturePath;
	}

	/**
	 * @param mIconTexturePath the mIconTexturePath to set
	 */
	public void setIconTexturePath(String mIconTexturePath) {
		this.mIconTexturePath = mIconTexturePath;
	}

	/**
	 * @return the frameHeight
	 */
	public int getFrameHeight() {
		return FrameHeight;
	}

	/**
	 * @param frameHeight the frameHeight to set
	 */
	public void setFrameHeight(int frameHeight) {
		FrameHeight = frameHeight;
	}

	/**
	 * @return the mCols
	 */
	public int getCols() {
		return mCols;
	}

	/**
	 * @param mCols the mCols to set
	 */
	public void setCols(int mCols) {
		this.mCols = mCols;
	}

	/**
	 * @return the mRows
	 */
	public int getRows() {
		return mRows;
	}

	/**
	 * @param mRows the mRows to set
	 */
	public void setRows(int mRows) {
		this.mRows = mRows;
	}

}
