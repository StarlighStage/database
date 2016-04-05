package game;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mesage.Errno;
public abstract class DataNormal extends Errno {
	
	public DataNormal(){
		this.map=new HashMap<String,String>();
	}
	
	protected HashMap<String,String> map;
	
	/*
	protected enum Aurg{
		
	}
	protected enum AurgString{
		
	}
	protected enum AurgOther{
		
	}
	 * */
	/*
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
	 * */
	/**check if name is a key word in this object
	 * @param name
	 * @return
	 */
	public boolean checkIn(String name) {
		try{
			Aurg_valueOf(name.toLowerCase(Locale.ENGLISH));
		}catch(java.lang.IllegalArgumentException e){
			try{
				AurgOther_valueOf(name.toLowerCase(Locale.ENGLISH));
			}catch(java.lang.IllegalArgumentException es){
				return false;
			}
		}
		return true;
	}
	
	public boolean setElements(String name,String val){
		if(!checkIn(name)){
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
	
	/**check if the object is complete. the each type should also be checked.
	 * @return
	 */
	public boolean complete() {
		String name="";
		try{
			for(String a : aurgs()){
				name = a;
				if(!this.map.containsKey(name)){
					this.errno=1;
					this.errorMessage = "lost "+name;
					return false;
				}
				try{AurgString_valueOf(name);continue;}catch(Exception e){};
				Integer.parseInt(this.map.get(name));
			}
		}catch(java.lang.NumberFormatException e){
			this.errno=4;
			this.errorMessage = name;
			return false;
		}
		Ereset();
		return true;
	}
	
	protected abstract void Aurg_valueOf(String name);
	protected abstract void AurgString_valueOf(String name);
	protected abstract void AurgOther_valueOf(String name);
	protected abstract String[] aurgs();
	
	public String toString(){
		String res = "";
		for(String s : map.keySet()){
			res += s+": "+map.get(s)+"\n";
		}
		res+="complet = "+complete();
		
		return res;
	}
	
	public Map<String, String> getMap(){
		return this.map;
	}
	
	public boolean containKey(String key){
		return map.containsKey(key);
	}
	public abstract String className();

	public  abstract List<String> aurgsString();
	public abstract int getId();
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof DataNormal))return false;
		DataNormal o = (DataNormal) obj;
		return o.className().equals(this.className())&&o.getId()==this.getId();
	}
}
