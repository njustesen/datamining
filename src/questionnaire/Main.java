package questionnaire;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import questionnaire.analyzers.DataAnalyzer;
import questionnaire.cleaners.Cleaner;
import questionnaire.models.Answer;
import questionnaire.readers.Reader;

public class Main {

	public static void main(String args[]) {
		try {
			// Read
			//String[][] data = new CSVFileReader().read("Data_Mining_Student_DataSet_Spring_2013.csv", false);
			List<List<String>> data = new Reader().read("Data_Mining_Student_DataSet_Spring_2013.csv");
			
			// Write
			for (List<String> line : data)
				System.out.println(Arrays.toString(line.toArray()));
			
			// Parse
			int rows = new Reader().rowsInHeader("Data_Mining_Student_DataSet_Spring_2013.csv");
			List<Answer> answers = new Cleaner().clean(data, rows);
			
			// Analyze
			new DataAnalyzer().analyse(answers);
			
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

}
