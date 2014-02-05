package parsers;

public class IntegerParser implements TypeParser<Integer>{

	int min;
	int max;
	
	public IntegerParser(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	public Integer parse(String string){
		string = string.replace(',', '.');
		string = string.replace("½", ".5");
		try{
			Integer i = Integer.parseInt(string);
			if (i != null && i >= min && i <= max)
				return i;
		} catch (NumberFormatException e){
			// Ignore
		}
		
		try{
			float f = Float.parseFloat(string);
			Integer i = Math.round(f);
			if (i != null && i >= min && i <= max)
				return i;
		} catch (NumberFormatException e){
			// Ignore
		}
		
		// if range, pick lowest
		if (string.split("-").length > 1){
			Integer i = parse(string.split("-")[0]);
			if (i != null && i >= min && i <= max)
				return i;
		}
		
		// wierd stuff
		if (string.split(" ").length > 1){
			Integer i = parse(string.split(" ")[0]);
			if (i != null && i >= min && i <= max)
				return i;
		}
		if (string.split(" ").length > 1){
			Integer i = parse(string.split(" ")[1]);
			if (i != null && i >= min && i <= max)
				return i;
		}
		return null;
	}
	
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
}
