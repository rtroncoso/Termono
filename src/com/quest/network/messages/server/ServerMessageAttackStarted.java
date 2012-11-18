package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.constants.ServerMessageFlags;

public class ServerMessageAttackStarted extends QuestServerMessage implements ServerMessageFlags{
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mMobEntityUserData;
	private String mPlayerEntityUserData;
	private boolean isMonsterAttacking;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageAttackStarted() {
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getMobID() {
		return mMobEntityUserData;
	}

	public void setMobEntityUserData(int pMobEntityUserData) {
		this.mMobEntityUserData = pMobEntityUserData;
	}

	public String getPlayerKey() {
		return mPlayerEntityUserData;
	}

	public void setPlayerEntityUserData(String pPlayerEntityUserData) {
		this.mPlayerEntityUserData = pPlayerEntityUserData;
	}

	public boolean isMonsterAttacking() {
		return isMonsterAttacking;
	}

	public void setMonsterAttacking(boolean isMonsterAttacking) {
		this.isMonsterAttacking = isMonsterAttacking;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mMobEntityUserData = pDataInputStream.readInt();
		this.mPlayerEntityUserData = pDataInputStream.readUTF();
		this.isMonsterAttacking = pDataInputStream.readBoolean();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mMobEntityUserData);
		pDataOutputStream.writeUTF(this.mPlayerEntityUserData);		
		pDataOutputStream.writeBoolean(this.isMonsterAttacking);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_ATTACK_STARTED;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
