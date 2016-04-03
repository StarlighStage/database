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
		database = new Database();
	}
	public DataCache(Database database){
		this();
		this.database = database;
		//TODO
	}
	public boolean hasCard(Cards card){
		int id = -1;
		try{
			id = Integer.parseInt(card.getMap().get("id"));
		}catch(Exception e){
			return false;
		}
		return this.hasCard(id);
	}
	public boolean hasCard(int id){
		return cards.containsKey(id);
	}
	
	public boolean hasIdol(Idol idol){
		int id = -1;
		try{
			id = Integer.parseInt(idol.getMap().get("chara_id"));
		}catch(Exception e){
			return false;
		}
		return this.hasIdol(id);
	}
	public boolean hasIdol(int id){
		return charas.containsKey(id);
	}
	
	public boolean setCard(Cards card){
		if(!card.complete()){
			this.errorMessage="card_not_complet";
			this.errno=2;
			return false;
		}
		if(hasCard(card)){
			this.errorMessage="card_exist";
			this.errno=1;
			return false;
		}
		this.cards.put((int)Integer.parseInt(card.getMap().get("id")), card);
		this.Ereset();
		return true;
	}
	public boolean setChara(Idol idol){
		if(!idol.complete()){
			this.errorMessage="idol_not_complet";
			this.errno=2;
			return false;
		}
		if(hasIdol(idol)){
			this.errorMessage="idol_exist";
			this.errno=1;
			return false;
		}
		this.charas.put((int)Integer.parseInt(idol.getMap().get("chara_id")), idol);
		this.Ereset();
		return true;
	}
	public Cards getCard(int i) {
		return cards.get(i);
	}
	public Idol getChara(int i) {
		return charas.get(i);
	}
	public boolean setSkill(Skill skill) {
		if(!skill.complete()){
			this.errorMessage="skill_not_complet";
			this.errno=2;
			return false;
		}
		if(hasSkill(skill)){
			this.errorMessage="skill_exist";
			this.errno=1;
			return false;
		}
		this.skills.put((int)Integer.parseInt(skill.getMap().get("id")), skill);
		this.Ereset();
		return true;
	}
	public boolean hasSkill(Skill skill) {
		int id = -1;
		try{
			id = Integer.parseInt(skill.getMap().get("id"));
		}catch(Exception e){
			return false;
		}
		return this.hasSkill(id);
	}
	public boolean hasSkill(int id) {
		return skills.containsKey(id);
	}
}
