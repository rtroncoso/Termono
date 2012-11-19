package com.quest.helpers;

import java.util.ArrayList;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.Player;
import com.quest.entities.objects.Item;
import com.quest.game.Game;
import com.quest.pools.ItemPool;

public class InventoryItemHelper implements GameFlags{

	private ArrayList<Item> mItems;
	private Player mOwner;//ID del owner
	private Item nullItem; 
	private ArrayList<Item> mEquippedItems;
	
	public InventoryItemHelper(Player pOwner) {
		this.mItems = new ArrayList<Item>();
		this.mOwner = pOwner;
		nullItem = new Item(0,0);
	}
	
	public void addItem(Item pBaseItem){
			boolean add = true;
			if(pBaseItem.isStackable()){
				if(getItembyID(pBaseItem.getItemFlag()) != null){
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

				//Equipped
				
				if(pBaseItem.isEqquiped()){
					this.getEquippedItems().add(pBaseItem);
					this.mOwner.addModifiers(pBaseItem.getItemModifiers());					
				}
			}		
	}
	
	public void addItem(Item pBaseItem, int pEquipped){
		this.addItem(pBaseItem);
		if(pEquipped==1)this.EquipItem(pBaseItem);
	}
	
	public void removeItem(Item pItem){
		this.decreaseItem(pItem, pItem.getAmount());
	}
	
	public void decreaseItem(Item pItem,int pAmount){//Le resta pAmount al amount que tiene el item, si el resultado es 0 borra el item. (si se quiere borrar de una usar .getItemAmount() como paramentro
		if(pItem!=null) {
			if(pItem.DecreaseAmount(pAmount)){
				this.mItems.remove(pItem);
				Game.getItemHelper().recycleItem(pItem);
			}
		} else {
			Log.d("Quest!","InventoryItemHelper: Decrease - No item matches key");
		}
	}

	public void increaseItem(Item pItem,int pAmount){//Es igual que llamarlo desde el item, pero uso esto para no confundirme con el decrease directo que daria errores de amount.
		if(pItem!=null) {
			pItem.IncreaseAmount(pAmount);
		} else {
			Log.d("Quest!","InventoryItemHelper: Increase - No item matches key");
		}
	}
	
	public void EquipItem(Item item){		
		item.Equip(true);
		this.mOwner.addModifiers(item.getItemModifiers());//server
		this.mOwner.getEquippedWeaponsLayer().attachChild(item);
		this.getEquippedItems().add(item);
	}
	
	public void UnequipItem(Item item){
		item.Equip(false);
		this.mOwner.substractModifiers(item.getItemModifiers());//server
		this.mOwner.getEquippedWeaponsLayer().detachChild(item);
		this.getEquippedItems().remove(item);
	}
	
	public ArrayList<Item> getEquippedItems(){
		if(mEquippedItems==null)mEquippedItems=new ArrayList<Item>();
		return mEquippedItems; 
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
				Game.getItemHelper().allocateItem(mItems.get(i).getItemFlag(), 1);
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
