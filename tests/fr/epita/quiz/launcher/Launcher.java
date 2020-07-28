package fr.epita.quiz.launcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.Quiz;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.AnswerDAO;
import fr.epita.quiz.services.CreateFailedException;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.QuizDAO;
import fr.epita.quiz.services.StudentDAO;
import fr.epita.quiz.datamodel.Answer;

public class Launcher {
	
	
	public static void main(String[] args) throws CreateFailedException, SQLException, IOException {
		// TODO Auto-generated method stub
		Main_Menu();
	}
	
	public static void Main_Menu() {
		int Utype = 0;
		Utype = Menu();
		if(Utype == 1){
			Teacher();
			}
		else if (Utype == 2) {
			student();
		}
		else {
			System.out.println("WRONG CHOICE");
		}
	}
	
	public static void Teacher() {
		System.out.println("\n\n ----- Welcome Teacher ----- \n");
		int selectedOption = 0;
		boolean check = true;
		while (check) {
			selectedOption = 0;	
			selectedOption = TeacherMenu();
		
			switch (selectedOption) {
			case 1: 
				quizCreation();
				System.out.println();
				AskforTeacherMainMenu();
				check = false;
				break;
			case 2:
				Question qs = new Question();
				try {
					AddQuestion(qs);
					check = false;
				} catch (CreateFailedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println();
				AskforTeacherMainMenu();
				break;
			case 3:
				SearchByQuestion();
				System.out.println();
				AskforTeacherMainMenu();
				check = false;
				break;
			case 4:
				updatequestion();
				System.out.println();
				//AskforTeacherMainMenu();
				break;
			case 5:
				Deletequestion();
				System.out.println();
				AskforTeacherMainMenu();
				check = false;
				break;
			case 6:
				SearchByTopics();
				AskforTeacherMainMenu();
				check = false;
				break;
			case 7:
				try {
					ExportQuestion();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println();
				AskforTeacherMainMenu();
				check = false;
				break;
			case 8:
				System.out.println("Have a nice day");
				check = false;
				break;
			default:
				System.out.println("Wrong Choice");
				}
			
			}
		}
	public static void student() {
		System.out.println("\n ----- Welcome Students ----- \n");		
			try {
				StartQuiz();
				//AskforTeacherMainMenu();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static int Menu() {
		System.out.println("WELCOME TO QUIZ MANAGER PORTAL");
		Scanner scan = new Scanner(System.in);
		int Utype;
		Utype = UserType(scan);
		return Utype;
		}
	private static int UserType(Scanner sc) {
			
		System.out.println("choose option");
		System.out.println("I AM:-");
		System.out.println("1.Teacher");
		System.out.println("2.Student");
		System.out.println("Enter your choice (1|2) : \n");
		int Sel;
		return Sel= sc.nextInt();	
	}
	
	private static int TeacherMenu() {		
		
		Scanner sc_TecherMenu = new Scanner(System.in);
		System.out.println("--Menu--");
		System.out.println("1.Create Quiz");
		System.out.println("2.Create Question");
		System.out.println("3.Search Question");
		System.out.println("4.Update Question");
		System.out.println("5.Delete Question");
		System.out.println("6.Search by topics");
		System.out.println("7.Export Quiz");
		System.out.println("8.Quit Application");
		System.out.println("Enter your choice (1|2|3|4|5|6|7|8)");
		int Sel;
		Sel= sc_TecherMenu.nextInt();
		return Sel;
	}
	
	private static void quizCreation() {

		boolean qid = false;
		System.out.println("Creating Quiz");
		Quiz qz = new Quiz();
		qid = qz.AddQuiz();	
		if(qid) {
			System.out.println();
			System.out.println("Please go to menu to add question in this quiz");
		}
		//AskforTeacherMainMenu();
	}
	
		public static void AddQuestion(Question qs) throws CreateFailedException, SQLException {
			try {
			System.out.println("Creating Question");
			String QuizName;
			int QuizId = 0;
			QuestionDAO Qsd = new QuestionDAO();
			Scanner sc = new Scanner(System.in);		
			System.out.println("Enter the QuizName in which you want to add question");
			QuizName = sc.nextLine();
			
			try {
				QuizDAO qz = new QuizDAO();
				QuizId = qz.search(QuizName.toLowerCase());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some error in seraching the Quiz \n Error is : "+ e);
				e.printStackTrace();
				System.exit(1);
			}
			// id = means no quiz with that name
			
			if (QuizId == 0) {
				System.out.println("Quiz Not found with " + QuizName+ " name");
			}
			else {
				qs.setQuizid(QuizId);
				boolean check = true;
				String ans;
			
				while(check) {
					
					SetQuestion(qs);
					int QsId = Qsd.insertQuestion(qs);
					String Qtype = qs.getQuestiontype();
					if(QsId > 0 && Qtype.equalsIgnoreCase("MCQ"))
					{
						if(Qtype.equalsIgnoreCase("Normal")) {
						System.out.println();
						System.out.println("Data Inserted Succesfully....");
						System.out.println();
						}
						else {
						CorrectAnswerOption(QsId, "I");
						}
					}
					
					System.out.println("To enter one more question press y else n");
					ans= sc.next();
					
					if (!ans.equalsIgnoreCase("y")) {
						check = false;
					}	
				}
			}	
			}catch(Exception ex) {
				System.out.println(ex.toString());
			}
			
		}
		
		private static void SetQuestion(Question qs) 
		{
			Scanner Sc_Ques = new Scanner(System.in);
			System.out.println("Enter Question ");
			qs.setSubject(Sc_Ques.nextLine());
						
			System.out.println("Is it a MCQ type Question Or Normal Question? ");
			System.out.println("A for MCQ  OR  B for normal type");
			String Qstype = Sc_Ques.nextLine();
		    
			if(Qstype.equalsIgnoreCase("A")) {
				qs.setQuestiontype("MCQ");
				AddOptions(qs);
			}else {
				qs.setQuestiontype("Normal");
				AddDificulty(qs);
			}
			
			
		}

		private static void AddOptions(Question qs) {
			
			Scanner sc_op = new Scanner(System.in);
			
			String [] Option = new String[4];
			int max = 4;
			System.out.println("Enter 4 options");
			for(int i =0 ;i<4 ; i++) {
				System.out.println("Option "+(i+1)+":  ");
				Option[i]=sc_op.nextLine();
			}
			qs.setOptions(Option);
			AddDificulty(qs);
		}
		
		

		//For Setting the difficulty level for the inserted question
		private static void AddDificulty(Question qs) {
			
			Scanner sc_dif = new Scanner(System.in);
			System.out.println("Enter The dificulty Level");
			System.out.print("1:Easy");
			System.out.print("2: Medium");
			System.out.println("3: Hard");
			System.out.println("Chose 1 | 2 | 3");
			
			qs.setDifficulty(sc_dif.nextInt());
			
			AddTopicName(qs);
		}
		
		//For Setting the topic name for the inserted question
		private static void AddTopicName(Question qs) {
			
			ArrayList<String> topics = new ArrayList<>();
			System.out.println("Enter the topic of question");
			Scanner sc_tn = new Scanner(System.in);
			for(int i =0 ;i<5 ; i++) {
				String topic = sc_tn.nextLine();
				topics.add(topic);
				System.out.println("Do You want to add more topics ? choose Y/N");
				Scanner sc = new Scanner(System.in);
				String selc = sc.nextLine();
				if(selc.equalsIgnoreCase("Y")) {
					System.out.println("Enter the topic of question Again");
					continue;
				}
				else
				{
					break;
				}
				}
			String res = String.join(",", topics);
			qs.setTopics(res);
			String arrayAsString = String.valueOf(qs.getTopics()); 
			}

		//For Setting the correct answer for inserted question
		private static void CorrectAnswerOption(int Qsid, String type) throws SQLException {
			
			Scanner sc = new Scanner(System.in);	
			int Right_Option=0;			
			AnswerDAO asd = new AnswerDAO();
			Answer ans = new Answer();
			while ( (Right_Option == 0) || Right_Option >4) {
				System.out.println("Enter the correct Answer option (1|2|3|4)");
				Right_Option = sc.nextInt();
				
				if ((Right_Option == 0) || Right_Option >4) {
					System.out.println("Wrong Choice");
				}else {
					
				}
			}
			ans.setRightOption(Right_Option);
			
			if(type.equalsIgnoreCase("I"))
			{
			asd.insertAnswers(Qsid, ans);
			System.out.println();
			System.out.println("Data Inserted Succesfully....");
			System.out.println();
			}
			else {			
				asd.UpdateAnswers(Qsid, ans);
			}
			
		}
		
		private static void SearchByQuestion(){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your Question for search");
			String Qus =sc.nextLine();
			
			//given
			try {
			Question criteria = new Question();
			criteria.setSubject(Qus);
			
			//when
			QuestionDAO dao = new QuestionDAO();
			List<Question> searchResults = new ArrayList<>();
			searchResults = dao.search(criteria);		
			
			//then
			if (searchResults.size() == 1) {
				//System.out.println(searchResults);
				System.out.println("Question:- " +searchResults.get(0).getSubject());
				System.out.println("Topics:- " + searchResults.get(0).getTopics());
				System.out.println("Difficulty:- "+searchResults.get(0).getDifficulty());
				System.out.println("QuestionType:- "+searchResults.get(0).getQuestiontype());
			
			}else {
				System.out.println("Not Found");
			}
			}catch(Exception ex) {
				
			}
		}
		
		private static void SearchByTopics(){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your Topics for search");
			String topics =sc.nextLine();
			
			//given
			try {
			Question criteria = new Question();
			criteria.setTopics(topics);
			
			//when
			QuestionDAO dao = new QuestionDAO();
			List<Question> searchResults = new ArrayList<>();
			searchResults = dao.searchByTopics(criteria);		
			
			//then
			if (searchResults.size() > 0) {
				for(int i=0;i<searchResults.size();i++){  
					System.out.println(); 				//System.out.println(searchResults);
					System.out.println("Question:- " +searchResults.get(i).getSubject());
					System.out.println("Topics:- " + searchResults.get(i).getTopics());
					System.out.println("Difficulty:- "+searchResults.get(i).getDifficulty());
					System.out.println("QuestionType:- "+searchResults.get(i).getQuestiontype());
					System.out.println(); 		
					System.out.println(); 		
				} 
			
			}else {
				System.out.println("Not Found");
			}
			}catch(Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
		private static void ExportQuestion() throws SQLException, IOException{
			try {
			Scanner scan = new Scanner(System.in);	
			System.out.println("Please Enter the Quiz Name which you want to Export.");
			String Title = scan.nextLine(); 
			QuizDAO Quizd = new QuizDAO();
			QuestionDAO qsd = new QuestionDAO();
			int qid = Quizd.search(Title.toLowerCase());
			if(qid > 0) {
				qsd.GetQuizQuestions(qid);
			}
			else {
				System.out.println("No Quiz foud for Export");
			}
			}catch(Exception ex) {
				System.out.println(ex.toString());
			}
		}
		
		private static void StartQuiz() throws InterruptedException, SQLException {
			String StudentName;
			Student st = new Student();
			Scanner sc_name = new Scanner(System.in);
			System.out.println("Please Enter Your Name");
			
			StudentName = sc_name.nextLine();
			st.setName(StudentName);
			System.out.println("...............Welcome " +StudentName +"...............");
			
			Scanner  sc = new Scanner(System.in);
			System.out.println("Enter the Quiz Name You want to attempt");
			String QuizName = sc.nextLine();
			
			int QuizId =0;;
			try {
				QuizDAO qzd = new QuizDAO();
				 QuizId=qzd.search(QuizName.toLowerCase());		
				}catch (SQLException e) {
					System.out.println("Somthing in connection went wrong ");
					System.out.println("Good Bye");
					System.exit(1);
				}
			if(QuizId==0) {
				System.out.println("No Quiz Found with this name ");
				System.out.println("");
				 
			 }
			else
			{
				Process(StudentName, QuizName, QuizId);
				
			}
			
		}
		
		public static void Process(String SName, String QuizName, int Qid) throws SQLException {
			
			StudentDAO Sd = new StudentDAO();
			QuestionDAO qsd = new QuestionDAO();
			int TotalMarks = qsd.ProcessOnlineQuiz(Qid) ;	
			 try {
				 //StudentDAO Sd = new StudentDAO();
				Sd.insertStudentInfor(SName, QuizName, TotalMarks);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println("Congratulation..! Your Final Marks For this Quiz is : "+ TotalMarks );
			 System.out.println("Note:-Marks is based on only MCQ question ");
			 System.out.println();
		}
		
		public static void updatequestion() {
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the Question which you want to update");
			String Qus =sc.nextLine();
			try {
			Question criteria = new Question();
			criteria.setSubject(Qus);
			
			//when
			QuestionDAO dao = new QuestionDAO();
			List<Question> searchResults = dao.search(criteria);		
			
			//then
			if (searchResults.size() == 1) {
				//System.out.println("Your question is:- "+ searchResults);
				Scanner sc1 = new Scanner(System.in);
				System.out.println("Enter new question for update");
				String Qus_1 = sc1.nextLine();
				criteria.setSubject(Qus_1);
				criteria.setQuestionId(searchResults.get(0).getQuestionId());
				String [] Option = new String[4];
				Option[0]= searchResults.get(0).getOptions()[0];
				Option[1]= searchResults.get(0).getOptions()[1];
				Option[2]= searchResults.get(0).getOptions()[2];
				Option[3]= searchResults.get(0).getOptions()[3];
				criteria.setOptions(Option);
				String Qstype = searchResults.get(0).getQuestiontype();
				if(Qstype.equalsIgnoreCase("MCQ")) {
					System.out.println("Do you want to update its option?(Y/N)");
					Scanner sc2 = new Scanner(System.in);
					String chk = sc2.nextLine();
					if(chk.equalsIgnoreCase("Y"))
					{
						AddOptionsforupdate(criteria);
					}
					dao.update(criteria);
					CorrectAnswerOption(criteria.getQuestionId(),"U");
					System.out.println("Data updated Succesfully.....");	
			
			}else {
				dao.update(criteria);
			}
				}else {
				System.out.println("Not Found for update");
			}
			}catch(Exception ex) {
				
			}
		}
		
		public static void Deletequestion() {
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your Question");
			String Qus =sc.nextLine();
			try {
			Question criteria = new Question();
			criteria.setSubject(Qus);
			
			//when
			QuestionDAO dao = new QuestionDAO();
			List<Question> searchResults = dao.search(criteria);		
			
			//then
			if (searchResults.size() == 1) {
				//System.out.println("Your question is:- "+ searchResults);
				criteria.setQuestionId(searchResults.get(0).getQuestionId());
				dao.delete(criteria);
			
			}else {
				System.out.println("Not Found");
			}
			}catch(Exception ex) {
				
			}
		}
		
		private static void AskforTeacherMainMenu() 
		{
			Scanner Sc_Ques = new Scanner(System.in);
			System.out.println();
			System.out.println("1: Teacher Menu, 2: Main Menu (Enter your Choice(1|2))");
			int Qstype = Sc_Ques.nextInt();	    
			if(Qstype == 1) {	
				Teacher();
			}else {
				Main_Menu();
			}		
		}
		
		private static void AddOptionsforupdate(Question qs) {
			
			Scanner sc_op = new Scanner(System.in);
			
			String [] Option = new String[4];
			int max = 4;
			System.out.println("Enter 4 options");
			for(int i = 0 ;i<qs.getOptions().length ; i++) {
				String op = "";
				System.out.println("Option "+(i+1)+":  ");
				op = sc_op.nextLine();
				if(op.isEmpty() == false) {
					Option[i]=op;
				}
				else {
					Option[i]=qs.getOptions()[i];
				}
			}
			qs.setOptions(Option);
		}
		
		

}
