package edu.usfca.cs112.project1.study_buddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
//First Deserialize then Serialize after each lesson
public class Course implements Serializable{
	private static final long serialVersionUID = 1L;
	// Contains the Lessons completed so far in the order they were taken.
	// Might be empty, for example if the user has never done a Lesson before.
	public ArrayList<Lesson> lessons;
	// Always contains the full list of topics, loaded from file. This list defines the proper sequence of the Course.
	private ArrayList<String> topics;
	private ArrayList<String> completedTopics;
	// Construct a Course by loading (deserializing) already finished Lessons into the lessons field
	// and reading all topics from file into the topics field
	public Course(String topicsFile, String completedTopicsFile) throws FileNotFoundException {
	lessons = new ArrayList<>();
	topics = new ArrayList<>();
	completedTopics = new ArrayList<>();
	loadLessonsFromFile(completedTopicsFile); // a Course instance method you would implement
	loadTopicsFromFile(topicsFile); // a Course instance method you would implement

	}
	@SuppressWarnings("unchecked")
	private void loadLessonsFromFile(String completedTopicsFile) {
		
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(completedTopicsFile))) {
	            //Object obj = ois.readObject();
	         completedTopics = (ArrayList<String>) ois.readObject();
	            System.out.println("Completed lessons successfully loaded.");
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Could not load completed lessons: " + e.getMessage());
	        }
	    } 
	
	private void loadTopicsFromFile(String topicsFile) throws FileNotFoundException {
		File file = new File(topicsFile);
		Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				topics.add(scanner.nextLine().trim());
			}
			scanner.close();
	}

//	public Lesson getNextLesson() {
//	    for (String topic : topics) {
//	        if (!getCompletedLessonTopics().contains(topic)) {
//	            Lesson newLesson = new Lesson(topic);
//	            topics.add(topic);
//	            saveToFile("src/main/resources/fullCourse.txt");
//	            return newLesson;
//	        }
//	    }
//	    System.out.println("You have completed all lessons.");
//	    return null;
//	}
	public Lesson getNextLesson() {
		for (String topic : topics) {
			Lesson newLesson = new Lesson(topic);
        if (!completedTopics.contains(topic)) {
        //	String nextTopic = topics.get(lessons.size());
        	//Lesson newLesson = new Lesson(topic);
        	completedTopics.add(topic);
        	lessons.add(newLesson);
        //	saveToFile("src/main/resources/fullCourse.txt");
        	return newLesson;
        }
}
		return null;
	}
	public String saveToFile(String completedTopicsFile) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(completedTopicsFile))) {
	        oos.writeObject(completedTopics); 
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
	        completedTopics.add(newLesson.getTopic());
          //  saveToFile("fullCourse.txt");
            return newLesson;
        } else {
            System.out.println("Enter Valid Number");
            return null;
        }
    }
    public void evaluateProgress() {
        System.out.println("Progress: " + lessons.size() + "/" + topics.size());
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
	public void getAvgLesson() {
		Double avgScore = null;
		int totalScore = 0;

		
		 
		for (Lesson lesson : lessons) {
			totalScore += lesson.score;
		}
		 avgScore = (double) (totalScore/lessons.size());
		System.out.println("Your Average Score was: " + avgScore);
	
		}
	public void getWorstLesson() {
		String worstLesson = "";
		double minScore = 0;
	    for (Lesson lesson : lessons) {
	        if (lesson.score <= minScore) {
	            minScore = lesson.score;
	            worstLesson = lesson.getTopic();
	        }
	    }
	    System.out.println("Your Worst lesson was: " + worstLesson + " With a Score of " + minScore);
	}

	public void getBestLesson() {
		String bestLesson = "";
		double maxScore = 0;
	    for (Lesson lesson : lessons) {
	        if (lesson.score >= maxScore) {
	            maxScore = lesson.score;
	            bestLesson = lesson.getTopic();
	        }
	    }
	    System.out.println("Your Best lesson was: " + bestLesson + " With a Score of: " + maxScore);
	}

		
	}


