package questionnaire;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import questionnaire.analyzers.DataAnalyzer;
import questionnaire.cleaners.Cleaner;
import questionnaire.models.Answer;
import questionnaire.readers.Reader;

public class Main {

	public static void main(String args[]) {
		
		analyse();
		
	}

	private static void analyse() {
		try {
			// Read
			List<List<String>> data = new Reader().read("Data_Mining_Student_DataSet_Spring_2013.csv");
			Date dateOfData = new SimpleDateFormat("yyyy-MM-dd").parse("2013-02-06");
			
			// Write
			for (List<String> line : data)
				System.out.println(Arrays.toString(line.toArray()));
			
			// Parse
			int rows = new Reader().rowsInHeader("Data_Mining_Student_DataSet_Spring_2013.csv");
			List<Answer> answers = new Cleaner().clean(data, rows, dateOfData);
			
			// Analyze
			new DataAnalyzer().analyse(answers);
			
		} catch (IOException | ParseException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	

}
