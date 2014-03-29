package questionnaire.id3;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import questionnaire.cleaners.Cleaner;
import questionnaire.id3.attributes.AGE;
import questionnaire.id3.attributes.CPLUSPLUS;
import questionnaire.id3.attributes.CSHARP;
import questionnaire.id3.attributes.ENGLISH_LEVEL;
import questionnaire.id3.attributes.JAVA;
import questionnaire.id3.attributes.PYTHON;
import questionnaire.id3.attributes.YEARS_OF_STUDY;
import questionnaire.models.Animal;
import questionnaire.models.Answer;
import questionnaire.readers.Reader;

/**
 * An implementation of the ID3 algorithm for student answers.
 * 
 * @author Niels
 *
 */
public class ID3 {
	
	DecisionTree tree;
	
	public void generateDecisionTree(List<Answer> trainingTuples, ArrayList<Object> attributes){
		
		ID3Node root = induceDecisionTree(trainingTuples, attributes);
		tree = new DecisionTree(root);
		
	}
	
	/**
	 * Run this to test ID3.
	 * @param args
	 */
	public static void main(String args[]) {
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
			
			// Remove answers with no answered animal
			List<Answer> filtered = new ArrayList<Answer>();
			for(Answer answer : answers){
				if (answer.getAnimal() != null)
					filtered.add(answer);
			}
			answers = filtered;
			
			// Attributes
			ArrayList<Object> attributes = new ArrayList<Object>();
			attributes.add(AGE.class);
			attributes.add(CPLUSPLUS.class);
			attributes.add(CSHARP.class);
			attributes.add(ENGLISH_LEVEL.class);
			attributes.add(JAVA.class);
			attributes.add(PYTHON.class);
			attributes.add(YEARS_OF_STUDY.class);
			
			// Training tuples
			int k = 8;
			int step = answers.size()/k;
			
			int correct = 0;
			int incorrect = 0;
			
			for(int i = 0; i < k; i++){
				int from = step*i;
				int to = from + step + 1;
				
				List<Answer> trainingTuples = new ArrayList<Answer>();
				List<Answer> testTuples = answers.subList(from, to);
				for(Answer answer : answers)
					if (!testTuples.contains(answer))
						trainingTuples.add(answer);
				
				System.out.println("Training tuples: " + trainingTuples.size());
				System.out.println("Test tuples: " + testTuples.size());
				
				// ID3
				ID3 id3 = new ID3();
				ArrayList<Object> attrs = new ArrayList<Object>();
				for(Object attr : attributes)
					attrs.add(attr);
				id3.generateDecisionTree(trainingTuples, attrs);
				
				// Test
				System.out.println(id3.getTree().count());
				id3.getTree().print();
				
				for(Answer answer : testTuples){
					if ((answer.getAnimal() == id3.getAnimal(answer))){
						correct++;
						//System.out.println("Correct!");
					} else {
						incorrect++;
						//System.out.println("Incorrect!");
					}
				}
			}
			
			System.out.println("-------------");
			System.out.println("Correct: " + correct);
			System.out.println("Inorrect: " + incorrect);
			System.out.println("Accuracy: " + (float)((float)correct / (correct+ incorrect)));
			
			
		} catch (IOException | ParseException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * Generates a decision tree based on the answers and attributes.
	 * @param answers
	 * @param attributes
	 * @return
	 */
	private ID3Node induceDecisionTree(List<Answer> answers, ArrayList<Object> attributes) {
		
		ID3Node node = new ID3Node();
		node.setAnswers(answers);
		
		// If answers have same class we are done
		if (sameClass(answers)){
			node.setAnimal(answers.get(0).getAnimal());
			return node;
		}
		
		// If no more attributes return majority class
		if (attributes.isEmpty()){
			Animal majorityAnimal = majorityAnimal(answers);
			node.setAnimal(majorityAnimal);
			return node;
		}
		
		// Split by best attribute
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
		
		Object attr = attributes.get(0);
		
		float info = info(answers);
		
		float bestInfo = 0;
		Object bestAttr = null;
		for(Object obj : attributes){
			float infoAttr = info - infoAttr(obj, answers);
			if (infoAttr >= bestInfo){
				bestAttr = obj;
				bestInfo = infoAttr;
			}
		}
		return bestAttr;
		//return attr; // Uncomment this to try the 'Pick first' method.
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
				infoMap.put(a.getAnimal(), 1f);
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
