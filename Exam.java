/* CS 342: Project 5: GUI
 * Name: Charlotte Norman
 * NetID: cnorma4
 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class Exam
{
	private String title; // Title of the exam.
	protected ArrayList<Question> questions; // Array list to hold Question objects (exam questions).
	protected ArrayList<Integer> skipArray;
    private int numQuestions; // Total number of questions in an exam.    
	private double studentScore; // Actual Student Score 
	private double maxScore; // Total Possible Score
	private double examScore; // Exam Score
	
	private String[] questionTypes = new String[] {"mcsaquestion", "mcmaquestion", "saquestion", "numquestion"}; // Array to hold question types.
	private String fileLine; // The lines of the exam file.
		
	public Exam(String title) {
		this.title = title;
		questions = new ArrayList<Question>();
		skipArray = new ArrayList<Integer>();
		numQuestions = 0;
		studentScore = 0.0;
		maxScore = 0.0;
		examScore = 0.0;
	}
	
	public Exam(Scanner scn) {
		questions = new ArrayList<Question>();
		numQuestions = 0;
		studentScore = 0.0;
		maxScore = 0.0;
		examScore = 0.0;

		// Reading in Exam Information
		
		title = scn.nextLine(); // Gets exam title from scanner.
		scn.nextLine(); // Discards date information.
		
		while (scn.hasNextLine()) { // Reads until EOF.			
			fileLine = scn.nextLine();
			
			while (fileLine.length() == 0 && scn.hasNextLine()) { // If the current line is empty (length 0) then skip to the next until it is not.
				fileLine = scn.nextLine();
			}
			
			for (int i = 0; i < questionTypes.length; i++) {
				if ((fileLine.toLowerCase().equals(questionTypes[i]))) { // If the line equals one of the question types, then create the question.			
					switch(fileLine.toLowerCase()) {
						case "mcsaquestion": // Creates MCSAQuestion object.
							this.addQuestion(new MCSAQuestion(scn)); // Adds question to exam.
							break;
						case "mcmaquestion": // Creates MCMAQuestion object.
							this.addQuestion(new MCMAQuestion(scn)); // Adds question to exam.
							break;
						case "saquestion": // Creates SAQuestion object.
							this.addQuestion(new SAQuestion(scn)); // Adds question to exam.
							break;
						case "numquestion": // Creates NumQuestion object.
							this.addQuestion(new NumQuestion(scn)); // Adds question to exam.
							break;
						default:
							break;				
					}
				}
			}				
		}
			
		System.out.println("\n\n" + numQuestions + " questions has been added to the exam.\n");
	}
	
	public void print() {	
		int i = 0;
		
		System.out.println("\nExam: " + title + "\n"); // Prints exam title.
		
		for (Question q: questions) { // Prints Questions
			System.out.print("Question " + (i + 1) + ". ");
			q.print(); 
			i++; // To get the questions' positions in their current order.
		}
	}
	
	public void guiPrint(JPanel panel, JTextArea textArea){
		int i = 1;
		textArea.append(title + "\n");
		textArea.append("\n");
		for(Question q: questions){
			textArea.append(i + ". ");
			q.guiPrint(panel, textArea);
			i++;

		}
		//panel.add(textArea)
	}
	
	public void printToGUI(JTextArea ta){
		int i = 1;
		
		ta.append(title + "\n");
		ta.append("\n");
		
		for(Question q: questions){
			ta.append("\n" + i + ". ");
			q.printToGUI(ta);
			i++;
		}
	}
	
	public void addQuestion(Question quesObj) {
		questions.add(quesObj);
		numQuestions++;				
	}
	
	public void reorderQuestions() { // Reordering all the questions.	
		Collections.shuffle(questions); 
	}
	
	public void reorderMCQuestions(int position) {
		if (position < 0) { // Reordering all the multiple choice questions' answers.
		
			for (Question q: questions) { 
				if (q instanceof MCQuestion) { // Checking if the question is a multiple choice type.
					((MCQuestion) q).reorderAnswers();
				}
			}
		}
		else if (position < numQuestions) { // Makes sure the position is in bounds.
											// Reordering just the applicable multiple choice question's answers.
			Question q = questions.get(position);
			
			if (q instanceof MCQuestion) {
				((MCQuestion) q).reorderAnswers(); // Checking if the question is a multiple choice type.
			}
		}
	}
	
	public void getAnswerFromStudent(int position) {		
		if (position < 0) { // Gets an answer for every question in the exam.	
			for (Question q: questions) { 
				q.getAnswerFromStudent();
			}
		}
		else if (position < numQuestions) { // Makes sure the position is in bounds.
											// Just gets an answer for a particular question.
			Question q = questions.get(position);
			q.getAnswerFromStudent();
		}
	}
	
	public double getValue() {		
		for (Question q: questions) { // Goes through each question to get the two scores to sum up separately.
			studentScore += q.getValue();
			maxScore += q.getMaxValue();
		}
		
		if (studentScore == 0.0 && maxScore == 0.0) { // Error checking: in case there are no questions on the exam to grade.
			examScore = -1.0;
		}
		else {
			examScore = studentScore;
		}
		
		return examScore;
	}
	
	public void reportQuestionValues() {
		int i = 0;
		
		System.out.println("\n\n              +---- " + title + " Exam Score ----+                 ");
		System.out.println("+----            Question  | Question Score         ----+");
		
		for (Question q: questions) { // Goes through each question to print out relevant scoring information.
			System.out.println("+----           Question " + (i + 1) + " | " + new DecimalFormat("#.##").format(q.getValue()) + "                    ----+"); // Keeps it to two decimal places.
			i++;
		}
		
		System.out.println("+----      Your Score: " + examScore + " | Total Score: " + maxScore + "      ----+");
	}
	
	public void save(PrintWriter pw) {
		Date currentDate = new Date();
		
		pw.println(title);
		pw.println(currentDate.toString());
		
		for (Question q: questions) {
			pw.println("");
			q.save(pw);		
		}
	}
	
	public void saveStudentAnswers(PrintWriter pw) {
		Scanner scn = ScannerFactory.getKeyboardScanner(); // The shared System.in scanner.	
		Date currentDate = new Date();
  
		System.out.print("Please enter name: ");
		pw.println(scn.nextLine());	
		pw.println(title);
		pw.println(currentDate.toString());
  
		for (Question q: questions) {
			pw.println();
			q.saveStudentAnswers(pw);
		}
	}
	
	public void restoreStudentAnswers(Scanner scn) {
		scn.nextLine(); // Gets the student name and discards.
		scn.nextLine(); // Gets the date information and discards.
		
		for (Question q: questions) {
			scn.nextLine();
			q.restoreStudentAnswer(scn);
		}
	}
	
	public int numQuestions() {
		return numQuestions;
	}

	
	public void saveQuestionValue(PrintWriter pw, ArrayList<String> arr) { // Saving question values to a CSV and table
		int i = 0;

		for (i = 0; i < questions.size(); i++) {
			arr.add(Double.toString(questions.get(i).getValue()));
			pw.print("," + questions.get(i).getValue());
		}
	}
}
