package game;

public class Skill extends DataNormal{

	protected enum Aurg{
		chara_id,skill_name,skill_type,judge_type,skill_trigger_type,skill_trigger_value,cutin_type,
		condition,value,probability_type,available_time_type;
	}
	protected enum AurgString{
		skill_name;
	}
	protected enum AurgOther{
		
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