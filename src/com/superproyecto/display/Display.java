package com.superproyecto.display;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXLayer;

import com.superproyecto.objects.Entity;

public class Display {
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Camera mCamera;
	private BoundCamera mBoundChaseCamera;
	private int mCameraWidth;
	private int mCameraHeight;
	private int mDisplayWidth;
	private int mDisplayHeight;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Display(int pCameraWidth, int pCameraHeight, int pDisplayWidth, int pDisplayHeight) {
		this.mCameraWidth = pCameraWidth;
		this.mCameraHeight = pCameraHeight;
		this.mDisplayWidth = pDisplayWidth;
		this.mDisplayHeight = pDisplayHeight;

		this.mBoundChaseCamera = new BoundCamera(0, 0, this.mCameraWidth, this.mCameraHeight) { 
			@Override
			public void setCenter(float pCenterX, float pCenterY) {
			        super.setCenter((int)pCenterX, (int)pCenterY);
			}
		};
		
		this.mCamera = new Camera(0, 0, this.mCameraWidth, this.mCameraHeight);
	}
	
	public Display(int pDisplayWidth, int pDisplayHeight) {
		this(pDisplayWidth, pDisplayHeight, pDisplayWidth, pDisplayHeight);
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	public void doFocusCamera(Entity pEntity, TMXLayer pTMXLayer) {

		this.mBoundChaseCamera.setBounds(0, pTMXLayer.getWidth(), 0, pTMXLayer.getHeight());
		this.mBoundChaseCamera.setBoundsEnabled(true);
		this.mCamera.setChaseEntity(pEntity.getAnimatedSprite());
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Camera getCamera() {
		return this.mCamera;
	}

	public int getCameraWidth() {
		return this.mCameraWidth;
	}

	public int getCameraHeight() {
		return this.mCameraHeight;
	}

	public int getDisplayWidth() {
		return this.mDisplayWidth;
	}

	public int getDisplayHeight() {
		return this.mDisplayHeight;
	}

	public void setCamera(Camera pCamera) {
		this.mCamera = pCamera;
	}

	public void setCameraWidth(int pCameraWidth) {
		// TODO Auto-generated method stub
		this.mCameraWidth = pCameraWidth;
	}

	public void setCameraHeight(int pCameraHeight) {
		// TODO Auto-generated method stub
		this.mCameraHeight = pCameraHeight;
	}

	public void setDisplayWidth(int pDisplayWidth) {
		// TODO Auto-generated method stub
		this.mDisplayWidth = pDisplayWidth;
	}

	public void setDisplayHeight(int pDisplayHeight) {
		// TODO Auto-generated method stub
		this.mDisplayHeight = pDisplayHeight;
	}
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
}
