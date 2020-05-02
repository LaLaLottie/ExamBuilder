/* CS 342: Project 5: GUI
 * Name: Charlotte Norman
 * NetID: cnorma4
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

public class NumQuestion extends Question {
	private double tolerance; // The acceptable range a student answer can be in order for it to still be correct.
	
	public NumQuestion(String question, double maxValue, double tolerance) {
		super(question, maxValue);
		
		this.questionType = "NumQuestion";
		
		this.tolerance = tolerance;
	}
	
	public NumQuestion(Scanner scn) {
		super(scn);
		
		this.questionType = "NumQuestion";
		this.rightAnswer = new NumAnswer(scn);
		
		tolerance = Double.parseDouble(scn.nextLine());
	}
	
	public Answer getNewAnswer() { // Returns empty "Numerical Answer" answer object.
		NumAnswer ansObj = new NumAnswer(0.0); // Dummy Content
		
		return ansObj;
	}
	
	public NumAnswer getNewAnswer(double answer) { // Returns empty "Numerical Answer" answer object.
		NumAnswer ansObj = new NumAnswer(answer); 
		
		this.rightAnswer = ansObj; // Sets the right answer.
		
		return ansObj;
	}
	
	public void getAnswerFromStudent() {
		Scanner scn = ScannerFactory.getKeyboardScanner(); // The shared System.in scanner.
		
		NumAnswer ansObj; // "Numerical Answer" Answer Object
		double answer; // Answer entered in by user.
		String value; // Value to signal where a question should be skipped or not.
		
		System.out.println("\n" + question);

		while (true) {
			try {
				System.out.print("Enter your answer (numeric values only!) or SKIP to come back to: ");	
				value = scn.nextLine();
				
				if (value.contains("SKIP")){
					this.skipped = true;
					ansObj = new NumAnswer("SKIP");
				}
				else {
					answer = Double.parseDouble(value);
					ansObj = new NumAnswer(answer, tolerance);
				}

				break;
			} catch (Exception e) {
				System.out.println("That is not a number.");
			}
		}
		
		this.studentAnswer = ansObj;	
	}
	
	public double getValue() {
		double value = studentAnswer.getCredit(rightAnswer);
		
		return (value * maxValue);
	}
	
	public void save(PrintWriter pw) {
		pw.println(this.questionType);
		pw.println(this.maxValue);
		pw.println(this.question);
		
		this.rightAnswer.save(pw); // Calls for the answer to save its information.
		
		pw.println(this.tolerance);
	}
	
	public void printToGUI(JTextArea ta) {
		ta.append(this.question + "\n");
		this.rightAnswer.printToGUI(ta);
	}
}