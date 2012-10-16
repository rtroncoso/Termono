package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import com.quest.constants.ServerMessageFlags;

public class ServerMessageFixedAttackData extends ServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mMobEntityUserData;
	private int mAttackID;
	private  int mDamage;
	private String mPlayerEntityUserData;
	private boolean isMonsterAttacking;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageFixedAttackData() {
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

	public int getAttackID() {
		return mAttackID;
	}

	public void setAttackID(int mAttackID) {
		this.mAttackID = mAttackID;
	}

	public int getDamage() {
		return mDamage;
	}

	public void setDamage(int mDamage) {
		this.mDamage = mDamage;
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
		this.mAttackID = pDataInputStream.readInt();
		this.mDamage = pDataInputStream.readInt();
		this.mPlayerEntityUserData = pDataInputStream.readUTF();
		this.isMonsterAttacking = pDataInputStream.readBoolean();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mMobEntityUserData);
		pDataOutputStream.writeInt(this.mAttackID);
		pDataOutputStream.writeInt(this.mDamage);
		pDataOutputStream.writeUTF(this.mPlayerEntityUserData);		
		pDataOutputStream.writeBoolean(this.isMonsterAttacking);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_FIXED_ATTACK_DATA;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}