package com.quest.entities.objects;

import org.andengine.entity.Entity;

import com.quest.game.Game;

public class BaseItem extends Entity{
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	protected String mEntityType;
	protected int mItemID;
	protected String mItemName;
	protected int mItemClass;
	protected int mItemType;
	protected String mItemDescription;
	protected int mItemBuyPrice;
	protected int mItemSellPrice;
	protected int[] mModifiers;
	protected boolean mStackable;
	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseItem(int pItemID) {
		if(pItemID!=0){
			this.mEntityType = "BaseItem";
			this.mItemID = pItemID;
			this.mItemName = Game.getDataHandler().getItemName(this.mItemID);
			this.mItemClass = Game.getDataHandler().getItemClass(this.mItemID);
			this.mItemType = Game.getDataHandler().getItemType(this.mItemID);
			this.mItemDescription = Game.getDataHandler().getItemDescription(this.mItemID);
			this.mItemBuyPrice = Game.getDataHandler().getItemBuyPrice(this.mItemID);	
			this.mItemSellPrice = Game.getDataHandler().getItemSellPrice(this.mItemID);
			this.mModifiers = Game.getDataHandler().getItemModifiers(this.mItemID);
			this.mStackable = Game.getDataHandler().isItemStackable(this.mItemID);
		}else{
			this.mEntityType = "NullItem";
		}
	}


	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getEntityType() {
		return mEntityType;
	}

	public void setEntityType(String mEntityType) {
		this.mEntityType = mEntityType;
	}

	public int getItemID() {
		return mItemID;
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
	
	public int getItemDefense() {
		return mModifiers[2];
	}
	
	public int getItemEndurance() {
		return mModifiers[3];
	}
	
	public boolean isStackable(){
		return mStackable;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
