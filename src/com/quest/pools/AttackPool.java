package com.quest.pools;

import java.util.List;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;
import org.andengine.util.debug.Debug;

import com.quest.entities.Mob;
import com.quest.entities.objects.Attack;

public class AttackPool {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final MultiPool<Attack> mAttackMultiPool = new MultiPool<Attack>();

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void registerAttack(final int ATTACK_FLAG) {
		this.mAttackMultiPool.registerPool(ATTACK_FLAG,
				new GenericPool<Attack>() {
					@Override
					protected Attack onAllocatePoolItem() {
						try {
							return new Attack(ATTACK_FLAG);
						} catch (final Throwable t) {
							Debug.e(t);
							return null;
						}
					}
					
					@Override
					protected void onHandleRecycleItem(final Attack pAttack) {
						//TODO fijarse como eliminarlo bien
						pAttack.setCullingEnabled(true);
						pAttack.detachSelf();
						pAttack.setAnimationStatus(0);
						pAttack.setVisible(false);
						pAttack.getAttackAnimation().stopAnimation();
						pAttack.getAttackAnimation().reset();
					}
					
					@Override
					protected void onHandleObtainItem(Attack pAttack) {
						pAttack.setVisible(true);
					};
				}
		);
	}

	
	public Attack obtainAttack(final int ATTACK_FLAG) {
		return this.mAttackMultiPool.obtainPoolItem(ATTACK_FLAG);
	}


	
	public void recycleAttack(final Attack pAttack) {
		this.mAttackMultiPool.recyclePoolItem(pAttack.getAttackFlag(), pAttack);
	}

	public void recycleAttacks(final List<Attack> pAttacks) {
		final MultiPool<Attack> attackMultiPool = this.mAttackMultiPool;
		for(int i = pAttacks.size() - 1; i >= 0; i--) {
			final Attack attack = pAttacks.get(i);
			attackMultiPool.recyclePoolItem(attack.getAttackFlag(), attack);
		}
	}

	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}