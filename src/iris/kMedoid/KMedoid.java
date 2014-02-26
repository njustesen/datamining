package iris.kMedoid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iris.data.Iris;
import iris.kMean.KMeanCluster;

public class KMedoid {

	public static List<KMedoidCluster> KMedoidPartition(int k, List<Iris> data)
	{
			
		List<Iris> seeds = selectRandomIris(k, data);
		
		// Assign to clusters
		Map<Iris, KMeanCluster> bestClusters = assignToClusters(k, seeds, data);
		/*			
		while(true){
			
			
			
		}
		*/
		return null;
		
	}
	
	private static Map<Iris, KMeanCluster> assignToClusters(int k, List<Iris> seeds, List<Iris> data) {
		
		// Make cluster map
		Map<Iris, KMeanCluster> clusters = new HashMap<>();
		for(Iris seed : seeds){
			clusters.put(seed, new KMeanCluster());
		}
		
		// Add data to clusters
		for(Iris iris : data){
			
			Iris closestSeed = null;
			float minDistance = Float.MAX_VALUE;
			
			for(Iris seed : seeds){
				float distance = distance(iris, seed);
				if (distance < minDistance){
					minDistance = distance;
					closestSeed = seed;
				}
			}
			
			clusters.get(closestSeed).ClusterMembers.add(iris);
			
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
