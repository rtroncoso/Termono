package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.quest.constants.ClientMessageFlags;

public class ClientMessageSendCollideTiles extends QuestClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mMapID;
	private String mTileList;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ClientMessageSendCollideTiles() {
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * @return the mMapID
	 */
	public int getMapID() {
		return mMapID;
	}

	/**
	 * @param mMapID the mMapID to set
	 */
	public void setMapID(int mMapID) {
		this.mMapID = mMapID;
	}

	/**
	 * @return the mTileList
	 */
	public ArrayList<int[]> getTileList() {
		return getCoordList(mTileList);
	}
	
	/**
	 * @param mTileList the mTileList to set
	 */
	public void setTileList(ArrayList<int[]> pTileList) {
		this.mTileList = arraylistoToString(pTileList);
	}


	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mMapID = pDataInputStream.readInt();
		this.mTileList = pDataInputStream.readUTF();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mMapID);
		pDataOutputStream.writeUTF(this.mTileList);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_CLIENT_SEND_COLLIDE_TILES;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}