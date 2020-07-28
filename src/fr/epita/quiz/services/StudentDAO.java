package fr.epita.quiz.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class StudentDAO {
	public void insertStudentInfor(String Name , String Qname , int Marks_Scored) throws SQLException {
		Configuration conf = Configuration.getInstance();
		Connection connection = getConnection(conf);
		String insert = "insert into STUDENTS_RESULT (NAME ,QUIZ ,TOTAL_MARKS )\r\n" + 
						"values('"+ Name +"','"+ Qname +"',"+ Marks_Scored +")";
		
		Statement st = connection.createStatement();
		st.executeUpdate(insert);
		st.close();
		
		connection.close();
	}
	
	private Connection getConnection(Configuration conf) throws SQLException {
		Connection connection = DriverManager.getConnection(conf.getConfValue("db.url"), conf.getConfValue("db.user"),
				conf.getConfValue("db.password")); // TODO : externalize
		return connection;
	}

}
