package analyzers;

import java.util.List;

import models.Answer;


public interface Analyzer {

	public void analyse(List<Answer> answers);
	
}
