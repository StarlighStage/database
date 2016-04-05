package game;

import java.util.ArrayList;

/**
 * 	int Idol_id;
	int use_type;
	int voice_flag;
	String discription;
	int insert_word_type;
	int story_cue_id;
 * @author laurencedu
 *
 */
public class Card_cmd_t extends DataNormal{

	protected enum Aurg{
		chara_id,use_type,voice_flag,discription
	}
	protected enum AurgString{
		discription
	}
	protected enum AurgOther{
		insert_word_type,story_cue_id
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
	public String className() {
		return "chara_valist";
	}
	@Override
	public ArrayList<String> aurgsString() {
		AurgString[] tt = AurgString.values();
		ArrayList<String> s = new ArrayList<String>(tt.length);
		for(int i = 0;i<tt.length;i++)s.add(tt[i].toString());
		return s;
	}
	@Override
	public int getId() {
		// TODO 自动生成的方法存根
		return Integer.parseInt(this.map.get("chara_id"));
	}
}
