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
	private int mX;
	private int mY;

	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageUpdateEntityPosition() {
	}

	public ServerMessageUpdateEntityPosition(String pPlayerKey, byte pPlayerDirection) {
		this.mPlayerKey = pPlayerKey;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void set(String pPlayerKey, int pX, int pY) {
		this.mPlayerKey = pPlayerKey;
		this.setX(pX);
		this.setY(pY);
	}
	
	public String getPlayerKey() {
		return mPlayerKey;
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
		this.mPlayerKey = pDataInputStream.readUTF();
		this.mX  = pDataInputStream.readInt();
		this.mY  = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeUTF(this.mPlayerKey);
		pDataOutputStream.writeInt(this.mX);
		pDataOutputStream.writeInt(this.mY);
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