package com.quest.network.messages.server;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import android.util.Log;


	public abstract class QuestServerMessage extends ServerMessage{
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		// ===========================================================
		// Constructors
		// ===========================================================

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================
		public String intArraytoString(int[] pArray){
			String stringarray="";
			for(int i = 0;i<pArray.length;i++){
				stringarray+=(pArray[i]+",");
			}
			stringarray+="]";
			stringarray.replace(",]","");
			return stringarray;
		}
		
		public int[] stringArraytoInt(String stringarray){
			String[] tmpArray;
			Log.d("Quest!","Stringarray: "+stringarray);
			tmpArray = stringarray.split(",");
			int[] intArray = new int[tmpArray.length];
			for(int i = 0;i<tmpArray.length;i++){
				intArray[i] = Integer.parseInt(tmpArray[i]);
			}
			return intArray;
		}
		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	
}
