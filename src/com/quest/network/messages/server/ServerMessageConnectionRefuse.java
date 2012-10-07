package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

public class ServerMessageConnectionRefuse extends ServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private String mMatchName;
	private boolean mReason;//True = bad password, False = Kicked/Banned 
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ServerMessageConnectionRefuse() {

	}
	
	public ServerMessageConnectionRefuse(final String pMatchName,final boolean pReason) {
		this.mMatchName = pMatchName;
		this.mReason = pReason;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getMatchName() {
		return this.mMatchName;
	}
	
	public void setMatchName(final String pMatchName) {
		this.mMatchName = pMatchName;
	}

	public boolean getReason() {
		return this.mReason;
	}
	
	public void setReason(final boolean pReason) {
		this.mReason = pReason;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_CONNECTION_REFUSE;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)throws IOException {
		this.mMatchName = pDataInputStream.readUTF();
		this.mReason = pDataInputStream.readBoolean();
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)throws IOException {
		pDataOutputStream.writeUTF(this.mMatchName);
		pDataOutputStream.writeBoolean(this.mReason);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}