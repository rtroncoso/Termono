package com.quest.scenes;

import org.andengine.entity.scene.Scene;

import com.quest.display.Display;
import com.quest.game.Game;
import com.quest.helpers.SceneHelper;

public class OptionsScene extends Scene {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Game mGame;
	private Display mDisplay;
	private SceneHelper mSceneManager;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public OptionsScene(Game pGame){
		this.mGame = pGame;
		this.mSceneManager = new SceneHelper(mGame);
		
		
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
