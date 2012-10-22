package com.quest.helpers;

import java.util.ArrayList;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.Mob;
import com.quest.pools.MobPool;

public class MobHelper implements GameFlags{		
		private ArrayList<Mob> mMobs;
		private final MobPool mMobPool = new MobPool();
		private Mob nullMob;
		private int MobCount = 0;
		
		public MobHelper() {
			this.mMobs = new ArrayList<Mob>();
			this.initMobPool();
			this.nullMob = new Mob(1);
		}
		
		private void initMobPool() {
			this.mMobPool.registerMob(FLAG_MOB_BAT);
			this.mMobPool.registerMob(FLAG_MOB_BEE);
		}
		
		public Mob getMob(int pMobID){
			return this.mMobs.get(pMobID);
		}
		
		public Mob addNewMob(int MOB_FLAG){
			final Mob mob = (Mob) (MobHelper.this.mMobPool.obtainMob(MOB_FLAG));
			if(mMobs.contains(this.nullMob)){
				this.mMobs.set(mMobs.indexOf(nullMob),mob);
			}else{
				this.mMobs.add(mob);
			}
			mob.setUserData(this.mMobs.indexOf(mob));
			mob.startMoveTimer();
			return mob;
		}
		
		public void deleteMob(int pMobID){
			this.mMobPool.recycleMob(this.mMobs.get(pMobID));
			this.mMobs.set(pMobID,this.nullMob);
		}
		
		public void deleteMobs(int pMobID){
			//Hacer que sea especifico por mapa, filtrando de la lista
			this.mMobPool.recycleMobs(this.mMobs);
			this.mMobs.clear();
		}
		
		
		public void clearMobsAlpha(){
			for(int i = 0;i<this.mMobs.size();i++){
				mMobs.get(i).getBodySprite().setAlpha(1f);
			}
		}

		/**
		 * @return the mMobs
		 */
		public ArrayList<Mob> getMobs() {
			return mMobs;
		}


}