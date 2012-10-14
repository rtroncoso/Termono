package com.quest.helpers;

import java.util.ArrayList;

import android.util.Log;

import com.quest.entities.BaseEntity;
import com.quest.entities.Player;
import com.quest.entities.objects.InventoryItem;
import com.quest.game.Game;

public class InventoryItemHelper {

	private ArrayList<InventoryItem> mItems;
	private String mOwnerID;//ID del owner
	public InventoryItemHelper(String pOwnerID) {
		this.mItems = new ArrayList<InventoryItem>();
		this.mOwnerID = pOwnerID;
	}
	
	public void addItem(InventoryItem pInventoryItem,int pItemKey){
		boolean add = true;
		if(pInventoryItem.isStackable()){
			if(!getItembyID(pInventoryItem.getItemID()).equals(null)){
				getItembyID(pInventoryItem.getItemID()).IncreaseAmount(pInventoryItem.getAmount());
				add = false;
			}
		}
		if(add){
			pInventoryItem.setUserData(pItemKey);
			this.mItems.add(pInventoryItem);
			if(pInventoryItem.isEqquiped())Game.getPlayerHelper().getPlayer(this.mOwnerID).addModifiers(pInventoryItem.getItemModifiers());
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
	
	private InventoryItem getItem(int pItemKey){
		for(InventoryItem tmpItem : this.mItems){
			if(tmpItem.getUserData()==String.valueOf(pItemKey))
				return tmpItem;
		}
		Log.d("Quest!","InventoryItemHelper: Get - No item matches key");
		return null;
	}
	
	private InventoryItem getItembyID(int pItemID){
		for(InventoryItem tmpItem : this.mItems){
			if(tmpItem.getItemID()==pItemID)
				return tmpItem;
		}
		Log.d("Quest!","InventoryItemHelper: Get - No item matches ID");
		return null;
	}
	
	

	/**
	 * @return the mEntities
	 */
	public ArrayList<InventoryItem> getEntities() {
		return mItems;
	}

	/**
	 * @param mEntities the mEntities to set
	 */
	public void setEntities(ArrayList<InventoryItem> pItems) {
		this.mItems = pItems;
	}
}
