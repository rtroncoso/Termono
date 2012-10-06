package com.quest.objects;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;

public class BooleanMessage{

// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private final String	mTitle;
	private final String	mMessage;
	private final String	mPositive;
	private final String	mNegative;
	private BaseGameActivity mContext;
	// ===========================================================
	// Constructors
	// ===========================================================
	public BooleanMessage(final String title, final String message,final String positivebutton,final String negativebutton,BaseGameActivity context) {
		this.mMessage = message;
		this.mTitle = title;
		this.mPositive = positivebutton;
		this.mNegative = negativebutton;
		this.mContext = context;
		showMessage();
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
	public void showMessage() {
		mContext.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

				alert.setTitle(BooleanMessage.this.mTitle);
				alert.setMessage(BooleanMessage.this.mMessage);

				alert.setPositiveButton(mPositive, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						onOK();
					}
				});

				alert.setNegativeButton(mNegative, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						onCancel();
					}
				});

				final AlertDialog dialog = alert.create();
				dialog.setOnShowListener(new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {

					}
				});
				dialog.show();
			}
		});
		
	}


	public void onOK(){};
	
	public void onCancel(){};
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	
}