package com.quest.network.messages.client;

import java.util.ArrayList;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;



	public abstract class QuestClientMessage extends ClientMessage{
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
		
		public String arraylistoToString(ArrayList<int[]> lista){
			String stringarray = "";
			for(int i = 0;i<lista.size();i++){
				stringarray+=(lista.get(i)[0]+","+lista.get(i)[1]+"/");
			}
			stringarray+="*";
			return stringarray;
		}
		
		public ArrayList<int[]> getCoordList(String stringarray){
			String[] tmpArray;
			tmpArray = stringarray.split("/");
			ArrayList<int[]> lista = new ArrayList<int[]>();
			for(int i = 0;i<tmpArray.length;i++){
				if(tmpArray[i].equals("*")==false){
					String[] tiles = tmpArray[i].split(",");
					lista.add(new int[]{Integer.parseInt(tiles[0]),Integer.parseInt(tiles[1])});				
				}
			}
			return lista;
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
