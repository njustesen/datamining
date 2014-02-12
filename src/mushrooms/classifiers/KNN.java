package mushrooms.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mushrooms.data.Class_Label;
import mushrooms.models.Mushroom;

public class KNN {

	List<Mushroom> trainingSet;
	int k;
	
	public KNN(List<Mushroom> trainingSet, int k) {
		super();
		this.trainingSet = trainingSet;
		this.k = k;
	}
	
	public boolean isEdible(Mushroom mushroom){
		
		Map<Mushroom, Float> kNearest = new HashMap<Mushroom, Float>(k);
		
		for(Mushroom other : trainingSet){
			float distance = distance(mushroom, other);
			float longest = longestDistance(kNearest);
			if (distance < longest || kNearest.size() < k){
				if (kNearest.size() == k)
					removeWorst(kNearest, longest);
				kNearest.put(other, distance);
			}
		}
		
		return meanEdibleValue(kNearest) >= 0.5;
		
	}

	private float meanEdibleValue(Map<Mushroom, Float> kNearest) {
		float edible = 0f;
		for(Mushroom mushroom : kNearest.keySet()){
			edible += mushroom.m_Class == Class_Label.edible ? 1 : 0;
		}
		return edible / kNearest.size();
	}

	private Mushroom removeWorst(Map<Mushroom, Float> mushrooms, float longest) {
		for(Mushroom mushroom : mushrooms.keySet()){
			if (mushrooms.get(mushroom) == longest){
				mushrooms.remove(mushroom);
				return mushroom;
			}	
		}
		return null;
	}

	private float longestDistance(Map<Mushroom, Float> mushrooms) {
		float longest = 0f;
		for(Mushroom mushroom : mushrooms.keySet()){
			float distance = mushrooms.get(mushroom);
			if (distance > longest)
				longest = distance;
		}
		return longest;
	}

	private float distance(Mushroom mushroom, Mushroom other) {
		
		float distance = 0f;
		
		for(int i = 0; i<Mushroom.getAttributeList().size(); i++){
			Object attr = Mushroom.getAttributeList().get(i);
			if (!mushroom.getAttributeValue(attr).equals(other.getAttributeValue(attr)))
				distance += 1f;
		}
		
		return distance;
	}
	
}
