package chess;

public class OutOfValidRange extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfValidRange(String message){
	    super(message);
	} 
}
