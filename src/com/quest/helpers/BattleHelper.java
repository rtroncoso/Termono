package com.quest.helpers;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.BaseEntity;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.entities.objects.Attack;
import com.quest.entities.objects.InventoryItem;
import com.quest.game.Game;

public class BattleHelper implements GameFlags{		
	
	
	public BattleHelper(){
	}
	
	
	public void startAttack(BaseEntity pAttackingEntity,int pAttackID, BaseEntity pAttackedEntity){
		Attack tmpAttack = Game.getAttacksHelper().getAttack(pAttackID);
		if(tmpAttack.getEffect()[1]!=3)pAttackingEntity.decreaseMP(tmpAttack.getManaCost());
		Game.getAttacksHelper().recycleAttack(tmpAttack);
		if(!Game.isServer()){
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
		damage = damage*pAttackID;
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
		
		if(pAttackedEntity.decreaseHP(damage)){
			if(Game.isServer()){
				if(!isMobAttacking){
					int[] drop = ((Mob)(pAttackedEntity)).getMobDrop();
					Game.getBattleHelper().killMob(((Mob)(pAttackedEntity)),drop[0],drop[1], ((Mob)(pAttackedEntity)).getExperience(), ((Mob)(pAttackedEntity)).getMoney(),(Player) (pAttackingEntity));
				}else{
					//murio el player
				}
			}
		}
		if(pAttackingEntity.getCurrentMap()==Game.getPlayerHelper().getOwnPlayer().getCurrentMap())
		displayAttack(pAttackingEntity, pAttackID, damage, pAttackedEntity, isMobAttacking);
	}
	
	
	public void displayAttack(BaseEntity pAttackingEntity,int pAttackID,int pDamage, BaseEntity pAttackedEntity, boolean ismobAttacking){//display grafico del attack, llamado por mensaje
		pAttackedEntity.onAttackedAction(pAttackingEntity, pDamage, pAttackID);
	}
	
	
	
	public void killMob(Mob mob,int pdroppeditem,int pdroppedItemAmount,int pexperience, int pmoney,Player player){
		if(Game.isServer()){
			player.addExperience(pexperience);
			player.addMoney(pmoney);
			if(pdroppeditem != 0){
				player.getInventory().addItem(new InventoryItem(pdroppeditem, pdroppedItemAmount, 0));
			}
			Game.getServer().sendMobDiedMessage((Integer)(mob.getUserData()), pexperience, pmoney, pdroppeditem, pdroppedItemAmount,player.getUserData().toString());
		}else{
			if(player.getUserID()==Game.getPlayerHelper().getOwnPlayer().getUserID()){
				//muestro graficamente que gane exp y que gano el item
				//dejar tirado el item?
				player.addExperience(pexperience);
				player.addMoney(pmoney);	
				if(pdroppeditem != 0){
					player.getInventory().addItem(new InventoryItem(pdroppeditem, pdroppedItemAmount, 0));
				}
			}		
		}
		mob.onDeathAction(player);
	}
	
	
}