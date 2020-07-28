package fr.epita.quiz.services;

public class CreateFailedException extends Exception {

	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	

}
