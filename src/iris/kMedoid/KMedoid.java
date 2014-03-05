package iris.kMedoid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iris.data.Iris;
import iris.kMean.KMeanCluster;

public class KMedoid {

	public static List<KMedoidCluster> KMedoidPartition(int k, int q, List<Iris> data)
	{
			
		List<Iris> seeds = selectRandomIris(k, data);
		
		List<KMedoidCluster> clusters = cluster(k, seeds, data);
		
		float utility = utility(clusters);
		
		int i = 0;
		while(i < q){
			
			List<Iris> newSeeds = changeRandomSeed(seeds, data);
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
			for (Iris iris : cluster.ClusterMembers){
				distance += distance(cluster.Medoid, iris);
			}
		}
		
		return -distance;
	}

	private static List<Iris> changeRandomSeed(List<Iris> seeds, List<Iris> data) {
		
		// Clone seeds
		List<Iris> newSeeds = new ArrayList<Iris>();
		newSeeds.addAll(seeds);
		
		// Remove random seed
		int rs = (int) Math.floor(Math.random() * seeds.size());
		Iris randomSeed = seeds.get(rs);
		newSeeds.remove(randomSeed);
		
		// Add random non-seed
		Iris randomIris = null;
		while(true){
			int ri = (int) Math.floor(Math.random() * data.size());
			randomIris = data.get(ri);
			if (!seeds.contains(randomIris))
				break;
		}
		newSeeds.add(randomIris);
		
		return newSeeds;
	}

	private static List<KMedoidCluster> cluster(int k, List<Iris> seeds, List<Iris> data) {
		
		// Make cluster map
		List<KMedoidCluster> clusters = new ArrayList<KMedoidCluster>();
		
		// Add seeds
		for (Iris seed : seeds)
			clusters.add(new KMedoidCluster(seed));
		
		// Add iris to clusters
		for(Iris iris : data){
			KMedoidCluster closestCluster = null;
			float closestDistance = 1000000f;
			for (KMedoidCluster cluster : clusters){
				float distance = distance(iris, cluster.Medoid);
				if (distance < closestDistance){
					closestDistance = distance;
					closestCluster = cluster;
				}
			}
			closestCluster.ClusterMembers.add(iris);
		}
		
		return clusters;
	}
	
	private static float distance(Iris a, Iris b) {
		
		float plDis = a.Petal_Length 	- 	b.Petal_Length;
		float pwDis = a.Petal_Width		-	b.Petal_Width;
		float slDis = a.Sepal_Length	-	b.Sepal_Length;
		float swDis = a.Sepal_Width		-	b.Sepal_Width;
		float distance = (float) Math.sqrt(plDis*plDis + pwDis*pwDis + slDis*slDis + swDis*swDis);
		
		return distance;
	}

	private static List<Iris> selectRandomIris(int k, List<Iris> data) {
		
		List<Iris> selected = new ArrayList<Iris>();
		
		while(selected.size() < k){
			int rand = (int) Math.floor( Math.random() * data.size() );
			Iris random = data.get(rand);
			if(!selected.contains(random))
				selected.add(random);
		}
		
		return selected;
	}
	
}
