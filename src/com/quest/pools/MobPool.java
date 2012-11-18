package com.quest.pools;

import java.util.List;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.quest.entities.Mob;
import com.quest.game.Game;


public class MobPool{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final QMultiPool<Mob> mMobMultiPool = new QMultiPool<Mob>();

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void registerMob(final int MOB_FLAG) {
		this.mMobMultiPool.registerPool(MOB_FLAG,
				new GenericPool<Mob>() {
					@Override
					protected Mob onAllocatePoolItem() {
						try {
							return new Mob(MOB_FLAG);
						} catch (final Throwable t) {
							Debug.e(t);
							return null;
						}
					}
					
					@Override
					protected void onHandleRecycleItem(final Mob pMob) {
						pMob.setPosition(-10, -10);
						Game.getTimerHelper().deleteTimer(pMob.getUserData()+";Recovery");
						pMob.setVisible(false);
						pMob.setAlpha(1f);
						pMob.setPursuit(false);
						pMob.setFollowing(false);
						pMob.setCooling(false);
						pMob.setDying(false);
						pMob.setUserData(null);
						pMob.setIgnoreUpdate(true);
						pMob.detachSelf();
					}
					
					@Override
					protected void onHandleObtainItem(Mob pMob) {
						pMob.setVisible(true);
						pMob.setIgnoreUpdate(false);
						pMob.Heal();
						pMob.startRecoveryTimer();
					};
				}
		);
	}

	
	public Mob obtainMob(final int MOB_FLAG) {
		return this.mMobMultiPool.obtainPoolItem(MOB_FLAG);
	}

	public void recycleMob(final Mob pMob) {
		this.mMobMultiPool.recyclePoolItem(pMob.getMobFlag(), pMob);
	}

	public void recycleMobs(final List<Mob> pMobs) {
		final QMultiPool<Mob> mobMultiPool = this.mMobMultiPool;
		for(int i = pMobs.size() - 1; i >= 0; i--) {
			final Mob mob = pMobs.get(i);
			mobMultiPool.recyclePoolItem(mob.getMobFlag(), mob);
		}
	}
	
	public int getTotalItemsAmount(int pPoolID){
		return (this.mMobMultiPool.getAvailableItemsAmount(pPoolID) + this.mMobMultiPool.getUnrecycledItemsAmount(pPoolID));
	}

	public GenericPool<Mob> getPool(int pPoolID){
		return this.mMobMultiPool.getPool(pPoolID);
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
