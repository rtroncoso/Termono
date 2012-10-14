package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.entities.Player;

	public class ServerMessageSendPlayer extends QuestServerMessage implements ServerMessageFlags {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================
		private String mUserID;
		private int mPlayerID;
		private int mLevel;
		private int mClass;
		private int mHeadID;		
	//	private int tileX,tileY;  *** Implementar cuando este
		private String mAttributes,currHPMP; 
		private String mItemID, mAmounts,isEquipped,mItemKeys;
				
		// ===========================================================
		// Constructors
		// ===========================================================
		@Deprecated
		public ServerMessageSendPlayer() {
		}

		public ServerMessageSendPlayer(final Player pPlayer,int[] pItemIDs,int[] pAmounts,int[] isEquipped,int[] pItemKeys) {
			LoadPlayer(pPlayer, pItemIDs, pAmounts, isEquipped, pItemKeys);
		}
		
		// ===========================================================
		// Getter & Setter
		// ===========================================================
		public void LoadPlayer(final Player pPlayer,int[] pItemIDs,int[] pAmounts,int[] isEquipped,int[] pItemKeys) {//No puedo usar los constructors
			this.setUserID(pPlayer.getUserID());
			this.setPlayerID(pPlayer.getPlayerID());
			this.setLevel(pPlayer.getLevel());
			this.setPlayerClass(pPlayer.getPlayerClass());
			this.setHeadID(pPlayer.getHeadID());
			//this.setTileX();
			//this.setTileY();
			this.setAttributes(pPlayer.getAttributes());
			this.setCurrHPMP(pPlayer.getCurrHPMP());
			this.setItemID(pItemIDs);
			this.setAmounts(pAmounts);
			this.setIsEquipped(isEquipped);
			this.setItemKeys(pItemKeys);
		}

		public String getUserID() {
			return mUserID;
		}

		public void setUserID(String mUserID) {
			this.mUserID = mUserID;
		}

		public int getPlayerID() {
			return mPlayerID;
		}

		public void setPlayerID(int mPlayerID) {
			this.mPlayerID = mPlayerID;
		}

		public int getLevel() {
			return mLevel;
		}

		public void setLevel(int mLevel) {
			this.mLevel = mLevel;
		}

		public int getPlayerClass() {
			return mClass;
		}

		public void setPlayerClass(int mClass) {
			this.mClass = mClass;
		}
/*
		public int getTileX() {
			return tileX;
		}

		public void setTileX(int tileX) {
			this.tileX = tileX;
		}

		public int getTileY() {
			return tileY;
		}

		public void setTileY(int tileY) {
			this.tileY = tileY;
		}
*/
		public int[] getAttributes() {
			return stringArraytoInt(mAttributes);
		}

		public void setAttributes(int[] mAttributes) {
			this.mAttributes = intArraytoString(mAttributes);
		}

		public int[] getCurrHPMP() {
			return stringArraytoInt(currHPMP);
		}

		public void setCurrHPMP(int[] currHPMP) {
			this.currHPMP = intArraytoString(currHPMP);
		}

		public int[] getItemID() {
			return stringArraytoInt(mItemID);
		}

		public void setItemID(int[] mItemID) {
			this.mItemID = intArraytoString(mItemID);
		}

		public int[] getAmounts() {
			return stringArraytoInt(mAmounts);
		}

		public void setAmounts(int[] mAmounts) {
			this.mAmounts = intArraytoString(mAmounts);
		}

		public int[] getIsEquipped() {
			return stringArraytoInt(isEquipped);
		}

		public void setIsEquipped(int[] isEquipped) {
			this.isEquipped = intArraytoString(isEquipped);
		}

		public int[] getItemKeys() {
			return stringArraytoInt(mItemKeys);
		}

		public void setItemKeys(int[] mItemKeys) {
			this.mItemKeys = intArraytoString(mItemKeys);
		}
		
		public int getHeadID() {
			return mHeadID;
		}

		public void setHeadID(int mHeadID) {
			this.mHeadID = mHeadID;
		}
		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		@Override
		public short getFlag() {
			return FLAG_MESSAGE_SERVER_SEND_PLAYER;
		}

		@Override
		protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
			this.mUserID = pDataInputStream.readUTF();
			this.mPlayerID = pDataInputStream.readInt();
			this.mLevel = pDataInputStream.readInt();
			this.mClass = pDataInputStream.readInt();
			this.mHeadID = pDataInputStream.readInt();
	//		this.tileX = pDataInputStream.readInt();
	//		this.tileY = pDataInputStream.readInt();
			this.mAttributes = pDataInputStream.readUTF();
			this.currHPMP = pDataInputStream.readUTF();
			this.mItemID = pDataInputStream.readUTF();
			this.mItemKeys = pDataInputStream.readUTF();
			this.isEquipped = pDataInputStream.readUTF();
			this.mAmounts = pDataInputStream.readUTF();		
		}

		@Override
		protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
			pDataOutputStream.writeUTF(this.mUserID);
			pDataOutputStream.writeInt(this.mPlayerID);
			pDataOutputStream.writeInt(this.mLevel);
			pDataOutputStream.writeInt(this.mClass);
			pDataOutputStream.writeInt(this.mHeadID);
		//	pDataOutputStream.writeInt(this.tileX);
	//		pDataOutputStream.writeInt(this.tileY);
			pDataOutputStream.writeUTF(this.mAttributes);
			pDataOutputStream.writeUTF(this.currHPMP);
			pDataOutputStream.writeUTF(this.mItemID);
			pDataOutputStream.writeUTF(this.mItemKeys);
			pDataOutputStream.writeUTF(this.isEquipped);
			pDataOutputStream.writeUTF(this.mAmounts);
		}



		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
