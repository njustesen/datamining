package mushrooms.classifiers.ID3;

import java.util.List;
import java.util.Map;

import mushrooms.models.Mushroom;

public class ID3Node {

	private Class attribute;
	private List<Mushroom> mushrooms;
	private Map<Object, ID3Node> children;
	private Boolean edible;
	
	public ID3Node() {
		super();
	}
	
	public ID3Node(Class attribute, List<Mushroom> mushrooms) {
		super();
		this.attribute = attribute;
		this.mushrooms = mushrooms;
		this.edible = null;
	}

	public Class getAttribute() {
		return attribute;
	}

	public void setAttribute(Class attribute) {
		this.attribute = attribute;
	}

	public List<Mushroom> getMushrooms() {
		return mushrooms;
	}

	public void setMushrooms(List<Mushroom> mushrooms) {
		this.mushrooms = mushrooms;
	}

	public Map<Object, ID3Node> getChildren() {
		return children;
	}

	public void setChildren(Map<Object, ID3Node> children) {
		this.children = children;
	}

	public Boolean getEdible() {
		return edible;
	}

	public void setEdible(Boolean edible) {
		this.edible = edible;
	}

	public void print(int level, Object value) {
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";
		
		String attr = attribute == null ? "null" : attribute.getName();
		String m = String.valueOf((mushrooms == null ? "null" : mushrooms.size()));
		String v = value == null ? "null" : ((Enum)value).name();
		if (edible == null){
			str += "<node value='" + v + "' split='" + attr + "' mushrooms='" + m + "'>";
		}else {
			str += "<node value='" + v + "' edible='" + edible + "'>";
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

	public boolean isEdible(Mushroom mushroom) {
		
		if (edible!=null)
			return edible;
		
		for(Object obj : children.keySet()){
			
			if (mushroom.getAttributeValue(attribute) == obj)
				return children.get(obj).isEdible(mushroom);
			
		}
		
		throw new IllegalStateException();
		
	}
	
}
