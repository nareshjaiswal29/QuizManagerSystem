package fr.epita.quiz.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.epita.quiz.datamodel.Quiz;

public class QuizDAO {
	
		public boolean insertQuiz(String QName) throws SQLException{
			Configuration conf = Configuration.getInstance();
			Connection connection = getConnection(conf);
			int id =0 ; 
			id = search(QName);
			if (id == 0) {
				String query = "insert into QUIZ (QUIZ_NAME) values (?) ";			
				PreparedStatement pstmt = connection.prepareStatement(query);
				pstmt.setString(1, QName);
				pstmt.execute();
				connection.close();
				return true;
			}
			else {
				System.out.println("This Quiz already exist");
				return false;
			}	
		}
		
		//For Searching and confirming that if quiz exist or not and returns quiz id. If returned id is 0 then searched quiz does not exist. 
		public int search(String QName) throws SQLException {
			int id=0;
			try {
			Configuration conf = Configuration.getInstance();
			Connection connection = getConnection(conf);
			String query = "Select ID , QUIZ_NAME From QUIZ where lower(QUIZ_NAME) = '"+QName+"'";
			
			PreparedStatement pstmt = connection.prepareStatement(query);	
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				 id = rs.getInt("ID");
			}		
			pstmt.close();
			rs.close();
			connection.close();
			}catch(Exception ex) {
				System.out.println(ex);
			}
			// return 0 id is quiz name is not present in database
			return id;
		}
		
		public void GetQuizQuestions(int QId) throws SQLException, IOException {	
			try {
			int id = 0;
			String SQ;
			Configuration conf = Configuration.getInstance();
			Connection connection = getConnection(conf);
			String query = "select ID, SUBJECT, TOPICS, DIFFICULTY, OPTION_ONE, OPTION_TWO,OPTION_THREE, OPTION_FOUR from QUESTION WHERE ID = '" + QId + "'";
			//String query = "Select ID , QUIZ_NAME From QUIZ where ID = '"+QId+"'";
			PreparedStatement pstmt = connection.prepareStatement(query);	
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				 id = rs.getInt("ID");
				 SQ = rs.getString("SUBJECT");
			}	
			int count = 0;
			
			while(rs.next()) {
				 FileWriter fstream = new FileWriter("C:\\testout.txt");
				 BufferedWriter out = new BufferedWriter(fstream);
				 while (rs.next()) {            
				         out.write(rs.getInt("SUBJECT"));
				         out.newLine();
				         out.write(rs.getInt("OPTION_TWO"));
				         out.newLine();
				         out.write(rs.getInt("OPTION_THREE"));
				         out.newLine();
				         out.write(rs.getInt("OPTION_FOUR"));
				         out.newLine();
				         out.write(rs.getInt("OPTION_ONE"));
				         
				         out.newLine();
				         out.newLine();
				         /*out.write(System.getProperty("line.separator"));*/
				 }
				 System.out.println("Completed writing into text file");
				 out.close();
			}		
			pstmt.close();
			rs.close();	
			}catch(Exception e) {
				 System.out.println(e);
			}
			//return 0;
			// return 0 id is quiz name is not present in database
		}
		
		
		
		private Connection getConnection(Configuration conf) throws SQLException {
			Connection connection = DriverManager.getConnection(conf.getConfValue("db.url"), conf.getConfValue("db.user"),
					conf.getConfValue("db.password")); // TODO : externalize
			return connection;
		}

}
