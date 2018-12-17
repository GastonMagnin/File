package File;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Main {
	public static void main(String[] args){
		test2();
	}
	public static void test1() {
		try {
			RSA r = new RSA(1024);
			String a;
			byte[] b;
			a=FileIO.fileRead("abc.txt");
			String v = r.encryptString(a);
			FileIO.fileWrite(v, "cba.txt");
			a = FileIO.fileRead("cba.txt");
			System.out.println(r.decryptString(v));
		} catch (IncorrectPathException e) {
			e.printStackTrace();
			System.out.println(e.getPath());
			
		}
	}
	public static void test2() {
		RSA r = new RSA(1024);
		String a;
		byte[] b;
		try {
			a=FileIO.fileRead("abc.txt");
			String v = r.encryptString(a);
			FileIO.fileWrite(v, "cba.txt");
			String name = "abc";
			saveRSAData(r, name);
			r = null;
			r = getRSAFromFile(name, 0);
			a = FileIO.fileRead("cba.txt");
			System.out.println(r.decryptString(v));
		} catch (IncorrectPathException e) {
			e.printStackTrace();
			System.out.println(e.getPath());
		
	}
	}
	/**
	 * Saves the bitLength, keys and RSA module for the RSA object in input to a csv file 
	 * with name *timestamp*_rsakeys.csv
	 * @param input RSA object whose contents are to  be saved
	 */
	public static void saveRSAData(RSA input) {
		FileIO.fileWrite(input.getRSAData(), System.currentTimeMillis() + "_rsakeys.csv");
	}
	/**
	 * Saves the bitLength, keys and RSA module for the RSA object in input to a csv file 
	 * with name *name*.csv
	 * @param input RSA object whose contents are to  be saved
	 * @param name name of the file that inputs contents will be saved to
	 */
	public static void saveRSAData(RSA input, String name) {
		if(!name.matches("(.csv)$")) name+= ".csv";
		FileIO.fileWrite(input.getRSAData(), name);
		System.out.println(input.getRSAData());
	}
	/**
	 * Saves the bitLength, keys and RSA module for all RSA objects in input to a csv file 
	 * with name *timestamp*_rsakeys.csv
	 * @param input RSA array containing the RSA objects whose contents are to be saved
	 * 
	 */
	public static void saveRSAData(RSA[] input) {
		String output = "";
		for(int i = 0; i < input.length; i++) {
			output += input[i].getRSAData() + "|\n";
		}
		FileIO.fileWrite(output, System.currentTimeMillis() + "_rsakeys.csv");
	}
	/**
	 * Returns a RSA object with the bitLength keys and RSA module nr.*index* in *path*
	 * @param path	Path to a csv file with RSA data 
	 * @param index	index of RSA dataset in case there are multiple datasets
	 * @return	RSA object with the bitLength keys and RSA module nr.*index* in *path*, null if there is no dataset nr. *index*
	 * @throws IncorrectPathException	
	 */
	public static RSA getRSAFromFile(String path, int index) throws IncorrectPathException {
		if(!path.matches("(.csv)$")) path+= ".csv";
		System.out.println(path);
		String fileContent = FileIO.fileRead(path);
		String[] data;
		if(fileContent.contains("|")) {
			data = fileContent.split("|");
		}else {
			data = new String[] {fileContent};
		}
		if(data.length <= index) {
			return null;
		}
		data = data[index].split(",");
		RSA output = new RSA(data[0], data[1], data[2], data[3]);
		return output;
	}
}
