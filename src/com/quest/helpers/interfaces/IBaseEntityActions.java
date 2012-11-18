package com.quest.helpers.interfaces;

import com.quest.entities.BaseEntity;

public interface IBaseEntityActions {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void onDeathAction(BaseEntity pKillerEntity);
	
	public void onAttackedAction(BaseEntity pAttackingEntity,int pDamage,int pAttackID);
	
	public void onAttackAction(BaseEntity pAttackedEntity, int ATTACK_FLAG);
	
	public void onDisplayAttackingAction();
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
