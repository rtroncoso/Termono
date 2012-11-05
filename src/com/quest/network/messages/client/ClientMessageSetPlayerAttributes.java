package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.constants.ClientMessageFlags;

public class ClientMessageSetPlayerAttributes extends QuestClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mPlayerID;
	private String mAttributes;
	private int mUnassigned;
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessageSetPlayerAttributes() {
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getPlayerID() {
		return mPlayerID;
	}

	public void setPlayerID(int mPlayerID) {
		this.mPlayerID = mPlayerID;
	}

	public int[] getAttributes() {
		return stringArraytoInt(mAttributes);
	}

	public void setAttributes(int[] mAttributes) {
		this.mAttributes = intArraytoString(mAttributes);
	}
	
	public int getUnassigned() {
		return mUnassigned;
	}

	public void setUnassigned(int mUnassigned) {
		this.mUnassigned = mUnassigned;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	public short getFlag() {
		return FLAG_MESSAGE_CLIENT_SET_PLAYER_ATTRIBUTES;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		 this.mPlayerID = pDataInputStream.readInt();
		 this.mAttributes = pDataInputStream.readUTF();
		 this.mUnassigned = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mPlayerID);	
		pDataOutputStream.writeUTF(this.mAttributes);
		pDataOutputStream.writeInt(this.mUnassigned);	
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}