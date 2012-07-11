package com.quest.entities.objects;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.quest.game.Game;

public class Spell {
	
	private int mID;
	private BitmapTextureAtlas mSpellTextureAtlas;
	private TiledTextureRegion mSpellTextureRegion;
	private AnimatedSprite mSpellAnimation;
	
	public Spell(int pID) {;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mSpellTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024, 1024);
		this.mSpellTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mSpellTextureAtlas, Game.getInstance().getApplicationContext(), "Attack6.png", 0, 0, 5, 2);
		this.mSpellTextureAtlas.load();
		
		this.mSpellAnimation = new AnimatedSprite(0 - (this.mSpellTextureRegion.getWidth() / 2), 0 - (this.mSpellTextureRegion.getWidth() / 2), this.mSpellTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		
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

}
