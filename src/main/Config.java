package main;

public class Config {

	public static final String dataFile = ".\\data\\";

	public static final String database = "database.db";

	public static String http="https://";
	public static String mainSitePage = "starlight.kirara.ca";
	
	//mainSitePage+"/"+cardListPage
	public static String cardListPage = "skill_table";
	public static String cardTimePage = "history";
	
	//mainSitePage+"/"+dataCardPageBegin+"/"+$id_card$+"/"+dataCardPageEnd
	public static String dataCardPageBegin = "card";
	public static String dataCardPageEnd = "table";
	
	public static String icon = "hoshimoriuta.kirara.ca/icons2";
	public static String iconCss = "icons.css";
	
	public static String version = "10014200";
	
	public static int numberCardsForIdol = 5;
	
	public static boolean debugLog = true;
	
	public static int threadNum=20;
}
