package com.quest.display;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.Entity;

public class Display {
	
	// ===========================================================
	// Fields
	// ===========================================================
	private SmoothCamera mCamera;
	private BoundCamera mBoundChaseCamera;
	private int mCameraWidth;
	private int mCameraHeight;
	private int mDisplayWidth;
	private int mDisplayHeight;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Display(int pCameraWidth, int pCameraHeight, int pDisplayWidth, int pDisplayHeight, float pZoom) {
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
		
		
		this.mCamera = new SmoothCamera(0, 0, this.mCameraWidth, this.mCameraHeight, 170, 170, 1.7f);
		this.mCamera.setZoomFactor(pZoom);
	}
	
		
	public Display(int pDisplayWidth, int pDisplayHeight, float pzoom) {
		this(pDisplayWidth, pDisplayHeight, pDisplayWidth, pDisplayHeight, pzoom);
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	public void doFocusCamera(Entity pEntity) {

		this.mCamera.setChaseEntity(pEntity);
	}
	
	public void setMapBounds(int pWidth, int pHeight) {
		this.mBoundChaseCamera.setBounds(0, pWidth, 0, pHeight);
		this.mBoundChaseCamera.setBoundsEnabled(true);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

		
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
	
	public SmoothCamera getCamera() {
		return this.mCamera;
	}

	public void setCamera(SmoothCamera pCamera) {
		this.mCamera = pCamera;
	}

	public void setZoom(Float pZoom){
		this.mCamera.setZoomFactor(pZoom);
	}
	
	public void setPos(float pX, float pY){
		//this.mCamera.setBoundsEnabled(false);
		//this.mCamera.reset();
		this.mCamera.setCenter(pX, pY);
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
