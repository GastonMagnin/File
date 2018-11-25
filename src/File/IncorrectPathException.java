package File;

/**
 * 
 * @author GastonMagnin
 *@see #getPath()
 */
public class IncorrectPathException extends Exception{
	
	private String path = "unknown";
	/**
	 * Creates a new IncorrectPathException
	 * @see #IncorrectPathException(String)
	 * @see #IncorrectPathException(Throwable)
	 * @see #IncorrectPathException(Throwable, String)
	 */
	public IncorrectPathException() {
		super("Incorrect Path");
	}
	/**
	 * Creates new IncorrectPathException that returns the incorrect file path with {@link #getPath()}
	 * @param path the incorrect file path
	 * @see #getPath()
	 * @see #IncorrectPathException(Throwable)
	 * @see #IncorrectPathException(Throwable, String)
	 */
	public IncorrectPathException(String path) {
		super("Incorrect Path");
		this.path = path;
	}
	/**
	 * Creates new IncorrectPathException that returns cause with {@link #getCause()}
	 * @param cause Throwable that caused this exception to be thrown
	 * @see #getCause(),
	 * @see #IncorrectPathException(String)
	 * @see #IncorrectPathException(Throwable, String)
	 */
	public IncorrectPathException(Throwable cause) {
		super("Incorrect Path", cause);
	}
	/**
	 * Creates new IncorrectPathException that returns the incorrect file path with {@link #getPath()} 
	 * and cause with {@link #getCause()}
	 * @param cause Throwable that caused this exception to be throw
	 * @param path the incorrect file path
	 * @see #getPath()
	 * @see #getCause()
	 * @see #IncorrectPathException(String)
	 * @see #IncorrectPathException(Throwable)
	 */
	public IncorrectPathException(Throwable cause, String path) {
		super("Incorrect Path", cause);
		this.path = path;
	}
	/**
	 * returns the path if the path has been specified
	 * @return path if path has been specified unknown if it hasn't
	 */
	public String getPath() {
		return path;
	}
	
}
