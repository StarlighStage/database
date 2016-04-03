package url;

import mesage.Errno;

/**analyze a String begin at 
 * xx_yy_t( /n
 * which in format a=10; a='asd'; aa_bb_t(...)/n
 * and end with ')'
 * @author laurencedu
 *
 */
public class Code_t_Analiser extends Errno{

	String source;
	
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
		//TODO
		Eunfinied();
		return false;
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
