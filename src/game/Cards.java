package game;

import java.util.ArrayList;



public class Cards extends DataNormal{
	
	
	public Chara idol;
	public Skill skill;
	
	protected enum Aurg{
		id , chara_id , rarity, attribute ,title_flag , evolution_id,series_id,pose, place,evolution_type , album_id,
		open_story_id ,open_dress_id ,skill_id ,leader_skill_id , grow_type,hp_min ,
		vocal_min , dance_min , visual_min ,hp_max ,vocal_max,
		dance_max,visual_max ,bonus_vocal,bonus_dance,bonus_visual,bonus_hp, solo_live,star_lesson_type ,title,has_spread;

	}
	protected enum AurgOther{
		
	}
	protected enum AurgString{
		title,has_spread;
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
		return "cards";
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
		return Integer.parseInt(this.map.get("id"));
	}
	
	public int getChara(){
		return Integer.parseInt("chara_id");
	}
}
