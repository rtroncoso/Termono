package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

public class ClientMessagePlayerCreate extends ClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mClass;
	private int mEndurance,mIntelligence,mPower,mDefense;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessagePlayerCreate() {
	}

	public ClientMessagePlayerCreate(final int[] pChoices){
		this.mClass = pChoices[0];
		this.mPower = pChoices[1];
		this.mIntelligence = pChoices[2];
		this.mDefense = pChoices[3];
		this.mEndurance = pChoices[4];
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public void setChoices(int[] pChoices){
		this.mClass = pChoices[0];
		this.mPower = pChoices[1];
		this.mIntelligence = pChoices[2];
		this.mDefense = pChoices[3];
		this.mEndurance = pChoices[4];
	}
	
	public int getPlayerClass(){
		return this.mClass;
	}
	
	public int[] getAttributes(){
		return new int[]{this.mPower,this.mIntelligence,this.mDefense,this.mEndurance};
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	public short getFlag() {
		return FLAG_MESSAGE_CLIENT_PLAYER_CREATE;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		 this.mClass = pDataInputStream.readInt();
		 this.mPower = pDataInputStream.readInt();
		 this.mIntelligence = pDataInputStream.readInt();
		 this.mDefense = pDataInputStream.readInt();
		 this.mEndurance = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mClass);
		pDataOutputStream.writeInt(this.mPower);
		pDataOutputStream.writeInt(this.mIntelligence);
		pDataOutputStream.writeInt(this.mDefense);
		pDataOutputStream.writeInt(this.mEndurance);		
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}