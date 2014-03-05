package iris;

import java.util.List;

import iris.kMean.KMeanCluster;
import iris.kMean.KMeans;
import iris.kMedoid.KMedoid;
import iris.kMedoid.KMedoidCluster;
import iris.data.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//First step load in iris data
		List<Iris> irisData = DataLoader.LoadAllIrisData();
		
		//Second step --> do the clustering using k-means!
		/*
		List<KMeanCluster> FoundClusters_KMeans = KMeans.KMeansPartition(3, irisData);
		
		for(KMeanCluster cluster : FoundClusters_KMeans){
			System.out.println("--- Cluster ---");
			for(Iris iris : cluster.ClusterMembers){
				System.out.println(iris.Class);
			}
		}
		*/
		
		//Third step --> do the clustering using k-medoids!
		List<KMedoidCluster> FoundClusters_KMedoids = KMedoid.KMedoidPartition(3, 10000, irisData);
		
		for(KMedoidCluster cluster : FoundClusters_KMedoids){
			System.out.println("--- Cluster ---");
			for(Iris iris : cluster.ClusterMembers){
				System.out.println(iris.Class);
			}
		}
		
	}


}
