package com.quest.entities.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class Item extends Entity implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	//x 4 y 6
	private int X_OFFSET = -4;
	private int Y_OFFSET = -32 - 6;//seria menos para el orco, fijarse ***
	// ===========================================================
	// Fields
	// ===========================================================
	private String mEntityType;
	private int mItemFlag;
	private String mItemName;
	private int mItemClass;
	private int mItemType;//0 - consumable, 1 weapon, 2 armor, 3 quest item
	private String mItemDescription;
	private int mItemBuyPrice;
	private int mItemSellPrice;
	private int[] mModifiers;
	private boolean mStackable;
	private int mAmount;
	private int mEquipped;//1 - 0, SQLite no maneja booleans
	

	private BitmapTextureAtlas mItemTextureAtlas;
	private TiledTextureRegion mItemTextureRegion;
	private AnimatedSprite mItemAnimation;
	private int animationStatus = 0;
	private Sprite mItemIcon;
	private ITextureRegion mIconTextureRegion;
	private String mAnimatedTexturePath;
	private String mIconTexturePath;
	private int FrameHeight,FrameWidth;
	private int mCols,mRows,extraCols;
	private Text mAmountText;
	private Entity mEntity;
	
	private Sprite[] mCollisionSprites;
	// ===========================================================
	// Constructors
	// ===========================================================
	public Item(int ITEM_FLAG) {
		if(ITEM_FLAG!=0){
			this.mEntityType = "BaseItem";
			this.mItemFlag = ITEM_FLAG;
			this.mAmount = 1;
			this.mEquipped = 0;
			//data
			this.mItemName = Game.getDataHandler().getItemName(this.mItemFlag);
			this.mItemClass = Game.getDataHandler().getItemClass(this.mItemFlag);
			this.mItemType = Game.getDataHandler().getItemType(this.mItemFlag);
			this.mItemDescription = Game.getDataHandler().getItemDescription(this.mItemFlag);
			this.mItemBuyPrice = Game.getDataHandler().getItemBuyPrice(this.mItemFlag);	
			this.mItemSellPrice = Game.getDataHandler().getItemSellPrice(this.mItemFlag);
			
			this.mStackable = Game.getDataHandler().isItemStackable(this.mItemFlag);
			
			//grafico
			this.mIconTexturePath = Game.getDataHandler().getItemIconTexture(mItemFlag);
			
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			this.mItemTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512, 512);
			this.mIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mItemTextureAtlas, Game.getInstance().getApplicationContext(), mIconTexturePath, 0, 0);
			this.mItemTextureAtlas.load();
						
			if(mItemType>0&&mItemType<3){
				this.mModifiers = Game.getDataHandler().getItemModifiers(this.mItemFlag);
				
				this.mAnimatedTexturePath = Game.getDataHandler().getItemAnimationTexture(mItemFlag);
				this.FrameHeight = Game.getDataHandler().getItemFrameHeight(mItemFlag);
				this.FrameWidth = Game.getDataHandler().getItemFrameWidth(mItemFlag);
				this.mCols = Game.getDataHandler().getItemAnimationCols(mItemFlag);
				this.mRows = Game.getDataHandler().getItemAnimationRows(mItemFlag);
				this.extraCols = Game.getDataHandler().getItemExtraCols(mItemFlag);

				this.mItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mItemTextureAtlas, Game.getInstance().getApplicationContext(), mAnimatedTexturePath, 0, 24, mCols, mRows);
				this.mItemAnimation = new AnimatedSprite(X_OFFSET, Y_OFFSET, this.mItemTextureRegion, Game.getInstance().getVertexBufferObjectManager());
				this.mItemAnimation.setCullingEnabled(true);
				this.attachChild(mItemAnimation);
			}
			
			
			
			this.mItemIcon = new Sprite(0, 0, mIconTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
						mGrabbed = true;
						this.setScale(2.5f);
					break;
				case TouchEvent.ACTION_MOVE:
					this.setPosition(pSceneTouchEvent.getX()-(mItemIcon.getWidthScaled()/2)-getmEntity().getX(), pSceneTouchEvent.getY()-(mItemIcon.getHeightScaled()/2)-getmEntity().getY());
					boolean collides =false;
					int i = 0;
					while(!collides && i<mCollisionSprites.length){
						if(this.collidesWith(mCollisionSprites[i])){
							mCollisionSprites[i].setAlpha(0.5f);
							collides = true;
						}else{
							mCollisionSprites[i].setAlpha(1f);
						}
						i++;
					}
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						//Game.getPlayerHelper().getOwnPlayer().setItem_Flag(mItemFlag);
						boolean collideS =false;
						int a = 0;
						while(a<mCollisionSprites.length){
							if(!collideS && this.collidesWith(mCollisionSprites[a])){
								Game.getSceneManager().getGameMenuScene().ActionOnCollide(Item.this, mCollisionSprites[a],Item.this.mEntity);
								collideS = true;
							}else{
								mCollisionSprites[a].setAlpha(1f);
							}
							a++;
						}
						this.setScale(2f);
					}
					break;
				}
				return true;
				}
			};
			this.mItemIcon.setScale(2f);
			this.mItemIcon.setCullingEnabled(true);
			this.mAmountText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_NORMAL, this.mItemIcon.getWidth()-4, -6, String.valueOf(this.mAmount), "Item;"+this.getUserData()+"_"+this.getItemName());
			this.mAmountText.setScale(0.6f);
			this.mItemIcon.attachChild(mAmountText);
			if(this.mAmount==1){
				mAmountText.setVisible(false);
			}
		}else{
			this.mEntityType = "NullItem";
		}
	}


	public Item(int pItemID,int pAmount) {
		this(pItemID);
		this.setAmount(pAmount);
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void setCollisionSprites(Sprite[] pSprites){
		this.mCollisionSprites = pSprites;
	}
	
	public Sprite getItemIcon(){
		return this.mItemIcon;
	}
	
	public String getEntityType() {
		return mEntityType;
	}

	public void setEntityType(String mEntityType) {
		this.mEntityType = mEntityType;
	}

	public int getItemFlag() {
		return mItemFlag;
	}

	public String getItemName() {
		return mItemName;
	}

	public int getItemClass() {
		return mItemClass;
	}

	public int getItemType() {
		return mItemType;
	}

	public String getItemDescription() {
		return mItemDescription;
	}

	public int getItemBuyPrice() {
		return mItemBuyPrice;
	}

	public int getItemSellPrice() {
		return mItemSellPrice;
	}
	
	public int[] getItemModifiers() {
		return mModifiers;
	}

	public int getItemPower() {
		return mModifiers[0];
	}
	
	public int getItemIntelligence() {
		return mModifiers[1];
	}
	
	/**
	 * @return the mItemAnimation
	 */
	public AnimatedSprite getItemAnimation() {
		return mItemAnimation;
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
	 * @return the frameWidth
	 */
	public int getFrameWidth() {
		return FrameWidth;
	}


	/**
	 * @param frameWidth the frameWidth to set
	 */
	public void setFrameWidth(int frameWidth) {
		FrameWidth = frameWidth;
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


	/**
	 * @return the extraCols
	 */
	public int getExtraCols() {
		return extraCols;
	}


	/**
	 * @param extraCols the extraCols to set
	 */
	public void setExtraCols(int extraCols) {
		this.extraCols = extraCols;
	}


	/**
	 * @param mItemAnimation the mItemAnimation to set
	 */
	public void setItemAnimation(AnimatedSprite mItemAnimation) {
		this.mItemAnimation = mItemAnimation;
	}


	public int getItemDefense() {
		return mModifiers[2];
	}
	
	public int getItemEndurance() {
		return mModifiers[3];
	}
	
	public boolean isStackable(){
		return mStackable;
	}
	
	public int getAmount() {
		return mAmount;
	}

	public void setAmount(int mAmount) {
		this.mAmount = mAmount;
		if(this.mAmountText!=null){
			this.mAmountText.setText(String.valueOf(mAmount));
			this.mAmountText.setVisible(false);
			if(mAmount>1)
				this.mAmountText.setVisible(true);	
		}
	}

	public void IncreaseAmount(){
		this.IncreaseAmount(1);
	}

	public void IncreaseAmount(int pAdded){
		this.mAmount+=pAdded;
		this.mAmountText.setText(String.valueOf(mAmount));
		this.mAmountText.setVisible(false);
		if(mAmount>1)
			this.mAmountText.setVisible(true);
	}
	
	public boolean DecreaseAmount(){//NO USAR, FUNCION INTERNA
		return this.DecreaseAmount(1);
	}

	public boolean DecreaseAmount(int pSubstracted){//NO USAR, FUNCION INTERNA
		this.mAmount-= pSubstracted;
		this.mAmountText.setText(String.valueOf(mAmount));
		this.mAmountText.setVisible(false);
		if(mAmount>1)
			this.mAmountText.setVisible(true);
		if(this.mAmount==0)return true;
		return false;
	}
	
	public boolean isEqquiped() {
		if(mEquipped==1)return true;
		return false;
	}

	public void Equip(boolean pEquip) {
		if(pEquip==true){
			this.mEquipped = 1;
		}else{
			this.mEquipped = 0;
		}
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	public Entity getmEntity() {
		return mEntity;
	}


	public void setmEntity(Entity mEntity) {
		this.mEntity = mEntity;
	}

	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
