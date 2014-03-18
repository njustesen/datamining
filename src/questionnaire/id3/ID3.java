package questionnaire.id3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import questionnaire.models.Animal;
import questionnaire.models.Answer;

import mushrooms.data.Class_Label;
import mushrooms.models.Mushroom;

public class ID3 {
	
	DecisionTree tree;
	
	public void generateDecisionTree(List<Answer> trainingTuples, ArrayList<Object> attributes){
		
		ID3Node root = induceDecisionTree(trainingTuples, attributes);
		tree = new DecisionTree(root);
		
	}
	
	private ID3Node induceDecisionTree(List<Answer> answers, ArrayList<Object> attributes) {
		
		ID3Node node = new ID3Node();
		node.setAnswers(answers);
		
		if (sameClass(answers)){
			node.setAnimal(answers.get(0).getAnimal());
			return node;
		}
		
		if (attributes.isEmpty()){
			Animal majorityAnimal = majorityAnimal(answers);
			node.setAnimal(majorityAnimal);
			return node;
		}
		
		Object bestAttr = bestSplittingAttr(answers, attributes);
		node.setAttribute((Class)bestAttr);
		attributes.remove(bestAttr);
		
		node.setChildren(new HashMap<Object, ID3Node>());
		for(Object c : ((Class)bestAttr).getEnumConstants()){
			
			List<Answer> subset = createSubset(answers, bestAttr, c);
			ID3Node child = new ID3Node();
			if(subset.isEmpty())
				child.setAnimal(majorityAnimal(answers));
			else
				node.getChildren().put(c, induceDecisionTree(subset, clone(attributes)));
			
		}
		
		return node;
	}

	private ArrayList<Object> clone(ArrayList<Object> attributes) {
		ArrayList<Object> clone = new ArrayList<Object>();
		for(Object obj : attributes){
			clone.add(obj);
		}
		return clone;
	}

	private List<Answer> createSubset(List<Answer> answers, Object attribute, Object value) {
		
		List<Answer> subset = new ArrayList<Answer>();
		
		for(Answer a : answers){
			if (a.getAttributeValue(attribute).equals(value))
				subset.add(a);
		}
		
		return subset;
	}

	private Object bestSplittingAttr(List<Answer> answers, ArrayList<Object> attributes) {
		
		//Object attr = attributes.get(0);
		
		float info = info(answers);
		
		float bestInfo = 0;
		Object bestAttr = null;
		for(Object obj : attributes){
			float infoAttr = info - infoAttr(obj, answers);
			if (infoAttr > bestInfo){
				bestAttr = obj;
				bestInfo = infoAttr;
			}
		}
		
		return bestAttr;
	}

	private float infoAttr(Object attr, List<Answer> answers) {
		
		float info = 0;
		for(Object label : ((Class)attr).getEnumConstants()){
			
			// Count prop
			List<Answer> Dj = new ArrayList<Answer>();
			for(Answer answer : answers){
				if(answer.getAttributeValue(attr).equals(label))
					Dj.add(answer);
			}
			
			// Calc info
			float prop = (float)Dj.size() / (float)answers.size();
			float infoD = info(Dj);
			float infoLabel = prop * infoD;
			
			info += infoLabel;
			
		}
		
		return info;
	}

	private float info(List<Answer> answers) {
		
		if (answers.isEmpty())
			return 1;
		
		// Count prop
		Map<Animal, Float> infoMap = new HashMap<Animal, Float>();
		for (Answer a : answers){
			if (infoMap.containsKey(a.getAnimal())){
				float c = infoMap.get(a.getAnimal());
				infoMap.put(a.getAnimal(), c+1);
			} else {
				infoMap.put(a.getAnimal(), 0f);
			}
		}
		
		// Calc infomation gains
		for(Animal animal : infoMap.keySet()){
			float count = infoMap.get(animal);
			float info = (float) ((count/answers.size()) * Math.log(count/answers.size()));
			infoMap.put(animal, info);
		}
	
		float info = 0f;
		for(Animal animal : infoMap.keySet()){
			info -= infoMap.get(animal);
		}
		
		return info;
	}

	private Animal majorityAnimal(List<Answer> answers) {
		Map<Animal, Integer> countMap = new HashMap<Animal, Integer>();
		
		for (Answer a : answers){
			if (countMap.containsKey(a.getAnimal())){
				int c = countMap.get(a.getAnimal());
				countMap.put(a.getAnimal(), c+1);
			} else {
				countMap.put(a.getAnimal(), 0);
			}
		}
		
		int max = 0;
		Animal maxAnimal = null;
		for(Animal animal : countMap.keySet()){
			if (countMap.get(animal) > max){
				max = countMap.get(animal);
				maxAnimal = animal;
			}
		}
		
		return maxAnimal;
	}

	private boolean sameClass(List<Answer> answers) {
		Animal label = null;
		for(Answer answer : answers){
			if (label == null)
				label = answer.getAnimal();
			else if (label != answer.getAnimal())
				return false;
		}
		return true;
	}

	public Animal getAnimal(Answer answer){
		
		if (tree == null)
			throw new IllegalStateException("ID3 not set up!");
		
		return tree.getAnimal(answer);
		
	}

	public DecisionTree getTree() {
		return tree;
	}

	public void setTree(DecisionTree tree) {
		this.tree = tree;
	}
	
}
