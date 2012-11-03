package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import com.quest.constants.ServerMessageFlags;

public class ServerMessageMobDied extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mMobEntityUserData;
	private float mExperience;
	private int mMoney;
	private int mDroppedItem;
	private int mDroppedAmount;
	private String mPlayerKey;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessageMobDied() {
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getMobEntityUserData() {
		return mMobEntityUserData;
	}

	public void setMobEntityUserData(int mMobEntityUserData) {
		this.mMobEntityUserData = mMobEntityUserData;
	}

	public float getExperience() {
		return mExperience;
	}

	public void setExperience(float mExperience) {
		this.mExperience = mExperience;
	}

	public int getMoney() {
		return mMoney;
	}

	public void setMoney(int mMoney) {
		this.mMoney = mMoney;
	}

	public int getDroppedItem() {
		return mDroppedItem;
	}

	public void setDroppedItem(int mDroppedItem) {
		this.mDroppedItem = mDroppedItem;
	}

	public int getDroppedAmount() {
		return mDroppedAmount;
	}

	public void setDroppedAmount(int mDroppedAmount) {
		this.mDroppedAmount = mDroppedAmount;
	}

	public String getPlayerKey() {
		return mPlayerKey;
	}

	public void setPlayerKey(String mPlayerKey) {
		this.mPlayerKey = mPlayerKey;
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mMobEntityUserData = pDataInputStream.readInt();
		this.mExperience = pDataInputStream.readFloat();
		this.mMoney = pDataInputStream.readInt();
		this.mDroppedItem = pDataInputStream.readInt();
		this.mDroppedAmount = pDataInputStream.readInt();
		this.mPlayerKey = pDataInputStream.readUTF();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mMobEntityUserData);
		pDataOutputStream.writeFloat(this.mExperience);
		pDataOutputStream.writeInt(this.mMoney);
		pDataOutputStream.writeInt(this.mDroppedItem);
		pDataOutputStream.writeInt(this.mDroppedAmount);
		pDataOutputStream.writeUTF(this.mPlayerKey);
	}

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_MOB_DIED;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
