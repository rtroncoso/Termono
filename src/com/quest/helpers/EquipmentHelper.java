package com.quest.helpers;

import org.andengine.entity.sprite.Sprite;

import com.quest.entities.objects.Item;

public class EquipmentHelper {
	// ===========================================================
	// Constants
	// ===========================================================
	//HACER UNA FUNCION PARA IDENTIFICAR PARTE DE ARMADURA/ARMA?  *****D*O*N*E******
	//HACER FUNCION PARA DETECTAR DONDE VA A TERMINAR EL COSO DESEQUIPADO
	//Meter la funcion de mType dentro de getEquiped y setEquiped?
	//Eliminar el mType y mandar directo el mEquiped/mEquipedXXXXX? ###########
	//Hacer que detecte armas complejas tipo arco (weapon + offhand)
	//Debuggear para ver si los mEquipedXXXX se cargan bien
	//limpiar el mEquiped? - checkear si esta bien
	
	//hacer codigo para que distinga a donde ir
	//cambiarle el rgb al containerspriet segun que item es
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Item mEquiped;
	private Item mEquipedHelm;
	private Item mEquipedPlate;
	private Item mEquipedLegs;
	private Item mEquipedExtra;
	private Item mEquipedOffhand;
	private Item mEquipedWeapon;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public EquipmentHelper(){
		//vacio para crearlo, ni idea. cargarlo en otro lugar asi queda guardado todo el equipment?
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
	
	
	public void EquipItem(Item pItem, int pType){//Cambiar el Sprite por un "item" || pasarle que tipo es
		//this.setEquiped(pType, pSprite);
		//setear los bonuses
	}
	
	
	
	public void UnequipItem(Item pItem, int pType){
		//Lo muevo a donde corresponde
		//*****************************
		this.getEquiped(pType);
		if(this.mEquiped != null){
		this.mEquiped.getSprite().setPosition(this.mEquiped.getInitialX(), this.mEquiped.getInitialY());//despues hacer la funcion que se fije donde quedaria(lo devuelve al final)
		}
		//*****************************
		
		//Lo saco de equipado y saco bonuses
		//***********************************
		this.setEquiped(pType,null);
		//sacar los bonuses y eso###########
		//***********************************
	}
	
	//TENGO QUE HACER QUE LOS EQUIPED SE CARGUEN CON LOS VALORES DE LA TABLA DE EQUIPAMIENTO QUE TENGO QUE CREAR
	
	
	public void setEquiped(int pType,Item pItem){
		this.mEquiped = pItem;
		switch(pType){//segun el tipo saco el coso
		case 0:
			this.mEquipedHelm = this.mEquiped;
			break;
		case 1:
			this.mEquipedPlate = this.mEquiped;
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
	
	
	
	public Item getEquiped(int pType){//Cambiar Sprite por "item" || pasarle que tipo es
		//########## Asignar asi "this.mEquiped = this.mEquipedHelm" y hacer return a "this.mEquiped"
		//########## O hacer el return en el case!????  (no se si se aplican las cosas de la primer manera)		
		//########## Consultar con escoba si no es mejor el codigo comentadod e abajo
		switch(pType){//segun el tipo saco el coso
		case 0:
			this.mEquiped = this.mEquipedHelm;
			break;
		case 1:
			this.mEquiped = this.mEquipedPlate;
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

















/*
case 0:
	this.mEquipedHelm.setPosition(this.mEquipedHelm.getInitialX(), this.mEquipedHelm.getInitialY());
	this.mEquipedHelm = null;
	break;
case 1:
	this.mEquipedPlate.setPosition(this.mEquipedPlate.getInitialX(), this.mEquipedPlate.getInitialY());
	this.mEquipedPlate = null;
	break;
case 2:
	this.mEquipedLegs.setPosition(this.mEquipedLegs.getInitialX(), this.mEquipedLegs.getInitialY());
	this.mEquipedLegs = null;
	break;
case 3:
	this.mEquipedExtra.setPosition(this.mEquipedExtra.getInitialX(), this.mEquipedExtra.getInitialY());
	this.mEquipedExtra = null;
	break;
case 4:
	this.mEquipedOffhand.setPosition(this.mEquipedOffhand.getInitialX(), this.mEquipedOffhand.getInitialY());
	this.mEquipedOffhand = null;
	break;
case 5:
	this.mEquipedWeapon.setPosition(this.mEquipedWeapon.getInitialX(), this.mEquipedWeapon.getInitialY());
	this.mEquipedWeapon = null;
	break;
	*/







/*switch(pType){//segun el tipo saco el coso
		case 0:
			return this.mEquipedHelm;//returnear esto o igualar mEquiped a estoy y returnear mEquiped?
		case 1:
			return this.mEquipedPlate;
		case 2:
			return this.mEquipedLegs;
		case 3:
			return this.mEquipedExtra;
		case 4:
			return this.mEquipedOffhand;
		case 5:
			return this.mEquipedWeapon;
		}
		//Cambiar la funcion a void // me suena que esto es re negrada*/
