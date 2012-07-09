package com.quest.helpers;

import com.quest.database.DataHandler;
import com.quest.entities.objects.Item;
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
	private Item mEquipped;
	private Item mEquippedHead;
	private Item mEquippedBody;
	private Item mEquippedLegs;
	private Item mEquippedExtra;
	private Item mEquippedOffhand;
	private Item mEquippedWeapon;
	private DataHandler mDataHandler;
	private GameMenuScene mGameMenuScene;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public EquipmentHelper(DataHandler pDataHandler, GameMenuScene pGameMenuScene){
		this.mDataHandler = pDataHandler;
		this.mGameMenuScene = pGameMenuScene;
	}
	
	public boolean EquipmentFunction(Item pItem){//Cambiar el Sprite por un "item"
		int tType = pItem.getType();
		if(this.IsEquipped(pItem,tType) == false){//me fijo si es igual a lo que ya esta ocupado para saber si estan switcheando items o sacando
		this.UnequipItem(tType);
		this.EquipItem(pItem,tType);
		return true;
		} else {
			this.UnequipItem(tType);
		return false;
		}
		//hacer una variable para sortEquip asi no lo llamo tantas veces y optimizo?
	}
	
		
	public boolean IsEquipped(Item pItem, int pType){//Usar IsEquipped o hacer el GetEquipped y comparar? || Cambiar el Sprite por un "item" || pasarle que tipo es
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
	
	
	public void EquipItem(Item pItem, int pType){
		this.setEquipped(pType, pItem);
		this.mDataHandler.EquipItem(pItem.getID(), 1);
		this.mGameMenuScene.getEquipmentUnEquippedItemsEntity().detachChild(pItem.getIcon());//lo saco de la entidad de los desequipados
		this.mGameMenuScene.getEquipmentEntity().attachChild(pItem.getIcon());//lo cambio a la entidad de los equipados
		pItem.setEntity(this.mGameMenuScene.getEquipmentEntity());//le paso la entidad para que se reste cuando lo mueva
		this.mGameMenuScene.setUnEquippedCount(this.mGameMenuScene.getUnEquippedCount()-1);
		//setear los bonuses
	}
	
	
	
	public void UnequipItem(int pType){
		this.getEquipped(pType);
		if(this.mEquipped != null){
		this.mDataHandler.EquipItem(this.mEquipped.getID(), 0);//lo des equipo
		this.mGameMenuScene.getEquipmentEntity().detachChild(this.mEquipped.getIcon());//lo saco de la entidad de los equipados
		this.mGameMenuScene.getEquipmentUnEquippedItemsEntity().attachChild(this.mEquipped.getIcon());//lo cambio a la entidad de los desequipados
		this.mEquipped.setEntity(this.mGameMenuScene.getEquipmentUnEquippedItemsEntity());//le paso la entidad para que se reste cuando lo mueva
		this.mGameMenuScene.PlaceEquipmentItem(this.mEquipped);
		this.setEquipped(pType,null);
		//sacar los bonuses y eso###########
		//***********************************
		}
	}
	
	//TENGO QUE HACER QUE LOS Equipped SE CARGUEN CON LOS VALORES DE LA TABLA DE EQUIPAMIENTO QUE TENGO QUE CREAR
	
	
	public void setEquipped(int pType,Item pItem){
		this.mEquipped = pItem;
		switch(pType){//segun el tipo saco el coso
		case 0:
			this.mEquippedHead = this.mEquipped;
			break;
		case 1:
			this.mEquippedBody = this.mEquipped;
			break;
		case 2:
			this.mEquippedLegs = this.mEquipped;
			break;
		case 3:
			this.mEquippedExtra = this.mEquipped;
			break;
		case 4:
			this.mEquippedOffhand = this.mEquipped;
			break;
		case 5:
			this.mEquippedWeapon = this.mEquipped;
			break;
		}
	}
	
		
	public Item getEquipped(int pType){
		switch(pType){
		case 0:
			this.mEquipped = this.mEquippedHead;
			break;
		case 1:
			this.mEquipped = this.mEquippedBody;
			break;
		case 2:
			this.mEquipped = this.mEquippedLegs;
			break;
		case 3:
			this.mEquipped = this.mEquippedExtra;
			break;
		case 4:
			this.mEquipped = this.mEquippedOffhand;
			break;
		case 5:
			this.mEquipped = this.mEquippedWeapon;
			break;
		}
		return this.mEquipped;
	}
	
	
	
	public void LoadEquippedItem(Item pItem){
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
