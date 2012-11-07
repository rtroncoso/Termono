package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

import com.quest.constants.ClientMessageFlags;

public class ClientMessageSelectedPlayer extends QuestClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mPlayerID;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessageSelectedPlayer() {
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getPlayerID() {
		return mPlayerID;
	}

	public void setPlayerID(int mPlayerID) {
		this.mPlayerID = mPlayerID;
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	public short getFlag() {
		return FLAG_MESSAGE_CLIENT_SELECTED_PLAYER;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		 this.mPlayerID = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mPlayerID);		
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
