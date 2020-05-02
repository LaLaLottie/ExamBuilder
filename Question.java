/* CS 342: Project 5: GUI
 * Name: Charlotte Norman
 * NetID: cnorma4
 */
import javax.swing.*;
import java.io.*;
import java.util.*;

abstract class Question
{
	protected String question; // Actual text of a question.
	protected double maxValue; // Max Question Score
	protected Answer studentAnswer; // Answer object holding the student answer.
	protected Answer rightAnswer; // Answer object holding the correct answer.
	protected boolean skipped; // Used to note which questions have been skipped.
	protected String questionType; // Used for printing on whether a question is SAQuestion, NumQuestion, MCSAQuestion, or a MCMAQuestion.
	
	protected Question(String question, double maxValue) {	
		this.maxValue = maxValue;
		this.question = question;
		this.skipped = false;
	}
	
	public Question(Scanner scn) {
		maxValue = Double.parseDouble(scn.nextLine());
		question = scn.nextLine();
		this.skipped = false;
	}
	
	public void print() {
		System.out.println(question); // Prints Question
		System.out.println("\n");
	}
	
	public void guiPrint(JPanel panel, JTextArea textArea){
		textArea.append(question + "\n");
	}
	
	public void printToGUI(JTextArea ta) {
		ta.append(question + "\n");
		this.rightAnswer.printToGUI(ta);
	}
	
	public void setRightAnswer(Answer ansObj) {
		rightAnswer = ansObj;
	}
	
	abstract public Answer getNewAnswer(); // Returns the appropriate newly created answer object.
	
	abstract public void getAnswerFromStudent(); // Collects the user's answer(s) into a new answer object.
	
	abstract public double getValue(); // Collects the question value based on the selected student answers.
	
	abstract public void save(PrintWriter pw); // Saves the relevant question information to an external file.
	
	public void saveStudentAnswers(PrintWriter pw) {
		
		switch(questionType) {
			case "SAQuestion":
				pw.println("SAAnswer");
				break;			
			case "NumQuestion":
				pw.println("NumAnswer");
				break;
			case "MCSAQuestion":
				pw.println("MCSAAnswer");
				break;
			default:
				break;
		}
		
		studentAnswer.save(pw);
	}
	
	public void restoreStudentAnswer(Scanner scn) {
		String fileLine = scn.nextLine();
				
		while (fileLine.length() == 0) {
			fileLine = scn.nextLine();
		}
		
		String qt = fileLine; // Gets the question type from the scanner.
		String a; // Gets the student answer from the scanner.	
				
		switch (qt) {
			case "SAAnswer":
				a = scn.nextLine();
				this.studentAnswer = new SAAnswer(a);
				break;
			case "NumAnswer":
				a = scn.nextLine();
				this.studentAnswer = new NumAnswer(Double.parseDouble(a));
				break;
			case "MCSAAnswer":
				Double credit;
				
				String line = scn.nextLine(); // Getting answer line and splitting
				String[] lineArr = line.split(" ", 2); // to get the separate values.
				
				a = lineArr[1];
			    credit = Double.parseDouble(lineArr[0]);
			    
				this.studentAnswer = new MCSAAnswer(a, credit);
				break;
			case "MCMAAnswer":
				this.restoreStudentAnswer(scn); // MCMAQuestion should handle this.
				break;
			default:
				break;
		}
		
	}
	
	public double getMaxValue() { // Extra method: returns the maximum value of each question.
		return maxValue;
	}
}
