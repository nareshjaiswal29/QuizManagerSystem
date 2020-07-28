package fr.epita.quiz.datamodel;

import java.sql.SQLException;
import java.util.Scanner;

import fr.epita.quiz.services.QuizDAO;

public class Quiz {

	private String Title;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
	
	public boolean AddQuiz() {
		Scanner scan = new Scanner(System.in);	
		System.out.println("Please Enter the Quiz Name ");
		Title = scan.nextLine();
		System.out.println(Title);
		boolean qid = false;
		QuizDAO QD = new QuizDAO();
		try {
			qid = QD.insertQuiz(Title.toLowerCase());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			if(qid)	
			{
				System.out.println("\n\nsuccessfully created a quiz with Name :- " + Title);
			}
			else 
			{
				System.out.println("Can not create :- " + Title +" Quiz name");
			}
			}catch(Exception e) {
				System.out.println("Exception encountered in inserting Quiz name /n Error :- " +e);
			}
			finally {
			}
		return qid;
		}
	
	
}
