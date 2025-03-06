package edu.usfca.cs112.project1.study_buddy;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
	// Main menu for the Study Buddy application.
	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("Welcome to the Study Buddy.");
			Course course = new Course("src/main/resources/topics.txt", "src/main/resources/fullCourse.txt");
			while(true) {
				System.out.println("What would you like to do? Please enter a number: ");
				System.out.println(" 1. Do a lesson in the sequence.");
				System.out.println(" 2. Choose a lesson out of sequence.");
				System.out.println(" 3. Evaluate progress.");
				System.out.println(" 4. Quit the program.");
				String choice = scan.nextLine();
				if(choice.equals("1")) {
				    System.out.println("Please be patient while I load your Lesson.");
				    Lesson l = course.getNextLesson();
				    
				    // Check if `l` is null before calling `doLesson()`
				    if (l == null) {
				        System.out.println("No more lessons available.");
				    } else {
				        l.doLesson();
				    }
				}

				else if (choice.equals("2")) {
					Lesson l = selectLesson(scan, course);
					l.doLesson();
				}
				else if (choice.equals("3")) {
					evaluateProgress(scan, course);
					getCompletedLessonTopics();
				}
				else if (choice.equals("4")){
					String message = course.saveToFile("fullCourse.txt");
					System.out.println(message);
					System.out.println("Thanks for using the Study Buddy, goodbye!");
					break; // Exits while-true loop.
				}
				else {
					System.out.println("Please only enter 1, 2, 3, or 4.");
				}
			}
			scan.close();
		}catch(FileNotFoundException e) {
			System.out.println("You did not load a valid file into the program." +e.getMessage());
		}
		}

	private static void getCompletedLessonTopics() {
	System.out.println("Here are the topics you have completed: \n" );
		
	}

	private static void evaluateProgress(Scanner scan, Course course) {
		System.out.println("Here is your progress: " );
	}


	private static Lesson selectLesson(Scanner scan, Course course) {
		return null;
	 
	    }



	}