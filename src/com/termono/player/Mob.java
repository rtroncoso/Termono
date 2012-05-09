/**
 * 
 */
package com.termono.player;

import java.util.Random;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.util.modifier.ease.EaseLinear;

import com.termono.game.Game;
import com.termono.objects.Entity;

/**
 * @author raccoon
 *
 */
//public class Enemy extends Entity implements IOnScreenControlListener{
public class Mob extends Entity{
	
	// ===========================================================
	// Constants
	// ===========================================================
	private final float SPEED_MODIFIER = 3.0f;
	private final float TILE_WIDTH = 32;
	private final float TILE_HEIGHT = 32;

	// ===========================================================
	// Fields
	// ===========================================================
	private int Vida;
	private PathModifier mPathModifier;
	private Path mPath;
	private Random rand;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Mob(Game pEngine) {
		super(pEngine);	
		// TODO Auto-generated constructor stub
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public void randomPath() 
	{		
		if(!this.isWalking) 
		{	boolean move = false;
			float moveToXTile = this.mAnimatedSprite.getX();
			float moveToYTile = this.mAnimatedSprite.getY();
			switch(this.getRandom(1, 10))
			{
			case 1://Arriba
				 moveToXTile = this.mAnimatedSprite.getX();
				 moveToYTile = this.mAnimatedSprite.getY() - 32;
				 move = true;
				break;
			case 2://Abajo
				 moveToXTile = this.mAnimatedSprite.getX();
				 moveToYTile = this.mAnimatedSprite.getY() + 32;
				 move = true;
				break;
			case 3://Derecha
				 moveToXTile = this.mAnimatedSprite.getX() + 32;
				 moveToYTile = this.mAnimatedSprite.getY();
				 move = true;
				break;
			case 4://Izquierda
				 moveToXTile = this.mAnimatedSprite.getX() - 32;
				 moveToYTile = this.mAnimatedSprite.getY();
				 move = true;
				break;			
			case 5://Arriba 2
				 moveToXTile = this.mAnimatedSprite.getX();
				 moveToYTile = this.mAnimatedSprite.getY() - 64;
				 move = true;
				break;
			case 6://Abajo 2
				 moveToXTile = this.mAnimatedSprite.getX();
				 moveToYTile = this.mAnimatedSprite.getY() + 64;
				 move = true;
				 break;
			case 7://Derecha 2
				 moveToXTile = this.mAnimatedSprite.getX() + 64;
				 moveToYTile = this.mAnimatedSprite.getY();
				 move = true;
				 break;
			case 8://Izquierda 2
				 moveToXTile = this.mAnimatedSprite.getX() - 32;
				 moveToYTile = this.mAnimatedSprite.getY();
				 move = true;
				 break;
			case 9:
				//nada, se queda en el lugar
				 moveToXTile = this.mAnimatedSprite.getX();
				 moveToYTile = this.mAnimatedSprite.getY();
				 move = false;
				 break;
			case 10:
				//nada, se queda en el lugar
				 moveToXTile = this.mAnimatedSprite.getX();
				 moveToYTile = this.mAnimatedSprite.getY();
				 move = false;
				break;
			}		
			if(move == true)
			{
				this.moveToTile(moveToXTile, moveToYTile, 1.0f);
			}
		}		
	}
	
	private int getRandom(int min, int max)
	{
		rand = new Random();	
		int RandomNum = rand.nextInt(max - min + 1) + min;
		return RandomNum;
	}
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
