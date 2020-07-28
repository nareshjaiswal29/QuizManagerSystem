package fr.epita.quiz.services;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;

public class QuestionDAO {

	// CRUD : CREATE, READ, UPDATE, DELETE
	public int insertQuestion(Question question) throws CreateFailedException {
		Configuration conf = Configuration.getInstance();
		int id = 0;
		
		String SQL_QUERY = "INSERT INTO QUESTION(SUBJECT,TOPICS, DIFFICULTY, OPTION_ONE, OPTION_TWO, OPTION_THREE, OPTION_FOUR, QUIZ_ID, QUESTIONTYPE) VALUES(?, ?, ? , ?, ?, ?, ?, ?, ?);";
		try {
			Connection connection = getConnection(conf);
			PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
			pstmt.setString(1, question.getSubject());
			//String arrayAsString = String.valueOf(question.getTopics()); //add comma between each part of the array
			pstmt.setString(2, question.getTopics());
			pstmt.setInt(3, question.getDifficulty());
			if(question.getOptions() != null)
			{
				pstmt.setString(4, question.getOptions()[0]);
				pstmt.setString(5, question.getOptions()[1]);
				pstmt.setString(6, question.getOptions()[2]);
				pstmt.setString(7, question.getOptions()[3]);
			}
			else {
				pstmt.setString(4,"");
				pstmt.setString(5, "");
				pstmt.setString(6, "");
				pstmt.setString(7, "");
			}
			
			pstmt.setLong(8, question.getQuizid());
			pstmt.setString(9, question.getQuestiontype());
			pstmt.execute();
			
			
			
			String QuestionId="SELECT max(id) as ID FROM QUESTION where SUBJECT = '"+ question.getSubject() +"'";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(QuestionId);
			while(rs.next()) {
				 id = rs.getInt("ID");
			}
			
			connection.close();
		} catch (SQLException e) {
			CreateFailedException createFailedException = new CreateFailedException();
			createFailedException.initCause(e);
			createFailedException.setObj(question);
			throw createFailedException;
		}
		return id;

	}

