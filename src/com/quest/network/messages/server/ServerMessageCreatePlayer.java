package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

public class ServerMessageCreatePlayer  extends ServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ServerMessageCreatePlayer() {
		//Mensaje para indicar que tiene que crear un player nue
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_CREATE_PLAYER;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)throws IOException {
		//dududuuu
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)throws IOException {
		//nein
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}