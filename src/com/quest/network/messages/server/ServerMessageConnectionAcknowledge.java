package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import android.util.Log;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 12:23:20 - 21.05.2011
 */
public class ServerMessageConnectionAcknowledge extends ServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private String mMatchName;
	private int mMatchID;//La ID que tiene el server de la match
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ServerMessageConnectionAcknowledge() {

	}

	public ServerMessageConnectionAcknowledge(final String pMatchName, final int pMatchID) {
		this.setMatchName(pMatchName);
		this.setMatchID(pMatchID);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getMatchName() {
		return mMatchName;
	}

	public void setMatchName(String mMatchName) {
		this.mMatchName = mMatchName;
	}

	public int getMatchID() {
		return mMatchID;
	}

	public void setMatchID(int mMatchID) {
		this.mMatchID = mMatchID;
	}	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		this.mMatchID = pDataInputStream.readInt();
		this.mMatchName = pDataInputStream.readUTF();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mMatchID);
		pDataOutputStream.writeUTF(this.mMatchName);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
