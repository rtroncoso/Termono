package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

public class ServerMessageExistingPlayer extends ServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mCharacterID;
	private int mLevel;
	private int mClass;
	//despues hacer que le pase lo equipado tambien
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ServerMessageExistingPlayer() {
	}

	public ServerMessageExistingPlayer(final int pCharacterID, final int pLevel, final int pClass) {
		this.mCharacterID = pCharacterID;
		this.mLevel = pLevel;
		this.mClass = pClass;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public int getCharacterID() {
		return this.mCharacterID;
	}

	public void setCharacterID(final int pCharacterID) {
		this.mCharacterID = pCharacterID;
	}
	
	public int getLevel() {
		return this.mLevel;
	}

	public void setLevel(final int pLevel) {
		this.mLevel = pLevel;
	}
	
	public int getPlayerClass() {
		return this.mClass;
	}

	public void setPlayerClass(final int pClass) {
		this.mClass = pClass;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_EXISTING_PLAYER;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		this.mCharacterID = pDataInputStream.readInt();
		this.mLevel = pDataInputStream.readInt();
		this.mClass = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mCharacterID);
		pDataOutputStream.writeInt(this.mLevel);
		pDataOutputStream.writeInt(this.mClass);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
