package url;

import mesage.Errno;

/**analyze a String begin at <br>
 * xx_yy_t( <br>
 * which in format a=10; a='asd'; valite = [aa_bb_t(...),aa_bb_t(...)...]<br>
 * and end with)<br>
 * but may be just a 'None'
 * @author laurencedu
 *
 */
public class Code_t_Analiser_change extends Errno{

	public String sName;
	protected String source;
	protected String name;
	protected String val;
	private String enAnal;
	private boolean fin = false;
	
	public Code_t_Analiser_change(String source){
		int index;
		if((index = source.indexOf('('))!=-1){
			out(1,"begin to analyse"+source);
			this.sName = source.substring(0, index);
			this.source = source.substring(index+1).trim();
			if(this.source.indexOf('=')==-1)this.fin=true;
		}
		else{
			this.fin=true;
			this.source = "";
			this.name = "unNamed";
			this.val = "unNamed";
			this.sName = source;
			out(1,"["+sName+"] has been analysed");
		}
	}
	
	public boolean hasNext(){
		return !this.fin;
	} 
	
	
	/**
	 * @return
	 */
	public boolean analyseNext(){
		if(!hasNext()){
		this.errno = 06;
			this.errorMessage="End of the Source";
			return false;
		}
		
		Ereset();
		return analyse();
	}
	
	/** Enter possible:<br>
	 * 1:chara_id=115, ... <br>
	 * 2:name='櫻井桃華', ... =>String<br>
	 * 3:model_height_id=0, ... <br>
	 * 4:valist=[...,...],...<br>
	 * *:[,...] may be change as ')'
	 * @return errno
	 * */
	protected boolean analyse(){
		int index = getFinArug();
		out(3,"find DOT in "+index+" with source ["+this.source+"]");
		if(this.errno!=-1){
		if(this.errno==100){
			this.errno=3;
			this.errorMessage="incroiable input page";
			return false;
		}
		if(this.errno==101){
			this.fin=true;
			this.errno = 8;
			this.errorMessage = "analyse already end";
			return false;
		}
		}
		Ereset();
		if(this.source.charAt(index)==')'){
			this.fin=true; //end of a_v_t()
			out(3,"the last aurg, fin = true");
		}
		this.enAnal = this.source.substring(0, index); // get a = 3 ||,.....
		out(3,"en analyse ["+enAnal+"]");
		this.source = (fin)?"":this.source.substring(index+1).trim();//...,||....
		if(!this.analyseName())return false;
		switch(this.enAnal.charAt(0)){
			case '\'':
				return this.analyseString();
			default:
				return this.analyseNormal();
		}
	}
	
	/**analyse the string in enAnal with form "'asd'" => "asd"
	 * and then put into value
	 * @return errno
	 */
	protected boolean analyseString(){
		this.val = this.enAnal.substring(enAnal.indexOf('\'')+1, enAnal.lastIndexOf('\''));
		this.enAnal = "";
		out(2,"find aurg str:["+this.name+"] = ["+this.val+"]");
		Ereset();
		return true;
	}
	/**analyse the string in enAnal with form "asd" => "asd"
	 * and then put into value
	 * @return errno
	 */
	protected boolean analyseNormal(){
		this.val = this.enAnal.trim();
		this.enAnal = "";
		out(2,"find aurg norm:["+this.name+"] = ["+this.val+"]");
		Ereset();
		return true;
	}
	
	protected int getFinArug(){
		int a = this.source.indexOf('(');
		int b = this.source.indexOf(',');
		int c = this.source.indexOf('[');
		if(b==-1){
			out(3,"[arug]:last one?");
			b = this.source.lastIndexOf(')');
			if(b==-1){
				 if(this.source.trim() == "")errno = 101;
				 else errno = 100;
			}
			return b;
		}
		Ereset();
		if(c>0&&c<b){
			out(2,"[arug]:format a = [...]");
			int index =  getIndexFinbrackets(c,']', source);
			b = this.source.indexOf(',', index);
			if(b==-1) b = this.source.indexOf(')', index);
			if(b==-1) errno = 100;
			return b;
		}
		else if(a>0&&a<b){
			out(2,"[arug]:format a = (...)");
			int index =  getIndexFinbrackets(a,')', source);
			b = this.source.indexOf(',', index);
			if(b==-1) b = this.source.indexOf(')', index);
			if(b==-1) errno = 100;
			return b;
		}
		else return b;
	}
	
	public static int getIndexFinbrackets(int indexBeginBracket,char bracketRight,String source){
		char bracketLeft = source.charAt(indexBeginBracket);
		int flag = 1;
		int index = indexBeginBracket+1;
		while(flag>0){
			if(index>=source.length())return -1;
			char dst = source.charAt(index);
			if(dst == bracketLeft)flag++;
			else if(dst == bracketRight)flag--;
			index++;
		}
		return index-1;
	}
	
	protected boolean analyseName(){
		if(this.enAnal.indexOf('=')==-1){
			out(1,"error: illegal aurg "+this.enAnal);
			this.analyse();
			this.errno=3;
			this.errorMessage = "Element not in format a = b";
			this.enAnal = "";
			return false;
		}
		String[] tmp = this.enAnal.split("=",2);
		this.name = tmp[0].trim();
		this.enAnal = tmp[1].trim();
		Ereset();
		return true;
	}
	
	
	/**get name id
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**get the contain value
	 * @return
	 */
	public String getValue(){
		return this.val;
	}
}
