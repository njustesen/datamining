import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import readers.Reader;

import cleaners.Cleaner;

import models.Answer;

import analyzers.DataAnalyzer;


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
