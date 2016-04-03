package mesage;

/**-1/t no error
 * 01:exist/not exist
 * 02:error element
 * 03:error input web
 * 04:enter an Waring type
 * 05:Negative Method
 * 06:Condition unchecked
 * 07:SQL Error
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
}
