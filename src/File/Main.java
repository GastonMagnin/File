package File;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Main {
	public static void main(String[] args){
		try {
			RSA r = new RSA(1024);
			String a;
			byte[] b;
			a=FileOI.fileRead("abc.txt");
			String v = r.encryptString(a);
			System.out.println(v.length());
			FileOI.fileWrite(v, "cba.txt");
			a = FileOI.fileRead("cba.txt");
			System.out.println(r.decryptString(a));
			
			
			;
			/*a = new String(r.encrypt(a.getBytes()));
			//System.out.println(a + "apppppp");
			FileOI.fileWrite(a, "cba.txt");
			a = FileOI.fileRead("cba.txt");
			*/
			
			
		} catch (IncorrectPathException e) {
			System.out.println("error");
			e.printStackTrace();
			System.out.println(e.getPath());
			
		}
	}

}
