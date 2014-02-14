package mushrooms.classifiers.ID3;

import mushrooms.models.Mushroom;

public class DecisionTree {
	
	ID3Node root;
	
	public DecisionTree(ID3Node root) {
		super();
		this.root = root;
	}

	public boolean isEdible(Mushroom mushroom) {
		
		return root.isEdible(mushroom);
		
	}

	public void print() {
		root.print(0, null);
	}

}
