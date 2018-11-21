package File;
import java.math.BigInteger;
import java.util.Random;

public class RSA {
	private BigInteger e;
	private BigInteger d;
	private BigInteger N;
	private BigInteger pN;
	public RSA(int bitLength) {
		do {
		getKeys(bitLength);
		//Generate two random test numbers and run them through encryption and decryption
		}while(!(test((int)(Math.random()*Character.MAX_VALUE)) && test(((int)(Math.random() * Character.MAX_VALUE)))));
	}
	
	public static void main(String[] args) {
		long a = (int) 'a';
		RSA b = new RSA(1024);
		System.out.println("asds");
		System.out.println((char)b.decrypt(b.encrypt(a)));
	}
	private void getKeys(double bitLength) {
		Random r = new Random();
		BigInteger p = BigInteger.probablePrime((int)Math.sqrt(bitLength), r);
		BigInteger q = BigInteger.probablePrime((int)Math.sqrt(bitLength), r);
		N = p.multiply(q);
		pN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		do {
		e = new BigInteger(pN.bitLength() - 1, r);
		}while(!ggT(pN, e).equals(BigInteger.ONE));
		
	}
	public BigInteger ggT(BigInteger inputA, BigInteger inputB) {
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
			System.out.println(currentCarryBeta);
			System.out.println("a: " + a +" b: " + b);
			System.out.println("beta: " + beta);
		}
		d = beta;
		return b;
	}
	public long encrypt(long input) {
		BigInteger b = BigInteger.valueOf(input);
		long output = b.modPow(e, N).longValue();
		return output;
	}
	public long decrypt(long input) {
		BigInteger b = BigInteger.valueOf(input);
		long output = b.modPow(d, N).longValue();
		return output;
	}
	private boolean test(int input) {
		BigInteger b = BigInteger.valueOf(input);
		b = b.modPow(e, N);
		int output = b.modPow(d, N).intValue();
		System.out.println(output+ "output");
		if(output == input) {
			return true;
		}else return false;
	}
	
}



