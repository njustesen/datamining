package questionnaire.kmedoid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import questionnaire.cleaners.Cleaner;
import questionnaire.models.Answer;
import questionnaire.readers.Reader;

/**
 * An implementation of KMedoid for student answers.
 * @author Niels
 *
 */
public class KMedoid {
	
	/**
	 * Run this to test KMedoid.
	 * @param args
	 * @throws ParseException
	 */
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

	public static List<KMedoidCluster> KMedoidPartition(int k, int q, List<Answer> data)
	{
			
		List<Answer> seeds = selectRandomAnswer(k, data);
		
		List<KMedoidCluster> clusters = cluster(k, seeds, data);
		
		float utility = utility(clusters);
		
		int i = 0;
		while(i < q){
			
			List<Answer> newSeeds = changeRandomSeed(seeds, data);
			List<KMedoidCluster> newClusters = cluster(k, newSeeds, data);
			float newUtility = utility(newClusters);
			
			if (newUtility > utility){
				utility = newUtility;
				seeds = newSeeds;
				clusters = newClusters;
				i=0;
			} else {
				i++;
			}
		}
		
		System.out.println("Utility: " + utility);
		
		return clusters;
		
	}
	
	private static float utility(List<KMedoidCluster> clusters) {
		
		float distance = 0;
		
		for(KMedoidCluster cluster : clusters){
			for (Answer Answer : cluster.ClusterMembers){
				distance += distance(cluster.Medoid, Answer);
			}
		}
		
		return -distance;
	}

	private static List<Answer> changeRandomSeed(List<Answer> seeds, List<Answer> data) {
		
		// Clone seeds
		List<Answer> newSeeds = new ArrayList<Answer>();
		newSeeds.addAll(seeds);
		
		// Remove random seed
		int rs = (int) Math.floor(Math.random() * seeds.size());
		Answer randomSeed = seeds.get(rs);
		newSeeds.remove(randomSeed);
		
		// Add random non-seed
		Answer randomAnswer = null;
		while(true){
			int ri = (int) Math.floor(Math.random() * data.size());
			randomAnswer = data.get(ri);
			if (!seeds.contains(randomAnswer))
				break;
		}
		newSeeds.add(randomAnswer);
		
		return newSeeds;
	}

	private static List<KMedoidCluster> cluster(int k, List<Answer> seeds, List<Answer> data) {
		
		// Make cluster map
		List<KMedoidCluster> clusters = new ArrayList<KMedoidCluster>();
		
		// Add seeds
		for (Answer seed : seeds)
			clusters.add(new KMedoidCluster(seed));
		
		// Add Answer to clusters
		for(Answer Answer : data){
			KMedoidCluster closestCluster = null;
			float closestDistance = 1000000f;
			for (KMedoidCluster cluster : clusters){
				float distance = distance(Answer, cluster.Medoid);
				if (distance < closestDistance){
					closestDistance = distance;
					closestCluster = cluster;
				}
			}
			closestCluster.ClusterMembers.add(Answer);
		}
		
		return clusters;
	}
	
	private static float distance(Answer a, Answer b) {

		float skillDis 		= 	10f;
		if(a.getSkill() != null && b.getSkill() != null)
			skillDis = a.getSkill() - b.getSkill();
		
		float englishDis 	= 	10f;
		if(a.getEnglishLevel() != null && b.getEnglishLevel() != null)
			englishDis = (a.getEnglishLevel() - b.getEnglishLevel()) * 0.2f;
		
		float yearsStudy 	= 10f;
		if(a.getYearsStudy() != null && b.getYearsStudy() != null)
			yearsStudy = (a.getYearsStudy() - b.getYearsStudy());
		
		/*
		 * Uncomment for 3D clustering
		float proDis = 0f;
		for (ProgrammingLanguage lang : a.getProLanParsed()){
			if (!b.getProLanParsed().contains(lang))
				proDis += 1;
		}
		for (ProgrammingLanguage lang : b.getProLanParsed()){
			if (!a.getProLanParsed().contains(lang))
				proDis += 1;
		}
		*/
		float distance = (float) Math.sqrt(skillDis*skillDis + englishDis*englishDis + yearsStudy*yearsStudy);
//		float distance = (float) Math.sqrt(skillDis*skillDis + englishDis*englishDis); // Uncomment for 3D clustering
		return distance;
	}

	private static List<Answer> selectRandomAnswer(int k, List<Answer> data) {
		
		List<Answer> selected = new ArrayList<Answer>();
		
		while(selected.size() < k){
			int rand = (int) Math.floor( Math.random() * data.size() );
			Answer random = data.get(rand);
			if(!selected.contains(random))
				selected.add(random);
		}
		
		return selected;
	}
	
}
