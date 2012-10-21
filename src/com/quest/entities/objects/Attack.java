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

public class Attack extends Entity implements IMeasureConstants {
	
	
	private BitmapTextureAtlas mAttackTextureAtlas;
	private TiledTextureRegion mAttackTextureRegion;
	private AnimatedSprite mAttackAnimation;
	private int animationStatus = 0;
	private Sprite mAttackIcon;
	private ITextureRegion mIconTextureRegion;
	
	private int mAttack_Flag;
	private int mAttackType;
	private String mAnimatedTexturePath;
	private String mIconTexturePath;
	private int FrameHeight,FrameWidth;
	private int mCols,mRows;
	
	public Attack(int ATTACK_FLAG) {;
		
		this.mAttack_Flag = ATTACK_FLAG;
		this.mAttackType = Game.getDataHandler().getAttackType(mAttack_Flag);
		this.mAnimatedTexturePath = Game.getDataHandler().getAttackAnimationTexture(mAttack_Flag);
		this.FrameHeight = Game.getDataHandler().getAttackFrameHeight(mAttack_Flag);
		this.setFrameWidth(Game.getDataHandler().getAttackFrameWidth(mAttack_Flag));
		this.mCols = Game.getDataHandler().getAttackAnimationCols(mAttack_Flag);
		this.mRows = Game.getDataHandler().getAttackAnimationRows(mAttack_Flag);
	
	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mAttackTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024, 1024);
		this.mAttackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mAttackTextureAtlas, Game.getInstance().getApplicationContext(), mAnimatedTexturePath, 0, 0, mCols, mRows);
		this.mAttackTextureAtlas.load();
		this.mAttackAnimation = new AnimatedSprite((TILE_SIZE / 2) - (this.mAttackTextureRegion.getWidth() / 2), (TILE_SIZE / 2) - (this.mAttackTextureRegion.getWidth() / 2), this.mAttackTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		this.mAttackAnimation.setCullingEnabled(true);
		
		if(mAttackType==1){//Si es ataque de player tiene icono, sino no
			this.mIconTexturePath = Game.getDataHandler().getAttackIconTexture(mAttack_Flag);
			
			this.mIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAttackTextureAtlas, Game.getInstance().getApplicationContext(), mIconTexturePath, 0, FrameHeight);
			this.mAttackIcon = new Sprite(0, 0, mIconTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
						Game.getPlayerHelper().getOwnPlayer().setAttack_Flag(mAttack_Flag);
						this.setScale(1.5f);
					}
					break;
				}
				return true;
				}
			};
			this.mAttackIcon.setScale(1.5f);
			this.mAttackIcon.setCullingEnabled(true);
		}
	}
	/**
	 * @return the mAttackAnimation
	 */
	public AnimatedSprite getAttackAnimation() {
		return mAttackAnimation;
	}

	/**
	 * @param mAttackAnimation the mAttackAnimation to set
	 */
	public void setAttackAnimation(AnimatedSprite pAttackAnimation) {
		this.mAttackAnimation = pAttackAnimation;
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
	 * @return the mAttackIcon
	 */
	public Sprite getAttackIcon() {
		return mAttackIcon;
	}

	/**
	 * @param mAttackIcon the mAttackIcon to set
	 */
	public void setAttackIcon(Sprite mAttackIcon) {
		this.mAttackIcon = mAttackIcon;
	}
	/**
	 * @return the mAttackFlag
	 */
	public int getAttackFlag() {
		return mAttack_Flag;
	}

	/**
	 * @param mAttackID the mAttackFlag to set
	 */
	public void setAttackFlag(int mAttackFlag) {
		this.mAttack_Flag = mAttackFlag;
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
