package url;

import java.util.HashMap;
import java.util.LinkedList;

import mesage.Errno;

/**analyze a String begin at 
 * xx_yy_t( /n
 * which in format a=10; a='asd'; aa_bb_t(...)
 * @author laurencedu
 *
 */
public class Code_t_Analiser extends Errno{

	String source;
	private String[] res;
	
	public Code_t_Analiser(String source){
		this.source=source;
	}
	
	public boolean hasNext(){
		
		return false;//TODO
	} 
	
	
	public boolean analyseNext(){
		if(!hasNext()){
		this.errno = 06;
			this.errorMessage="End of the Source";
			return false;
		}
		String chara= this.stringAnalise()[0];
		String chara2 = this.stringAnalise()[1];
		String[] s;
		s = chara2.split(",", 2);
		res= s;
		String s1= res[0];
		String s2= res[1];
		HashMap map = new HashMap();
		while (s2!=null){
			//name= s1.getName();
			//value= s1.getValue();
			//map.put(name, value);
			
		}
		//TODO
		
		Eunfinied();
		return false;
	}
	
	public String[] stringAnalise(){
		
		String[] s = new String[2];
		String s1, s2;
		s1= this.source.substring(0, (this.source.indexOf('(', 1)-1));
		s2= this.source.substring((this.source.indexOf('(', 1)+1 ) , (this.source.lastIndexOf(')')-1));
		s[0]= s1;
		s[1]= s2;
		return s;
	}
	
	
	public boolean stringCorrect(){
		return true;
		
	}
	
	private String[] parantheseAnalise() {
		// TODO 自动生成的方法存根
		
		return null;
	}

	/**get name id
	 * @return
	 */
	public String getName(){
		//TODO
		return null;
	}
	
	/**get the contain value
	 * @return
	 */
	public String getValue(){
		//TODO
		return null;
	}
}
