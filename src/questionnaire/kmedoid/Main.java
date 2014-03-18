package questionnaire.kmedoid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import questionnaire.cleaners.Cleaner;
import questionnaire.models.Answer;
import questionnaire.readers.Reader;

import questionnaire.kmedoid.KMedoid;
import questionnaire.kmedoid.KMedoidCluster;

public class Main {

	public static void main(String args[]) throws ParseException {
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
		
			//Third step --> do the clustering using k-medoids!
			List<KMedoidCluster> FoundClusters_KMedoids = KMedoid.KMedoidPartition(4, 10000, answers);
			
			for(KMedoidCluster cluster : FoundClusters_KMedoids){
				System.out.println(cluster);
				for(Answer answer : cluster.ClusterMembers){
					if (answer.getSkill() != null && answer.getEnglishLevel() != null && answer.getYearsStudy() != null){
						System.out.println(answer.getSkill() + "\t" + answer.getEnglishLevel() + "\t" + answer.getYearsStudy());
//					if (answer.getSkill() != null && answer.getEnglishLevel() != null){
//						System.out.println(answer.getSkill() + ";" + answer.getEnglishLevel());
						
						/*
						System.out.print("Age: " + answer.getAge() + ", skill: " + answer.getSkill() + ", english: " + answer.getEnglishLevel());
						if (!answer.getProLanParsed().isEmpty())
							System.out.print(", programming: " + answer.getProLanParsed().get(0));
							*/
					}
					//System.out.print("\n");
				}
			}
		} catch (Exception e){
			System.out.println(e);
		}
		
	}


}
