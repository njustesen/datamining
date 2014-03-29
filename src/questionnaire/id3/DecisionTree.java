package questionnaire.id3;

import questionnaire.models.Animal;
import questionnaire.models.Answer;
import mushrooms.models.Mushroom;

public class DecisionTree {
	
	ID3Node root;
	
	public DecisionTree(ID3Node root) {
		super();
		this.root = root;
	}

	public Animal getAnimal(Answer answer) {
		
		return root.getAnimal(answer);
		
	}

	public void print() {
		root.print(0, null);
	}

	public int count() {
		return root.count();
	}

}
