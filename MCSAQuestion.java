/* CS 342: Project 5: GUI
 * Name: Daisy Arellano
 * NetID: darell3
 */

import java.io.*;
import java.util.*;

public class MCSAQuestion extends MCQuestion {
	public MCSAQuestion(String question, double maxValue, int numAnswers) {
		super(question, maxValue, numAnswers);
		
		this.questionType = "MCSAQuestion";
	}
	
	public MCSAQuestion(Scanner scn) {
		super(scn);
		
		this.questionType = "MCSAQuestion";
		this.numAnswers = Integer.parseInt(scn.nextLine());
				
		for (int i = 0; i < numAnswers; i++) {
			this.addAnswer(new MCSAAnswer(scn)); // Add answer to question.
		}
	}
	
	public Answer getNewAnswer() { // Returns empty "Multiple Choice Single Answer" answer object.
		MCSAAnswer ansObj = new MCSAAnswer("", 0.0); // Dummy Content
		
		return ansObj;
	}
	
	public Answer getNewAnswer(String answer, double creditIfSelected) { // Same as the above but considers partial credit.
		MCSAAnswer ansObj = new MCSAAnswer(answer, creditIfSelected); 
		
		return ansObj;
	}
	
	public void getAnswerFromStudent() {
		Scanner scn = ScannerFactory.getKeyboardScanner();
		String ansChoices;
		MCSAAnswer ansObj; // "Multiple Choice Single Answer" Answer Object
		char ansChoice; // The multiple choice letter selected by the user.
		int ansPosition; // The position of the answer in the array that the student selected.
		
		System.out.println("");
		
		print(); // Prints the question and multiple choice answers.
		
		System.out.print("Enter your answer choice or SKIP to come back: ");

		ansChoices = scn.nextLine().toUpperCase(); // This is the multiple choice answer choice e.g. A - E, etc.
		
		if (ansChoices.contains("SKIP")) {
			this.skipped = true;
			ansObj = new MCSAAnswer("SKIP", 0);
			this.studentAnswer = ansObj;
			return;
		}
    
		ansChoice = ansChoices.charAt(0);

		ansPosition = ansChoice - 'A'; // This will convert that char into an int that will be used to look up the answer object in the array.

		ansObj = (MCSAAnswer) answers.get(ansPosition); // Gets the corresponding answer object from array.
		this.studentAnswer = ansObj;
	}
	
	public double getValue() {
		double value = studentAnswer.getCredit(studentAnswer);
		
		return (value * maxValue);
	}
	
	public void save(PrintWriter pw) {
		super.save(pw);
		
		pw.println(numAnswers);
		
		for (Answer a: answers) {
			a.save(pw);
		}
	}
}