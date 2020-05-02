/* CS 342: Project 5: GUI
 * Name: Charlotte Norman
 * NetID: cnorma4
 */

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ExamBuilder 
{
	private static Exam currExam = null; // Current Exam Object
	private static JTextField SAQValue;
	private static JTextField NAQValue;
	private static JTextField NACorrectAnswer;
	private static JTextField MCSAQValue;
	private static JTextField MCMAQValue;
	private static JTextField MCSACorrectAnswer;
	private static JTextField mcsaOtherAnswer1;
	private static JTextField mcsaOtherAnswer2;
	private static JTextField mcsaOtherAnswer3;
	private static JTextField mcsaOtherAnswer4;
	private static JTextField addQStatus;
	private static JTextField toleranceTA;
	private static JTextField numAnswersTA;
	private static JTextField mcmaAnswer2;
	private static JTextField mcmaAnswer3;
	private static JTextField mcmaAnswer1;
	private static JTextField mcmaAnswer4;
	private static JTextField mcmaAnswer5;
	private static JTextField mcmaNumAnswersTA;
	private static JTextField MCMABaseCredit;
	private static JTextField MCMACredit1;
	private static JTextField MCMACredit5;
	private static JTextField MCMACredit4;
	private static JTextField MCMACredit3;
	private static JTextField MCMACredit2;
	
	//private static JTextField txtInsertQuestionNumber;
	
	private static void showGUI() {
		// Main Frame
		JFrame mainFrame = new JFrame("Exam Builder");
		mainFrame.setSize(800, 600);
		mainFrame.getContentPane().setBackground(new Color(230, 230, 250));
		
		// Main Content Area
		Container c = mainFrame.getContentPane(); 
		c.setSize(800, 600);
		mainFrame.getContentPane().setLayout(null);
		
		CardLayout cl_actionsPanel = new CardLayout();
		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setBounds(300, 21, 450, 400);
		mainFrame.getContentPane().add(actionsPanel);
		actionsPanel.setLayout(cl_actionsPanel);
		
		// Default Card
		
		JPanel defaultCard = new JPanel();
		actionsPanel.add(defaultCard, "defaultCard");
		
		// Load Exam Card
		
		JPanel loadExamCard = new JPanel();
		actionsPanel.add(loadExamCard, "loadExamCard");
		loadExamCard.setLayout(null);
		
		JTextArea loadStatus = new JTextArea();
		loadStatus.setEditable(false);
		loadStatus.setBounds(105, 126, 248, 95);
		loadExamCard.add(loadStatus);
		
		JButton btnLoadExam = new JButton("Load Exam");
		btnLoadExam.setBounds(155, 60, 155, 30);
		loadExamCard.add(btnLoadExam);
		
		btnLoadExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currExam != null) {
					String[] options = { "YES", "NO" };
					
					int ret = JOptionPane.showOptionDialog(null, "Are you sure you want to load another exam?  This will delete the current one.", "Warning",
					             JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					             null, options, options[1]);
					
					if (ret == JOptionPane.YES_OPTION) {
						currExam = null;
					}
					else {
						cl_actionsPanel.show(actionsPanel, "defaultCard"); // Goes back to initial panel.
						return;
					}
				}
				
				JFileChooser fc = new JFileChooser();
				int ret = fc.showOpenDialog(loadExamCard);
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					currExam = loadExam(currExam, file);
					
					if (currExam != null) {
						loadStatus.setText("Exam has been loaded!"); // Clear in case of left over text.
					}
					else {
						loadStatus.setText("Error: Exam file could not be loaded.");
					}
				}
				else if (ret == JFileChooser.CANCEL_OPTION) {
					// Do nothing.
				}
				else {
					loadStatus.setText("Error: Exam file could not be loaded.");
				}	
			}
		});	
		
		// Add Questions Card
		
		JPanel addQsCard = new JPanel();
		actionsPanel.add(addQsCard, "addQsCard");
		addQsCard.setLayout(null);
		
		JMenu mnQuestionType = new JMenu("Question Type");
		mnQuestionType.setBounds(169, 59, 107, 22);
		addQsCard.add(mnQuestionType);
		
		mnQuestionType.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // Opens and closes pop out menu.
				if (mnQuestionType.isPopupMenuVisible() == false) { 
					mnQuestionType.setPopupMenuVisible(true);
				}
				else {
					mnQuestionType.setPopupMenuVisible(false);
				}
			}
		});
		
		JPanel questionsPanel = new JPanel();
		questionsPanel.setBounds(50, 100, 350, 250);
		addQsCard.add(questionsPanel);
		
		CardLayout qCL = new CardLayout(); // Card Layout for the Question Panels
		
		questionsPanel.setLayout(qCL);
		
		JLabel lblAddQuestion = new JLabel("Add Question");
		lblAddQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddQuestion.setBounds(183, 24, 100, 22);
		addQsCard.add(lblAddQuestion);
		
		addQStatus = new JTextField();
		addQStatus.setEditable(false);
		addQStatus.setBounds(73, 361, 306, 20);
		addQsCard.add(addQStatus);
		addQStatus.setColumns(10);
		
		// Default Question Sub-Panel
		
		JPanel defaultQPanel = new JPanel();
		defaultQPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				addQStatus.setText(""); // Clears status text area.
			}
		});
		
		questionsPanel.add(defaultQPanel, "defaultQPanel");
		
		// SA
		
		JPanel saPanel = new JPanel();
		questionsPanel.add(saPanel, "saPanel");
		saPanel.setLayout(null);
		
		JTextArea SAQTA = new JTextArea();
		SAQTA.setText("Question...");
		SAQTA.setBounds(102, 11, 162, 76);
		saPanel.add(SAQTA);
		
		SAQValue = new JTextField();
		SAQValue.setText("Question Value...");
		SAQValue.setBounds(102, 98, 162, 20);
		saPanel.add(SAQValue);
		SAQValue.setColumns(10);
		
		JTextArea SACorrectAnswer = new JTextArea();
		SACorrectAnswer.setText("Correct Answer...");
		SACorrectAnswer.setBounds(102, 129, 162, 76);
		saPanel.add(SACorrectAnswer);
		
		JButton saAddQ = new JButton("Add Question");
		saAddQ.setBounds(226, 216, 114, 23);
		saPanel.add(saAddQ);
		
		saAddQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQStatus.setText(""); // Clear the status.
				
				if (currExam == null) {
					createExamGUI(mainFrame);
				}
				
				if (SAQTA.getText() == null || SAQValue.getText() == null || SACorrectAnswer == null) {
					addQStatus.setText("Question wasn't added because some field(s) are blank.");
					return;
				}
				
				String question = SAQTA.getText();
				double maxValue = Double.parseDouble(SAQValue.getText());
				String answer = SACorrectAnswer.getText();
				
				SAQuestion saQues = new SAQuestion(question, maxValue);
				saQues.getNewAnswer(answer);
				
				currExam.addQuestion(saQues);
				
				addQStatus.setText("Question was added to the exam.");
				
				//qCL.show(questionsPanel, "defaultQPanel");
			}
		});
		
		// NA
		
		JPanel naPanel = new JPanel();
		questionsPanel.add(naPanel, "naPanel");
		naPanel.setLayout(null);
		
		JTextArea NAQTA = new JTextArea();
		NAQTA.setText("Question...");
		NAQTA.setBounds(101, 11, 162, 76);
		naPanel.add(NAQTA);
		
		NAQValue = new JTextField();
		NAQValue.setText("Question Value...");
		NAQValue.setColumns(10);
		NAQValue.setBounds(101, 98, 162, 20);
		naPanel.add(NAQValue);
		
		toleranceTA = new JTextField();
		toleranceTA.setText("Answer Tolerance...");
		toleranceTA.setColumns(10);
		toleranceTA.setBounds(101, 129, 162, 20);
		naPanel.add(toleranceTA);
		
		NACorrectAnswer = new JTextField();
		NACorrectAnswer.setText("Correct Answer...");
		NACorrectAnswer.setColumns(10);
		NACorrectAnswer.setBounds(101, 160, 162, 20);
		naPanel.add(NACorrectAnswer);
		
		JButton naAddQ = new JButton("Add Question");
		naAddQ.setBounds(226, 216, 114, 23);
		naPanel.add(naAddQ);
		
		naAddQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQStatus.setText(""); // Clear the status.
				
				if (currExam == null) {
					createExamGUI(mainFrame);
				}
				
				if (NAQTA.getText() == null || NAQValue.getText() == null || toleranceTA == null || NACorrectAnswer == null) {
					addQStatus.setText("Question wasn't added because some field(s) are blank.");
					return;
				}
				
				String question = NAQTA.getText();
				double maxValue = Double.parseDouble(NAQValue.getText());
				double tolerance = Double.parseDouble(toleranceTA.getText());
				String answer = NACorrectAnswer.getText();
				
				NumQuestion numQues = new NumQuestion(question, maxValue, tolerance);
				numQues.getNewAnswer(Double.parseDouble(answer));
				
				currExam.addQuestion(numQues);
				
				addQStatus.setText("Question was added to the exam.");
							
				//qCL.show(questionsPanel, "defaultQPanel");
			}
		});
			
		// MCSA
		
		JPanel mcsaPanel = new JPanel();
		questionsPanel.add(mcsaPanel, "mcsaPanel");
		mcsaPanel.setLayout(null);
			
		JTextArea MCSAQTA = new JTextArea();
		MCSAQTA.setText("Question...");
		MCSAQTA.setBounds(10, 11, 162, 76);
		mcsaPanel.add(MCSAQTA);
		
		MCSAQValue = new JTextField();
		MCSAQValue.setText("Question Value...");
		MCSAQValue.setColumns(10);
		MCSAQValue.setBounds(10, 98, 162, 20);
		mcsaPanel.add(MCSAQValue);
		
		numAnswersTA = new JTextField();
		numAnswersTA.setText("Number of Answers...");
		numAnswersTA.setColumns(10);
		numAnswersTA.setBounds(10, 129, 162, 20);
		mcsaPanel.add(numAnswersTA);
		
		MCSACorrectAnswer = new JTextField();
		MCSACorrectAnswer.setText("Correct Answer...");
		MCSACorrectAnswer.setColumns(10);
		MCSACorrectAnswer.setBounds(178, 13, 162, 20);
		mcsaPanel.add(MCSACorrectAnswer);
		
		mcsaOtherAnswer1 = new JTextField();
		mcsaOtherAnswer1.setText("Other Answer...");
		mcsaOtherAnswer1.setColumns(10);
		mcsaOtherAnswer1.setBounds(178, 44, 162, 20);
		mcsaPanel.add(mcsaOtherAnswer1);
		
		mcsaOtherAnswer2 = new JTextField();
		mcsaOtherAnswer2.setText("Other Answer...");
		mcsaOtherAnswer2.setColumns(10);
		mcsaOtherAnswer2.setBounds(178, 75, 162, 20);
		mcsaPanel.add(mcsaOtherAnswer2);
		
		mcsaOtherAnswer3 = new JTextField();
		mcsaOtherAnswer3.setText("Other Answer...");
		mcsaOtherAnswer3.setColumns(10);
		mcsaOtherAnswer3.setBounds(178, 106, 162, 20);
		mcsaPanel.add(mcsaOtherAnswer3);
		
		mcsaOtherAnswer4 = new JTextField();
		mcsaOtherAnswer4.setText("Other Answer...");
		mcsaOtherAnswer4.setColumns(10);
		mcsaOtherAnswer4.setBounds(178, 137, 162, 20);
		mcsaPanel.add(mcsaOtherAnswer4);
		
		JButton mcsaAddQ = new JButton("Add Question");
		mcsaAddQ.setBounds(178, 216, 162, 23);
		mcsaPanel.add(mcsaAddQ);
		
		mcsaAddQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQStatus.setText(""); // Clear the status.
				
				if (currExam == null) {
					createExamGUI(mainFrame);
				}
				
				if (MCSAQTA.getText() == null || MCSAQValue.getText() == null || MCSACorrectAnswer == null) {
					addQStatus.setText("Question wasn't added because some field(s) are blank.");
					return;
				}
				
				String question = MCSAQTA.getText();
				double maxValue = Double.parseDouble(MCSAQValue.getText());
				int numAnswers = Integer.parseInt(numAnswersTA.getText());
				String correctAnswer = MCSACorrectAnswer.getText();
				
				MCSAQuestion mcsaQues = new MCSAQuestion(question, maxValue, numAnswers);
				MCSAAnswer mcsaAns; 
				
				mcsaAns = (MCSAAnswer)mcsaQues.getNewAnswer(correctAnswer, 1.0); // Correct Answer
				JTextField[] answers = {mcsaOtherAnswer1, mcsaOtherAnswer2, mcsaOtherAnswer3, mcsaOtherAnswer4}; // Other Answers
				
				for (JTextField a: answers) { // Goes through all the other answers and checks if they're not empty before adding to question.

					if (a.getText() != null) {
						mcsaAns = (MCSAAnswer)mcsaQues.getNewAnswer(a.getText(), 0.0);
						mcsaQues.addAnswer(mcsaAns); // Adding answer to question.
					}
				}
				
				currExam.addQuestion(mcsaQues);
				
				addQStatus.setText("Question was added to the exam.");
				
				//qCL.show(questionsPanel, "defaultQPanel");
			}
		});
		
		// MCMA
		
		JPanel mcmaPanel = new JPanel();
		questionsPanel.add(mcmaPanel, "mcmaPanel");
		mcmaPanel.setLayout(null);

		JTextArea MCMAQTA = new JTextArea();
		MCMAQTA.setText("Question...");
		MCMAQTA.setBounds(10, 11, 162, 76);
		mcmaPanel.add(MCMAQTA);
		
		MCMAQValue = new JTextField();
		MCMAQValue.setText("Question Value...");
		MCMAQValue.setColumns(10);
		MCMAQValue.setBounds(10, 94, 162, 20);
		mcmaPanel.add(MCMAQValue);
		
		mcmaNumAnswersTA = new JTextField();
		mcmaNumAnswersTA.setText("Number of Answers...");
		mcmaNumAnswersTA.setColumns(10);
		mcmaNumAnswersTA.setBounds(10, 126, 162, 20);
		mcmaPanel.add(mcmaNumAnswersTA);
		
		mcmaAnswer1 = new JTextField();
		mcmaAnswer1.setText("Answer...");
		mcmaAnswer1.setColumns(10);
		mcmaAnswer1.setBounds(185, 13, 140, 20);
		mcmaPanel.add(mcmaAnswer1);
		
		mcmaAnswer2 = new JTextField();
		mcmaAnswer2.setText("Answer...");
		mcmaAnswer2.setColumns(10);
		mcmaAnswer2.setBounds(185, 45, 140, 20);
		mcmaPanel.add(mcmaAnswer2);
		
		mcmaAnswer3 = new JTextField();
		mcmaAnswer3.setText("Answer...");
		mcmaAnswer3.setColumns(10);
		mcmaAnswer3.setBounds(185, 76, 140, 20);
		mcmaPanel.add(mcmaAnswer3);	
		
		mcmaAnswer4 = new JTextField();
		mcmaAnswer4.setText("Answer...");
		mcmaAnswer4.setColumns(10);
		mcmaAnswer4.setBounds(185, 107, 140, 20);
		mcmaPanel.add(mcmaAnswer4);
		
		mcmaAnswer5 = new JTextField();
		mcmaAnswer5.setText("Answer...");
		mcmaAnswer5.setColumns(10);
		mcmaAnswer5.setBounds(185, 138, 140, 20);
		mcmaPanel.add(mcmaAnswer5);
		
		JButton mcmaAddQ = new JButton("Add Question");
		mcmaAddQ.setBounds(185, 216, 155, 23);
		mcmaPanel.add(mcmaAddQ);
		
		MCMABaseCredit = new JTextField();
		MCMABaseCredit.setText("Base Credit...");
		MCMABaseCredit.setColumns(10);
		MCMABaseCredit.setBounds(10, 157, 162, 20);
		mcmaPanel.add(MCMABaseCredit);
		
		MCMACredit1 = new JTextField();
		MCMACredit1.setBounds(322, 13, 28, 20);
		mcmaPanel.add(MCMACredit1);
		MCMACredit1.setColumns(10);
		
		MCMACredit2 = new JTextField();
		MCMACredit2.setColumns(10);
		MCMACredit2.setBounds(322, 45, 28, 20);
		mcmaPanel.add(MCMACredit2);
		
		MCMACredit3 = new JTextField();
		MCMACredit3.setColumns(10);
		MCMACredit3.setBounds(322, 76, 28, 20);
		mcmaPanel.add(MCMACredit3);
		
		MCMACredit4 = new JTextField();
		MCMACredit4.setColumns(10);
		MCMACredit4.setBounds(322, 107, 28, 20);
		mcmaPanel.add(MCMACredit4);
		
		MCMACredit5 = new JTextField();
		MCMACredit5.setColumns(10);
		MCMACredit5.setBounds(322, 138, 28, 20);
		mcmaPanel.add(MCMACredit5);
		
		mcmaAddQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addQStatus.setText(""); // Clear the status.
				
				if (currExam == null) {
					createExamGUI(mainFrame);
				}
				
				if (MCMAQTA.getText() == null || MCMAQValue.getText() == null || MCMABaseCredit.getText() == null || mcmaAnswer1 == null) {
					addQStatus.setText("Question wasn't added because some field(s) are blank.");
					return;
				}
				
				String question = MCMAQTA.getText();
				double maxValue = Double.parseDouble(MCMAQValue.getText());
				double baseCredit = Double.parseDouble(MCMABaseCredit.getText());
				int numAnswers = Integer.parseInt(mcmaNumAnswersTA.getText());
				
				MCMAQuestion mcmaQues = new MCMAQuestion(question, maxValue, baseCredit, numAnswers);
				MCMAAnswer mcmaAns;
								
				JTextField[] answers = {mcmaAnswer1, mcmaAnswer2, mcmaAnswer3, mcmaAnswer4, mcmaAnswer5}; // Answer Fields
				JTextField[] answerCredits = {MCMACredit1, MCMACredit2, MCMACredit3, MCMACredit4, MCMACredit5}; // Answer Credit Fields
				
				for (int i = 0; i < 5; i++) { // Goes through all the other answers and checks if they're not empty before adding to question.

					if (answers[i].getText() != null || answers[i].getText() != "") {
						double credit;
						
						if (answerCredits[i].getText() == null || answerCredits[i].getText() == "") {
							credit = 0.0;
						}
						else {
							credit = Double.parseDouble(answerCredits[i].getText());
						}
						
						mcmaAns = (MCMAAnswer)mcmaQues.getNewAnswer(answers[i].getText(), credit);
						mcmaQues.addAnswer(mcmaAns); // Adding answer to question.
					}
				}
				
				currExam.addQuestion(mcmaQues);
				
				addQStatus.setText("Question was added to the exam.");
				
				//qCL.show(questionsPanel, "defaultQPanel");
			}
		});
		
		// Pop Out Menu for Question Types
		
		JMenuItem mntmShortQuestion = new JMenuItem("Short Question");
		mntmShortQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnQuestionType.setPopupMenuVisible(false); // Closes pop out menu.
				
				qCL.show(questionsPanel, "saPanel");	
			}
		});
		
		mnQuestionType.add(mntmShortQuestion);
		
		JMenuItem mntmNumericalQuestion = new JMenuItem("Numerical Question");
		mntmNumericalQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnQuestionType.setPopupMenuVisible(false); // Closes pop out menu.
				
				qCL.show(questionsPanel, "naPanel");
			}
		});
		
		mnQuestionType.add(mntmNumericalQuestion);
		
		JMenuItem mntmMultipleChoiceSA = new JMenuItem("Multiple Choice - Single Answer");
		mntmMultipleChoiceSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnQuestionType.setPopupMenuVisible(false); // Closes pop out menu.
				
				qCL.show(questionsPanel, "mcsaPanel");
			}
		});
		
		mnQuestionType.add(mntmMultipleChoiceSA);
		
		JMenuItem mntmMultipleChoiceMA = new JMenuItem("Multiple Choice - Multiple Answers");
		mntmMultipleChoiceMA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnQuestionType.setPopupMenuVisible(false); // Closes pop out menu.
				
				qCL.show(questionsPanel, "mcmaPanel");
			}
		});
		
		mnQuestionType.add(mntmMultipleChoiceMA);
		
		// Remove Questions Card 
		
		JPanel removeQsCard = new JPanel();
		actionsPanel.add(removeQsCard, "removeQsCard");
		removeQsCard.setLayout(null);
			
		JTextField txtInsertQuestionNumber; 
		txtInsertQuestionNumber = new JTextField();
		txtInsertQuestionNumber.setText("Insert question number...");
		txtInsertQuestionNumber.setBounds(85, 354, 150, 20);
		removeQsCard.add(txtInsertQuestionNumber);
		txtInsertQuestionNumber.setColumns(10);
		
		JTextArea showExamTA = new JTextArea();
		showExamTA.setEditable(false);
		showExamTA.setBounds(59, 22, 346, 313);
		removeQsCard.add(showExamTA);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(263, 353, 89, 23);
		removeQsCard.add(btnRemove);
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtInsertQuestionNumber.getText() != null) { // Check if text area is not empty first.
					
					int questionNum = Integer.parseInt(txtInsertQuestionNumber.getText());
				
					if (currExam == null || currExam.questions.size() < 1) {
						showExamTA.setText("There are no questions to delete.");
						return;
					}			
					
					if (questionNum > 0 && questionNum <= currExam.questions.size()) { // Makes sure the selection is within range.
						currExam.questions.remove((questionNum - 1)); // Gets the actual position of the question within the array.
						printToGUI(currExam, showExamTA);
						
						System.out.println("Question " + questionNum + " has been removed.");			
					}
					else {
						showExamTA.setText("Please enter a valid Question #.");
					}	
				}
			}
		});
					
		// Reorder Exam Card
		
		JPanel reorderQsCard = new JPanel();
		actionsPanel.add(reorderQsCard, "reorderQsCard");
		reorderQsCard.setLayout(null);
		
		JTextArea reorderedExamTA = new JTextArea();
		reorderedExamTA.setEditable(false);
		reorderedExamTA.setBounds(50, 29, 357, 315);
		reorderQsCard.add(reorderedExamTA);
		
		JButton btnReorder = new JButton("Reorder");
		btnReorder.setBounds(180, 355, 89, 23);
		reorderQsCard.add(btnReorder);
		
		btnReorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				if (currExam == null) {
					reorderedExamTA.setText("There is no exam available, please load or create a exam first.");
				}
				else {
					currExam = reorderExam(currExam);
					printToGUI(currExam, reorderedExamTA);
				}
			}
		});
		
		// Print Exam Card
		
		JPanel printExamCard = new JPanel();
		actionsPanel.add(printExamCard, "printExamCard");
		printExamCard.setLayout(null);
		
		// Save Exam Card
		
		JPanel saveExamCard = new JPanel();
		actionsPanel.add(saveExamCard, "saveExamCard");
		saveExamCard.setLayout(null);
		
		JButton btnSaveExam = new JButton("Save Exam");
		btnSaveExam.setBounds(155, 60, 155, 30);
		saveExamCard.add(btnSaveExam);
		
		JTextArea saveStatus = new JTextArea();
		saveStatus.setEditable(false);
		saveStatus.setBounds(105, 126, 248, 95);
		saveExamCard.add(saveStatus);
		
		btnSaveExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int ret = fc.showOpenDialog(loadExamCard);
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					saveExam(currExam, file);
					
					if (currExam != null) {
						saveStatus.setText("Exam has been saved!");
					}
					else {
						saveStatus.setText("Error: Exam file could not be saved.");
					}
				}
				else if (ret == JFileChooser.CANCEL_OPTION) {
					// Do nothing.
				}
				else {
					saveStatus.setText("Error: Exam file could not be saved.");
				}	
			}
		});
			
		// Application Title/Header and Menu
		
		JLabel menuHeader = new JLabel("Please select a menu option below:");
		menuHeader.setBounds(20, 42, 226, 14);
		mainFrame.getContentPane().add(menuHeader);
		
		JLabel appTitle = new JLabel("CS 362 - Exam Builder");
		appTitle.setBounds(21, 17, 172, 14);
		mainFrame.getContentPane().add(appTitle);
				
		// Main Menu Radio Buttons
		
		// Load Exam Button
		JRadioButton optionBtn1 = new JRadioButton("Load a saved exam from a file.");
		optionBtn1.setBounds(21, 69, 225, 23);
		mainFrame.getContentPane().add(optionBtn1);
		
		optionBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_actionsPanel.show(actionsPanel, "loadExamCard");	
			}
		});	
		
		// Add Questions Button
		
		JRadioButton optionBtn2 = new JRadioButton("Add questions interactively.");
		optionBtn2.setBounds(20, 95, 226, 23);
		mainFrame.getContentPane().add(optionBtn2);
		
		optionBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_actionsPanel.show(actionsPanel, "addQsCard");
				qCL.show(questionsPanel, "defaultQPanel");
			}
		});
		
		// Load Exam Button
		
		JRadioButton optionBtn3 = new JRadioButton("Remove questions interactively.");
		optionBtn3.setBounds(20, 121, 226, 23);
		mainFrame.getContentPane().add(optionBtn3);
		
		optionBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_actionsPanel.show(actionsPanel, "removeQsCard");
				
				if (currExam == null) {
					showExamTA.setText("There is no exam available, please load or create a exam first.");
				}
				else if (currExam.questions.size() < 1) {
					showExamTA.setText("There are no questions to this exam to remove.");
				}
				else {	
					printToGUI(currExam, showExamTA);
				}
			}
		});
		
		// Load Exam Button
		
		JRadioButton optionBtn4 = new JRadioButton("Reorder questions and/or answers.");
		optionBtn4.setBounds(20, 147, 226, 23);
		mainFrame.getContentPane().add(optionBtn4);
		
		optionBtn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_actionsPanel.show(actionsPanel, "reorderQsCard");
				
				currExam = reorderExam(currExam);
				
				if (currExam == null) {
					reorderedExamTA.setText("There is no exam available, please load or create a exam first.");
				}
				else {
					printToGUI(currExam, reorderedExamTA);
				}
			}
		});
		
		// Load Exam Button
		
		JTextArea printExamTA = new JTextArea();
		printExamTA.setEditable(false);
		printExamTA.setBounds(50, 29, 357, 337);
		printExamCard.add(printExamTA);
		
		JRadioButton optionBtn5 = new JRadioButton("Print the exam to the screen.");
		optionBtn5.setBounds(20, 173, 226, 23);
		mainFrame.getContentPane().add(optionBtn5);
		
		optionBtn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_actionsPanel.show(actionsPanel, "printExamCard");
				
				if (currExam == null) {
					printExamTA.setText("There is no exam available, please load or create a exam first.");
				}
				else {
					printToGUI(currExam, printExamTA);
				}
			}
		});
		
		
		// Save Exam Button
		
		JRadioButton optionBtn6 = new JRadioButton("Save the exam to a file.");
		optionBtn6.setBounds(20, 199, 226, 23);
		mainFrame.getContentPane().add(optionBtn6);
		
		optionBtn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_actionsPanel.show(actionsPanel, "saveExamCard");
			}
		});	
		
		// Quit Application Button
		
		JRadioButton optionBtn7 = new JRadioButton("Quit.");
		optionBtn7.setBounds(20, 225, 226, 23);
		mainFrame.getContentPane().add(optionBtn7);
		
		optionBtn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = { "OK", "CANCEL" };
				
				int ret = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Warning",
				             JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				             null, options, options[0]);
				
				if (ret == JOptionPane.OK_OPTION) {
					mainFrame.dispose();
					System.exit(0);
				}
				else {
					cl_actionsPanel.show(actionsPanel, "defaultCard"); // Goes back to initial panel.
				}
			}
		});
		
		ButtonGroup menuBtns = new ButtonGroup();
		menuBtns.add(optionBtn1);
		menuBtns.add(optionBtn2);
		menuBtns.add(optionBtn3);
		menuBtns.add(optionBtn4);
		menuBtns.add(optionBtn5);
		menuBtns.add(optionBtn6);
		menuBtns.add(optionBtn7);		
		
		mainFrame.setVisible(true);
	}
	
	private static Scanner getExamFileScanner(File file) {
		Scanner scanFile = null; 
		
		try {
			scanFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return scanFile;	
	}
	
	private static PrintWriter getExamFileWriter(File file) { // Gets the file used to save an exam.
		PrintWriter savedFile = null; // To save the modified exam.
		
		try {
			savedFile = new PrintWriter(file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 

		return savedFile;
	}
	
	private static void closeExamFileWriter(PrintWriter pw) { 
		pw.close();
	}
	
	private static void createExamGUI(JFrame frame) {		
		String title = (String)JOptionPane.showInputDialog(
                frame,
                "Please enter a title for the exam (including file extension): ",
                "Input Exam Title",
                JOptionPane.PLAIN_MESSAGE);
		
		currExam = new Exam(title);
	}
	
	private static Exam loadExam(Exam currExam, File file) { // Creates a new exam from file.
		if (currExam != null) {
			System.out.println("Cannot load an exam because another exam is being modified.");
			return currExam;
		}
		
		return new Exam(getExamFileScanner(file)); // Exam
	}	
	
	private static Exam reorderExam(Exam currExam) {
		if (currExam == null) {
			System.out.println("\nThere is no exam available, please load a exam first.");
			return currExam;
		}
		
		currExam.reorderQuestions();
		currExam.reorderMCQuestions(-1); 
		
		return currExam;
	}
	
	private static void printToGUI(Exam currExam, JTextArea ta) {
		ta.setText(""); // Clears text area before adding more content in.
		currExam.printToGUI(ta);
	}
	
	private static void saveExam(Exam currExam, File file) {
		PrintWriter examPW; // Used to save the exam.
		
		if (currExam == null) {
			System.out.println("\nThere is no exam available, please load a exam first.");
			return;
		}
		
		examPW = getExamFileWriter(file);
		currExam.save(examPW); // Saves exam using a print writer.
		
		closeExamFileWriter(examPW);
	}
		
	public static void main(String args[]) {		
		// Printing Program Introduction in Console
		
		System.out.println("Starting up...");
		System.out.println("Student Name: Charlotte Norman");
		System.out.println("Student NetID: cnorma4 \n");
			
		showGUI(); // Display and Interact with GUI
		
		return;
	}
}