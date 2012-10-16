package com.quest.helpers;

import android.util.Log;

import com.quest.constants.MobFlags;
import com.quest.entities.BaseEntity;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.entities.objects.InventoryItem;
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
	//	Multiplicar damage por el bonus del ataque y esou
		
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
	
	public void killMob(Mob mob,int pdroppeditem,int pdroppedItemAmount,int pexperience, int pmoney,Player player){
		if(!Game.getServer().equals(null)){
			player.addExperience(pexperience);
			player.addMoney(pmoney);
			player.getInventory().addItem(new InventoryItem(pdroppeditem, pdroppedItemAmount, 0));
			Game.getServer().sendMobDiedMessage((Integer)(mob.getUserData()), pexperience, pmoney, pdroppeditem, pdroppedItemAmount,player.getUserData().toString());
		}
		//*** playerbyindex, asegurarme de que sea el propio SIEMPRE
		if(player.getUserID()==Game.getPlayerHelper().getPlayerbyIndex(0).getUserID()){
			//muestro graficamente que gane exp y que gano el item
			//dejar tirado el item?
		}		
		Game.getMobHelper().deleteMob((Integer)(mob.getUserData()));
	}
	
	
}