package com.quest.helpers;

import java.util.ArrayList;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.objects.Item;
import com.quest.game.Game;
import com.quest.pools.ItemPool;

public class InventoryItemHelper implements GameFlags{

	private ArrayList<Item> mItems;
	private String mOwnerID;//ID del owner
	private Item nullItem; 
	private final ItemPool mItemPool = new ItemPool();
	
	
	public InventoryItemHelper(String pOwnerID) {
		this.mItems = new ArrayList<Item>();
		this.mOwnerID = pOwnerID;
		nullItem = new Item(0, 0, 0);
		this.initItemPool();
	}
	
	private void initItemPool() {
		this.mItemPool.registerItem(FLAG_ITEM_BIG_FLAMED_SWORD);
	}
	
	public void addItem(Item pBaseItem){
			boolean add = true;
			if(pBaseItem.isStackable()){
				if(!getItembyID(pBaseItem.getItemFlag()).equals(null)){
					getItembyID(pBaseItem.getItemFlag()).IncreaseAmount(pBaseItem.getAmount());
					add = false;
				}
			}
			if(add){
				if(mItems.contains(this.nullItem)){
					this.mItems.set(mItems.indexOf(nullItem),pBaseItem);
				}else{
					this.mItems.add(pBaseItem);
				}
				pBaseItem.setUserData(this.mItems.indexOf(pBaseItem));
				
				if(pBaseItem.isEqquiped())Game.getPlayerHelper().getPlayer(this.mOwnerID).addModifiers(pBaseItem.getItemModifiers());
			}		
	}
	
		
	
	public void decreaseItem(int pItemKey,int pAmount){//Le resta pAmount al amount que tiene el item, si el resultado es 0 borra el item. (si se quiere borrar de una usar .getItemAmount() como paramentro
		if(!this.getItem(pItemKey).equals(null)) {
			if(this.getItem(pItemKey).DecreaseAmount(pAmount))this.mItems.remove(this.getItem(pItemKey));
		} else {
			Log.d("Quest!","InventoryItemHelper: Decrease - No item matches key");
		}
	}

	public void increaseItem(int pItemKey,int pAmount){//Es igual que llamarlo desde el item, pero uso esto para no confundirme con el decrease directo que daria errores de amount.
		if(!this.getItem(pItemKey).equals(null)) {
			this.getItem(pItemKey).IncreaseAmount(pAmount);
		} else {
			Log.d("Quest!","InventoryItemHelper: Increase - No item matches key");
		}
	}
	
	public void EquipItem(int pItemKey){
		getItem(pItemKey).Equip(true);
		Game.getPlayerHelper().getPlayer(this.mOwnerID).addModifiers(getItem(pItemKey).getItemModifiers());//server
	}
	
	public void UnequipItem(int pItemKey){
		getItem(pItemKey).Equip(false);
		Game.getPlayerHelper().getPlayer(this.mOwnerID).substractModifiers(getItem(pItemKey).getItemModifiers());//server
	}
	
	public int getItemAmount(int pItemKey){
		return getItem(pItemKey).getAmount();
	}
	
	public Item getItem(int pItemKey){
		for(Item tmpItem : this.mItems){
			if(tmpItem.getUserData()==String.valueOf(pItemKey))
				return tmpItem;
		}
		Log.d("Quest!","InventoryItemHelper: Get - No item matches key");
		return null;
	}
	
	public Item getItembyID(int pItemID){
		for(Item tmpItem : this.mItems){
			if(tmpItem.getItemFlag()==pItemID)
				return tmpItem;
		}
		Log.d("Quest!","InventoryItemHelper: Get - No item matches ID");
		return null;
	}
	
	public Item getItembyIndex(int index){
		return this.mItems.get(index);
	}	
	
	public void writeInventorytoDB(int pPlayerID){
		for(int i = mItems.size()-1;i>=0;i--)
			if(mItems.get(i)==nullItem)
				mItems.remove(i);
		
		Game.getDataHandler().deleteInventory(pPlayerID);
		int[] ItemIDs,Amounts,Equipped;
		ItemIDs = new int[mItems.size()];
		Amounts = new int[mItems.size()];
		Equipped = new int[mItems.size()];
		for(int i = 0;i<this.mItems.size();i++){
			Item tmpItem = this.mItems.get(i);
			ItemIDs[i] = tmpItem.getItemFlag();
			Log.d("Quest!", "itemflag: "+ItemIDs[i]);
			Amounts[i] = tmpItem.getAmount();
			Equipped[i] = 0;
			if(tmpItem.isEqquiped())Equipped[i] = 1;
		}
		Game.getDataHandler().addInventoryItems(pPlayerID, ItemIDs, Amounts, Equipped);
	}
	
	
	public void allocateItems(){
		for(int i = mItems.size()-1;i>=0;i--)
			if(mItems.get(i)!=nullItem)
			{
				Log.d("Quest!", "flag: "+mItems.get(i).getItemFlag());
				this.mItemPool.getPool(mItems.get(i).getItemFlag()).batchAllocatePoolItems(1);
			}
	}

	/**
	 * @return the mEntities
	 */
	public ArrayList<Item> getEntities() {
		return mItems;
	}

	/**
	 * @param mEntities the mEntities to set
	 */
	public void setEntities(ArrayList<Item> pItems) {
		this.mItems = pItems;
	}


}
