package mushrooms;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import mushrooms.classifiers.KNN;
import mushrooms.data.Cap_Shape;
import mushrooms.data.Class_Label;
import mushrooms.data.DataManager;
import mushrooms.models.Mushroom;

/**
 * Main class to run program from.
 * 
 * @author Anders Hartzen
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// First step - Load data and convert to Mushroom objects.
		List<Mushroom> mushrooms = DataManager.LoadData();
		System.out.println("DataManager loaded "+mushrooms.size() + " mushrooms");
		List<Mushroom> trainingSet = mushrooms.subList(0, mushrooms.size()/10*9);
		List<Mushroom> testSet = mushrooms.subList(mushrooms.size()/10*9, mushrooms.size());
		KNN knn = new KNN(trainingSet, 10);
		int correct = 0;
		int incorrect = 0;
		for(Mushroom mushroom : testSet){
			if ((mushroom.m_Class == Class_Label.edible) == (knn.isEdible(mushroom))){
				correct++;
				//System.out.println("Correct!");
			} else {
				incorrect++;
				//System.out.println("Incorrect!");
			}
		}
		System.out.println("-------------");
		System.out.println("Correct: " + correct);
		System.out.println("Inorrect: " + incorrect);
		System.out.println("Accuracy: " + (float)((float)correct / (float)testSet.size()) * 100);
		
	}

}
