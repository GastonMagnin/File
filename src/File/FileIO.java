package File;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;




public class FileIO {
	/**
	 * Checks if the given path contains a file and throws exception if not
	 * @param filePath String that contains the path
	 * @return	File object with the specified path
	 * @throws IncorrectPathException thrown if the path does not contain a file
	 */
	private static File getFile(String filePath) throws IncorrectPathException {
		File f;
		if(!filePath.contains("/")) {
			f = new File(System.getProperty("user.dir") + "/"+filePath);
			
		}else {
			f = new File(filePath);
		}
		if(!f.exists()) throw new IncorrectPathException(filePath);
		return f;
	}
	public static String fileRead(String path) throws IncorrectPathException{
		String output = "";
		char[] inputChar = new char[1]; 
		try(		InputStreamReader isr = new InputStreamReader(new FileInputStream(getFile(path)), StandardCharsets.ISO_8859_1);
					){
			if((isr.read(inputChar)) != -1)
				output += inputChar[0];
			while(isr.read(inputChar) != -1) {
				output +=  inputChar[0];
			}			
		} catch (FileNotFoundException e) {
			throw new IncorrectPathException(e, path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return output;
	}
	public static void fileWrite(String input, String targetPath) {
		File f;
		if(!targetPath.contains("/")) {
			f = new File(System.getProperty("user.dir") + "/"+ targetPath);
			
		}else {
			f = new File(targetPath);
		}
		try(
				OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.ISO_8859_1);
				FileWriter fw = new FileWriter(f);){
			//fw.write(input);
			fos.write(input);
			fos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
