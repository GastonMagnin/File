package File;

public class IncorrectPathnameException extends Exception{
	

	public IncorrectPathnameException() {
		super("Incorrect Pathname");
	}
	public IncorrectPathnameException(String pathname) {
		
	}
}
