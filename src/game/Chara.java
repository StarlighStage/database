package game;

import java.util.ArrayList;

import main.Config;

	

/**	int chara_id;
	String name;
	String name_kana;
	int age;
	int home_town;
	int height;
	int weight;
	int body_size_1;
	int body_size_2;
	int body_size_3;
	int birth_month;
	int birth_day;
	int constellation;
	int blood_type;
	int hand;
	String favorite;
	String voice;
	int model_height_id;
	int model_weight_id;
	int model_bust_id;
	int model_skin_id;
	int spine_size;
	int personality;
	int type;
	String conventional;
	String kana_spaced;
	String kanji_spaced;
 * @author laurencedu
 *
 */
public class Chara extends DataNormal{

	Valist valist_Idol = new Valist();
	
	public ArrayList<Cards> cards = new ArrayList<Cards>(Config.numberCardsForIdol);
	
	protected enum Aurg{
		chara_id, lst_name, lst_name_kana, age, home_town, height, weight, body_size_1, body_size_2, body_size_3,
		birth_month,birth_day, constellation, blood_type, hand, favorite, voice, model_height_id,model_weight_id,
		model_bust_id, model_skin_id,spine_size,personality,type,conventional;
	}
	protected enum AurgOther{
		 fst_name, fst_name_kana;
	}
	protected enum AurgString{
		lst_name,lst_name_kana,fst_name,fst_name_kana,voice,favorite,conventional;
	}
	
	public Valist getValist_Idol(){
		return this.valist_Idol;
	}
	@Override
	protected void Aurg_valueOf(String name) {
		Aurg.valueOf(name);
	}
	@Override
	protected void AurgString_valueOf(String name) {
		AurgString.valueOf(name);
	}
	@Override
	protected void AurgOther_valueOf(String name) {
		AurgOther.valueOf(name);
	}
	@Override
	protected String[] aurgs() {
		Aurg[] tt = Aurg.values();
		String[] s = new String[tt.length];
		for(int i = 0;i<tt.length;s[i]=tt[i++].toString());
		return s;
	}
	@Override
	public ArrayList<String> aurgsString() {
		AurgString[] tt = AurgString.values();
		ArrayList<String> s = new ArrayList<String>(tt.length);
		for(int i = 0;i<tt.length;i++)s.add(tt[i].toString());
		return s;
	}
	@Override
	public String className() {
		return "chara";
	}
	@Override
	public int getId() {
		return Integer.parseInt(this.map.get("chara_id"));
	}
	@Override
	public boolean setElements(String name,String val){
		if(name.equals("kanji_spaced")){// name with spase
			int index = val.indexOf(' ');
			if(index==-1)map.put("lst_name", val);
			else{
				String fstName = val.substring(0, index).trim();
				String lstName = val.substring(index+1).trim();
				map.put("fst_name", fstName);
				map.put("lst_name", lstName);
				Ereset();
				return true;
			}
		}//TODO Valist
		else if(name.equals("kana_spaced")){
			int index = val.indexOf(' ');
			if(index==-1)map.put("lst_name_kana", val);
			else{
				String fstName = val.substring(0, index).trim();
				String lstName = val.substring(index+1).trim();
				map.put("fst_name_kana", fstName);
				map.put("lst_name_kana", lstName);
				Ereset();
				return true;
			}
		}
		else if(!checkIn(name)){
			this.errno = 3;
			this.errorMessage = name+" isn't a key word";
			return false;
		}
		if(map.containsKey(name)){
			this.errno = 1;
			this.errorMessage = name+" already exist";
			return false;
		}
		map.put(name.toLowerCase(), val);
		Ereset();
		return true;
	}
	public String getIdolName(){
		String res = "";
		if(map.containsKey("fst_name"))res += map.get("fst_name");
		res+=map.get("lst_name");
		return res;
	}
}