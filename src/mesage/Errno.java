package mesage;

/**-1/t no error
 * 01:exist/not exist
 * 02:error element
 * 03:error input web
 * 04:enter an Waring type
 * 05:Negative Method
 * 06:Condition unchecked
 * 07:SQL Error
 * 08:method can't do anything but all right 
 * @author laurencedu
 *
 */
public abstract class Errno {
	/**
	 * 01:exist/not exist
	 * 02:error element
	 * 03:error input web
	 * 04:enter an Waring type
	 * 05:Negative Method
	 * 06:Condition unchecked
	 */
	public int errno = -1;
	public String errorMessage = "nothing";
	public boolean log = false;
	
	/**4: out put all
	 * 3: only for end of method
	 * 2: only for end of class
	 * 1: only for resultat
	 * moin important, plus elev
	 */
	public int elev = 1;
	/**
	 * reset error message
	 */
	protected void Ereset(){
		this.errno = -1;
		this.errorMessage = "nothing";
	}
	/**
	 * use when a new method create but not finish\n
	 * change it into this.Ereset() when finish.
	 */
	protected void Eunfinied(){
		this.errno = 6;
		this.errorMessage = "writint methode";
	}
	public void out(String message){
		if(log)System.out.println("["+this.getClass().getName()+"] : "+message);
	}
	public void out(int elev,String message){
		if(log&&elev<=this.elev)System.out.println("["+this.getClass().getName()+"] : "+message);
	}
}
