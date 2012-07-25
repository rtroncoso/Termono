package com.quest.helpers;

import android.util.Log;

import com.quest.database.DataHandler;
import com.quest.entities.objects.ItemIcon;
import com.quest.scenes.GameMenuScene;

public class EquipmentHelper {
	// ===========================================================
	// Constants
	// ===========================================================
	//hacer que se ordenen cuando saco uno
	//hacer que switcheen los items equipados
	//hacer bien la carga de objetos (para que no se creen devuelta con cada tab click)
	//hacer el switcheo de entidades entre Equipped y unEquipped
	
	// ===========================================================
	// Fields
	// ===========================================================
private ItemIcon mEquippedHead;
	private ItemIcon mEquippedBody;
	private ItemIcon mEquippedLegs;
	private ItemIcon mEquippedExtra;
	private ItemIcon mEquippedOffhand;
	private ItemIcon mEquippedWeapon;
	private DataHandler mDataHandler;
	private GameMenuScene mGameMenuScene;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public EquipmentHelper(DataHandler pDataHandler, GameMenuScene pGameMenuScene){
		this.mDataHandler = pDataHandler;
		this.mGameMenuScene = pGameMenuScene;
	}
	
	public boolean EquipmentFunction(ItemIcon pItem){//Cambiar el Sprite por un "item"
		int tType = pItem.getType();
		if(this.IsEquipped(pItem,tType) == false){//me fijo si es igual a lo que ya esta ocupado para saber si estan switcheando items o sacando
			this.UnequipItem(tType,pItem);
			this.EquipItem(pItem,tType);
			return true;
		} else {
			this.UnequipItem(tType,pItem);
		return false;
		}
		//hacer una variable para sortEquip asi no lo llamo tantas veces y optimizo?
	}
	
		
	public boolean IsEquipped(ItemIcon pItem, int pType){//Usar IsEquipped o hacer el GetEquipped y comparar? || Cambiar el Sprite por un "item" || pasarle que tipo es
		if(this.getEquipped(pType) != null){		
			if(pItem.getID() == this.getEquipped(pType).getID()){//Checkear por id por si tiene el mismo item?(se desequiparia). tengo que ahcer que se loadee el mEquipped al principio, si no tiene nada que hago?...
			return true;
			} else{
			return false;
			}
		} else {
			return false;
		}
	}
	
	
	public void EquipItem(ItemIcon pItem, int pType){
		this.setEquipped(pType, pItem);
		this.mDataHandler.EquipItem(pItem.getID(), 1);
		this.mGameMenuScene.getEquipmentUnEquippedItemsEntity().detachChild(pItem.getIcon());//lo saco de la entidad de los desequipados
		this.mGameMenuScene.getEquipmentEntity().attachChild(pItem.getIcon());//lo cambio a la entidad de los equipados
		pItem.setEntity(this.mGameMenuScene.getEquipmentEntity());//le paso la entidad para que se reste cuando lo mueva
		this.mGameMenuScene.setUnEquippedCount(this.mGameMenuScene.getUnEquippedCount()-1);
		//setear los bonuses
	}
	
	
	
	public void UnequipItem(int pType,ItemIcon pItem){
		ItemIcon pEquipped = this.getEquipped(pType);
		if(pEquipped != null){
		if(this.mGameMenuScene.getItemList().contains(pItem)){//si existe pItem(osea que se esta switcheando un item)
			this.mGameMenuScene.getItemList().set(this.mGameMenuScene.getItemList().indexOf(pItem),pEquipped);//cambia la id del equipado por la del nuevo
		}else{//si se esta desequipando
			this.mGameMenuScene.getItemList().add(this.mGameMenuScene.getUnEquippedCount(), pEquipped);//agrega lo que estaba equipado como ultimo
		}
		this.mDataHandler.EquipItem(pEquipped.getID(), 0);//lo des equipo
		this.mGameMenuScene.getEquipmentEntity().detachChild(pEquipped.getIcon());//lo saco de la entidad de los equipados
		this.mGameMenuScene.getEquipmentUnEquippedItemsEntity().attachChild(pEquipped.getIcon());//lo cambio a la entidad de los desequipados
		pEquipped.setEntity(this.mGameMenuScene.getEquipmentUnEquippedItemsEntity());//le paso la entidad para que se reste cuando lo mueva
		pEquipped.getIcon().setPosition(pEquipped.getIcon().getX()-this.mGameMenuScene.getEquipmentUnEquippedItemsEntity().getX(), pEquipped.getIcon().getY()-this.mGameMenuScene.getEquipmentUnEquippedItemsEntity().getY());//Le resto la entidad nueva asi se anima lindo
		this.mGameMenuScene.PlaceEquipmentItem(pEquipped);
		this.setEquipped(pType,null);
		//sacar los bonuses y eso###########
		//***********************************
		}else{//si no habia nada equipado(osea queda un agujero) || esto solo pasa cuando se equipa algo nuevo
			int pStart = this.mGameMenuScene.getItemList().indexOf(pItem);
			for(int i = 0;pStart + i<this.mGameMenuScene.getItemList().size();i++){
				if(pStart+i != this.mGameMenuScene.getItemList().size()-1){
					this.mGameMenuScene.getItemList().set(pStart+i, this.mGameMenuScene.getItemList().get(pStart+i+1));
					this.mGameMenuScene.setUnEquippedCount(this.mGameMenuScene.getUnEquippedCount()-1);//lo cambie de lugar - cambia?
					this.mGameMenuScene.PlaceEquipmentItem((ItemIcon) this.mGameMenuScene.getItemList().get(pStart+i));
					
				}else{
					this.mGameMenuScene.getItemList().remove(pStart +i);
					}
			}
		}
	}
	
	
	
	public void setEquipped(int pType,ItemIcon pItem){
		ItemIcon mEquipped = pItem;
		switch(pType){//segun el tipo saco el coso
		case 0:
			this.mEquippedHead = mEquipped;
			break;
		case 1:
			this.mEquippedBody = mEquipped;
			break;
		case 2:
			this.mEquippedLegs = mEquipped;
			break;
		case 3:
			this.mEquippedExtra = mEquipped;
			break;
		case 4:
			this.mEquippedOffhand = mEquipped;
			break;
		case 5:
			this.mEquippedWeapon = mEquipped;
			break;
		}
	}
	
		
	public ItemIcon getEquipped(int pType){
		ItemIcon sEquipped = null;
		switch(pType){
		case 0:
			sEquipped = this.mEquippedHead;
			break;
		case 1:
			sEquipped = this.mEquippedBody;
			break;
		case 2:
			sEquipped = this.mEquippedLegs;
			break;
		case 3:
			sEquipped = this.mEquippedExtra;
			break;
		case 4:
			sEquipped = this.mEquippedOffhand;
			break;
		case 5:
			sEquipped = this.mEquippedWeapon;
			break;
		}
		return sEquipped;
	}
	
	
	
	public void LoadEquippedItem(ItemIcon pItem){
		switch(pItem.getType()){
		case 0:
			this.mEquippedHead = pItem;
			break;
		case 1:
			this.mEquippedBody = pItem;
			break;
		case 2:
			this.mEquippedLegs = pItem;
			break;
		case 3:
			this.mEquippedExtra = pItem;
			break;
		case 4:
			this.mEquippedOffhand = pItem;
			break;
		case 5:
			this.mEquippedWeapon = pItem;
			break;
		}
	}
	
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
