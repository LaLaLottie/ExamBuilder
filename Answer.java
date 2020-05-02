/* CS 342: Project 5: GUI
 * Name: Daisy Arellano
 * NetID: darell3
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

abstract public class Answer
{	
	protected Answer() {}
	
	public Answer(Scanner scn) {}
	
	abstract public void print();
	
	abstract public double getCredit(Answer rightAnswerObj);
	
	abstract public void save(PrintWriter pw);
	
	abstract public void printToGUI(JTextArea ta);
}
