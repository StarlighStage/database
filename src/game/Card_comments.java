package game;

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
public class Card_comments extends DataNormal{

	protected enum Aurg{
		Idol_id,use_type,voice_flag,discription
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

}
