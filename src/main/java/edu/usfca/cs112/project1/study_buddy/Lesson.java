package edu.usfca.cs112.project1.study_buddy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lesson implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient Model myModel;
	private String topic;
	private List<String> questions;
	 private Map<String, String> answerKey;
	private int correctAnswers;
	private int incorrectAnswers;
	private int skippedQuestions;
	private transient Scanner scanner;
	private String Description;
	public double score;
//… etc. lots more instance variables here …
	public Lesson(String topic){
			this.myModel = new Model();
			this.topic = topic;
	        this.questions = new ArrayList<>();
	        this.answerKey = new HashMap<>();
	        this.scanner = new Scanner(System.in);
	        this.correctAnswers = 0;
	        this.incorrectAnswers = 0;
	        this.skippedQuestions = 0;  
	        generateQuestions();
// etc.
	}
	private void generateQuestions() {
		 this.Description = myModel.generate("Generate a few paragraphs about "+ topic);
		String alreadyAsked = "";
		  for (int i = 0; i < 3; i++) {
	          try {
	        	  String QA = myModel.generate("Generate a multipile choice question from the information from the paragraphs you gave about " + topic + "Format it like this: \n" + "Question\n" + "A) Option1\n" + "B) Option2\n" + "C) Option3\n" + "D) Option4\n" + "Answer: either 'A','B','C','D'. Make sure the question is not already in this string: " + alreadyAsked);
	        	  alreadyAsked += QA;
	        	  String[] parts = QA.split("Answer:");
	        	  String question = parts[0];
	            String correctAnswer = parts[1].trim();
	            questions.add(question);
	            answerKey.put(question, correctAnswer);
	            }
	          catch (Exception e){
	        	  System.out.println("Error Generating" +e.getMessage());
	          }
	            }
	}
	
	public void doLesson(){
			// administer a single lesson on this.topic
			// to your user through a Scanner interaction
			System.out.println("The next topic is " + topic);
			System.out.println("Description: " + Description);
	        for(String question : questions) {
	        	askQuestion(question);
	        }
	        ratePerformance();
	        
	    }
	private void askQuestion(String question) {
		System.out.println("\n" + question);
        System.out.println("Enter your answer (A, B, C, D) or type 'SKIP' to move on: ");
        String userAns = scanner.nextLine().trim().toUpperCase();

        if (userAns.equals("SKIP")) {
            System.out.println("Question skipped");
            skippedQuestions++;
            return;
        }
        else if (answerKey.get(question).equals(userAns)) {
            System.out.println("Correct");
            correctAnswers++;
        } 
        else {
            System.out.println("Incorrect. The correct answer was: " + answerKey.get(question));
            incorrectAnswers++;
        }
		
	}
	private void ratePerformance() {
		int totalQuestions = correctAnswers + incorrectAnswers + skippedQuestions;
         score = ((double) correctAnswers / totalQuestions) * 100;
        System.out.println("\nLesson completed.");
        System.out.println("Correct Answers: " + correctAnswers);
        System.out.println("Incorrect Answers: " + incorrectAnswers);
        System.out.println("Skipped Questions: " + skippedQuestions);
        System.out.println("Your Score: " + score + "%");

        if (score >= 67) {
            System.out.println("Good job!");
        } else if (score >= 34) {
            System.out.println("Not bad");
        } else {
            System.out.println("Bad");
        }
		
	}
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
	    ois.defaultReadObject();
	    reset();
	}

	//…plus plenty of other helper methods …
	public void reset(){
		myModel = new Model();
		scanner = new Scanner(System.in);
	}
	public String getTopic() {
		// TODO Auto-generated method stub
		return topic;
	}
}
