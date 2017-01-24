/**
 * Authored by Eric McCord-Snook for use in linear algebra settings or whenever
 * matrix manipulations are necessary or useful
 **/

public class Matrix {

	private int numRows;
	private int numCols;
	private Fraction[][] matrixVals;

	public Matrix() {
		this.numRows = 3;
		this.numCols = 3;
		this.setMatrixVals(new Fraction[this.numRows][this.numCols]);
	}

	public Matrix(int r, int c) {
		this.numRows = r;
		this.numCols = c;
		this.setMatrixVals(new Fraction[r][c]);
	}

	public Matrix(int[] vals) {
		this(Fraction.toFractionArray(vals));
	}

	public Matrix(Fraction[] vals) {
		this.numRows = 1;
		this.numCols = vals.length;
		this.setMatrixVals(new Fraction[][] { vals });
	}

	public Matrix(int[][] matVals) {
		this.numRows = matVals.length;
		this.numCols = matVals[0].length;
		Fraction[][] newVals = new Fraction[matVals.length][matVals[0].length];
		for (int r = 0; r < matVals.length; r++) {
			newVals[r] = Fraction.toFractionArray(matVals[r]);
		}
		this.setMatrixVals(newVals);
	}

	public Matrix(Fraction[][] matVals) {
		this.numRows = matVals.length;
		this.numCols = matVals[0].length;
		this.setMatrixVals(matVals);
	}

