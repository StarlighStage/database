package url;

import game.Cards;
import game.DataNormal;
import game.Chara;
import game.Skill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import main.Config;
import mesage.Errno;

import database.DataCache;

public class HtmlAnaliser extends Errno{

	public static int flag = 1;
	public static int text = 4;
	public static int endflag =2; 
	public String score;

	
	public HtmlAnaliser(String text) {
		this.score = text;
	}

	/**Setter
	 * @param text
	 */
	public void putText(String text){
		this.score = text;
	}
	
	/**Getter
	 * @return
	 */
	public String getLeftText(){
		return this.score;
	}
	/**remove all others. Example, "head" rest which enter \<head....\> and \<\/head\>
	 * @param sc 
	 */
	public void setMainScore(String sc){
		String[] tmp;
		tmp = score.split("<"+sc, 2);
		score = tmp[1];
		tmp = score.split(">",2);
		score = tmp[1];
		tmp = score.split("</"+sc,2);
		score = tmp[0];
	}
	
	public int getNextType(){
		if(score.startsWith("</"))return endflag;
		if(score.startsWith("<"))return flag;
		return text;
	}
	
	public String getFlag(){
		if((getNextType()&(flag|endflag)) ==0) throw new IncredibleElementException("next score is text");
		String[] tmp = score.split(">", 2);// "<!DOCTYPE html":"<..."
		score = tmp[1];
		return tmp[0].substring(1);
	}
	public String getText(){
		if(getNextType()!=text)throw new IncredibleElementException("next score isn't text");
		String[] tmp;
		tmp = score.split("<", 2);
		score ="<"+tmp[1];
		out(4,tmp[0]);
		return tmp[0];
	}
	/**check flag if it's same as ty, if yes, remove it.
	 * @param ty
	 * @return
	 */
	public boolean checkFlag(String ty){
		String tmp;
		boolean res = true;
		if((getNextType()&(flag|endflag)) ==0)return false;
		tmp =getFlag();
		out(4,"["+tmp+"]");
		out(4,"["+ty+"]");
		if(!tmp.equals(ty)){
			score ="<"+tmp+">"+score;
			res = false;
		}
		return res;
	}
	/**<tr.><td.>chara_id</td.><td.>197</td.></tr.> =>[chara_i,197]
	 * @return
	 */
	public ArrayList<String> table_getRow(){
		ArrayList<String> resultat = new ArrayList<String>();
		if(getNextType()==text)getText();
		if(!checkFlag("tr"))throw new IncredibleElementException("can't find a <tr>");
		while(!checkFlag("/tr"))resultat.add(table_getCell());
		return resultat;
	}
	public String table_getCell(){
		String res = "";
		if(!checkFlag("td"))throw new IncredibleElementException("can't find a <td>");
		if(getNextType()==text)res = getText();
		if(!checkFlag("/td"))throw new IncredibleElementException("can't find a </td>");
		return res;
	}
	
	
	public List<Integer>getListCards(){
		LinkedList<Integer> listCards = new LinkedList<Integer>();
		String[] s = this.score.split("\\.icon\\.icon_");
		for(int i=1;i<s.length;i++)listCards.add(Integer.parseInt(s[i].substring(0, 6)));
		out(1,"get list card for ["+listCards.size()+"] cards");
		return listCards;
	}
	
	public static String icon_page(){
		return Config.http+Config.icon+'/'+Config.iconCss;
	}
	
	public static String card_table_page(int id){
		return Config.http+Config.mainSitePage+"/"+Config.dataCardPageBegin+"/"+id+"/"+Config.dataCardPageEnd;
	}
	
