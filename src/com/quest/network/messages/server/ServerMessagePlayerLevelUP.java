package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.constants.ServerMessageFlags;

public class ServerMessagePlayerLevelUP extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private String mPlayerKey;
	private int mLevel;
	private int mUnassignedPoints;

	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessagePlayerLevelUP() {
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void set(String pPlayerKey, int pLevel, int pUnassignedPoints) {
		this.mPlayerKey = pPlayerKey;
		this.mLevel = pLevel;
		this.mUnassignedPoints = pUnassignedPoints;
	}
	
	public String getPlayerKey() {
		return mPlayerKey;
	}

	public int getLevel() {
		return mLevel;
	}

	public int getUnassignedPoints() {
		return mUnassignedPoints;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mPlayerKey = pDataInputStream.readUTF();
		this.mLevel  = pDataInputStream.readInt();
		this.mUnassignedPoints  = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeUTF(this.mPlayerKey);
		pDataOutputStream.writeInt(this.mLevel);
		pDataOutputStream.writeInt(this.mUnassignedPoints);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_PLAYER_LEVELUP;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
