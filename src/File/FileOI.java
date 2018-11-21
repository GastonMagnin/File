package File;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileOI {
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
		String inputLine, output = "";
		try(		BufferedReader br = new BufferedReader(new FileReader(getFile(path)));
					){
			while((inputLine = br.readLine()) != null) {
				output += inputLine + "\n";
			}			
		} catch (FileNotFoundException e) {
			throw new IncorrectPathException(e, path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return output;
	}
	public static ArrayList<String> fileRead(String path, boolean a) throws IncorrectPathException{
		ArrayList<String> output = new ArrayList<String>();
		try(		BufferedReader br = new BufferedReader(new FileReader(getFile(path)));
				){
		String inputLine;
		while((inputLine = br.readLine()) != null) {
			output.add(inputLine);
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
				FileWriter fw = new FileWriter(f);){
			fw.write(input);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
