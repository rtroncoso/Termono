package com.quest.helpers;

import java.util.ArrayList;

import android.util.Log;

import com.quest.entities.BaseEntity;
import com.quest.entities.Player;

public class PlayerHelper {
	
	private ArrayList<Player> mPlayers;
	
	public PlayerHelper() {

		this.mPlayers = new ArrayList<Player>();
	}
	
	public void addPlayer(Player pPlayer, String pKey) {
		pPlayer.setUserData(pKey);
		this.mPlayers.add(pPlayer);
	}
	
	public void deletePlayer(String pKey) {
		if(!this.getPlayer(pKey).equals(null)) {
			this.mPlayers.remove(this.getPlayer(pKey));
		} else {
			Log.d("Quest!","PlayerHelper: Erase - No Player matches key");
		}
	}
	
	public BaseEntity getPlayer(String pKey) {
		
		for(Player tmpEntity : this.mPlayers) {
			if(tmpEntity.getUserData().equals(pKey)) {
				return tmpEntity;
			} else {
				Log.e("Quest!","PlayerHelper: Search - No Player matches key");
				return null;
			}
		}
		return null;
	}

	/**
	 * @return the mEntities
	 */
	public ArrayList<Player> getEntities() {
		return mPlayers;
	}

	/**
	 * @param mEntities the mEntities to set
	 */
	public void setEntities(ArrayList<Player> pPlayers) {
		this.mPlayers = pPlayers;
	}
}
