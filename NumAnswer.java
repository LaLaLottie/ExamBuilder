/* CS 342: Project 5: GUI
 * Name: Charlotte Norman
 * NetID: cnorma4
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

public class NumAnswer extends Answer {
	protected double answer; // Numerical answer.
	protected String skip;
	
	public NumAnswer(double answer) {
		this.answer = answer;
	}
  
	public NumAnswer(String answer){
		this.skip = answer;
  }
  
	public NumAnswer(double answer, double tolerance) {
		this.answer = answer;
	}
	
	public NumAnswer(Scanner scn) {
		answer = Double.parseDouble(scn.nextLine());		
	}
	
	public void print() {
		System.out.println(answer);
	}
	
	public double getCredit(Answer ansObj) {
		double credit = 0.0; // Default or No Credit 
		
		NumAnswer rightAnsObj = (NumAnswer)ansObj; // Type casting to a more specific answer type.
		
		if (answer == rightAnsObj.answer) { // Credit - Compares the answers between the two.
			credit = 1.0; 
		}
		
		return credit;
	}
	
	public double getCredit(Answer ansObj, double tolerance) { // Added method to pass tolerance down to answer.
		double credit = 0.0; // Default or No Credit 
		
		NumAnswer rightAnsObj = (NumAnswer)ansObj; // Type casting to a more specific answer type.
		
		if (answer == rightAnsObj.answer || ((answer < (answer + tolerance)) && (answer > (answer - tolerance)))) { // Credit - Compares the answers between the two.
			credit = 1.0; 
		}
		
		return credit;
	}
	
	public void save(PrintWriter pw) {
		pw.println(this.answer);
	}
	
	public void printToGUI(JTextArea ta) {
		ta.append("   " + Double.toString(this.answer) + "\n");
	}
}
