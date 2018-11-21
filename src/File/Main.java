package File;

import java.io.UnsupportedEncodingException;

public class Main {
	public static void main(String[] args){
		try {
			RSA r = new RSA(1024);
			String a;
			byte[] b;
			System.out.println((a=FileOI.fileRead("abc.txt").trim()));
			System.out.println(a);
			System.out.println("___________");
			b = r.encrypt('a');
			FileOI.byteWrite(b, "cba.txt");
			b = r.encrypt('b');
			FileOI.byteWrite(b,"cba.txt");
			b = FileOI.byteRead("cba.txt");
			System.out.println(r.decryptToString(b));
			
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
