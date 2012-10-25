package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import com.quest.constants.ServerMessageFlags;

public class ServerMessageMoveMob extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mMobKey;
	private int mX;
	private int mY;

	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageMoveMob() {
	}

	public ServerMessageMoveMob(int pMobKey, byte pMobDirection) {
		this.mMobKey = pMobKey;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void set(int pMobKey, int pX, int pY) {
		this.mMobKey = pMobKey;
		this.mX = pX;
		this.mY = pY;
	}
	
	public int getMobKey() {
		return mMobKey;
	}

	public int getX() {
		return mX;
	}

	public void setX(int mX) {
		this.mX = mX;
	}

	public int getY() {
		return mY;
	}

	public void setY(int mY) {
		this.mY = mY;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mMobKey = pDataInputStream.readInt();
		this.mX = pDataInputStream.readInt();
		this.mY = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mMobKey);
		pDataOutputStream.writeInt(this.mX);
		pDataOutputStream.writeInt(this.mY);
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
