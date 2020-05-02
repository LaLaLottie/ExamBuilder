/* CS 342: Project 5: GUI
 * Name: Funmilola Akintoye
 * NetID: fakint3
 */

import java.io.*;
import java.util.*;

import javax.swing.JTextArea;

public class MCAnswer extends Answer {
	protected String answer; // Actual text of an answer.
	protected boolean selected; // // Determines if the right answer is selected or unselected.
	protected double creditIfSelected; // Considers partial credit for certain selected answers.
	
	protected String answerLine; // Line that holds the answer and credit information.
	protected String[] answerLineArr; // Array that holds the split answer line information.
	
	protected MCAnswer(String answer, double creditIfSelected) {
		this.answer = answer;
		this.creditIfSelected = creditIfSelected;
	}
	
	public MCAnswer(Scanner scn) {
		answerLine = scn.nextLine();
		answerLineArr = answerLine.split(" ", 2); // Just splits the line once.
		
		creditIfSelected = Double.parseDouble(answerLineArr[0]);
		answer = answerLineArr[1];	
	}
	
	public void print() {
		System.out.print(answer);
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public double getCredit(Answer ansObj) {
		double credit = 0.0; // Default or No Credit
		
    	if (this instanceof MCSAAnswer && ((answer.toLowerCase()).equals((((MCAnswer)ansObj).answer).toLowerCase()))) { // If current answer is selected and matches the right answer, then apply the credit.
																							                            // Answers are case insensitive.
			credit = creditIfSelected;
		}
    	else { // MCMAAnswer
    		credit = ((MCAnswer)ansObj).creditIfSelected;
    	}
		
		return credit;
	}
	
	public void save(PrintWriter pw) {
		pw.println(creditIfSelected + " " + answer);
	}
	
	public void printToGUI(JTextArea ta) {
		ta.append(answer);
	}
}
