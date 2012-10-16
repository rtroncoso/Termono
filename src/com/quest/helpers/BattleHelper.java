package com.quest.helpers;

import org.andengine.entity.IEntity;

import android.util.Log;

import com.quest.constants.MobFlags;
import com.quest.entities.BaseEntity;
import com.quest.game.Game;

public class BattleHelper implements MobFlags{		
	
	
	public BattleHelper(){
	}
	
	
	public void startAttack(BaseEntity pAttackingEntity,int pAttackID, BaseEntity pAttackedEntity){
		if(Game.getServer().equals(null)){
			Game.getClient().sendAttackMessage((Integer)(pAttackedEntity.getUserData()), pAttackID);
		}else{
			manageAttack(pAttackingEntity, pAttackID, pAttackedEntity);
		}
	}
	
	public void manageAttack(BaseEntity pAttackingEntity,int pAttackID, BaseEntity pAttackedEntity){
		boolean isMobAttacking = false;
		int MobUserData;
		String PlayerUserData;
		int damage;
		damage = (pAttackingEntity.getModPower()*4)-(pAttackedEntity.getModDefense()*3);
		if(damage<1)damage=0;
		damage+=pAttackingEntity.getModPower();
		Multiplicar damage por el bonus del ataque y esou
		
		if(pAttackingEntity.getEntityType().equals("Mob")){
			isMobAttacking = true;
			PlayerUserData = pAttackedEntity.getUserData().toString();
			MobUserData = (Integer)(pAttackingEntity.getUserData());
		}else{
			if(!pAttackingEntity.getEntityType().equals("Player"))Log.e("Quest!", "Battle helper - manage - entity type not recognized: "+pAttackingEntity.getEntityType());//Funcion de debbuggeo
			PlayerUserData = pAttackingEntity.getUserData().toString();
			MobUserData = (Integer)(pAttackedEntity.getUserData());
		}
		Game.getServer().sendFixedAttackData(MobUserData, pAttackID, damage, PlayerUserData, isMobAttacking);
		
		displayAttack(pAttackingEntity, pAttackID, damage, pAttackedEntity);
	}
	
	
	public void displayAttack(BaseEntity pAttackingEntity,int pAttackID,int pDamage, BaseEntity pAttackedEntity){//display grafico del attack, llamado por mensaje
		pAttackedEntity.onAttackedAction(pAttackingEntity, pDamage, pAttackID);
		pAttackingEntity.onAttackAction(pAttackedEntity, pAttackID);
	}
	Hacer los mensajes para llamar a onDeath
}