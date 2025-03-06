package edu.usfca.cs112.project1.study_buddy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
//First Deserialize then Serialize after each lesson
public class Course implements Serializable{
	private static final long serialVersionUID = 1L;
	// Contains the Lessons completed so far in the order they were taken.
	// Might be empty, for example if the user has never done a Lesson before.
	private ArrayList<Lesson> lessons;
	// Always contains the full list of topics, loaded from file. This list defines the proper sequence of the Course.
	private ArrayList<String> topics;
	// Construct a Course by loading (deserializing) already finished Lessons into the lessons field
	// and reading all topics from file into the topics field
	public Course(String topicsFile, String lessonsFile) throws FileNotFoundException {
	lessons = new ArrayList<>();
	topics = new ArrayList<>();
	loadLessonsFromFile(lessonsFile); // a Course instance method you would implement
	loadTopicsFromFile(topicsFile); // a Course instance method you would implement

	}
	private void loadLessonsFromFile(String lessonsFile) throws FileNotFoundException {
	    File file = new File(lessonsFile);
	    Scanner scanner = new Scanner(file);
	    while (scanner.hasNextLine()) {
	        String topic = scanner.nextLine().trim();
	        lessons.add(new Lesson(topic));
	    }
	    scanner.close();
	}


	private void loadTopicsFromFile(String topicsFile) throws FileNotFoundException {
		File file = new File(topicsFile);
		Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				topics.add(scanner.nextLine().trim());
			}
			scanner.close();
	}

	public Lesson getNextLesson() {
        if (lessons.size() < topics.size()) {
        	String nextTopic = topics.get(lessons.size());
        	Lesson newLesson = new Lesson(nextTopic);
        	lessons.add(newLesson);
        	saveToFile("fullCourse.txt");
        	return newLesson;
        }
        else {
                System.out.println("You Completed all the lessons");
                return null;            
        }

	}
	public String saveToFile(String lessonsFile) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(lessonsFile))) {
	        for (Lesson lesson : lessons) {
	            writer.println(lesson.getTopic());
	        }
	        return "Progress saved";
	    } catch (IOException e) {
	        return e.getMessage();
	    }
	}

    public Lesson selectLesson(int index) {
        if (index >= 0 && index < topics.size()) {
            for (Lesson lesson : lessons) {
                if (lesson.getTopic().equals(topics.get(index))) {
                    return lesson;
                }
            }
            Lesson newLesson = new Lesson(topics.get(index));
            lessons.add(newLesson);
            saveToFile("fullCourse.txt");
            return newLesson;
        } else {
            System.out.println("Enter Valid Number");
            return null;
        }
    }
    public void evaluateProgress() {
        System.out.println("Progress: " + lessons.size() + "/" + topics.size());
        for (Lesson lesson : lessons) {
            System.out.println(lesson.getTopic());
        }
    }
    public int getCompletedLessonCount() {
        return lessons.size();
    }

    public int getLessonCount() {
        return topics.size();
    }

    public ArrayList<String> getCompletedLessonTopics() {
        ArrayList<String> completedTopics = new ArrayList<>();
        for (Lesson lesson : lessons) {
            completedTopics.add(lesson.getTopic());
        }
        return completedTopics;
    }
    public ArrayList<String> getAllTopics() {
        return topics;
    }
	public ArrayList<Lesson> getLessons() {
		return lessons;
	}


}
