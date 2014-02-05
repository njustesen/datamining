package analyzers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Statistics {

	public static Float mean(List<Float> floats) {
		
		if (floats.isEmpty())
			return null;
			
		float sum = 0;
		for(Float f : floats)
			sum += f;
		return sum / floats.size();
		
	}
	
	public static Float median(List<Float> floats) {
		
		if (floats.isEmpty())
			return null;
		
		Collections.sort(floats);
		
		// odd
		if (floats.size() % 2 == 1)
			return floats.get(floats.size() / 2);
		
		// even
		int firstIdx = (int) Math.floor(floats.size() / 2f);
		int secondIdx = (int) Math.ceil(floats.size() / 2f);
		
		List<Float> middleVals = new ArrayList<Float>();
		middleVals.add(floats.get(firstIdx));
		middleVals.add(floats.get(secondIdx));
		
		return mean(middleVals);
		
	}
	
	public static Float quantile(List<Float> floats, int quantile) {
		
		if (floats.isEmpty())
			return null;
		
		if (quantile < 0 || quantile > 100)
			return null;
		
		Collections.sort(floats);
		
		return floats.get((int) (floats.size() * ((0f+quantile)/100f)));
		
	}
	
	public static List<Float> mode(List<Float> floats) {
		
		HashMap<Float, List<Integer>> countMap = new HashMap<Float, List<Integer>>();
		
		for(Float f : floats){
			if (countMap.containsKey(f)){
				countMap.get(f).add(1);
			} else {
				List<Integer> list = new ArrayList<Integer>();
				list.add(1);
				countMap.put(f, list);
			}
		}
		
		List<Float> modes = new ArrayList<Float>();
		int maxCount = 0; 
		for(Float f : countMap.keySet()){
			if (countMap.get(f).size() == 1){
				continue;
			}
			if (countMap.get(f).size() == maxCount){
				modes.add(f);
			}else if (countMap.get(f).size() > maxCount){
				modes.clear();
				modes.add(f);
			}
		}
			
		return modes;
		
	}

	public static Float min(List<Float> floats) {
		if (floats.isEmpty())
			return null;
		float min = Float.MAX_VALUE;
		for(Float f : floats){
			if (f < min)
				min = f;
		}
		return min;
	}
	
	public static Float max(List<Float> floats) {
		if (floats.isEmpty())
			return null;
		float max = Float.MIN_VALUE;
		for(Float f : floats){
			if (f > max)
				max = f;
		}
		return max;
	}

	
	
	
}
