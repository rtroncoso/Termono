package com.quest.pools;

import java.util.ArrayList;
import java.util.List;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.debug.Debug;

import com.quest.entities.objects.TextureHolder;

/*
 * @note trabajando en esta clase
 */
public class TexturePool {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final QMultiPool<TextureHolder> mTextureMultiPool = new QMultiPool<TextureHolder>();
	private ArrayList<BitmapTextureAtlas> mTextureAtlasList = new ArrayList<BitmapTextureAtlas>();

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * @return the mTextureAtlasList
	 */
	public ArrayList<BitmapTextureAtlas> getTextureAtlasList() {
		return mTextureAtlasList;
	}


	/**
	 * @param mTextureAtlasList the mTextureAtlasList to set
	 */
	public void setTextureAtlasList(ArrayList<BitmapTextureAtlas> mTextureAtlasList) {
		this.mTextureAtlasList = mTextureAtlasList;
	}

	public GenericPool<TextureHolder> getPool(int pPoolID){
		return this.mTextureMultiPool.getPool(pPoolID);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void registerTexture(final int TEXTURE_FLAG) {
		this.mTextureMultiPool.registerPool(TEXTURE_FLAG,
				new GenericPool<TextureHolder>() {
					@Override
					protected TextureHolder onAllocatePoolItem() {
						try {
							//return new ITextureRegion(TEXTURE_FLAG);
						} catch (final Throwable t) {
							Debug.e(t);
							return null;
						}
						return null;
					}
					
					@Override
					protected void onHandleRecycleItem(final TextureHolder pAttack) {
						//TODO fijarse como eliminarlo bien
						/*pAttack.
						pAttack.setCullingEnabled(true);
						pAttack.detachSelf();
						pAttack.setAnimationStatus(0);
						pAttack.setVisible(false);
						pAttack.getAttackAnimation().stopAnimation();
						pAttack.getAttackAnimation().reset();*/
					}
					
					@Override
					protected void onHandleObtainItem(TextureHolder pAttack) {
						//pAttack.setVisible(true);
					};
				}
		);
	}

	
	public TextureHolder obtainTexture(final int TEXTURE_FLAG) {
		return this.mTextureMultiPool.obtainPoolItem(TEXTURE_FLAG);
	}
	
	public void recycleAttack(final TextureHolder pAttack) {
		//this.mTextureMultiPool.recyclePoolItem(pAttack.getAttackFlag(), pAttack);
	}

	public void recycleAttacks(final List<TextureHolder> pTextures) {
		final QMultiPool<TextureHolder> textureMultiPool = this.mTextureMultiPool;
		for(int i = pTextures.size() - 1; i >= 0; i--) {
			final TextureHolder texture = pTextures.get(i);
			//attackMultiPool.recyclePoolItem(attack.getAttackFlag(), attack);
		}
	}
	// ===========================================================
	// Methods
	// ===========================================================



	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}