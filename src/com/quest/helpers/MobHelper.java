package com.quest.helpers;

import java.util.ArrayList;
import java.util.List;

import com.quest.constants.MobFlags;
import com.quest.entities.Mob;
import com.quest.network.QServer;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.pools.MobPool;

public class MobHelper implements MobFlags{		
		private ArrayList<Mob> mMobs;
		private final MobPool mMobPool = new MobPool();
		private Mob nullMob;
		
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
			return mob;
		}
		
		public void deleteMob(int pMobID){
			this.mMobPool.recycleMob(this.mMobs.get(pMobID));
			this.mMobs.set(pMobID,this.nullMob);
		}
		
		public void deleteMobs(int pMobID){
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