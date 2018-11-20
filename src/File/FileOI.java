package File;

import java.io.File;
import java.nio.file.Path;

public class FileOI {
	public static void main(String[] args) {
		try {
			getFile("/");
		} catch (IncorrectPathnameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static File getFile(String filePath) throws IncorrectPathnameException {
		File a;
		if(!filePath.contains("/")) {
			a = new File(System.getProperty("user.dir") + filePath);			
		}else {
			a = new File("filePath");
		}
		if(!a.exists()) throw new IncorrectPathnameException();
		
		return a;
	}
	
}
