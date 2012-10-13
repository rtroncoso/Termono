/**
 * 
 */
package com.quest.entities;

import java.util.Random;

import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;

import com.quest.entities.objects.Spell;
import com.quest.game.Game;

/**
 * @author raccoon
 *
 */
//public class Enemy extends Entity implements IOnScreenControlListener{
public class Mob extends BaseEntity implements ITouchArea {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Random rand;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Mob(String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		// TODO Auto-generated constructor stub
		super(pTextureName, pFrameWidth, pFrameHeight, pFramePosX, pFramePosY, pCols, pRows);
		
		this.mEntityType = "Mob";
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public void randomPath() 
	{		
		if(!this.isWalking) 
		{	boolean move = false;
			float moveToXTile = this.getX();
			float moveToYTile = this.getY();
			switch(this.getRandom(1, 10))
			{
			case 1://Arriba
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() - 32;
				 move = true;
				break;
			case 2://Abajo
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() + 32;
				 move = true;
				break;
			case 3://Derecha
				 moveToXTile = this.getX() + 32;
				 moveToYTile = this.getY();
				 move = true;
				break;
			case 4://Izquierda
				 moveToXTile = this.getX() - 32;
				 moveToYTile = this.getY();
				 move = true;
				break;			
			case 5://Arriba 2
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() - 64;
				 move = true;
				break;
			case 6://Abajo 2
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() + 64;
				 move = true;
				 break;
			case 7://Derecha 2
				 moveToXTile = this.getX() + 64;
				 moveToYTile = this.getY();
				 move = true;
				 break;
			case 8://Izquierda 2
				 moveToXTile = this.getX() - 32;
				 moveToYTile = this.getY();
				 move = true;
				 break;
			case 9:
				//nada, se queda en el lugar
				 moveToXTile = this.getX();
				 moveToYTile = this.getY();
				 move = false;
				 break;
			case 10:
				//nada, se queda en el lugar
				 moveToXTile = this.getX();
				 moveToYTile = this.getY();
				 move = false;
				break;
			}		
			if(move == true)
			{

				// Is it a legal position?
				if(!Game.getMapManager().isLegalPosition((int) moveToXTile, (int) moveToYTile)) return;
				
				// Gets the Tile
				final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(moveToXTile, moveToYTile);
				
				// Animate the Character
				long frameDuration = (long) ((1.0f / SPEED_MODIFIER) * 1000) / 4;
				long[] frameDurations = { frameDuration, frameDuration, frameDuration, frameDuration };
				this.setAnimationDirection(this.getFacingDirectionToTile(tmxTileTo), frameDurations, false);
				
				// Perform Move
				this.moveToTile(tmxTileTo);
			}
		}		
	}
	
	private int getRandom(int min, int max)
	{
		rand = new Random();	
		int RandomNum = rand.nextInt(max - min + 1) + min;
		return RandomNum;
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		this.mSpellsLayer.add(new Spell(0));
		
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
				pTouchAreaLocalY);
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