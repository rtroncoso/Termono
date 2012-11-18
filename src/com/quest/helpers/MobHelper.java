package com.quest.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.andengine.extension.tmx.TMXTile;

import com.quest.constants.GameFlags;
import com.quest.entities.Mob;
import com.quest.game.Game;
import com.quest.pools.MobPool;
import com.quest.util.constants.IGameConstants;

public class MobHelper implements GameFlags,IGameConstants{		
		private ArrayList<Mob> mMobs;
		private final MobPool mMobPool = new MobPool();
		private int MobCount = 0;
		
		public MobHelper() {
			this.mMobs = new ArrayList<Mob>();
			this.initMobPool();
		}
		
		private void initMobPool() {
			this.mMobPool.registerMob(FLAG_MOB_BAT);
			this.mMobPool.registerMob(FLAG_MOB_BEE);
			this.mMobPool.registerMob(FLAG_MOB_FAIRY);
			this.mMobPool.registerMob(FLAG_MOB_CHICKEN);
		}
		
		public Mob getMob(int pMobID){
			Mob tmpMob = null;
			for(int i = mMobs.size()-1;i>=0;i--)
			{
				if(mMobs.get(i).getUserData().equals(String.valueOf(pMobID)))
				{
					tmpMob = mMobs.get(i); 	
				}
			}
			return tmpMob;
		}
		
		public Mob addNewMob(int MOB_FLAG,int pMapID,int pTileX,int pTileY,int pMobID){
			final Mob mob = (Mob) (MobHelper.this.mMobPool.obtainMob(MOB_FLAG));
			this.mMobs.add(mob);
			mob.setUserData(pMobID);
			mob.setCurrentMap(pMapID);
			mob.setTileAt(pTileX, pTileY);
			if(Game.isServer())mob.startMoveTimer();
			this.MobCount+=1;//ID unico, total de mobs creados
			return mob;
		}
		
		public Mob addNewMob(int MOB_FLAG,int pMapID,int pTileX,int pTileY){
			return this.addNewMob(MOB_FLAG, pMapID, pTileX, pTileY, this.MobCount);
		}
		
		public void deleteMob(Mob pMob){
			this.mMobs.remove(pMob);
			this.mMobPool.recycleMob(pMob);			
		}
		
		public void deleteMobs(ArrayList<Mob> pMobs){
			this.mMobPool.recycleMobs(pMobs);
			this.mMobs.clear();
		}
		
		
		public void clearMobsAlpha(){
			for(int i = 0;i<this.mMobs.size();i++){
				mMobs.get(i).getBodySprite().setAlpha(1f);
			}
		}

		public ArrayList<Mob> getMobsInArea(int tileX, int tileY, int range){
			ArrayList<Mob> tmpMobs = new ArrayList<Mob>();
			for(int i = mMobs.size()-1;i>=0;i--){
					if(mMobs.get(i).getTMXTileAt().getTileColumn()>=(tileX-range)&&mMobs.get(i).getTMXTileAt().getTileColumn()<=(tileX+range)){
						if(mMobs.get(i).getTMXTileAt().getTileRow()>=(tileY-range)&&mMobs.get(i).getTMXTileAt().getTileRow()<=(tileY+range)){
							tmpMobs.add(mMobs.get(i));
						}
					}
			}
			return tmpMobs;
		}
		
		public Mob getMobInDirection(byte pDirection,int col,int row,int MapID){
			Mob rtnMob = null;
			
			boolean vertical = false;
			if(pDirection == DIRECTION_NORTH || pDirection == DIRECTION_SOUTH) vertical = true;	
			
			int tileOff = 0;
			switch (pDirection) {
			case DIRECTION_SOUTH:
				tileOff = 1;
				break;
			case DIRECTION_NORTH:
				tileOff = -1;
				break;	
			case DIRECTION_EAST:
				tileOff = 1;
				break;
			case DIRECTION_WEST:
				tileOff = -1;
				break;	
			}
			
			boolean found = false;
			int i = mMobs.size()-1;
			while(!found && i>=0){
				if(mMobs.get(i).getCurrentMap() == MapID){
					if(vertical){
						if(mMobs.get(i).getTMXTileAt().getTileRow()==(row+tileOff) && mMobs.get(i).getTMXTileAt().getTileColumn() == col){
							rtnMob = mMobs.get(i);
							found = true;
						}
					}else{
						if(mMobs.get(i).getTMXTileAt().getTileColumn()==(col+tileOff) && mMobs.get(i).getTMXTileAt().getTileRow() == row){
							rtnMob = mMobs.get(i);
							found = true;
						}
					}
				}
				i--;
			}
			
			return rtnMob;
		}
		
		public ArrayList<Mob> getMobsInMap(int pMapID)
		{
			ArrayList<Mob> tmpMobs = new ArrayList<Mob>();
			
			for(int i = mMobs.size()-1;i>=0;i--)
			{
					if(mMobs.get(i).getCurrentMap()==pMapID)
					{
						tmpMobs.add(mMobs.get(i));
					}
			}
			return tmpMobs;
		}
		
		public void allocateDefaultMobs(ArrayList<Object[]> pMobsToAllocate)
		{
			for(int i = pMobsToAllocate.size()-1;i>=0;i--)
			{
				if((Integer)pMobsToAllocate.get(i)[1]>this.mMobPool.getTotalItemsAmount((Integer)pMobsToAllocate.get(i)[0]))
				{//Si la cantidad a spawnear es mayor a la cantidad reciclada hago un allocate de la diferencia
					this.mMobPool.getPool((Integer)pMobsToAllocate.get(i)[0]).batchAllocatePoolItems((Integer)pMobsToAllocate.get(i)[1]-this.mMobPool.getTotalItemsAmount((Integer)pMobsToAllocate.get(i)[0]));
				}
			}
		}
		
		/**
		 * @return the mMobs
		 */
		public ArrayList<Mob> getMobs() {
			return mMobs;
		}

		public int getMobCount() {
			return this.MobCount;
		}

		public boolean MobExists(int mobKey)
		{
			boolean exists = false;
			
			for(int i = mMobs.size()-1;i>=0;i--)
			{
					if(mMobs.get(i).getUserData().equals(String.valueOf(mobKey)))
					{
						exists = true;
					}
			}
			
			return exists;
			
		}

		public boolean MobIsInMap(int mobKey,int pMapID)
		{
			if(MobExists(mobKey))
			{
				if(this.getMob(mobKey).getCurrentMap()==pMapID)return true;
			}
			return false;
		}

}