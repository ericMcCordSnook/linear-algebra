
/**Authored by Eric McCord-Snook for use in solving systems of linear equations of any dimension, this driver file runs the whole project**/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SystemSolver {

	public static void exit(Scanner console) {
		System.out.println("Press enter to exit...");
		console.nextLine();
		System.exit(0);
	}

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.println("Welcome to the Linear System of Equations Solver.");
		System.out.println(
				"Enter \"file\" to read system(s) from a file, or \"console\" to read system from the console. (Enter \"exit\" to exit)");
		String inputType = console.nextLine();
		while (!inputType.equalsIgnoreCase("file") && !inputType.equalsIgnoreCase("console")
				&& !inputType.equalsIgnoreCase("exit")) {
			System.out.println(
					"Invalid input type, please choose between \"file\" and \"console\". (Enter \"exit\" to exit)");
			inputType = console.nextLine();
		}
		switch (inputType) {
		case "exit":
			exit(console);
			break;
		case "console":
			System.out.println("Enter number of variables/equations.");
			LinearSystem lsC = new LinearSystem(Integer.parseInt(console.nextLine()));
			lsC.readEquationsFromConsole(console);
			lsC.solveSystem();
			System.out.println(lsC);
			System.out.println();
			break;
		case "file":
			String fileName = "";
			System.out.println("Would you like to update the systems.txt file before proceeding?"
					+ "\nType 'y' if so, and systems.txt will be used by default."
					+ "\nType 'n' if not, and systems.txt will be used by default."
					+ "\nType 'o' if you would like to use an alternative file (must use same format as systems.txt).");
			String yno = console.nextLine();
			while (!yno.equals("y") && !yno.equals("n") && !yno.equals("o")) {
				System.out.println("Please enter either 'y', 'n', or 'o'.");
				yno = console.nextLine();
			}
			if (yno.equals("y")) {
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "systems.txt");
				try {
					pb.start();
					fileName = "systems.txt";
					System.out.println("Press enter when changes to systems.txt have been changed...");
					console.nextLine();
				} catch (IOException e1) {
					System.out.println("Error: systems.txt not found");
					exit(console);
				}
			} else if (yno.equals("n")) {
				fileName = "systems.txt";
			}
			Scanner inFile = null;
			try {
				if (fileName.equals("")) {
					System.out.println("Enter filename.");
					fileName = console.nextLine();
				}
				File inputFile = new File(fileName);
				inFile = new Scanner(inputFile);
				System.out.println();
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
				exit(console);
			}
			int numSystems = Integer.parseInt(inFile.nextLine().split(":")[1].trim());
			for (int systemNumber = 0; systemNumber < numSystems; systemNumber++) {
				LinearSystem lsF = new LinearSystem(Integer.parseInt(inFile.nextLine().split(":")[1].trim()));
				lsF.readEquationsFromFile(inFile);
				lsF.solveSystem();
				System.out.println(lsF);
				System.out.println();
			}
			break;
		default:
			System.exit(0);
			break;
		}
		exit(console);
	}
}
