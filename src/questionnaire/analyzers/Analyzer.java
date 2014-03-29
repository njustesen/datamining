package questionnaire.analyzers;

import java.util.List;

import questionnaire.models.Answer;

/**
 * The purpose of Analyzer is to write statistics to the console based on a list of answers.
 * @author Niels
 *
 */
public interface Analyzer {

	public void analyse(List<Answer> answers);
	
}
