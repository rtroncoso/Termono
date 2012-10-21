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
		protected int mInstance;
		protected int mOriginalHash;
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
			stringarray+="*";
			return stringarray;
		}
		
		public int[] stringArraytoInt(String stringarray){
			String[] tmpArray;
			tmpArray = stringarray.split(",");
			int[] intArray = new int[tmpArray.length-1];
			for(int i = 0;i<tmpArray.length;i++){
				if(tmpArray[i].equals("*")==false)intArray[i] = Integer.parseInt(tmpArray[i]);
			}
			return intArray;
		}
		
		public void setMsgInstance(int pInstance){
			mInstance = pInstance;
		}
		
		public int getMsgInstance(){
			return this.mInstance;
		}
		
		public void setMsgHash(int pHash){
			this.mOriginalHash = pHash; 
		}
		
		public int getOriginalHash(){
			return this.mOriginalHash;
		}
		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	
}
