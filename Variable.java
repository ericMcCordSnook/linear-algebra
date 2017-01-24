/**Authored by Eric McCord-Snook to handle variables and their powers within monomials in a multivariable expression**/
import java.lang.Comparable;
public class Variable implements Comparable<Variable>{
	
	//fields
	private String id;
	private Fraction power;
	
	//constructors
	public Variable() {
		this.id = "x";
		this.power = new Fraction(1);
	}
	
	public Variable(String s) {
		if(s.contains("^")) {
			this.id = s.substring(0, s.indexOf('^'));
			this.power = new Fraction(s.substring(s.indexOf('^') + 1));
		} else {
			this.id = s;
			this.power = new Fraction(1);
		}
	}
	
	public Variable(String s, int p) {
		this.id = s;
		this.power = new Fraction(p);
	}
	
	public Variable(String s, Fraction p) {
		this.id = s;
		this.power = p;
	}
	
	public Variable(Variable v) {
		this.id = v.getId();
		this.power = v.getPower();
	}
	
	//compareTo method
	@Override
	public int compareTo(Variable v2) {
		return this.id.compareTo(v2.id);
	}
	
	//toString method
	public String toString() {
		if (this.power.equals(new Fraction(1))) {
			return this.id;
		}
		return id + "^" + power;
	}
	
	//equals method
	public boolean equals(Object o) {
		if (o instanceof Variable) {
			if(((Variable) o).getId().equals(this.id) && ((Variable) o).getPower().equals(this.power)) {
				return true;
			} 
		}
		return false;
	}
	
	//getters and setters
	public String getId() {
		return this.id;
	}
	
	public void setId(String s) {
		this.id = s;
	}
	
	public Fraction getPower() {
		return this.power;
	}
	
	public void setPower(Fraction p) {
		this.power = p;
	}
}
