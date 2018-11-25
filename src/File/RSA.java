package File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Creates keys for RSA Encryption and provides methods to encrypt and decrypt different types of values
 * 
 * @author GastonMagnin
 *
 */
public class RSA {
	private BigInteger e;
	private BigInteger d;
	private BigInteger N;
	private BigInteger pN;
	private int bitLength;
	public RSA(int bitLength) {
		do {
		this.bitLength = bitLength;
		getKeys(bitLength);
		//Generate two random test numbers and run them through encryption and decryption
		}while(!(test((int)(Math.random()*Character.MAX_VALUE)) && test(((int)(Math.random() * Character.MAX_VALUE)))));
	}
	/**
	 * Generates the keys for RSA encryption with bit length {@link #bitLength}
	 * @param bitLength the required bitLength for N, this is also the length that anything encrypted with this will have
	 */
	private void getKeys(int bitLength) {
		Random r = new Random();
		BigInteger p = new BigInteger(((int)(0.5 * bitLength)), 100, r);
		BigInteger q = new BigInteger((int)(0.5*bitLength), 100, r);
		N = p.multiply(q);
		pN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		BigInteger[] temp;
		do {
			do {
				e = new BigInteger(pN.bitLength() - 1, r);
			}while(e.equals(BigInteger.ZERO));
			temp = gcdExtended(pN, e);
			d = temp[1];
		}while(!temp[0].equals(BigInteger.ONE));
		
	}
	/**
	 * Calculates the greatest common divisor(using the euclidian algorithm) and the extended euclidian algorithm for the two inputs 
	 * @param inputA BigInteger != inputB 
	 * @param inputB BigInteger != inputA
	 * @return BigInteger array that contains [0] the greatest common divisor of inputA and inputB
	 * and [1] the result of the extended euclidean algorithm for inputA and inputB
	 */
	
	private BigInteger[] gcdExtended(BigInteger inputA, BigInteger inputB) {
		// If they are the same they are the biggest shared divisor of both
		if(inputA == inputB) return null;
		//a has to be the bigger input because otherwise mod would just return a
		BigInteger minusOne = new BigInteger("-1");
		BigInteger a = (inputA.compareTo(inputB) == 1) ? inputA : inputB;
		BigInteger b = (inputA.equals(a)) ? inputB : inputA;
		BigInteger c;
		BigInteger beta = minusOne.multiply(a.divide(b));//Integer division without remainder
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
		BigInteger[] output = new BigInteger[] {b, beta};
		return output;
	}
	
	/**
	 * Splits input into pieces of max (bitLength/8 - 1)encrypts them and puts them back together
	 * splitting the string is necessary to make sure that no data gets lost in the process
	 * @param input String to encrypt
	 * @return	the encrypted String 
	 * @see #encrypt(byte[]) encrypt
	 */
	public String encryptString(String input) {
		String outputString = "", testBuffer;
		int byteLength = bitLength/8;

		for(int i= 0; i< input.length(); i+= byteLength - 1) {
			byte[] temp;
			//Cut string into pieces  that can be en/decrypted without any data loss and convert to byte array
			if(input.length() - i > byteLength) {
				temp = input.substring(i, i+byteLength - 1).getBytes(StandardCharsets.ISO_8859_1);
			}else {
				temp = input.substring(i, input.length()).getBytes(StandardCharsets.ISO_8859_1);
			}
			//encrypt the byte array
			byte[] encrypted = encrypt(temp);
			//adjust array length
			if(encrypted[0] == 0) {
				temp = new byte[encrypted.length - 1];
				System.arraycopy(encrypted, 1, temp, 0, encrypted.length - 1);
				encrypted = temp;
			}
			if(encrypted.length < bitLength/8) {
				encrypted = getPadding(encrypted, bitLength/8 - encrypted.length);
			}
			// convert the encrypted byte array to String and add it to output
			outputString += new String(encrypted, StandardCharsets.ISO_8859_1);;
		}
		return outputString;
	}
	/**
	 * Splits a encrypted String into decryptable pieces(length = bitLength of encryption / 8) +
	 * decrypts them and puts the decrypted pieces back together
	 * @param input a string that has been decrypted with the same keys this RSA object uses
	 * @return a decrypted version of input
	 * @see #decrypt(byte[]) decrypt
	 */
	public String decryptString(String input) {
		String outputString = "";
		int byteLength = bitLength/8;
		for(int i = 0; i < input.length()/byteLength; i++) {
			
			byte[] temp = input.substring(byteLength*i, byteLength*(i+1)).getBytes(StandardCharsets.ISO_8859_1);
			outputString += new String(decryptToBytes(temp),StandardCharsets.ISO_8859_1);
		}
		
		return outputString;
	}
	/**
	 * Encrypts the input
	 * @param input integer to encrypt
	 * @return a byte array of length bitLength / 8 that contains a encrypted version of input
	 * @see #encrypt(byte[]) encrypt(byte[])
	 */
	public byte[] encrypt(int input) {
		BigInteger b = BigInteger.valueOf(input);
		b = b.modPow(e, N);
		return b.toByteArray();
	}
	/**
	 * Encrypts the input
	 * @param input byte[] to encrypt
	 * @return a byte array of length bitLength / 8 that contains a encrypted version of input
	 * @see #encrypt(int) encrypt(int)
	 */
	public byte[] encrypt(byte[] input) {
		BigInteger b = new BigInteger(1, input);
		b = b.modPow(e, N);
		return b.toByteArray();
	}
	/**
	 * Decrypts the input
	 * @param input BigInteger that has been encrypted with the same keys this RSA Object uses
	 * @return decrypted integer
	 * @see #decrypt(byte[]) decrypt(byte[]) 
	 * @see #decryptToBytes(byte[]) 
	 */
	public int decrypt(BigInteger input) {
		int output = input.modPow(d, N).intValue();
		return output;
	}
	/**
	 * Decrypts the input
	 * @param input byte array that has been encrypted with the same keys this RSA Object uses
	 * @return decrypted integer
	 * @see #decrypt(BigInteger) 
	 * @see #decryptToBytes(byte[]) 
	 */
	public int decrypt(byte[] input) {
		BigInteger b = new BigInteger(1, input);
		b = b.modPow(d, N);
		return b.intValue();
	}
	/**
	 * Decrypts the input
	 * @param input BigInteger that has been encrypted with the same keys this RSA Object uses
	 * @return decrypted byte array
	 * @see #decrypt(byte[])  
	 * @see #decrypt(BigInteger) 
	 */
	public byte[] decryptToBytes(byte[] input) {
		BigInteger b = new BigInteger(1, input);
		b = b.modPow(d, N);
		return b.toByteArray();
	}
	

	/**
	 * Test if the en/decryption keys work to avoid errors like BigInteger.probablePrime not returning a prime	
	 * @param input integer with which en/decryption will be tested
	 * @return
	 */
	private boolean test(int input) {
		BigInteger b = BigInteger.valueOf(input);
		b = b.modPow(e, N);
		b = b.modPow(d, N);		
		int output = b.intValue();
		if(output == input) {
			return true;
		}else return false;
	}
	/**
	 * Add padding to a byte array
	 * @param input byte array that needs padding
	 * @param padding amount of padding
	 * @return padded byte array
	 */
	private byte[] getPadding(byte[] input, int padding) {
		byte[] temp = new byte[input.length + padding];
		System.arraycopy(input, 0, temp, padding, temp.length - padding);
		return temp;
	}
}



 