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
		
		public MobHelper() {
			this.mMobs = new ArrayList<Mob>();
			this.initMobPool();
		}
		
		private void initMobPool() {
			this.mMobPool.registerMob(FLAG_MOB_BAT);		
		}
		
		public Mob addNewMob(int MOB_FLAG){
			final Mob mob = (Mob) (MobHelper.this.mMobPool.obtainMob(MOB_FLAG));
			this.mMobs.add(mob);
			mob.setUserData(this.mMobs.size()-1);
			return mob;
		}
		
		public void deleteMob(int pMobID){
			this.mMobPool.recycleMob(this.mMobs.get(pMobID));
		}
		
		public void deleteMobs(int pMobID){
			this.mMobPool.recycleMobs(this.mMobs);
		}

}