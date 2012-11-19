package com.quest.pools;

import java.util.List;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.debug.Debug;

import com.quest.entities.objects.Item;

public class ItemPool {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final QMultiPool<Item> mItemMultiPool = new QMultiPool<Item>();

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void registerItem(final int ITEM_FLAG) {
		this.mItemMultiPool.registerPool(ITEM_FLAG,
				new GenericPool<Item>() {
					@Override
					protected Item onAllocatePoolItem() {
						try {
							return new Item(ITEM_FLAG);
						} catch (final Throwable t) {
							Debug.e(t);
							return null;
						}
					}
					
					@Override
					protected void onHandleRecycleItem(final Item pItem) {
						//TODO fijarse como eliminarlo bien
						pItem.setCullingEnabled(true);
						pItem.detachSelf();
						pItem.setVisible(false);
						pItem.setEntity(null);
						pItem.setAmount(0);
						pItem.getItemIcon().setScale(2.0f);
						//unequip?
					}
					
					@Override
					protected void onHandleObtainItem(Item pItem) {
						pItem.setVisible(true);
						pItem.setAmount(1);
					};
				}
		);
	}

	
	public Item obtainItem(final int ITEM_FLAG) {
		return this.mItemMultiPool.obtainPoolItem(ITEM_FLAG);
	}
	
	public void recycleItem(final Item pItem) {
		this.mItemMultiPool.recyclePoolItem(pItem.getItemFlag(), pItem);
	}

	public void recycleItems(final List<Item> pItems) {
		final QMultiPool<Item> ItemMultiPool = this.mItemMultiPool;
		for(int i = pItems.size() - 1; i >= 0; i--) {
			final Item item = pItems.get(i);
			ItemMultiPool.recyclePoolItem(item.getItemFlag(), item);
		}
	}

	public GenericPool<Item> getPool(int pPoolID){
		return this.mItemMultiPool.getPool(pPoolID);
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
