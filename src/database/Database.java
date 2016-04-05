package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mesage.Errno;
import game.Cards;
import game.Idol;

public class Database extends Errno{
	Connection database;
	
	public Database(){
		database = null;
		try {
			Class.forName("org.sqlite.JDBC");
			database = DriverManager.getConnection("jdbc:sqlite:.\\database\\database.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public boolean hasCard(Cards card){
		return false;//TODO
	}
	public boolean hasCard(int id){
		return false;//TODO
	}
	
	public boolean hasIdol(Idol idol){
		return false;//TODO
	}
	public boolean hasIdol(int id){
		return false;//TODO
	}
	
	public boolean setCard(Cards card){
		Eunfinied();
		return false;//TODO
	}
	public boolean setChara(Idol idol){
		Eunfinied();
		return false;//TODO
	}

	public boolean runCode(String sql){
		Statement stmt = null;
		try {
			stmt = database.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch ( Exception e ) {
			errorMessage=( e.getClass().getName() + ": " + e.getMessage());
			errno=7;
			out(1,"[error]:for SQL: "+sql+"\n Error 7>>>"+errorMessage);
			return false;
		}
		out(2,"Records created successfully");
		return true;
	}
	public boolean insert(String table,Map<String, String> map,ArrayList<String> strings){
		return runCode(insertCode(table, map, strings));
	}
	protected String insertCode(String table,Map<String, String> map,ArrayList<String> strings){
		String code1 = "INSERT INTO "+table+" (";
		String keys = "";
		String values = "";
		boolean begin = true;
		
		for(String key : map.keySet()){
			if(begin)begin=false;
			else{keys+=",";values+=',';}
			
			keys+=key;
			boolean key_is_string =false;
			if(strings.contains(key))key_is_string = true;
			String value = "";
			if(key_is_string)value+="'";
			value += map.get(key);
			if(key_is_string)value+="'";
			out(4,"for "+key+" is string ["+key_is_string+"] get resultat :["+value+"]");
			values+=value;
		}
		String sql = code1+keys+") VALUES ("+values+");";
		out(2,"get insert code:"+sql);
		//this.runCode(code1+keys+") VALUES ("+values+");");//INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (2, 'Allen', 25, 'Texas', 15000.00 ); 
		
		return sql;
	}

	public LinkedList<HashMap<String,String>> select(String SQLcode,List<String>keys){
		LinkedList<HashMap<String,String>> res = new LinkedList<HashMap<String,String>>();
		Statement stmt = null;
		try {
			database.setAutoCommit(false);
			
			stmt = database.createStatement();
			ResultSet rs = stmt.executeQuery(SQLcode);//"SELECT * FROM COMPANY;"

			while ( rs.next() ) {
				HashMap<String,String> map = new HashMap<String,String>();
				
				for(String key:keys){
					String name = key;
					String value = rs.getString(key);
					map.put(name, value);
				}
				res.add(map);
			}
			
			rs.close();
			stmt.close();
			
			database.setAutoCommit(true);
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		return res;
	}

	public void close()  {
		try {
			database.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