	/**Analyze card page /card/$card_id$/table<br>
	 * then put card info and idol info(if not exist) into data
	 * @param data
	 * @return
	 */
	public boolean analyseCardPage(DataCache data){
		this.setMainScore("table");
		List<String> list;
		String id = "-1";
		try{
			list = this.table_getRow();
			if(!list.get(0).equals("id")){
				this.errorMessage = "Can't find ID in 1st row:"+list.get(0);
				this.errno=3;
				return false;
			}
			id = list.get(1);
			if(data.hasCard(Integer.parseInt(id))){
				this.errorMessage="card_exist";
				this.errno=1;
				return false;
			}
			Cards card = new Cards();
			out(1,"setting cards:"+id);// debug
			card.setElements("id", id);
			while(this.score.length()>2){
				list = this.table_getRow();
				out(2,"set>"+list.get(0)+":"+list.get(1));// debug
				if(!card.setElements(list.get(0), list.get(1))){
					if(card.errno==3)
					switch(list.get(0)){
					case "chara":
						out(2,"setting for chara<<<");
						if(!analyseChara(list.get(1), new Chara() ,data)){
							out(1,"chara>>>>>errno:"+this.errorMessage);
						}
						continue;
					case "skill":
						out(2,"setting for skill<<<");
						if(!analyseSkill(list.get(1), new Skill(),data)){
							out(1,"skill>>>>>errno:"+this.errorMessage);
						}
						continue;
					case "valist"://TODO
					default: out(3,">>>Useless:"+"["+list.get(0)+"]");
					}
				}
			}
			if(!card.complete()){
				this.errorMessage = "[cards]element card haven't complet for "+id+" "+card.errno+"["+card.errorMessage+"]";
				this.errno = 3;
				return false;
			}
			if(!data.setCard(card)){
				this.errno=data.errno;
				this.errorMessage="DataCache."+data.errorMessage;
				return false;
			}
		}catch(IncredibleElementException e){
			this.errno=3;
			this.errorMessage=("id :["+id+"] "+e);
			return false;
		}
		Ereset();
		return true;
	}

	/**Analyze a String like<br>
	 * chara_data_t(
	 * chara_id=115,
	 * name='櫻井桃華', 
	 * name_kana='さくらいももか', 
	 * age=12, 
	 * home_town=13, 
	 * height=145, 
	 * weight=39, 
	 * body_size_1=72, 
	 * body_size_2=53, 
	 * body_size_3=75, 
	 * birth_month=4, 
	 * birth_day=8, 
	 * constellation=1002, 
	 * blood_type=2001, 
	 * hand=3001, 
	 * favorite='ティータイム', 
	 * voice='照井春佳', 
	 * model_height_id=0, 
	 * model_weight_id=0, 
	 * model_bust_id=0, 
	 * model_skin_id=1, 
	 * spine_size=0, 
	 * personality=3, 
	 * type=1, 
	 * valist=[card_comments_t(id=115, use_type=1, index=1, voice_flag=1, discription='おはようございます。朝食はきちんと食べまして？基本ですわよ', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=2, voice_flag=1, discription='お昼ですわね。ランチにつれて行ってくださいませんこと？', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=3, voice_flag=1, discription='ふわぁ…ね、眠くなんかありませんわっ ! お手伝いして差し上げます', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=4, voice_flag=1, discription='今ならいける気がしますわ ! 桃華にお任せなさい !', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=5, voice_flag=1, discription='ちひろさんが呼んでいましたわ。行ってさしあげて？', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=6, voice_flag=1, discription='お仕事仲間の方から申請ですわ。お返事をしてくださいね', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=7, voice_flag=1, discription='イベントですって… ! 一緒に…は、はしゃいでなんていませんわっ', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=8, voice_flag=1, discription='へぇ、プレゼント…。こういうのはよく貰うんですの？', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=1, index=9, voice_flag=1, discription='{0}ちゃま…？わたくしを前にそのような…許しませんわよ', insert_word_type=100, story_cue_id=0), card_comments_t(id=115, use_type=3, index=1, voice_flag=1, discription='ふぅ…わたくし、少し疲れました。休みますわ', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=3, index=2, voice_flag=1, discription='ふあぁ…眠くなってきましたわ', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=3, index=3, voice_flag=1, discription='こ、こういうのもいいですわね。子どもらしいですけど… !', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=3, index=4, voice_flag=1, discription='こーれーも、レディの嗜みですわ♪', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=3, index=5, voice_flag=1, discription='行きますわよ、桃華の力をみせてさしあげます !', insert_word_type=0, story_cue_id=0), card_comments_t(id=115, use_type=3, index=6, voice_flag=1, discription='あら、どうかしまして？', insert_word_type=0, story_cue_id=0)], 
	 * kanji_spaced='櫻井 桃華', 
	 * kana_spaced='さくらい ももか', 
	 * conventional='Sakurai Momoka'
	 * )<br>
	 * into a Idol<br>
	 * errno:3 error input web<br>
	 * dataCache.setChara();
	 * @param codeChara
	 * @param data 
	 * @return
	 */
	private boolean analyseChara(String codeChara, Chara idol, DataCache data){
		Code_t_Analiser_change analiser = new Code_t_Analiser_change(codeChara);
		String name;
		String value;
		if(!analiser.analyseNext()){
			this.errno=analiser.errno;
			this.errorMessage="analyseChara."+analiser.sName+"."+analiser.errorMessage;
			out(1,"[errno]:"+errorMessage);
			return false;
		}
		name = analiser.getName();
		value = analiser.getValue();
		if(!name.equals("chara_id")){//careful
			this.errno=3;
			this.errorMessage="illegal format chara";
			out(1,"[errno]:"+errorMessage);
			return false;
		}
		out(3,"chara>>>>> setting for "+name+" ["+value+"]");
		if(data.hasIdol(Integer.parseInt(value))){//careful
			out(2,"chara>>> id:["+value+"] exist>>> finish without problem");
			Ereset();
			return true;
		}
		idol.setElements(name, value);
		int id = Integer.parseInt(value);
		if(!analyseAA_t(idol, analiser, ""+id))return false;
		if(!data.setChara(idol)){
			this.errno=data.errno;
			this.errorMessage="DataCache."+data.errorMessage;
			out(1,"[errno]:"+errorMessage);
			return false;
		}
		Ereset();
		return true;//TODO check
	}

