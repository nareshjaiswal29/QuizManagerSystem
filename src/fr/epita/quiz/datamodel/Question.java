package fr.epita.quiz.datamodel;

public class Question {
	
	private String subject;
	
	private String topics;
	
	private Integer difficulty;
	
	private String Question;
	
	private int questionId;
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public int getQuizid() {
		return quizid;
	}

	public void setQuizid(int quizid) {
		this.quizid = quizid;
	}

	private String[] options;
	
	private int quizid;
	
	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = question;
	}

	public String getQuestiontype() {
		return Questiontype;
	}

	public void setQuestiontype(String questiontype) {
		Questiontype = questiontype;
	}

	private String Questiontype;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	
	
	
}
