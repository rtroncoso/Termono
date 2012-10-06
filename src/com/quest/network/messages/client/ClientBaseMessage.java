package com.quest.network.messages.client;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

public abstract class ClientBaseMessage extends ClientMessage implements ClientMessageFlags  {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public String cleanMessage(String pDirtyMessage){
		pDirtyMessage.trim();
		String clean = ""; 
		for(int i=0;i<pDirtyMessage.length();i+=2){
			clean=clean+pDirtyMessage.substring(i,i+1);
		}
		//String temp = clean.split(";");
		return clean;//temp;
	}
	
	public String replaceWriteCharacters(String pString){
		pString.replace(";", "ª");
		return pString;
	}
	
	public String replaceReadCharacters(String pString){
		pString.replace("ª", ";");
		return pString;
	}
	
	public void SplitMessage(String pMessage){
		
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