	/**Analyze a String like\n
	 * skill_data_t(
	 * id=200023, 
	 * skill_name=\'オーラ\', 
	 * explain=\'9秒ごとに低確率でかなりの間、COMBOボーナスが8%アップ\', 
	 * skill_type=4, 
	 * judge_type=1, 
	 * skill_trigger_type=0, 
	 * skill_trigger_value=0, 
	 * cutin_type=2, 
	 * condition=9, 
	 * value=108, 
	 * probability_type=2, 
	 * available_time_type=5)\n
	 * into a Skill\n
	 * errno:3\n
	 * dataCache.setChara();
	 * @see url.HtmlAnaliser.analyseChara(String codeChara, Idol idol, DataCache data)
	 * @param codeChara
	 * @param skill
	 * @param data
	 * @return
	 */
	private boolean analyseSkill(String codeChara, Skill skill, DataCache data){
		Code_t_Analiser_change analiser = new Code_t_Analiser_change(codeChara);
		if(!analiser.hasNext())return true;
		String name;
		String value;
		if(!analiser.analyseNext()){
			this.errno=analiser.errno;
			this.errorMessage="Code_t_Analiser_change."+analiser.errorMessage;
			out(1,"[errno]:"+errorMessage);
			return false;
		}
		name = analiser.getName();
		value = analiser.getValue();
		if(!name.equals("id")){//careful
			this.errno=3;
			this.errorMessage="illegal format chara";
			out(1,"[errno]:"+errorMessage);
			return false;
		}
		if(data.hasSkill(Integer.parseInt(value))){//careful
			Ereset();
			return true;
		}
		skill.setElements(name, value);
		int id = Integer.parseInt(value);
		if(!analyseAA_t(skill, analiser, ""+id))return false;
		if(!data.setSkill(skill)){
			this.errno=data.errno;
			this.errorMessage="DataCache."+data.errorMessage;
			out(1,"[errno]:"+errorMessage);
			return false;
		}
		Ereset();
		return true;//TODO check
	}
	
	private <S extends DataNormal> boolean analyseAA_t(S chara,Code_t_Analiser_change analiser,String id){
		String name;
		String value;
		while(analiser.hasNext()){
			analiser.analyseNext();
			name = analiser.getName();
			value = analiser.getValue();
			out(2,"set>"+name+":"+value);// debug
			if(!chara.setElements(name, value)){
				out(3,">>>Useless:"+"["+name+"]");
			}
		}
		if(!chara.complete()){
			this.errorMessage = "["+chara.className()+"]"+"element card haven't complet for "+id+" "+chara.errno+"["+chara.errorMessage+"]";
			this.errno = 3;
			out(1,"[errno]:"+errorMessage);
			return false;
		}
		return true;	
	}
	
}
