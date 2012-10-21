package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

import com.quest.constants.ClientMessageFlags;

public class ClientMessagePlayerCreate extends ClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private String mUserID;
	private int mClass;
	private int mEndurance,mIntelligence,mPower,mDefense,mUnassigned;
	private int mHeadID;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessagePlayerCreate() {
	}

	public ClientMessagePlayerCreate(final int[] pChoices,String pUserID){
		this.mUserID = pUserID;
		this.mClass = pChoices[0];
		this.mHeadID = pChoices[1];
		this.mPower = pChoices[2];
		this.mIntelligence = pChoices[3];
		this.mDefense = pChoices[4];
		this.mEndurance = pChoices[5];		
		this.mUnassigned = pChoices[6];
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public void setChoices(int[] pChoices){
		this.mClass = pChoices[0];
		this.mHeadID = pChoices[1];
		this.mPower = pChoices[2];
		this.mIntelligence = pChoices[3];
		this.mDefense = pChoices[4];
		this.mEndurance = pChoices[5];		
		this.mUnassigned = pChoices[6];
	}
	
	public int getPlayerClass(){
		return this.mClass;
	}
	
	public int[] getAttributes(){
		return new int[]{this.mPower,this.mIntelligence,this.mDefense,this.mEndurance};
	}
	
	public int getPlayerHeadID(){
		return this.mHeadID;
	}

	public int getUnassigned() {
		return mUnassigned;
	}

	public void setUnassigned(int mUnassigned) {
		this.mUnassigned = mUnassigned;
	}

	public String getUserID() {
		return mUserID;
	}

	public void setUserID(String mUserID) {
		this.mUserID = mUserID;
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
		 this.mUserID = pDataInputStream.readUTF();
		 this.mClass = pDataInputStream.readInt();
		 this.mPower = pDataInputStream.readInt();
		 this.mIntelligence = pDataInputStream.readInt();
		 this.mDefense = pDataInputStream.readInt();
		 this.mEndurance = pDataInputStream.readInt();
		 this.mUnassigned = pDataInputStream.readInt();
		 this.mHeadID = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeUTF(this.mUserID);
		pDataOutputStream.writeInt(this.mClass);
		pDataOutputStream.writeInt(this.mPower);
		pDataOutputStream.writeInt(this.mIntelligence);
		pDataOutputStream.writeInt(this.mDefense);
		pDataOutputStream.writeInt(this.mEndurance);
		pDataOutputStream.writeInt(this.mUnassigned);
		pDataOutputStream.writeInt(this.mHeadID);		
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}