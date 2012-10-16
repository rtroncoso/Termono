package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

import com.quest.constants.ClientMessageFlags;

public class ClientMessageAttackMessage extends ClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mAttackedMobID;
	private int mAttackID;
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessageAttackMessage() {
	}

	public ClientMessageAttackMessage(final int pAttackedMobID,final int pAttackID){
		this.mAttackedMobID = pAttackedMobID;
		this.mAttackID = pAttackID;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getAttackedMobID() {
		return mAttackedMobID;
	}

	public void setAttackedMobID(int mAttackedMobID) {
		this.mAttackedMobID = mAttackedMobID;
	}

	public int getAttackID() {
		return mAttackID;
	}

	public void setAttackID(int mAttackID) {
		this.mAttackID = mAttackID;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	public short getFlag() {
		return FLAG_MESSAGE_CLIENT_ATTACK_MESSAGE;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		 this.mAttackedMobID = pDataInputStream.readInt();
		 this.mAttackID = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mAttackedMobID);
		pDataOutputStream.writeInt(this.mAttackID);	
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}