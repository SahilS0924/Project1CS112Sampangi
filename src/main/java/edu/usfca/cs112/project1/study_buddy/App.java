package edu.usfca.cs112.project1.study_buddy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
				    if (l != null) {
				    	l.reset();
				        l.doLesson();
				    }
				} 
				else if (choice.equals("3")) {

				    if (course.lessons.size() > 0) {
					evaluateProgress(course);
					getCompletedLessonTopics(course);
				    getBestLesson(course);
				    getWorstLesson(course);
				    getAvgLesson(course);}
				    else {
				    	System.out.println("Please do a lesson first");
				    }
				} 

				else if (choice.equals("4")){
					String message = course.saveToFile("src/main/resources/fullCourse.txt");
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

	private static void getAvgLesson(Course course) {
		// TODO Auto-generated method stub
		course.getAvgLesson();
	}

	private static void getWorstLesson(Course course) {
		// TODO Auto-generated method stub
		course.getWorstLesson();
	}

	private static void getBestLesson(Course course) {
		course.getBestLesson();
	}

	private static void getCompletedLessonTopics(Course course) {
	System.out.println("Here are the topics you have completed: " );
		for(String topic : course.getCompletedLessonTopics()) {
			System.out.println(topic);
		}
	}

	private static void evaluateProgress(Course course) {
		System.out.println("Here is your progress: ");
		course.evaluateProgress();
	}


	private static Lesson selectLesson(Scanner scan, Course course) {
	    System.out.println("Select a lesson from the following topics:");
	    ArrayList<String> topics = course.getAllTopics();
	    for (int i = 0; i < topics.size(); i++) {
	        System.out.println((i + 1) + ". " + topics.get(i));
	    }
	    System.out.print("Enter the number corresponding lesson you want to select: ");
	    int choice;
	    try {
	        choice = Integer.parseInt(scan.nextLine()) - 1;
	        System.out.println("Please wait while your lesson loads.");
	    } catch (NumberFormatException e) {
	        System.out.println("Enter a Number");
	        return selectLesson(scan, course);
	    }

	    if (choice < 0 || choice >= topics.size()) {
	        System.out.println("Enter an Accurate Number");
	        return selectLesson(scan, course);
	    }
	    return course.selectLesson(choice);
	}

	}