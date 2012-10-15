package com.quest.helpers;

import java.util.ArrayList;

import com.quest.constants.MobFlags;
import com.quest.entities.Mob;
import com.quest.pools.MobPool;

public class MobHelper implements MobFlags{		
		private ArrayList<Mob> mMobs;
		private final MobPool mMobPool = new MobPool();
		
		public MobHelper() {
			this.mMobs = new ArrayList<Mob>();
			this.initMobPool();
		}
		
		private void initMobPool() {
			this.mMobPool.registerMob(FLAG_MOB_BAT,new Mob(FLAG_MOB_BAT));		
		}
		
		/*
		 * 
		 * Hacer metodos y eso
		 * 
		 * 
		 */

}