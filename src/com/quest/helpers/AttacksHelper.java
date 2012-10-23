package com.quest.helpers;

import java.util.ArrayList;

import org.andengine.entity.text.Text;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.Mob;
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
		this.mAttackPool.registerAttack(FLAG_ATTACK_MOB_DEATH);
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
	
	public void recycleAttack(Attack pAttack){
		this.mAttacksList.remove(pAttack);
		this.mAttackPool.recycleAttack(pAttack);		
	}
	
	public void recycleAttackList(ArrayList<Attack> pAttacksList){
		this.mAttacksList.removeAll(pAttacksList);
		this.mAttackPool.recycleAttacks(pAttacksList);
	}
	
}
