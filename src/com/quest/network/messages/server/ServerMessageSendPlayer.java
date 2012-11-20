package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.constants.ServerMessageFlags;
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
		private String mAttributes,currHPMP; 
		private String mItemID, mAmounts,isEquipped;
		private int mMoney;
		private float mExperience;
		private int tileX,tileY,mapID;
		// ===========================================================
		// Constructors
		// ===========================================================
		@Deprecated
		public ServerMessageSendPlayer() {
		}

		public ServerMessageSendPlayer(final Player pPlayer,int[] pItemIDs,int[] pAmounts,int[] isEquipped) {
			LoadPlayer(pPlayer, pItemIDs, pAmounts, isEquipped);
		}
		
		// ===========================================================
		// Getter & Setter
		// ===========================================================
		public void LoadPlayer(final Player pPlayer,int[] pItemIDs,int[] pAmounts,int[] isEquipped) {//No puedo usar los constructors
			this.setUserID(pPlayer.getUserID());
			this.setPlayerID(pPlayer.getPlayerID());
			this.setLevel(pPlayer.getLevel());
			this.setMoney(pPlayer.getMoney());
			this.setExperience(pPlayer.getExperience());
			this.setPlayerClass(pPlayer.getPlayerClass());
			this.setHeadID(pPlayer.getHeadID());
			this.setMapID(pPlayer.getCurrentMap());
			this.setTileX(pPlayer.getCoords()[0]);
			this.setTileY(pPlayer.getCoords()[1]);
			this.setAttributes(pPlayer.getAttributes());
			this.setCurrHPMP(new int[]{(int)pPlayer.getCurrHP(),(int)pPlayer.getCurrMana()});
			this.setItemID(pItemIDs);
			this.setAmounts(pAmounts);
			this.setIsEquipped(isEquipped);			
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
		
		public int getMoney() {
			return mMoney;
		}

		public void setMoney(int mMoney) {
			this.mMoney = mMoney;
		}

		public float getExperience() {
			return mExperience;
		}

		public void setExperience(float mExperience) {
			this.mExperience = mExperience;
		}

		public int getHeadID() {
			return mHeadID;
		}

		public void setHeadID(int mHeadID) {
			this.mHeadID = mHeadID;
		}
		
		/**
		 * @return the tileX
		 */
		public int getTileX() {
			return tileX;
		}

		/**
		 * @param tileX the tileX to set
		 */
		public void setTileX(int tileX) {
			this.tileX = tileX;
		}

		/**
		 * @return the tileY
		 */
		public int getTileY() {
			return tileY;
		}

		/**
		 * @param tileY the tileY to set
		 */
		public void setTileY(int tileY) {
			this.tileY = tileY;
		}

		/**
		 * @return the mapID
		 */
		public int getMapID() {
			return mapID;
		}

		/**
		 * @param mapID the mapID to set
		 */
		public void setMapID(int mapID) {
			this.mapID = mapID;
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
			this.mMoney = pDataInputStream.readInt();
			this.mExperience = pDataInputStream.readFloat();
			this.mapID = pDataInputStream.readInt();
			this.tileX = pDataInputStream.readInt();
			this.tileY = pDataInputStream.readInt();
			this.mAttributes = pDataInputStream.readUTF();
			this.currHPMP = pDataInputStream.readUTF();
			this.mItemID = pDataInputStream.readUTF();
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
			pDataOutputStream.writeInt(this.mMoney);
			pDataOutputStream.writeFloat(this.mExperience);
			pDataOutputStream.writeInt(this.mapID);
			pDataOutputStream.writeInt(this.tileX);
			pDataOutputStream.writeInt(this.tileY);
			pDataOutputStream.writeUTF(this.mAttributes);
			pDataOutputStream.writeUTF(this.currHPMP);
			pDataOutputStream.writeUTF(this.mItemID);
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
