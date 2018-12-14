package File;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Main {
	public static void main(String[] args){
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

}