	public Matrix(Matrix m) {
		this.numRows = m.getNumRows();
		this.numCols = m.getNumCols();
		this.setMatrixVals(m.getMatrixVals());
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < this.getNumRows(); i++) {
			ret += "|\t";
			for (int j = 0; j < this.getNumCols(); j++) {
				ret += this.getMatrixVals()[i][j] + "\t";
			}
			ret += "|\n";
		}
		return ret;
	}

	public static Matrix matrixAdd(Matrix m1, Matrix m2) {
		if (m1.getNumRows() != m2.getNumRows() || m1.getNumCols() != m2.getNumCols()) {
			System.out.println("Addition/Subtraction Failure: Dimension Mismatch");
			return null;
		} else {
			Fraction[][] newVals = new Fraction[m1.getNumRows()][m1.getNumCols()];
			for (int i = 0; i < m1.getNumRows(); i++) {
				for (int j = 0; j < m1.getNumCols(); j++) {
					newVals[i][j] = Fraction.add(m1.getMatrixVals()[i][j], m2.getMatrixVals()[i][j]);
				}
			}
			return new Matrix(newVals);
		}
	}

	public static Matrix scalarMult(Matrix m, Fraction a) {
		Fraction[][] newVals = new Fraction[m.getNumRows()][m.getNumCols()];
		for (int i = 0; i < m.getNumRows(); i++) {
			for (int j = 0; j < m.getNumCols(); j++) {
				newVals[i][j] = Fraction.multiply(a, m.getMatrixVals()[i][j]);
			}
		}
		return new Matrix(newVals);
	}

	public static Matrix matrixSub(Matrix m1, Matrix m2) {
		return matrixAdd(m1, scalarMult(m2, new Fraction(-1)));
	}

	public static Matrix matrixMult(Matrix m1, Matrix m2) {
		if (m1.getNumCols() != m2.getNumRows()) {
			System.out.println("Multiplication Failure: Dimension Mismatch");
			return null;
		} else {
			Fraction[][] newVals = new Fraction[m1.getNumRows()][m2.getNumCols()];
			for (int r = 0; r < m1.getNumRows(); r++) {
				for (int c = 0; c < m2.getNumCols(); c++) {
					newVals[r][c] = dot(m1.getRow(r), m2.getColumn(c));
				}
			}
			return new Matrix(newVals);
		}
	}

	public static Fraction dot(Matrix m1, Matrix m2) {
		if (m1.getNumRows() != m2.getNumRows() || m1.getNumCols() != m2.getNumCols()) {
			System.out.println("Dot Product Failure: Dimension Mismatch");
			return new Fraction(0);
		} else {
			Fraction ret = new Fraction(0);
			for (int r = 0; r < m1.getNumRows(); r++) {
				for (int c = 0; c < m1.getNumCols(); c++) {
					ret = Fraction.add(ret, Fraction.multiply(m1.getMatrixVals()[r][c], m2.getMatrixVals()[r][c]));
				}
			}
			return ret;
		}
	}

	public static Fraction dot(Fraction[] a, Fraction[] b) {
		if (a.length != b.length) {
			System.out.println("Dot Product Failure: Dimension Mismatch");
			return new Fraction(0);
		} else {
			Fraction ret = new Fraction(0);
			for (int i = 0; i < a.length; i++) {
				ret = Fraction.add(ret, Fraction.multiply(a[i], b[i]));
			}
			return ret;
		}
	}

	public static Fraction trace(Matrix m) {
		if (m.getNumRows() != m.getNumCols()) {
			System.out.println("Trace Failure: Non-Square Matrix");
			return new Fraction(0);
		} else {
			Fraction sum = new Fraction(0);
			for (int rc = 0; rc < m.getNumRows(); rc++) {
				sum = Fraction.add(sum, m.getMatrixVals()[rc][rc]);
			}
			return sum;
		}
	}

	public static Matrix transpose(Matrix m) {
		Fraction[][] newVals = new Fraction[m.getNumCols()][m.getNumRows()];
		for (int i = 0; i < m.getNumCols(); i++) {
			newVals[i] = m.getColumn(i);
		}
		return new Matrix(newVals);
	}

	public static Matrix inverse(Matrix m) {
		if (m.getNumRows() != m.getNumCols()) {
			System.out.println("Inverse Matrix Failure: Non-Square Matrix");
			return null;
		} else if (det(m) == new Fraction(0)) {
			System.out.println("Inverse Matrix Failure: Singular Matrix");
			return null;
		} else {
			Matrix augmentedM = new Matrix(m);
			for (int i = 0; i < m.getNumRows(); i++) {
				int[] idCol = new int[m.getNumRows()];
				idCol[i] = 1;
				augmentedM = addColumn(augmentedM, Fraction.toFractionArray(idCol), augmentedM.getNumCols());
			}
			Matrix inv = gaussianElimination(augmentedM, m.getNumRows());
			while (inv.getNumRows() != inv.getNumCols()) {
				inv = removeColumn(inv, 0);
			}
			return inv;
		}
	}

	public static Matrix gaussianElimination(Matrix m, int numPivots) {
		Matrix ret = new Matrix(m);
		for (int var = 0; var < numPivots; var++) {
			ret = scaleRow(ret, var, Fraction.reciprocal(ret.getMatrixVals()[var][var]));
			for (int r = 0; r < ret.numRows; r++) {
				if (r != var) {
					ret = replaceRow(ret, r, new Fraction(1), var, Fraction.negate(ret.getMatrixVals()[r][var]));
				}
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		System.out.println("Matrix.java is running properly.");
	}

	public static Fraction det(Matrix m) {
		if (m.getNumRows() != m.getNumCols()) {
			System.out.println("Determinant Failure: Non-Square Matrix");
			return new Fraction(0);
		} else {
			Fraction[][] matVals = m.getMatrixVals();
			if (m.getNumRows() == 2) {
				return Fraction.subtract(Fraction.multiply(matVals[0][0], matVals[1][1]),
						Fraction.multiply(matVals[0][1], matVals[1][0]));
			}
			Fraction ret = new Fraction(0);
			for (int c = 0; c < m.getNumCols(); c++) {
				Matrix curMinor = removeRow(removeColumn(m, c), 0);
				if (c % 2 == 0) {
					ret = Fraction.add(ret, Fraction.multiply(matVals[0][c], det(curMinor)));
				} else {
					ret = Fraction.subtract(ret, Fraction.multiply(matVals[0][c], det(curMinor)));
				}
			}
			return ret;
		}
	}

	public static Matrix removeRow(Matrix m, int r) {
		Fraction[][] newVals = new Fraction[m.getNumRows() - 1][m.getNumCols()];
		boolean remFlag = false;
		for (int i = 0; i < m.getNumRows(); i++) {
			if (i == r) {
				remFlag = true;
			} else {
				if (remFlag) {
					newVals[i - 1] = m.getMatrixVals()[i];
				} else {
					newVals[i] = m.getMatrixVals()[i];
				}
			}
		}
		return new Matrix(newVals);
	}

	public static Matrix removeColumn(Matrix m, int c) {
		Fraction[][] newVals = new Fraction[m.getNumRows()][m.getNumCols() - 1];
		for (int r = 0; r < m.getNumRows(); r++) {
			boolean remFlag = false;
			for (int i = 0; i < m.getNumCols(); i++) {
				if (i == c) {
					remFlag = true;
				} else {
					if (remFlag) {
						newVals[r][i - 1] = m.getMatrixVals()[r][i];
					} else {
						newVals[r][i] = m.getMatrixVals()[r][i];
					}
				}
			}
		}
		return new Matrix(newVals);
	}

	public static Matrix swapRows(Matrix m, int r1, int r2) {
		Fraction[][] newVals = m.getMatrixVals();
		Fraction[] temp = newVals[r1];
		newVals[r1] = newVals[r2];
		newVals[r2] = temp;
		return new Matrix(newVals);
	}

	public static Matrix replaceRow(Matrix m, int rtr, Fraction rtrFactor, int ass, Fraction assFactor) {
		Matrix rowToReplace = new Matrix(m.getMatrixVals()[rtr]);
		Matrix assassinRow = new Matrix(m.getMatrixVals()[ass]);
		Matrix replacementRowMatrix = matrixAdd(scalarMult(rowToReplace, rtrFactor),
				scalarMult(assassinRow, assFactor));
		Fraction[] replacementRow = replacementRowMatrix.getMatrixVals()[0];
		Fraction[][] newVals = m.getMatrixVals();
		newVals[rtr] = replacementRow;
		return new Matrix(newVals);
	}

	public static Fraction[] scaleRow(Fraction[] row, Fraction factor) {
		for (int c = 0; c < row.length; c++) {
			row[c] = Fraction.multiply(row[c], factor);
		}
		return row;
	}

	public static Matrix scaleRow(Matrix m, int row, Fraction factor) {
		Fraction[] rowToScale = m.getMatrixVals()[row];
		Fraction[] scaledRow = scaleRow(rowToScale, factor);
		Matrix remOldRow = removeRow(m, row);
		Matrix ret = addRow(remOldRow, scaledRow, row);
		return ret;
	}

	public static Matrix addRow(Matrix m, Fraction[] row, int r) {
		if (row.length != m.getNumCols()) {
			System.out.println("Error Adding Row to Matrix: Mismatching Dimensions");
			return null;
		}
		Fraction[][] newVals = new Fraction[m.getNumRows() + 1][m.getNumCols()];
		boolean addFlag = false;
		for (int curRow = 0; curRow < newVals.length; curRow++) {
			if (curRow == r) {
				addFlag = true;
				newVals[curRow] = row;
			} else {
				if (addFlag) {
					newVals[curRow] = m.getMatrixVals()[curRow - 1];
				} else {
					newVals[curRow] = m.getMatrixVals()[curRow];
				}
			}
		}
		return new Matrix(newVals);
	}

	public static Matrix addColumn(Matrix m, Fraction[] col, int c) {
		if (col.length != m.getNumRows()) {
			System.out.println("Error Adding Column to Matrix: Mismatching Dimensions");
			return null;
		}
		Fraction[][] newVals = new Fraction[m.getNumRows()][m.getNumCols() + 1];
		for (int r = 0; r < newVals.length; r++) {
			boolean addFlag = false;
			for (int curCol = 0; curCol < newVals[0].length; curCol++) {
				if (curCol == c) {
					addFlag = true;
					newVals[r][c] = col[r];
				} else {
					if (addFlag) {
						newVals[r][curCol] = m.getMatrixVals()[r][curCol - 1];
					} else {
						newVals[r][curCol] = m.getMatrixVals()[r][curCol];
					}
				}
			}
		}
		return new Matrix(newVals);
	}

	public Fraction[] getRow(int r) {
		return this.getMatrixVals()[r];
	}

	public Fraction[] getColumn(int c) {
		Fraction[] colVals = new Fraction[this.getNumRows()];
		for (int r = 0; r < this.getNumRows(); r++) {
			colVals[r] = this.getMatrixVals()[r][c];
		}
		return colVals;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public Fraction[][] getMatrixVals() {
		return matrixVals;
	}

	public void setMatrixVals(Fraction[][] matVals) {
		this.matrixVals = matVals;
	}

}