package test;

import game.Cards;
import game.Chara;
import game.Skill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import database.DataCache;
import database.Database;

import url.HtmlAnaliser;
import url.IncredibleElementException;
import url.WebsiteReader;

public class TestMain {

	
	public static void main(String[] args){
		/*
		String ty = WebsiteReader.pageReader("https://starlight.kirara.ca/card/200023/table");
		System.out.println("Finish for loading web page\n");
		HtmlAnaliser analyser = new HtmlAnaliser(ty);
		DataCache cache = new DataCache();
		analyser.log=false;
		analyser.elev=2;
		if(!analyser.analyseCardPage(cache))System.out.println("error: "+analyser.errorMessage);
		/*
		Database database = new Database();
		System.out.println(cache.hasIdol(197));
		System.out.println(cache.getSkill(200023).toString());
		database.log=true;
		database.elev=5;
		database.insert("skill", cache.getSkill(200023).getMap(), new Skill().aurgsString());
		*/
/*		ArrayList<String>keys =new ArrayList<String>();
		keys.add("id");
		keys.add("type");
		keys.add("name_cn1");
		keys.add("name_cn2");
		LinkedList<HashMap<String,String>> res = database.select("SELECT * FROM leader_skill_view", keys);
		for(HashMap<String,String> m:res){
			for(String s:keys){
				System.out.print(s+":"+"["+m.get(s)+"]");
			}
			System.out.println();
		}
*/		/*database.close();*/
		DataCache data = new DataCache();
		data.update();
		
	}
}
