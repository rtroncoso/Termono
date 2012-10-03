package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

public class ServerMessageConnectionEstablished extends ServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	public boolean mCharacterExists;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ServerMessageConnectionEstablished() {

	}

	public ServerMessageConnectionEstablished(final boolean pCharacterExists) {
		this.mCharacterExists = pCharacterExists;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public boolean getCharacterExists(){
		return this.mCharacterExists;
	}
	
	public void setCharacterExists(final boolean pCharacterExists) {
		this.mCharacterExists = pCharacterExists;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED1;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mCharacterExists = pDataInputStream.readBoolean();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeBoolean(this.mCharacterExists);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
