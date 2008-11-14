package scp.cli;

public class NoFileSpecifiedException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoFileSpecifiedException(String message) {
		super(message);
	}
}
