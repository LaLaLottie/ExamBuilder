/* CS 342: Project 4: Developing Applications Using Exam-Related Classes
 * Name: Funmilola Akintoye
 * NetID: fakint3
 */

import java.util.*;

public class ScannerFactory {
	private static Scanner keyboardScanner;
	
	public static Scanner getKeyboardScanner() {
		if (keyboardScanner == null) { // Checks if a scanner has already been created to ensure there's only one instance.
			keyboardScanner = new Scanner(System.in);
		}
		
		return keyboardScanner;
	}
	
	public static void closeKeyboardScanner() { // Added new method to close the scanner at end of program execution.
		keyboardScanner.close();
	}
}
