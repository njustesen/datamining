package mushrooms.classifiers.ID3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mushrooms.data.Class_Label;
import mushrooms.models.Mushroom;

public class ID3 {
	
	DecisionTree tree;
	
	public void generateDecisionTree(List<Mushroom> trainingTuples){
		
		ID3Node root = induceDecisionTree(trainingTuples, Mushroom.getAttributeList());
		tree = new DecisionTree(root);
		
	}
	
	private ID3Node induceDecisionTree(List<Mushroom> mushrooms, ArrayList<Object> attributes) {
		
		ID3Node node = new ID3Node();
		node.setMushrooms(mushrooms);
		
		if (sameClass(mushrooms)){
			node.setEdible(mushrooms.get(0).m_Class == Class_Label.edible);
			return node;
		}
		
		if (attributes.isEmpty()){
			boolean edible = isMajorityEdible(mushrooms);
			node.setEdible(edible);
			return node;
		}
		
		Object bestAttr = bestSplittingAttr(mushrooms, attributes);
		node.setAttribute((Class)bestAttr);
		attributes.remove(bestAttr);
		
		node.setChildren(new HashMap<Object, ID3Node>());
		for(Object c : ((Class)bestAttr).getEnumConstants()){
			
			List<Mushroom> subset = createSubset(mushrooms, bestAttr, c);
			ID3Node child = new ID3Node();
			if(subset.isEmpty())
				child.setEdible(isMajorityEdible(mushrooms));
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

	private List<Mushroom> createSubset(List<Mushroom> mushrooms, Object attribute, Object value) {
		
		List<Mushroom> subset = new ArrayList<Mushroom>();
		
		for(Mushroom m : mushrooms){
			if (m.getAttributeValue(attribute).equals(value))
				subset.add(m);
		}
		
		return subset;
	}

	private Object bestSplittingAttr(List<Mushroom> mushrooms, ArrayList<Object> attributes) {
		
		Object attr = attributes.get(0);
		
		return attr;
	}

	private boolean isMajorityEdible(List<Mushroom> mushrooms) {
		int e = 0;
		for (Mushroom m : mushrooms){
			if (m.m_Class == Class_Label.edible)
				e++;
		}
		return e > mushrooms.size() / 2;
	}

	private boolean sameClass(List<Mushroom> mushrooms) {
		Class_Label label = null;
		for(Mushroom m : mushrooms){
			if (label == null)
				label = m.m_Class;
			else if (label != m.m_Class)
				return false;
		}
		return true;
	}

	public boolean isEdible(Mushroom mushroom){
		
		if (tree == null)
			throw new IllegalStateException("ID3 not set up!");
		
		return tree.isEdible(mushroom);
		
	}

	public DecisionTree getTree() {
		return tree;
	}

	public void setTree(DecisionTree tree) {
		this.tree = tree;
	}
	
}
