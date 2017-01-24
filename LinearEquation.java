import java.util.Arrays;

/**
 * Authored by Eric McCord-Snook for use in linear algebra for parsing through
 * strings meant to represent linear equations in several variables
 **/
public class LinearEquation {

	private Fraction[] coefficientsAndConstant;
	private String eqnString = "";

	public LinearEquation() {
		this("x+y=0");
	}

	public LinearEquation(String strEq) {
		eqnString = strEq.replaceAll("\\s","");
		String[] varsVsConstant = eqnString.split("=");
		String[] monomialStrings = varsVsConstant[0].split("[+-]");
		String operators = varsVsConstant[0].replaceAll("[a-zA-Z0-9]*","");
		for (int mon = 0; mon < operators.length(); mon++) {
			monomialStrings[mon] = operators.charAt(mon) + monomialStrings[mon];
		}
		Monomial[] monomials = new Monomial[monomialStrings.length];
		for (int i = 0; i < monomials.length; i++) {
			monomials[i] = new Monomial(monomialStrings[i]);
		}
		Arrays.sort(monomials);
		coefficientsAndConstant = new Fraction[monomials.length + 1];
		for(int i = 0; i < monomials.length; i++) {
			coefficientsAndConstant[i] = monomials[i].getCoefficient();
		}
		coefficientsAndConstant[monomials.length] = new Fraction(varsVsConstant[1]);
	}
	
//	public LinearEquation(String strEq) {
//		eqnString = strEq.replaceAll("\\s", "");
//		String[] varsVsConstant = eqnString.split("=");
//		String[] coeffStrings = varsVsConstant[0].split("[a-z]");
//		
//		coefficientsAndConstant = new Fraction[coeffStrings.length + 1];
//		for (int i = 0; i < coeffStrings.length; i++) {
//			coefficientsAndConstant[i] = new Fraction(coeffStrings[i]);
//		}
//		coefficientsAndConstant[coeffStrings.length] = new Fraction(varsVsConstant[1]);
//	}

	public String toString() {
		return eqnString;
	}

	public static void main(String[] args) {
		System.out.println("LinearEquation.java is running properly.");
	}

	public Fraction[] getCoefficientsAndConstant() {
		return this.coefficientsAndConstant;
	}

	public void setCoefficientsAndConstant(Fraction[] c) {
		this.coefficientsAndConstant = c;
	}
}
