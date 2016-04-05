package game;

import java.util.ArrayList;

public class Skill extends DataNormal{

	public Chara idol;
	
	protected enum Aurg{
		id,skill_name,skill_type,judge_type,skill_trigger_type,skill_trigger_value,cutin_type,
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
	@Override
	public String className() {
		return "skill";
	}
	@Override
	public ArrayList<String> aurgsString() {
		AurgString[] tt = AurgString.values();
		ArrayList<String> s = new ArrayList<String>(tt.length);
		for(int i = 0;i<tt.length;i++)s.add(tt[i].toString());
		return s;
	}
	public int getId() {
		return Integer.parseInt(this.map.get("id"));
	}
}
