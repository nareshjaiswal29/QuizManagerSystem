package fr.epita.quiz.datamodel;

public class MCQChoice {

	private int choiceLabel;

	private boolean valid;
	
	public int getChoiceLabel() {
		return choiceLabel;
	}

	public void setChoiceLabel(int choiceLabel) {
		this.choiceLabel = choiceLabel;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	
}
