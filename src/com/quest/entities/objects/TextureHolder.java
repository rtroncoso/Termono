package com.quest.entities.objects;

import org.andengine.opengl.texture.region.ITextureRegion;

/*
 * @note trabajando en esta clase
 */
public class TextureHolder {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mTextureFlag;
	private ITextureRegion mTextureRegion;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public TextureHolder(int pTextureFlag) {
		this.mTextureFlag = pTextureFlag;
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * @return the mTextureFlag
	 */
	public int getTextureFlag() {
		return mTextureFlag;
	}

	/**
	 * @param mTextureFlag the mTextureFlag to set
	 */
	public void setTextureFlag(int mTextureFlag) {
		this.mTextureFlag = mTextureFlag;
	}

	/**
	 * @return the mTextureRegion
	 */
	public ITextureRegion getTextureRegion() {
		return mTextureRegion;
	}

	/**
	 * @param mTextureRegion the mTextureRegion to set
	 */
	public void setTextureRegion(ITextureRegion mTextureRegion) {
		this.mTextureRegion = mTextureRegion;
	}
	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
