package scp.logic;

/**
 * @author Simon Stiefel
 *
 */
public class WrongRemovalException extends Exception {
	
	public WrongRemovalException(String reason) {
		super(reason);
	}

}
