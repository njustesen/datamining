package questionnaire.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class Reader {
	/**
	 * The read method reads in a csv file as a two dimensional string array.
	 * Please note it is assumed that ';' is used as separation character.
	 * @param csvFile Path to file
	 * @param useNullForBlank Use empty string for missing values?
	 * @return Two dimensional string array containing the data from the csv file
	 * @throws IOException
	 */
	public List<List<String>> read(String csvFile)throws IOException {
		
		List<List<String>> lines = new ArrayList<List<String>>();

		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
		
		// read the header
		String line = bufRdr.readLine();

		// read each line of text file
		while ((line = bufRdr.readLine()) != null){
			line = line.replace(";;", "; ;");
			lines.add(Arrays.asList(line.split(";")));
		}
		
		bufRdr.close();
		return lines;
	}

	public int rowsInHeader(String csvFile) throws IOException {
		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
		
		// read the header
		String line = bufRdr.readLine();
		return line.split(";").length;

	}

}
