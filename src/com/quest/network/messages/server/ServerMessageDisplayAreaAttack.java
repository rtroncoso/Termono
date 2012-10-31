package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.constants.ServerMessageFlags;

public class ServerMessageDisplayAreaAttack extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private String mUserID;
	private int mAttack_Flag;
	private int mTileX;
	private  int mTileY;
	private int mMap;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageDisplayAreaAttack() {
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * @return the mAttack_Flag
	 */
	public int getAttack_Flag() {
		return mAttack_Flag;
	}

	/**
	 * @param mAttack_Flag the mAttack_Flag to set
	 */
	public void setAttack_Flag(int mAttack_Flag) {
		this.mAttack_Flag = mAttack_Flag;
	}

	/**
	 * @return the mTileX
	 */
	public int getTileX() {
		return mTileX;
	}

	/**
	 * @param mTileX the mTileX to set
	 */
	public void setTileX(int mTileX) {
		this.mTileX = mTileX;
	}

	public String getUserID() {
		return mUserID;
	}

	public void setUserID(String mUserID) {
		this.mUserID = mUserID;
	}

	public int getMap() {
		return mMap;
	}

	public void setMap(int mMap) {
		this.mMap = mMap;
	}

	/**
	 * @return the mTileY
	 */
	public int getTileY() {
		return mTileY;
	}

	/**
	 * @param mTileY the mTileY to set
	 */
	public void setTileY(int mTileY) {
		this.mTileY = mTileY;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mUserID = pDataInputStream.readUTF();
		this.mAttack_Flag = pDataInputStream.readInt();
		this.mTileX = pDataInputStream.readInt();
		this.mTileY = pDataInputStream.readInt();
		this.mMap = pDataInputStream.readInt();
	}


	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeUTF(this.mUserID);
		pDataOutputStream.writeInt(this.mAttack_Flag);
		pDataOutputStream.writeInt(this.mTileX);
		pDataOutputStream.writeInt(this.mTileY);
		pDataOutputStream.writeInt(this.mMap);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_DISPLAY_AREA_ATTACK;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}