	public List<Question> search(Question question) throws SQLException {
		List<Question> questionResult = new ArrayList<>();
		try {
		Configuration conf = Configuration.getInstance();
		
		Connection connection = getConnection(conf);
		PreparedStatement statement = connection
				//.prepareStatement("select SUBJECT, TOPICS, DIFFICULTY, QUESTIONTYPE from QUESTION where ID = '1'");
				.prepareStatement("select SUBJECT, TOPICS, DIFFICULTY, QUESTIONTYPE, QUIZ_ID, OPTION_ONE, OPTION_TWO, OPTION_THREE, OPTION_FOUR, ID  from QUESTION where  lower(SUBJECT) LIKE ?");
			statement.setString(1, "%" + question.getSubject().toLowerCase() + "%");
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			String subject = rs.getString(1);
			String topics = rs.getString(2);
			//String OTPTION1 = rs.getString(4);
			Integer difficulty = rs.getInt(3);
			String Questiontype = rs.getString(4);
			Integer qUIZ = rs.getInt(5);
			String op1 = rs.getString(6);
			String op2 = rs.getString(7);
			String op3 = rs.getString(8);
			String op4 = rs.getString(9);
			Integer Qid = rs.getInt(10);
			Question currentQuestion = new Question();
			currentQuestion.setSubject(subject);
			currentQuestion.setTopics(topics);
			currentQuestion.setDifficulty(difficulty);
			currentQuestion.setQuestiontype(Questiontype);
			String [] Option = new String[4];
			Option[0]= op1;
			Option[1]= op2;
			Option[2]= op3;
			Option[3]= op4;
			currentQuestion.setOptions(Option);
			currentQuestion.setQuestiontype(Questiontype);
			currentQuestion.setQuestiontype(Questiontype);
			currentQuestion.setQuestiontype(Questiontype);
			currentQuestion.setQuestionId(Qid);
			questionResult.add(currentQuestion);
		}
		
		connection.close();
		}catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
		return questionResult;
	}
	
	
	public List<Question> searchByTopics(Question question) throws SQLException {
		List<Question> questionResult = new ArrayList<>();
		try {
		Configuration conf = Configuration.getInstance();
		
		Connection connection = getConnection(conf);
		PreparedStatement statement = connection
				//.prepareStatement("select SUBJECT, TOPICS, DIFFICULTY, QUESTIONTYPE from QUESTION where ID = '1'");
				.prepareStatement("select SUBJECT, TOPICS, DIFFICULTY, QUESTIONTYPE, QUIZ_ID, OPTION_ONE, OPTION_TWO, OPTION_THREE, OPTION_FOUR, ID  from QUESTION where  lower(TOPICS) LIKE ?");
			statement.setString(1, "%" + question.getTopics().toLowerCase() + "%");
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			String subject = rs.getString(1);
			String topics = rs.getString(2);
			//String OTPTION1 = rs.getString(4);
			Integer difficulty = rs.getInt(3);
			String Questiontype = rs.getString(4);
			Integer qUIZ = rs.getInt(5);
			String op1 = rs.getString(6);
			String op2 = rs.getString(7);
			String op3 = rs.getString(8);
			String op4 = rs.getString(9);
			Integer Qid = rs.getInt(10);
			Question currentQuestion = new Question();
			currentQuestion.setSubject(subject);
			currentQuestion.setTopics(topics);
			currentQuestion.setDifficulty(difficulty);
			currentQuestion.setQuestiontype(Questiontype);
			String [] Option = new String[4];
			Option[0]= op1;
			Option[1]= op2;
			Option[2]= op3;
			Option[3]= op4;
			currentQuestion.setOptions(Option);
			currentQuestion.setQuestiontype(Questiontype);
			currentQuestion.setQuestiontype(Questiontype);
			currentQuestion.setQuestiontype(Questiontype);
			currentQuestion.setQuestionId(Qid);
			questionResult.add(currentQuestion);
		}
		
		connection.close();
		}catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
		return questionResult;
	}
	

	private Connection getConnection(Configuration conf) throws SQLException {
		Connection connection = DriverManager.getConnection(conf.getConfValue("db.url"), conf.getConfValue("db.user"),
				conf.getConfValue("db.password")); // TODO : externalize
		return connection;
	}

	public void update(Question question) throws CreateFailedException {
		
		Configuration conf = Configuration.getInstance();
		int id = 0;
		try {
		String SQL_QUERY = "UPDATE QUESTION SET SUBJECT = '"+ question.getSubject() +"', OPTION_ONE = '"+ question.getOptions()[0].toString() +"', OPTION_TWO = '"+ question.getOptions()[1].toString() +"', OPTION_THREE = '"+ question.getOptions()[2].toString() +"', OPTION_FOUR = '"+question.getOptions()[3].toString() +"' WHERE ID = '" + question.getQuestionId()+"';";
		
			Connection connection = getConnection(conf);
			Statement stmt = connection.createStatement();
			stmt.execute(SQL_QUERY);
			stmt.close();	
			connection.close();
		} 
		catch (SQLException e) {
			CreateFailedException createFailedException = new CreateFailedException();
			createFailedException.initCause(e);
			createFailedException.setObj(question);
			throw createFailedException;
		}catch(Exception ex) {
			System.out.println(ex);	
		}
	}

	public void delete(Question question) throws SQLException {
		Configuration conf = Configuration.getInstance();
		int id = 0;
		Connection connection = getConnection(conf);	
		String DeleteQuery="DELETE FROM QUESTION where ID = '" + question.getQuestionId()+"';";
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(DeleteQuery);
		stmt.close();
		connection.close();
		System.out.println("Data Deleted Succesfully.....");	
	}
	
	public void GetQuizQuestions(int QId) throws SQLException {
		try {
	    String fileName = "Questions.txt";
		File file = new File(fileName);
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		PreparedStatement statement = connection
				.prepareStatement("select SUBJECT, OPTION_ONE, OPTION_TWO, OPTION_THREE, OPTION_FOUR, QUESTIONTYPE from QUESTION where QUIZ_ID = '"+ QId +"'");
		ResultSet rs = statement.executeQuery();
		FileWriter  writer = new FileWriter (file, false);
		writer.write("\r\n"); 
		writer.write("----------------- QUIZ -------------------");
		writer.write("\r\n"); 
		while(rs.next()) {	
			 String QsType= rs.getString("QUESTIONTYPE");
			 writer.write(rs.getString("SUBJECT"));
			 writer.write("\r\n"); 
			 if(QsType.equalsIgnoreCase("MCQ")) {
			 writer.write("1. "+rs.getString("OPTION_ONE"));
	         writer.write("\r\n");   // write new line
	         writer.write("2. "+rs.getString("OPTION_TWO"));
	         writer.write("\r\n");   // write new line
	         writer.write("3. "+rs.getString("OPTION_THREE"));
	         writer.write("\r\n");   // write new line
	         writer.write("4. "+rs.getString("OPTION_FOUR"));
	         writer.write("\r\n"); 
			 }
			 else {
				 writer.write("Answer:-");
				 writer.write("\r\n"); 
			 }
	         writer.write("\r\n");  
		}
		System.out.println("Question Exported Succesfully.....");
		System.out.println("Quetion Exported Path : "+ System.getProperty("user.dir") +"\\" +fileName);
		writer.close();
		connection.close();
		}catch(Exception ex) {
			System.out.print(ex);
		}
	}
	
	
	public int ProcessOnlineQuiz(int QId) throws SQLException {
		Scanner scan_ans = new Scanner(System.in);
		Scanner scan_MCQ = new Scanner(System.in);
		int Score = 0;
		try {
	    String fileName = "application.log";
		File file = new File(fileName);
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		PreparedStatement statement = connection
				.prepareStatement("select ID, SUBJECT, OPTION_ONE, OPTION_TWO, OPTION_THREE, OPTION_FOUR, QUESTIONTYPE from QUESTION where QUIZ_ID = '"+ QId +"'");
		ResultSet rs = statement.executeQuery();
			
		while(rs.next()) {
			 String QsType= rs.getString("QUESTIONTYPE");
			 int QuesId= rs.getInt("ID");
			 System.out.println(rs.getString("SUBJECT"));
			 System.out.println(); 
			 if(QsType.equalsIgnoreCase("MCQ")) {
				 System.out.println("1. "+rs.getString("OPTION_ONE"));
				 System.out.println("2. "+rs.getString("OPTION_TWO"));
				 System.out.println("3. "+rs.getString("OPTION_THREE"));
				 System.out.println("4. "+rs.getString("OPTION_FOUR"));
				 System.out.println(); 
				 
				 System.out.println("Enter The Choice (Only Options (1|2|3|4))");
					int Choice = scan_MCQ.nextInt();
					MCQChoice Mc = new MCQChoice();
					AnswerDAO Asd = new AnswerDAO();
				    Mc.setChoiceLabel(Choice);
				    int CorrectAnswer = Asd.CorrectAnswer(QuesId);
				    if(Mc.getChoiceLabel()== CorrectAnswer)
				    {
				    	Mc.setValid(true);
						Score = Score + 1;
					}
				    else {
				    	Mc.setValid(false);
				    }
			 }
			 else {
				 Answer as = new Answer();
				 System.out.println("Enter The answer:-");
			     String stAns = scan_ans.nextLine();
				 as.setAnswer(stAns);
			 }
			 System.out.println("\r\n");  
		}
		//writer.close();
		connection.close();
		}catch(Exception ex) {
			System.out.print(ex);
		}
		
		
		return Score;
	}
	
}
