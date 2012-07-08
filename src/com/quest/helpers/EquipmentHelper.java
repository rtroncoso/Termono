package com.quest.helpers;

import com.quest.database.DataHandler;
import com.quest.entities.objects.Item;
import com.quest.scenes.GameMenuScene;

public class EquipmentHelper {
	// ===========================================================
	// Constants
	// ===========================================================
	//funcion para calcular posicion
	//carga de cosas no equipadas
	//hacer bien la carga de objetos (para que no se creen devuelta con cada tab click)
	//hacer el switcheo de entidades entre equiped y unequiped
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Item mEquiped;
	private Item mEquipedHead;
	private Item mEquipedBody;
	private Item mEquipedLegs;
	private Item mEquipedExtra;
	private Item mEquipedOffhand;
	private Item mEquipedWeapon;
	private DataHandler mDataHandler;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public EquipmentHelper(DataHandler pDataHandler){
		this.mDataHandler = pDataHandler;
	}
	
	public boolean EquipmentFunction(Item pItem){//Cambiar el Sprite por un "item"
		int tType = pItem.getType();
		if(this.IsEquiped(pItem,tType) == false){//me fijo si es igual a lo que ya esta ocupado para saber si estan switcheando items o sacando
		this.UnequipItem(pItem,tType);
		this.EquipItem(pItem,tType);
		return true;
		} else {
			this.UnequipItem(pItem,tType);
		return false;
		}
		//hacer una variable para sortEquip asi no lo llamo tantas veces y optimizo?
	}
	
		
	public boolean IsEquiped(Item pItem, int pType){//Usar IsEquiped o hacer el GetEquiped y comparar? || Cambiar el Sprite por un "item" || pasarle que tipo es
		if(this.getEquiped(pType) != null){		
			if(pItem.getID() == this.getEquiped(pType).getID()){//Checkear por id por si tiene el mismo item?(se desequiparia). tengo que ahcer que se loadee el mEquiped al principio, si no tiene nada que hago?...
			return true;
			} else{
			return false;
			}
		} else {
			return false;
		}
	}
	
	
	public void EquipItem(Item pItem, int pType){
		this.setEquiped(pType, pItem);
		this.mDataHandler.EquipItem(pItem.getID(), 1);
		//setear los bonuses
	}
	
	
	
	public void UnequipItem(Item pItem, int pType){
		this.getEquiped(pType);
		if(this.mEquiped != null){
		this.mDataHandler.EquipItem(this.mEquiped.getID(), 0);
		this.mEquiped.getIcon().setPosition(this.mEquiped.getIcon().getInitialX(), this.mEquiped.getIcon().getInitialY());//despues hacer la funcion que se fije donde quedaria(lo devuelve al final)
		//Lo saco de equipado y saco bonuses
				//***********************************
				this.setEquiped(pType,null);
				//sacar los bonuses y eso###########
				//***********************************
		}
	}
	
	//TENGO QUE HACER QUE LOS EQUIPED SE CARGUEN CON LOS VALORES DE LA TABLA DE EQUIPAMIENTO QUE TENGO QUE CREAR
	
	
	public void setEquiped(int pType,Item pItem){
		this.mEquiped = pItem;
		switch(pType){//segun el tipo saco el coso
		case 0:
			this.mEquipedHead = this.mEquiped;
			break;
		case 1:
			this.mEquipedBody = this.mEquiped;
			break;
		case 2:
			this.mEquipedLegs = this.mEquiped;
			break;
		case 3:
			this.mEquipedExtra = this.mEquiped;
			break;
		case 4:
			this.mEquipedOffhand = this.mEquiped;
			break;
		case 5:
			this.mEquipedWeapon = this.mEquiped;
			break;
		}
	}
	
		
	public Item getEquiped(int pType){
		switch(pType){
		case 0:
			this.mEquiped = this.mEquipedHead;
			break;
		case 1:
			this.mEquiped = this.mEquipedBody;
			break;
		case 2:
			this.mEquiped = this.mEquipedLegs;
			break;
		case 3:
			this.mEquiped = this.mEquipedExtra;
			break;
		case 4:
			this.mEquiped = this.mEquipedOffhand;
			break;
		case 5:
			this.mEquiped = this.mEquipedWeapon;
			break;
		}
		return this.mEquiped;
	}
	
	
	
	public void LoadEquipedItem(Item pItem){
		switch(pItem.getType()){
		case 0:
			this.mEquipedHead = pItem;
			break;
		case 1:
			this.mEquipedBody = pItem;
			break;
		case 2:
			this.mEquipedLegs = pItem;
			break;
		case 3:
			this.mEquipedExtra = pItem;
			break;
		case 4:
			this.mEquipedOffhand = pItem;
			break;
		case 5:
			this.mEquipedWeapon = pItem;
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
