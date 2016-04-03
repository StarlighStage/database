package test;

import game.Cards;

import java.util.HashMap;
import java.util.List;

import database.DataCache;

import url.HtmlAnaliser;
import url.IncredibleElementException;
import url.WebsiteReader;

public class TestMain {

	
	public static void main(String[] args){
		WebsiteReader asd = new WebsiteReader();
		String ty = asd.pageReader("https://starlight.kirara.ca/card/200023/table");
		System.out.println("Finish for loading web page\n");
		HtmlAnaliser analyser = new HtmlAnaliser(ty);
		DataCache cache = new DataCache();
		cache.hasCard(0);
		if(!analyser.analyseCardPage(cache))System.out.println("error: "+analyser.errorMessage);
		System.out.print(cache.getCard(200023).toString());
		//analyser.analyseCard(cards);
		
		HashMap<String,String>a;
	}
}
