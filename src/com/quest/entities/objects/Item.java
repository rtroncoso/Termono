package com.quest.entities.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.game.Game;

public class Item extends Entity{
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mItemTextureAtlas;
	private ITextureRegion mIconTextureRegion;
	private ITextureRegion mItemTextureRegion;
	private Sprite mIconSprite;
	private Sprite mItemSprite;
	
	
	private String mNombre; // El nombre superficial del ítem.
	private int mID; // El ID clave del ítem.
	private int mNivel; // El nivel mínimo del ítem para ser equipado.
	private float mPeso; // El peso del ítem en kgs, ejemplo para una armadura metálica: 30.4.
	private int mClase; // Tipo de equipo: espada, botas, etcétera.
	private boolean mQuestItem; // Si el ítem le pertenece a una quest.
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public Item(String pPath, int itemID) {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mItemTextureAtlas  = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		this.mIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mItemTextureAtlas, Game.getInstance().getApplicationContext(), pPath, 0, 0);
		this.mItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mItemTextureAtlas, Game.getInstance().getApplicationContext(), pPath, 0, 0);
		this.mItemTextureAtlas.load();
		
		this.mIconSprite = new Sprite(0, 0, this.mIconTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		this.mItemSprite = new Sprite(0, 0, this.mItemTextureRegion, Game.getInstance().getVertexBufferObjectManager());
	}
	
	
	public void loadNewTexture() {	
		
	} // 


	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public ITextureRegion getIconTextureRegion() {
		return mIconTextureRegion;
	}


	public void setIconTextureRegion(ITextureRegion mIconTextureRegion) {
		this.mIconTextureRegion = mIconTextureRegion;
	}


	public Sprite getIconSprite() {
		return mIconSprite;
	}


	public void setIconSprite(Sprite pIconSprite) {
		this.mIconSprite = pIconSprite;
	}
	
	public Sprite getItemSprite() {
		return mIconSprite;
	}


	public void setItemSprite(Sprite pItemSprite) {
		this.mItemSprite = pItemSprite;
	}
	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
