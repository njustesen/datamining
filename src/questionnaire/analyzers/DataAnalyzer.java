package questionnaire.analyzers;

import java.util.ArrayList;
import java.util.List;

import questionnaire.models.Answer;



public class DataAnalyzer implements Analyzer {

	@Override
	public void analyse(List<Answer> answers) {
		
		ageStats(answers);
		
	}

	private void ageStats(List<Answer> answers) {
		
		// Average
		List<Float> ages = new ArrayList<Float>();
		for(Answer answer : answers){
			if (answer.getAge() == null)
				continue;
			ages.add(0.0f + answer.getAge());
		}
		
		float min = Statistics.min(ages);
		float max = Statistics.max(ages);
		float mean = Statistics.mean(ages);
		float median = Statistics.median(ages);
		List<Float> modes = Statistics.mode(ages);
		float quantile25 = Statistics.quantile(ages, 25);
		float quantile50 = Statistics.quantile(ages, 75);
		
		System.out.println("Min="+min);
		System.out.println("Max="+max);
		System.out.println("Ages="+ages);
		System.out.println("Mean="+mean);
		System.out.println("Median="+median);
		System.out.println("Mode="+modes);
		System.out.println("Quantile25="+quantile25);
		System.out.println("Quantile50="+quantile50);
		
	}

}
