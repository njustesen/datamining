package questionnaire.analyzers;

import java.util.List;

import questionnaire.models.Answer;



public interface Analyzer {

	public void analyse(List<Answer> answers);
	
}
