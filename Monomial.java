import java.lang.Comparable;
import java.util.Arrays;

/**
 * Authored by Eric McCord-Snook to identify monomials in a multivariable
 * equation
 **/
public class Monomial implements Comparable<Monomial>{

	// fields
	private Fraction coefficient;
	private Variable[] variables;

	// constructors
	public Monomial() {
		this.coefficient = new Fraction(1);
		this.variables = new Variable[1];
	}

	public Monomial(Fraction c, Variable[] v) {
		this.coefficient = c;
		this.variables = v;
		sortVariables();
	}

	public Monomial(String s) {
		String pureString = s.replaceAll("\\s", "");
		pureString.replace("+", "");
		String[] components = pureString.split("[*](?=[a-zA-Z])");
		boolean hasCoeff = true;
		if(components[0].matches(".*[a-zA-Z].*")) {
			hasCoeff = false;
			if(components[0].startsWith("-")) {
				this.coefficient = new Fraction(-1);
			} else {
				this.coefficient = new Fraction(1);
			}
		} else {
			this.coefficient = new Fraction(components[0]);
		}
		for(int i = 0; i < components.length; i++) {
			if(i == 0 && hasCoeff) {
				variables = new Variable[components.length - 1];
				continue;
			} else if (i == 0){
				variables = new Variable[components.length];
			}
			if(hasCoeff) {
				variables[i - 1] = new Variable(components[i]);
			} else {
				variables[i] = new Variable(components[i]);
			}
		}
		sortVariables();
	}
	
	public void sortVariables() {
		Arrays.sort(this.variables);
	}
	
	//compare method
	@Override
	public int compareTo(Monomial m2) {
		return this.variables[0].compareTo(m2.variables[0]);
	}

	public String toString() {
		String ret = coefficient.toString();
		for (int i = 0; i < variables.length; i++) {
			ret += variables[i].toString();
		}
		return ret;
	}
	
	public static void main(String[] args) {
		Monomial m = new Monomial("3*x^2*y");
		System.out.println(m);
	}
	
	public Fraction getCoefficient() {
		return this.coefficient;
	}
}
