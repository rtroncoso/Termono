package com.quest.pools;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.adt.pool.MultiPool;

import android.util.SparseArray;

public class QMultiPool<T>{
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		private final SparseArray<GenericPool<T>> mPools = new SparseArray<GenericPool<T>>();

		// ===========================================================
		// Constructors
		// ===========================================================

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public void registerPool(final int pID, final GenericPool<T> pPool) {
			this.mPools.put(pID, pPool);
		}

		public T obtainPoolItem(final int pID) {
			final GenericPool<T> pool = this.mPools.get(pID);
			if(pool == null) {
				return null;
			} else {
				return pool.obtainPoolItem();
			}
		}

		public void recyclePoolItem(final int pID, final T pItem) {
			final GenericPool<T> pool = this.mPools.get(pID);
			if(pool != null) {
				pool.recyclePoolItem(pItem);
			}
		}

		public GenericPool<T> getPool(int pPoolID){
			return mPools.get(pPoolID);
		}
		
		public int getUnrecycledItemsAmount(int pPoolID){
			return getPool(pPoolID).getAvailableItemCountMaximum();
		}
		
		public int getAvailableItemsAmount(int pPoolID){
			return getPool(pPoolID).getAvailableItemCount();
		}
		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
