
/**
 * Authored by Eric McCord-Snook for use in solving systems of linear equations
 **/

import java.util.TreeMap;
import java.util.Scanner;

public class LinearSystem {

	LinearEquation[] equations;
	int numEquations;
	Matrix equationCoefficients;
	TreeMap<Variable, Fraction> solutions;
	Fraction[] solution;
	boolean solutionExists;

	public LinearSystem(int numEquationsOrVars) {
		equations = new LinearEquation[numEquationsOrVars];
		numEquations = this.equations.length;
		solution = new Fraction[numEquationsOrVars];
		solutions = new TreeMap<Variable, Fraction>();
		solutionExists = true;
	}

	public String toString() {
		String ret = "System of equations:\n";
		for (LinearEquation eq : this.equations) {
			ret += eq.toString() + "\n";
		}
		if (solutionExists) {
			ret += "Solution: {";
			for (Fraction f : solution) {
				ret += f + ", ";
			}
			ret = ret.substring(0, ret.length() - 2) + "}";
			return ret;
		} else {
			ret += "This system has no unique solution.";
			return ret;
		}
	}

	public void readEquationsFromFile(Scanner inFile) {
		Fraction[][] matVals = new Fraction[this.numEquations][this.numEquations + 1];
		for (int equationNumber = 0; equationNumber < this.numEquations; equationNumber++) {
			LinearEquation eq = new LinearEquation(inFile.nextLine());
			this.equations[equationNumber] = eq;
			matVals[equationNumber] = eq.getCoefficientsAndConstant();
		}
		equationCoefficients = new Matrix(matVals);
	}

	public void readEquationsFromConsole(Scanner console) {
		Fraction[][] matVals = new Fraction[this.equations.length][this.numEquations + 1];
		for (int equationNumber = 0; equationNumber < this.numEquations; equationNumber++) {
			System.out.println("Enter equation " + (equationNumber + 1) + ":");
			LinearEquation eq = new LinearEquation(console.nextLine());
			this.equations[equationNumber] = eq;
			matVals[equationNumber] = eq.getCoefficientsAndConstant();
		}
		equationCoefficients = new Matrix(matVals);
	}

	public void solveSystem() {
		if (Matrix.det(this.equationCoefficients).equals(new Fraction(0))) {
			this.solutionExists = false;
			return;
		}
		Matrix solutionMatrix = Matrix.gaussianElimination(this.equationCoefficients,
				this.equationCoefficients.getNumRows());
		this.solution = solutionMatrix.getColumn(solutionMatrix.getNumCols() - 1);
	}

	public int getNumEquations() {
		return this.numEquations;
	}

	public Matrix getEquationCoefficients() {
		System.out.println("eqcofs");
		return this.equationCoefficients;
	}

	public static void main(String[] args) {
		System.out.println("LinearSystem.java is running properly.");
	}
}
