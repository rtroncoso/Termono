package com.quest.pools;

import java.util.List;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.debug.Debug;

import com.quest.constants.GameFlags;
import com.quest.database.queries.IQuery;
import com.quest.entities.Mob;
import com.quest.entities.objects.Attack;

public class QueryPool<M extends IQuery> implements GameFlags {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private final QMultiPool<M> mQueryMultiPool = new QMultiPool<M>();
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public void registerQuery(final int flagQueryRegisterPlayerPosition, final Class<? extends M> pQueryClass) {
		this.mQueryMultiPool.registerPool(flagQueryRegisterPlayerPosition,
			new GenericPool<M>() {
				@Override
				protected M onAllocatePoolItem() {
					try {
						return pQueryClass.newInstance();
					} catch (final Throwable t) {
						Debug.e(t);
						return null;
					}
				}
			}
		);
	}

	public M obtainQuery(final int pFlag) {
		return this.mQueryMultiPool.obtainPoolItem(pFlag);
	}

	public void recycleQuery(final M pQuery) {
		this.mQueryMultiPool.recyclePoolItem(pQuery.getFlag(), pQuery);
	}

	public void recycleQuery(final List<? extends M> pQueries) {
		final QMultiPool<M> queryMultiPool = this.mQueryMultiPool;
		for(int i = pQueries.size() - 1; i >= 0; i--) {
			final M query = pQueries.get(i);
			queryMultiPool.recyclePoolItem(query.getFlag(), query);
		}
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public GenericPool<M> getPool(int pPoolID){
		return this.mQueryMultiPool.getPool(pPoolID);
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
