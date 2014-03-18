package questionnaire.id3;

import java.util.List;
import java.util.Map;

import questionnaire.models.Animal;
import questionnaire.models.Answer;

import mushrooms.models.Mushroom;

public class ID3Node {

	private Class attribute;
	private List<Answer> answers;
	private Map<Object, ID3Node> children;
	private Animal animal;
	
	public ID3Node() {
		super();
	}
	
	public ID3Node(Class attribute, List<Mushroom> mushrooms) {
		super();
		this.attribute = attribute;
		this.answers = answers;
		this.animal = null;
	}

	public Class getAttribute() {
		return attribute;
	}

	public void setAttribute(Class attribute) {
		this.attribute = attribute;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Map<Object, ID3Node> getChildren() {
		return children;
	}

	public void setChildren(Map<Object, ID3Node> children) {
		this.children = children;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public void print(int level, Object value) {
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
		
		String attr = attribute == null ? "null" : attribute.getName();
		String m = String.valueOf((answers == null ? "null" : answers.size()));
		String v = value == null ? "null" : ((Enum)value).name();
		if (animal == null){
			str += "<node value='" + v + "' split='" + attr + "' mushrooms='" + m + "'>";
		}else {
			str += "<node value='" + v + "' animal='" + animal.name() + "'>";
		}
		
		System.out.println(str);
		
		if (children != null){
			for(Object obj : children.keySet()){
				//str += "\t" + obj.getClass().getName();
				children.get(obj).print(level+1, obj);
			}
		}
		
		str = "";
		for(int i = 0; i < level; i++){
			str += "\t";
		}
		System.out.println(str + "</node>");
		
	}

	public Animal getAnimal(Answer answer) {
		
		if (animal!=null)
			return animal;
		
		for(Object obj : children.keySet()){
			
			if (answer.getAttributeValue(attribute) == obj)
				return children.get(obj).getAnimal(answer);
			
		}
		
		throw new IllegalStateException();
		
	}
	
}
