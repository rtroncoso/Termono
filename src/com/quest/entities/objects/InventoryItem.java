package com.quest.entities.objects;


public class InventoryItem extends BaseItem{

	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mAmount;
	private int mEquipped;//1 - 0, SQLite no maneja booleans
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public InventoryItem(int pItemID,int pAmount,int pEquipped) {
		super(pItemID);
		this.mAmount = pAmount;
		this.mEquipped = pEquipped;
	}



	// ===========================================================
	// Methods
	// ===========================================================	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getAmount() {
		return mAmount;
	}

	public void setAmount(int mAmount) {
		this.mAmount = mAmount;
	}

	public void IncreaseAmount(){
		this.mAmount+=1;
	}

	public void IncreaseAmount(int pAdded){
		this.mAmount+=pAdded;
	}
	
	public boolean DecreaseAmount(){//NO USAR, FUNCION INTERNA
		this.mAmount-=1;
		if(this.mAmount==0)return true;
		return false;
	}

	public boolean DecreaseAmount(int pSubstracted){//NO USAR, FUNCION INTERNA
		this.mAmount-= pSubstracted;
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
	// Inner and Anonymous Classes
	// ===========================================================
	

}
