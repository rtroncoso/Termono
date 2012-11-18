package com.quest.helpers;

import java.util.ArrayList;

import com.quest.constants.GameFlags;
import com.quest.entities.BaseEntity;
import com.quest.entities.objects.Attack;
import com.quest.pools.AttackPool;

public class AttacksHelper implements GameFlags{

	private ArrayList<Attack> mAttacksList;
	private final AttackPool mAttackPool = new AttackPool();
	
	public AttacksHelper() {
		
		this.mAttacksList = new ArrayList<Attack>();
		this.initAttackPool();
	}
	
	private void initAttackPool() {
		this.mAttackPool.registerAttack(FLAG_ATTACK_SPELL_FIREBALL);
		this.mAttackPool.registerAttack(FLAG_ATTACK_SPELL_BLAST);
		this.mAttackPool.registerAttack(FLAG_ATTACK_SPELL_THUNDER);
		this.mAttackPool.registerAttack(FLAG_ATTACK_SPELL_ICE_RING);
		this.mAttackPool.registerAttack(FLAG_ATTACK_SPELL_ICE_BASH);
		this.mAttackPool.registerAttack(FLAG_ATTACK_NORMAL);
		this.mAttackPool.registerAttack(FLAG_ATTACK_MOB_DEATH);
		this.mAttackPool.registerAttack(FLAG_ATTACK_PLAYER_DEATH);
		this.mAttackPool.registerAttack(FLAG_ATTACK_PLAYER_LEVEL_UP);
	}
	
	
	
	/**
	 * @return the mAttacksList
	 */
	public ArrayList<Attack> getAttacksList() {
		return mAttacksList;
	}

	/**
	 * @param mAttacksList the mAttacksList to set
	 */
	public void setAttacksList(ArrayList<Attack> mAttacksList) {
		this.mAttacksList = mAttacksList;
	}
	
	
	public Attack addNewAttack(int ATTACK_FLAG){
		final Attack attack = (Attack) (AttacksHelper.this.mAttackPool.obtainAttack(ATTACK_FLAG));
		this.mAttacksList.add(attack);
		return attack;
	}
	
	public Attack getAttack(int ATTACK_FLAG){
		return (Attack) (AttacksHelper.this.mAttackPool.obtainAttack(ATTACK_FLAG));
	}
	
	public void recycleAttack(Attack pAttack){
		if(this.mAttacksList.contains(pAttack))this.mAttacksList.remove(pAttack);
		this.mAttackPool.recycleAttack(pAttack);		
	}
	
	public void recycleAttackList(ArrayList<Attack> pAttacksList){
		this.mAttacksList.removeAll(pAttacksList);
		this.mAttackPool.recycleAttacks(pAttacksList);
	}
	
	public void allocateAttack(int ATTACK_FLAG,int pAmountToAllocate){
		this.mAttackPool.getPool(ATTACK_FLAG).batchAllocatePoolItems(pAmountToAllocate);
	}
	
	public boolean canAttack(BaseEntity pEntity,int ATTACK_FLAG){
		Attack tmpAttack = (Attack) (AttacksHelper.this.mAttackPool.obtainAttack(ATTACK_FLAG));
		if(pEntity.getCurrMana()>=tmpAttack.getManaCost()){
			recycleAttack(tmpAttack);
			return true;
		}else{
			recycleAttack(tmpAttack);
			return false;
		}
	}
	
	public int getAttackManaCost(int ATTACK_FLAG){
		Attack tmpAttack = (Attack) (AttacksHelper.this.mAttackPool.obtainAttack(ATTACK_FLAG));
		int answer = tmpAttack.getManaCost();
		recycleAttack(tmpAttack);
		return answer;
	}
	
}
