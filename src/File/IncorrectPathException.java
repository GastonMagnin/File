package File;

public class IncorrectPathException extends Exception{
	
	private String path = "unknown";
	public IncorrectPathException() {
		super("Incorrect Pathname");
	}
	public IncorrectPathException(String path) {
		this.path = path;
	}
	public IncorrectPathException(Throwable cause) {
		super("Incorrect Pathname", cause);
	}
	public IncorrectPathException(Throwable cause, String path) {
		super("Incorrect Pathname", cause);
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	
}
