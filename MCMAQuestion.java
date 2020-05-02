/* CS 342: Project 5: GUI
 * Name: Funmilola Akintoye
 * NetID: fakint3
 */

import java.io.*;
import java.util.*;

public class MCMAQuestion extends MCQuestion {
	protected ArrayList<MCMAAnswer> studentAnswers; // Array to hold multiple student answer objects.
	protected double baseCredit; // Base credit to balance the multiple answer credits.

	protected int numStudentAnswers; // Number of student answers added.
	
	public MCMAQuestion(String question, double maxValue, double baseCredit, int numAnswers) {
		super(question, maxValue, numAnswers);
		
		numStudentAnswers = numAnswers;
		
		this.questionType = "MCMAQuestion";
		this.baseCredit = baseCredit;
	}
	
	public MCMAQuestion(Scanner scn) {
		super(scn);
		
		studentAnswers = new ArrayList<MCMAAnswer>();
		numStudentAnswers = 0;
		
		this.questionType = "MCMAQuestion";
		this.baseCredit = Double.parseDouble(scn.nextLine());
		this.numAnswers = Integer.parseInt(scn.nextLine());
		
		for (int i = 0; i < numAnswers; i++) {
			this.addAnswer(new MCMAAnswer(scn)); // Add answer to question.
		}
	}
	
	public Answer getNewAnswer() { // Returns empty "Multiple Choice Single Answer" answer object.
		MCMAAnswer ansObj = new MCMAAnswer("", 0.0); // Dummy Content
		
		return ansObj;
	}
	
	public Answer getNewAnswer(String answer) { // Returns newly created "Multiple Choice Single Answer" answer object that has content. 
		MCMAAnswer ansObj = new MCMAAnswer(answer, 0.0); // Default credit as 0.
		
		return ansObj;
	}
	
	public Answer getNewAnswer(String answer, double creditIfSelected) { // Same as the above but considers partial credit.
		MCMAAnswer ansObj = new MCMAAnswer(answer, creditIfSelected); 
		
		return ansObj;
	}
	
	public void getAnswerFromStudent() {
		Scanner scn = ScannerFactory.getKeyboardScanner();
		
		MCMAAnswer ansObj; // "Multiple Choice Multiple Answer" Answer Object
		String ansChoices; // This contains all the selected answers separated by a space.
		char[] ansChoicesArr; // This is the previous string split into an array.
		
		char ansChoice; // The multiple choice letter selected by the user.
		int ansPosition; // The position of the answer in the array that the student selected.
		
		System.out.println("");
		
		print(); // Prints the question and multiple choice answers.
		
		System.out.print("Enter your answer choice(s): ");

		ansChoices = scn.nextLine().toUpperCase().replaceAll("\\s", ""); // Gets rid of all whitespace before parsing the answers into the array.
		
		if (ansChoices.contains("SKIP")) {
			ansObj = new MCMAAnswer("SKIP", 0);
			studentAnswers.add(ansObj);
			this.skipped = true;
			numStudentAnswers++;
			return;
		}

		ansChoicesArr = ansChoices.toCharArray();

		while (ansChoicesArr.length < 1 || ansChoicesArr.length > numAnswers) { // Error checking if the selected answers fall out of bounds with the amount of answers provided.
			System.out.print("Error: Please enter your answer choice(s) separated by a space: ");
			ansChoices = scn.nextLine().toUpperCase().replaceAll("\\s", "");
			ansChoicesArr = ansChoices.toCharArray();
		}
		
		for (int i = 0; i < ansChoicesArr.length; i++) {
			ansChoice = ansChoicesArr[i];
			ansPosition = ansChoice - 'A'; // This will convert that char into an int that will be used to look up the answer object in the array.

			if (ansPosition >= 0 && ansPosition < numAnswers) { // Makes sure the answer choices are in bounds.
				ansObj = (MCMAAnswer)answers.get(ansPosition); // Gets the corresponding answer object from array.
				studentAnswers.add(ansObj); 
				numStudentAnswers++;
			}
		}	
	}
	
	public double getValue() {			
		if (numStudentAnswers == 0) { // If no answers were selected.
			return (baseCredit + maxValue);
		}
		
		for (MCMAAnswer a: studentAnswers) {
			baseCredit += a.getCredit(a);
		}
		
		return (baseCredit * maxValue);
	}
	
	public void save(PrintWriter pw) {
		super.save(pw);
		
		pw.println(baseCredit);
		pw.println(numAnswers);
		
		for (Answer a: answers) {
			a.save(pw);
		}
	}
	
	public void saveStudentAnswers(PrintWriter pw) {
		pw.println("MCMAAnswer");
		
		pw.println(numStudentAnswers);

		for (MCMAAnswer a: studentAnswers) {
			a.save(pw);	
		}
	}
	
	public void restoreStudentAnswers(Scanner scn) {
		MCMAAnswer ansObj; // MCMAAnswer Object
		String answerLine; // Line containing answer information.
		String[] answerLineArr; // Line from above split into an array.
		String a; // Answer
		Double credit; // Answer Credit
		
		while (scn.nextLine().equals("")) {	
			scn.nextLine(); // For whatever reason, this helps align the scanner with non-blank lines for the MCMAs.
		}
		
		numStudentAnswers = Integer.parseInt(scn.nextLine());
			
		for (int i = 0; i < numStudentAnswers; i++) {
			answerLine = scn.nextLine();
			answerLineArr = answerLine.split(" ", 2);
			a = answerLineArr[1];
			credit = Double.parseDouble(answerLineArr[0]);
			ansObj = new MCMAAnswer(a, credit);
			studentAnswers.add(ansObj);
		}
	}
}
