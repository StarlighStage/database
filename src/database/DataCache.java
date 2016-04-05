package database;

import game.Cards;
import game.Idol;
import game.Skill;

import java.util.HashMap;

import mesage.Errno;

public class DataCache extends Errno{

	HashMap<Integer,Cards>cards;
	HashMap<Integer,Idol>charas;
	HashMap<Integer,Skill>skills;
	Database database;
	
	public DataCache(){
		cards = new HashMap<Integer,Cards>();
		charas = new HashMap<Integer,Idol>();
		skills = new HashMap<Integer,Skill>();
		database = new Database();
	}
	public DataCache(Database database){
		this();
		this.database = database;
		//TODO
	}
	public boolean hasCard(Cards card){
		return this.hasCard(card.getId());
	}
	public boolean hasCard(int id){
		return cards.containsKey(id);
	}
	
	public boolean hasIdol(Idol idol){
		return this.hasIdol(idol.getId());
	}
	public boolean hasIdol(int id){
		return charas.containsKey(id);
	}
	public boolean hasSkill(Skill skill){
		return this.hasSkill(skill.getId());
	}
	public boolean hasSkill(int id){
		return skills.containsKey(id);
	}
	
	public boolean setCard(Cards card){
		if(!card.complete()){
			out(1,"errno: 2- card for ["+card.getId()+"] isn't complete");
			this.errorMessage="card_not_complet";
			this.errno=2;
			return false;
		}
		if(hasCard(card)){
			this.errorMessage="card_exist";
			this.errno=1;
			return false;
		}
		this.cards.put(card.getId(), card);
		this.Ereset();
		return true;
	}
	public boolean setChara(Idol idol){
		if(!idol.complete()){
			out(1,"errno: 2- idol for ["+idol.getId()+"] isn't complete");
			this.errorMessage="idol_not_complet";
			this.errno=2;
			return false;
		}
		if(hasIdol(idol)){
			this.errorMessage="idol_exist";
			this.errno=1;
			return false;
		}
		this.charas.put(idol.getId(), idol);
		this.Ereset();
		return true;
	}
	public boolean setSkill(Skill skill) {
		if(!skill.complete()){
			out(1,"errno: 2- idol for ["+skill.getId()+"] isn't complete");
			this.errorMessage="skill_not_complet";
			this.errno=2;
			return false;
		}
		if(hasSkill(skill)){
			this.errorMessage="skill_exist";
			this.errno=1;
			return false;
		}
		this.skills.put(skill.getId(), skill);
		this.Ereset();
		return true;
	}
	public Cards getCard(int i) {
		return cards.get(i);
	}
	public Idol getChara(int i) {
		return charas.get(i);
	}
	public Skill getSkill(int i) {
		return skills.get(i);
	}

}
