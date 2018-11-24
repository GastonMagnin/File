package File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RSA {
	private BigInteger e;
	private BigInteger d;
	private BigInteger N;
	private BigInteger pN;
	private int paddingLength;
	private int bitLength;
	private byte[] test;
	public RSA(int bitLength) {
		do {
		this.bitLength = bitLength;
		getKeys(bitLength);
		//Generate two random test numbers and run them through encryption and decryption
		}while(!(test((int)(Math.random()*Character.MAX_VALUE)) && test(((int)(Math.random() * Character.MAX_VALUE)))));
		paddingLength = encrypt('a').length + 10;
	}
	
	public static void main(String[] args) {
		int a = (int) 'a';
		RSA b = new RSA(1024);
		String enc_a = b.encryptString("aramsamsam");
		String dec_a = b.decryptString(enc_a);
		System.out.println(dec_a);
		b.test((int) a);
	}
	private void getKeys(int bitLength) {
		Random r = new Random();
		BigInteger p = new BigInteger(((int)(0.5 * bitLength)), 100, r);
		BigInteger q = new BigInteger((int)(0.5*bitLength), 100, r);
		N = p.multiply(q);
		pN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		do {
			do {
				e = new BigInteger(pN.bitLength() - 1, r);
			}while(e.equals(BigInteger.ZERO));
		}while(!gcd(pN, e).equals(BigInteger.ONE));
		
	}
	public BigInteger gcd(BigInteger inputA, BigInteger inputB) {
		// If they are the same they are the biggest shared divisor of both
		if(inputA == inputB) return inputA;
		//a has to be the bigger input because otherwise mod would just return a
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
	public String encryptString(String input) {
		String outputString = "", testBuffer;
		System.out.println(input.length());
		for(int i= 0; i< input.length(); i++) {
			byte[] temp = input.substring(i, i+1).getBytes(StandardCharsets.ISO_8859_1);
			byte[] test = encrypt(temp);
			if(test[0] == 0) {
				temp = new byte[test.length - 1];
				System.arraycopy(test, 1, temp, 0, test.length - 1);
				test = temp;
			}
			if(test.length < bitLength/8) {
				test = getPadding(test, bitLength/8 - test.length);
			}
			testBuffer = new String(test, StandardCharsets.ISO_8859_1);
			outputString += testBuffer;
			
			if(testBuffer.length() != 128) System.out.println(testBuffer.length() + "   " + test[0]);
		}
		return outputString;
	}
	public String decryptString(String input) {
		String outputString = "";
		int byteLength = bitLength/8;
		for(int i = 0; i < input.length()/byteLength; i++) {
			
			byte[] temp = input.substring(byteLength*i, byteLength*(i+1)).getBytes(StandardCharsets.ISO_8859_1);
			outputString += new String(decryptToBytes(temp),StandardCharsets.ISO_8859_1);
		}
		
		return outputString;
	}
	public byte[] encrypt(int input) {
		BigInteger b = BigInteger.valueOf(input);
		b = b.modPow(e, N);
		return b.toByteArray();
	}
	public byte[] encrypt(byte[] input) {
		BigInteger b = new BigInteger(1, input);
		b = b.modPow(e, N);
		return b.toByteArray();
	}
	public int decrypt(BigInteger input) {
		int output = input.modPow(d, N).intValue();
		return output;
	}
	public int decrypt(byte[] input) {
		BigInteger b = new BigInteger(1, input);
		b = b.modPow(d, N);
		return b.intValue();
	}
	public byte[] decryptToBytes(byte[] input) {
		BigInteger b = new BigInteger(1, input);
		b = b.modPow(d, N);
		return b.toByteArray();
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
	private byte[] getPadding(byte[] input, int padding) {
		byte[] temp = new byte[input.length + padding];
		System.arraycopy(input, 0, temp, padding, temp.length - padding);
		return temp;
	}
}



