package scp.logic;

/**
 * @author Simon Stiefel
 *
 */
public class WrongPlacementException extends Exception {

	public WrongPlacementException(String reason) {
		super(reason);
	}
	
	private static final long serialVersionUID = 1L;
}
