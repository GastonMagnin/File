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
	public static byte[] byteRead(String path) throws IncorrectPathException{
		byte[] output = null;
		try(		FileInputStream fir = new FileInputStream(getFile(path));
				){
		output = fir.readAllBytes();
	} catch (FileNotFoundException e) {
		throw new IncorrectPathException(e, path);
	} catch (IOException e) {
		e.printStackTrace();
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
		System.out.println(f.exists());
		try(
				FileWriter fw = new FileWriter(f);){
			fw.write(input);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void byteWrite(byte[] input, String targetPath) throws IncorrectPathException {
		File f;
		if(!targetPath.contains("/")) {
			f = new File(System.getProperty("user.dir") + "/"+ targetPath);
			
		}else {
			f = new File(targetPath);
		}
		try(FileOutputStream fos = new FileOutputStream(f, true);){
			fos.write(input);
			fos.flush();
		} catch (FileNotFoundException e) {
			throw new IncorrectPathException(e, targetPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
