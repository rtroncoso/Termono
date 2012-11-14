package com.quest.helpers;

import java.util.ArrayList;

import com.quest.constants.GameFlags;
import com.quest.entities.BaseEntity;
import com.quest.entities.objects.Attack;
import com.quest.entities.objects.Item;
import com.quest.pools.AttackPool;
import com.quest.pools.ItemPool;

public class ItemHelper implements GameFlags{


	private ArrayList<Item> mItems;
	private final ItemPool mItemPool = new ItemPool();
	
	public ItemHelper() {
		this.mItems = new ArrayList<Item>();
		this.initItemPool();
	}
	
	private void initItemPool() {
		this.mItemPool.registerItem(FLAG_ITEM_BIG_FLAMED_SWORD);
		this.mItemPool.registerItem(FLAG_ITEM_HEALTH_POTION);
	}
	
	
	public Item addNewItem(int ITEM_FLAG){
		final Item item = ItemHelper.this.mItemPool.obtainItem(ITEM_FLAG);
		this.mItems.add(item);
		return item;
	}
	
	public Item addNewItem(int ITEM_FLAG,int amount){
		Item item = this.addNewItem(ITEM_FLAG);
		item.setAmount(amount);
		return item;
	}
	
	public Item getItem(int ITEM_FLAG){
		return ItemHelper.this.mItemPool.obtainItem(ITEM_FLAG);
	}
	
	public void recycleItem(Item pItem){
		if(this.mItems.contains(pItem))this.mItems.remove(pItem);
		this.mItemPool.recycleItem(pItem);		
	}
	
	public void recycleAttackList(ArrayList<Item> pItemList){
		this.mItems.removeAll(pItemList);
		this.mItemPool.recycleItems(pItemList);
	}
	
	public void allocateItem(int ITEM_FLAG,int pAmountToAllocate){
		this.mItemPool.getPool(ITEM_FLAG).batchAllocatePoolItems(pAmountToAllocate);
	}
	

}