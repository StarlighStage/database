package database;

import game.Cards;
import game.Chara;
import game.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import url.HtmlAnaliser;
import url.WebsiteReader;

import main.Config;
import mesage.Errno;

public class DataCache extends Errno{

	HashMap<Integer,Cards>cards;
	HashMap<Integer,Chara>charas;
	HashMap<Integer,Skill>skills;
	Database database;
	boolean block = false;
	int count = 0;
	Random random = new Random();

	public DataCache(){
		cards = new HashMap<Integer,Cards>();
		charas = new HashMap<Integer,Chara>();
		skills = new HashMap<Integer,Skill>();
		database = new Database();
	}
	public DataCache(Database database){
		this();
		this.database = database;
		//TODO
	}

	public boolean update(){
		HtmlAnaliser analyser = new HtmlAnaliser(WebsiteReader.pageReader(HtmlAnaliser.icon_page()));
		List<Integer> cards =analyser.getListCards();
		int addition = cards.size()/Config.threadNum;
		ArrayList<UploadThread> threads = new ArrayList<UploadThread>(Config.threadNum);
		this.block=true;
		for(int i=0;i<Config.threadNum;i++){
			threads.add(i, new UploadThread(cards, i*addition, (i+1)*addition-1, this)); 
			threads.get(i).start();
			count++;
		}
		this.block=false;
		for(int i=Config.threadNum*addition;i<cards.size();i++){
			if(i%2==0)continue;
			int id = cards.get(i);
			if(this.cards.containsKey(id))continue;
			HtmlAnaliser analysers = new HtmlAnaliser(WebsiteReader.pageReader(HtmlAnaliser.card_table_page(id)));
			while(block){
				try {
					Thread.currentThread();
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			block = true;
			out(1,"[Thread]add for card ["+id+"]");
			if(!analysers.analyseCardPage(this))System.out.println("Errno "+analysers.errno+" "+analysers.errorMessage);
			block = false;
		}

		while(count>0){
			try {
				out(1,"waiting for left "+count+" thread");
				Thread.currentThread();
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		out(1,"[thread]All thread finish");
		out(1,"totalement ["+this.charas.size()+"] charas whith ["+this.cards.size()*2+"] cards and ["+this.skills.size()*2+"] skills in database");
		
		Ereset();
		return true;
	}

	public boolean init(){
		for(int i : this.cards.keySet()){
			Cards card = this.cards.get(i);
			Chara chara = this.charas.get(card.getId());
			Skill skill = this.skills.containsKey(card.getId())?this.skills.get(this.cards.get(i)):null;
			card.idol=chara;
			card.skill=skill;
			if(!chara.cards.contains(card))chara.cards.add(card);
			if(skill!=null)skill.idol=chara;
		}
		Ereset();
		return true;
	}

	public boolean hasCard(Cards card){
		return this.hasCard(card.getId());
	}
	public boolean hasCard(int id){
		return cards.containsKey(id);
	}

	public boolean hasIdol(Chara idol){
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
	public boolean setChara(Chara idol){
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
	public Chara getChara(int i) {
		return charas.get(i);
	}
	public Skill getSkill(int i) {
		return skills.get(i);
	}


	private class UploadThread extends Thread {

		List<Integer> cardss;
		int begin;
		int end;
		DataCache data;

		UploadThread(List<Integer> cards, int begin, int end,DataCache data) {
			this.cardss = cards;
			this.begin = begin;
			this.end = end;
			this.data = data;
		}

		public void run() {
			for(int i = begin; i<=end;i++){
				if(i%2==0)continue;
				int id = cardss.get(i);
				if(cards.containsKey(id))continue;
				HtmlAnaliser analyser = new HtmlAnaliser(WebsiteReader.pageReader(HtmlAnaliser.card_table_page(id)));
				while(block){
					try {
						Thread.currentThread();
						Thread.sleep(10*random.nextInt(32));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				block = true;
				out(1,"[thread:"+begin+"]add for card ["+id+"]");
				if(!analyser.analyseCardPage(data))System.out.println("Errno "+analyser.errno+" "+analyser.errorMessage);
				out(1,"[thread:"+begin+"]freed");
				block = false;
			}
			while(block){
				try {
					Thread.currentThread();
					Thread.sleep(10*random.nextInt(32));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			block = true;
			count--;
			out(1,"[thread:"+begin+"]finish : left "+count);
			block = false;
		}
	}

}
