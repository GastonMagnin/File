package File;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Main {
	public static void main(String[] args){
		try {
			RSA r = new RSA(1024);
			//saveRSAData(r);
			getRSAFromFile("1544823341944_rsakeys.csv", 0);
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
	public static void saveRSAData(RSA input) {
		FileIO.fileWrite(input.getRSAData(), System.currentTimeMillis() + "_rsakeys.csv");
		System.out.println(input.getRSAData());
	}
	public static void saveRSAData(RSA[] input) {
		String output = "";
		for(int i = 0; i < input.length; i++) {
			output += String.valueOf(i) + input[i].getRSAData() + "|\n";
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
		String[] data = FileIO.fileRead(path).split("|");
		if(data.length <= index) {
			return null;
		}
		data = data[index].split(",");
		RSA output = new RSA(data[1], data[2], data[3], data[4]);
		return output;
	}
}
