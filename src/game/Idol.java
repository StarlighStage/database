package game;

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
public class Idol extends DataNormal{

	Valist valist_Idol = new Valist();
	
	protected enum Aurg{
		chara_id, lst_name, lst_name_kana, age, home_town, height, weight, body_size_1, body_size_2, body_size_3,
		birth_month,birth_day, constellation, blood_type, hand, favorite, voice, model_height_id,model_weight_id,
		model_bust_id, model_skin_id,spine_size,personality,type,conventional,kana_spaced,kanji_spaced;
	}
	protected enum AurgOther{
		 fst_name, fst_name_kana;
	}
	protected enum AurgString{
		lst_name,lst_name_kana,fst_name,fst_name_kana,voice,favorite;
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

}