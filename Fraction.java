
//Authored by Eric McCord-Snook

import java.math.BigInteger;

public class Fraction {
	private int numerator;
	private int denominator;

	public Fraction() {
		this.numerator = 1;
		this.denominator = 1;
	}

	public Fraction(int n) {
		this.numerator = n;
		this.denominator = 1;
	}

	public Fraction(int n, int d) {
		if (d == 0) {
			throw new IllegalArgumentException("Fraction Error: Denominator Set to 0");
		}
		this.numerator = n;
		this.denominator = d;
		simplify();
	}

	public Fraction(String s) {
		if (s.equals("+") || s.equals("")) {
			this.numerator = 1;
			this.denominator = 1;
		} else if (s.equals("-")) {
			this.numerator = -1;
			this.denominator = 1;
		} else if (s.contains("/")) {
			String[] numDen = s.split("/");
			this.numerator = Integer.parseInt(numDen[0]);
			this.denominator = Integer.parseInt(numDen[1]);
		} else {
			this.numerator = Integer.parseInt(s);
			this.denominator = 1;
		}
		simplify();
	}

	public void simplify() {
		if (this.numerator < 0 ^ this.denominator < 0) {
			this.numerator = -1 * Math.abs(this.numerator);
			this.denominator = Math.abs(this.denominator);
		} else {
			this.numerator = Math.abs(this.numerator);
			this.denominator = Math.abs(this.denominator);
		}
		int gcd = gcd(this.numerator, this.denominator);
		this.numerator = this.numerator / gcd;
		this.denominator = this.denominator / gcd;
	}

	public static int gcd(int n, int d) {
		BigInteger b1 = BigInteger.valueOf(n);
		BigInteger b2 = BigInteger.valueOf(d);
		BigInteger gcd = b1.gcd(b2);
		return gcd.intValue();
	}

	public static Fraction add(Fraction f1, Fraction f2) {
		return new Fraction(f1.numerator * f2.denominator + f2.numerator * f1.denominator,
				f1.denominator * f2.denominator);
	}

	public static Fraction subtract(Fraction f1, Fraction f2) {
		return add(f1, new Fraction(-1 * f2.numerator, f2.denominator));
	}

	public static Fraction multiply(Fraction f1, Fraction f2) {
		return new Fraction(f1.numerator * f2.numerator, f1.denominator * f2.denominator);
	}

	public static Fraction divide(Fraction f1, Fraction f2) {
		return new Fraction(f1.numerator * f2.denominator, f1.denominator * f2.numerator);
	}

	public static Fraction reciprocal(Fraction f) {
		return new Fraction(f.denominator, f.numerator);
	}

	public static Fraction negate(Fraction f) {
		int negNum = -1 * f.numerator;
		return new Fraction(negNum, f.denominator);
	}

	public String toString() {
		if (this.denominator == 1) {
			return this.numerator + "";
		} else {
			return this.numerator + "/" + this.denominator;
		}
	}

	public boolean equals(Object o) {
		if (o instanceof Fraction) {
			if (this.toDouble() == ((Fraction) o).toDouble()) {
				return true;
			}
		}
		return false;
	}

	public double toDouble() {
		double nDoub = (double) this.numerator;
		double dDoub = (double) this.denominator;
		return nDoub / dDoub;

	}

	public static Fraction[] toFractionArray(Fraction[] arr) {
		return arr;
	}

	public static Fraction[] toFractionArray(int[] arr) {
		Fraction[] ret = new Fraction[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = new Fraction(arr[i]);
		}
		return ret;
	}

	public static Fraction[] toFractionArray(String[] arr) {
		Fraction[] ret = new Fraction[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = new Fraction(arr[i]);
		}
		return ret;
	}

	public static void main(String[] args) {
		System.out.println("Fraction.java is running properly.");
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
}
