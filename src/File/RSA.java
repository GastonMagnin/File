package File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Random;

public class RSA {
	private BigInteger e;
	private BigInteger d;
	private BigInteger N;
	private BigInteger pN;
	private int charLength;
	private byte[] test;
	public RSA(int bitLength) {
		do {
		getKeys(bitLength);
		//Generate two random test numbers and run them through encryption and decryption
		}while(!(test((int)(Math.random()*Character.MAX_VALUE)) && test(((int)(Math.random() * Character.MAX_VALUE)))));
		charLength = encrypt('a').length;
	}
	
	public static void main(String[] args) {
		int a = (int) 'a';
		RSA b = new RSA(1024);
		
		/*System.out.println("asds");
		System.out.println(b.encrypt(a));
		BigInteger temp = new BigInteger(b.encrypt(a));
		String temp2 = new String(temp.toByteArray());
		System.out.println(temp2);
		temp = new BigInteger(temp2.getBytes());
		System.out.println(b.decrypt(temp) + "decrypted");*/
		//System.out.println(b.decrypt(temp) +" d" + temp.toByteArray().length + (new String(temp.toByteArray())));
		
		b.test((int) a);
	}
	private void getKeys(int bitLength) {
		Random r = new Random();
		BigInteger p = new BigInteger(((int)(0.5 * bitLength)), 100, r);
		BigInteger q = new BigInteger((int)(0.5*bitLength), 100, r);
		N = p.multiply(q);
		pN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		do {
		e = new BigInteger(pN.bitLength() - 1, r);
		}while(!gcd(pN, e).equals(BigInteger.ONE));
		
	}
	public BigInteger gcd(BigInteger inputA, BigInteger inputB) {
		// If they are the same they are the biggest shared divisor of both
		if(inputA == inputB) return inputA;
		//a is the bigger input because otherwise mod would just return a
		BigInteger minusOne = new BigInteger("-1");
		BigInteger a = (inputA.compareTo(inputB) == 1) ? inputA : inputB;
		BigInteger b = (inputA.equals(a)) ? inputB : inputA;
		BigInteger c;
		BigInteger beta = minusOne.multiply(a.divide(b));//ganzzahlig ohne rest
		BigInteger currentCarryBeta = BigInteger.ZERO;
		BigInteger nextCarryBeta = BigInteger.ONE;
		BigInteger currentFactor = BigInteger.ZERO;
		while(!(c = a.mod(b)).equals(BigInteger.ZERO) ) {
			if(a != ((inputA.compareTo(inputB) == 1) ? inputA : inputB)) {
				currentFactor = a.divide(b).multiply(minusOne);
				beta = beta.multiply(currentFactor);
				beta = beta.add(currentCarryBeta);
			}
			currentCarryBeta = nextCarryBeta;
			nextCarryBeta = beta;
			a=b;
			b = c;
		}
		d = beta;
		return b;
	}
	public String encryptString(String input) throws UnsupportedEncodingException {
		String outputString = "";
		byte[] temp = input.getBytes("UTF-8");
		test = encrypt(temp);
		outputString = new String(encrypt(temp), "UTF-8");
		/*
		for(int i = 0; i < input.length(); i++) {
			temp = encrypt((int)input.charAt(i));
			outputString += new String(temp);
			System.out.println(outputString);
		}*/
		return outputString;
	}
	public String decryptString(String input) throws UnsupportedEncodingException {
		String outputString = "";
		
		
		
		return outputString;
	}
	public byte[] encrypt(int input) {
		BigInteger b = BigInteger.valueOf(input);
		b = b.modPow(e, N);
		return b.toByteArray();
	}
	public byte[] encrypt(byte[] input) {
		BigInteger b = new BigInteger(input);
		b = b.modPow(e, N);
		return b.toByteArray();
	}
	public int decrypt(BigInteger input) {
		int output = input.modPow(d, N).intValue();
		return output;
	}
	public int decrypt(byte[] input) {
		BigInteger b = new BigInteger(input);
		b = b.modPow(d, N);
		return b.intValue();
	}
	public String decryptToString(byte[] input) {
		String outputString = "";
		System.out.println(input.length + " charlength "+ charLength);
		if(input.length > charLength) {
			byte[] temp = new byte[charLength];
			System.out.println(input.length + " charlength "+ charLength);
			for(int i = 0; i < input.length; i += charLength) {
				
				for(int j = 0; j < charLength; j++) {
					if(i+j < input.length)
					temp[j] = input[i+j];
				}
				outputString += (char) decrypt(temp);
				
			}
		}else {
			outputString += (char) decrypt(input);
		}
		return outputString;
		
	}
	private boolean test(int input) {
		BigInteger b = BigInteger.valueOf(input);
		b = b.modPow(e, N);
		b = b.modPow(d, N);		
		int output = b.intValue();
		if(output == input) {
			return true;
		}else return false;
	}
	
}



