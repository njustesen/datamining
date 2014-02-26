package iris.kMean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iris.data.*;


public class KMeans {

	public static List<KMeanCluster> KMeansPartition(int k, List<Iris> data)
	{
		
		List<Iris> seeds = selectRandomIris(k, data);
		
		while(true){
			
			// Assign to clusters
			Map<Iris, KMeanCluster> clusters = assignToClusters(k, seeds, data);
			
			// Update cluster means
			Map<Iris, KMeanCluster> newClusters = updateClusterMeans(clusters);
			
			// Change?
			Object[] oldMeans = clusters.keySet().toArray();
			Object[] newMeans = newClusters.keySet().toArray();
			System.out.println("Old means:");
			for(int i = 0; i < oldMeans.length; i++)
				System.out.println(oldMeans[i]);
			System.out.println("New means:");
			for(int i = 0; i < newMeans.length; i++)
				System.out.println(newMeans[i]);
			
			boolean change = false;
			for(int i = 0; i < oldMeans.length; i++){
				if (!((Iris)oldMeans[i]).sameProperties((Iris)newMeans[i])){
					change = true;
					break;
				}
			}

			if (!change)
				return new ArrayList<KMeanCluster>(newClusters.values());
			
			seeds = new ArrayList<Iris>(newClusters.keySet());
			
		}
		
	}

	private static Map<Iris, KMeanCluster> updateClusterMeans(Map<Iris, KMeanCluster> clusters) {
		
		//boolean change = false;
		Map<Iris, KMeanCluster> newClusters = new HashMap<>();
		
		for(Iris oldMean : clusters.keySet()){
			
			Iris mean = createMean(clusters.get(oldMean));
			
			if (mean.sameProperties(oldMean)){
				newClusters.put(oldMean, clusters.get(oldMean));
				continue;
			}
			
			//change = true;
			newClusters.put(mean, clusters.get(oldMean));
			
		}
		
		return newClusters;
		
	}

	private static Iris createMean(KMeanCluster cluster) {
		
		//IrisClass meanClass = IrisClass.Iris_setosa;
		Iris mean = new Iris(0, 0, 0, 0, "MEAN");
		//Map<IrisClass, Integer> classCount = new HashMap<>();
		
		for(Iris iris : cluster.ClusterMembers){
			mean.Petal_Length 	+= iris.Petal_Length;
			mean.Petal_Width 	+= iris.Petal_Width;
			mean.Sepal_Length 	+= iris.Sepal_Length;
			mean.Sepal_Width 	+= iris.Sepal_Width;
			/*
			Integer cc = classCount.get(iris.Class)+1;
			classCount.put(iris.Class, cc);
			*/
		}
		
		mean.Petal_Length 	= mean.Petal_Length / cluster.ClusterMembers.size();
		mean.Petal_Width 	= mean.Petal_Width 	/ cluster.ClusterMembers.size();
		mean.Sepal_Length 	= mean.Sepal_Length / cluster.ClusterMembers.size();
		mean.Sepal_Width 	= mean.Sepal_Width 	/ cluster.ClusterMembers.size();
		/*
		for(IrisClass c : classCount.keySet()){
			if (classCount.get(c) > classCount.get(meanClass))
				meanClass = c;
		}
		*/
		return mean;
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
