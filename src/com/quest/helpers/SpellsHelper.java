package com.quest.helpers;

import java.util.ArrayList;

import com.quest.entities.objects.Spell;

public class SpellsHelper {

	private ArrayList<Spell> mSpellsList;
	
	
	public SpellsHelper() {
		
		this.mSpellsList = new ArrayList<Spell>();
	}


	/**
	 * @return the mSpellsList
	 */
	public ArrayList<Spell> getSpellsList() {
		return mSpellsList;
	}


	/**
	 * @param mSpellsList the mSpellsList to set
	 */
	public void setSpellsList(ArrayList<Spell> mSpellsList) {
		this.mSpellsList = mSpellsList;
	}
	
	
}
