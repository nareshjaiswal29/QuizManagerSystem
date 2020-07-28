package fr.epita.quiz.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.epita.quiz.datamodel.Answer;

public class AnswerDAO {
	
	// for inserting the correct answer for the inserted question. 
	public void insertAnswers(int Q_id, Answer ans) throws SQLException {
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		String InsertQuery="insert into ANSWERS_MAP(Question_ID,CORRECT_OPTION)\r\n" + 
							"values("+ Q_id +","+ ans.getRightOption() +") ";
		
		Statement st = connection.createStatement();
		st.executeUpdate(InsertQuery);
		st.close();
		connection.close();
	}
	public void UpdateAnswers(int Q_id, Answer ans) throws SQLException {
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		String uPDQuery="UPDATE ANSWERS_MAP SET CORRECT_OPTION = '"+ ans.getRightOption() +"' WHERE QUESTION_ID = "+ Q_id +"";
		
		Statement st = connection.createStatement();
		st.executeUpdate(uPDQuery);
		st.close();
		connection.close();
	}
	
	private Connection getConnection(Configuration conf) throws SQLException {
		Connection connection = DriverManager.getConnection(conf.getConfValue("db.url"), conf.getConfValue("db.user"),
				conf.getConfValue("db.password")); // TODO : externalize
		return connection;
	}
	
	public int CorrectAnswer(int Ques_Id) throws SQLException {
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		
		String query = "SELECT * FROM  ANSWERS_MAP where Question_id ="+ Ques_Id;
		int  ans=-1;
		PreparedStatement pstmt = connection.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			 ans = rs.getInt("CORRECT_OPTION" );
		}		
		pstmt.close();
		rs.close();
		connection.close();
		return ans;
	}

}
