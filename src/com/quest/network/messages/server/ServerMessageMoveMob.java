package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import com.quest.constants.ServerMessageFlags;

public class ServerMessageMoveMob extends ServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mMobKey;
	private byte mMobDirection;

	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageMoveMob() {
	}

	public ServerMessageMoveMob(int pMobKey, byte pMobDirection) {
		this.mMobKey = pMobKey;
		this.mMobDirection = pMobDirection;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void set(int pMobKey, byte pMobDirection) {
		this.mMobKey = pMobKey;
		this.mMobDirection = pMobDirection;
	}
	
	public int getMobKey() {
		return mMobKey;
	}

	public byte getMobDirection() {
		return mMobDirection;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mMobKey = pDataInputStream.readInt();
		this.mMobDirection  = pDataInputStream.readByte();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mMobKey);
		pDataOutputStream.writeByte(this.mMobDirection);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_MOVE_MOB;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
