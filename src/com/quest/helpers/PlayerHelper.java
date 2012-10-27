package com.quest.helpers;

import java.util.ArrayList;

import android.util.Log;

import com.quest.entities.BaseEntity;
import com.quest.entities.Player;
import com.quest.game.Game;

public class PlayerHelper {
	
	private ArrayList<Player> mPlayers;
	
	public PlayerHelper() {

		this.mPlayers = new ArrayList<Player>();
	}
	
	public void addPlayer(Player pPlayer) {
		pPlayer.setUserData(pPlayer.getUserID());
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
		Player tmpEntity = null;
		for(int i = 0;i<this.mPlayers.size();i++) {
			tmpEntity = this.mPlayers.get(i);
			if(tmpEntity.getUserData().equals(pKey))
				return tmpEntity;
		}
		Log.e("Quest!","PlayerHelper: Base Search - No Player matches key: "+pKey);
		return null;
	}
	
	public Player getPlayerbyIndex(int index) {
		return this.mPlayers.get(index);
	}
	
	public Player getOwnPlayer(){
		if(Game.isAVD_DEBUGGING()){
			return (Player)(getPlayer(Game.getDataHandler().getUserID(1)));
		}else{
			return (Player)(getPlayer(Game.getUserID()));
		}
	}

	public Player getPlayerbyPlayerID(int pPlayerID){
		for(Player tmpEntity : this.mPlayers) {
			if(tmpEntity.getPlayerID()==pPlayerID)
				return tmpEntity;
		}
		Log.e("Quest!","PlayerHelper: IDSearch - No Player matches ID");
		return null;
	}
	
	public boolean isAloneInMap(Player pPlayer){
		for(int i = mPlayers.size()-1;i>=0;i--){
			if(!mPlayers.get(i).equals(pPlayer) && mPlayers.get(i).getCurrentMap()==pPlayer.getCurrentMap()){
				return false;
			}
		}	
		return true;
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
