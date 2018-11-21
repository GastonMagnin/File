package File;

public class Main {
	public static void main(String[] args){
		try {
			String a;
			System.out.println((a=FileOI.fileRead("abc.txt")));
			FileOI.fileWrite(a, "cba.txt");
			System.out.println((FileOI.fileRead("abc.txt", true)).size());
			for(int i = 0; i < a.length(); i++) {
				System.out.print((int)a.charAt(i));
			}
		} catch (IncorrectPathException e) {
			System.out.println(e.getPath());
			
		}
	}

}
