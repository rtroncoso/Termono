package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import com.quest.constants.ServerMessageFlags;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 19:48:32 - 28.02.2011
 */
public class ServerMessageUpdateEntityPosition extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private String mPlayerKey;
	private byte mPlayerDirection;

	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageUpdateEntityPosition() {
	}

	public ServerMessageUpdateEntityPosition(String pPlayerKey, byte pPlayerDirection) {
		this.mPlayerKey = pPlayerKey;
		this.mPlayerDirection = pPlayerDirection;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void set(String pPlayerKey, byte pPlayerDirection) {
		this.mPlayerKey = pPlayerKey;
		this.mPlayerDirection = pPlayerDirection;
	}
	
	public String getPlayerKey() {
		return mPlayerKey;
	}

	public byte getPlayerDirection() {
		return mPlayerDirection;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mPlayerKey = pDataInputStream.readUTF();
		this.mPlayerDirection  = pDataInputStream.readByte();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeUTF(this.mPlayerKey);
		pDataOutputStream.writeByte(this.mPlayerDirection);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}