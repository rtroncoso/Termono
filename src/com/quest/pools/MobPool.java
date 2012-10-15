package com.quest.pools;

import java.util.List;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;
import org.andengine.util.debug.Debug;

import com.quest.entities.Mob;


public class MobPool{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final MultiPool<Mob> mMobMultiPool = new MultiPool<Mob>();

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
							//return pMob;
							return new Mob(MOB_FLAG);
						} catch (final Throwable t) {
							Debug.e(t);
							return null;
						}
					}
					
					@Override
					protected void onHandleRecycleItem(final Mob pMob) {
						pMob.Heal();
						pMob.setVisible(false);
						pMob.setUserData(null);
						pMob.setIgnoreUpdate(true);
						pMob.detachSelf();
						//TODO fijarse como eliminarlo bien
					}
					
					@Override
					protected void onHandleObtainItem(Mob pMob) {
						pMob.setVisible(true);
						pMob.setIgnoreUpdate(false);
					};
				}
		);
	}

	
	public Mob obtainMob(final int pMobID) {
		return this.mMobMultiPool.obtainPoolItem(pMobID);
	}

/*
	public M obtainMessage(final short pFlag, final DataInputStream pDataInputStream) throws IOException {
		final M message = this.mMessageMultiPool.obtainPoolItem(pFlag);
		if(message != null) { 
			message.read(pDataInputStream);
			return message;
		} else {
			throw new IllegalArgumentException("No message found for pFlag='" + pFlag + "'.");
		}
	}
*/
	
	public void recycleMob(final Mob pMob) {
		this.mMobMultiPool.recyclePoolItem(pMob.getMobFlag(), pMob);
	}

	public void recycleMobs(final List<Mob> pMobs) {
		final MultiPool<Mob> mobMultiPool = this.mMobMultiPool;
		for(int i = pMobs.size() - 1; i >= 0; i--) {
			final Mob mob = pMobs.get(i);
			mobMultiPool.recyclePoolItem(mob.getMobFlag(), mob);
		}
	}

	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
