package com.quest.helpers;

import org.andengine.entity.sprite.Sprite;

public class InventoryHelper {

	// ===========================================================
	// Constants
	// ===========================================================
	//hacer un "applyeffect" que se guarde en el item o buscar cual es el efecto y hacerlo ahi?
	//Como guardo cuantos tiene de cada uno, funcion de carga de SQL?
	//Si tengo varias pociones, se arrastra solo 1 o todas y vuelven al mismo lugar? (si hago lo primero ahorro codigo de checkeo, solo lo hago al agarrarlo)
	// ===========================================================
	// Fields
	// ===========================================================
	private int mAlpha;
	private int mType;
	// ===========================================================
	// Constructors
	// ===========================================================
	public InventoryHelper(){
		//vacio para cargarlo
	}
	
	//fijarme si es el ultimo/unico
	
	public boolean UseFunction(Sprite pSprite){//Cambiar el Sprite por un "item"
		//checkear que tipo de item es
			//si es consumible
		//aplicar el effecto
		//sacar el efecto
		//fijarme, si es el ultimo moverlo y ordenar los otros
			//si es de quest o algo asi
			//devolverlo al mismo lugar
		return true;//devolver si se uso
	}		
	
	
	public boolean TossFunction(Sprite pSprite){//Cambiar el Sprite por un "item"
		//checkear que tipo de item es
			//si es consumible
		//Tirarlo            --------Si son 5, tiro todos? Pregunto?---------------
		//ordenar los otros
			//si es de quest o algo asi
			//devolverlo al mismo lugar
		return true;//devolver si se tiro
	}	
	
	
	public int CheckItem(Sprite pSprite){
		this.mAlpha = (int)(pSprite.getAlpha() * 100);
		switch(this.mAlpha){
		case 95:
			this.mType = 0; //Pocion
			break;
		case 96:
			this.mType = 1; //Otra cosa
			break;
		case 97:
			this.mType = 2; // No se
			break;
		case 98:
			this.mType = 3; //MAs
			break;
		case 99:
			this.mType = 4; //Etc
			break;
		case 100:
			this.mType = 5; //lalalala
			break;	
		}
		return mType;
	}
	
	public void UseItem(Sprite pSprite){
		//aca checkearia cuantos quedan
		pSprite.setAlpha(0.0000001f);//aca lo borraria si es el ultimo

	}
	
	public void TossItem(Sprite pSprite){
		pSprite.setAlpha(0.0000001f);//aca lo borraria 
		}
	
	public void CountItem(){
		//checkeo cuantos quedan en la tabla, devuelvo el numero
	}
	
	public void MoveItem(){
		//funcion para volver a ordenar los items una ves que se usa(si son varios)
	}
	
	
	public void LoadInventory(){
	//Cargar la tabla SQL que contiene el inventario con los objetos y las cantidades 
	
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
