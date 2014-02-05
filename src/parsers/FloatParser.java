package parsers;


public class FloatParser implements TypeParser<Float>{

	float min;
	float max;
	
	public FloatParser(float min, float max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	public Float parse(String string){
		string = string.replace(',', '.');
		string = string.replace("½", ".5");
		try{
			Float f = Float.parseFloat(string);
			if (f!=null && f >= min && f <= max)
				return f;
		} catch (NumberFormatException e){
			// Ignore
		}
		
		// if range, pick lowest
		if (string.split("-").length > 1){
			Float f = parse(string.split("-")[0]);
			if (f!=null && f >= min && f <= max)
				return f;
		}
		
		// wierd stuff
		if (string.split(" ").length > 1){
			Float f = parse(string.split(" ")[0]);
			if (f!=null && f >= min && f <= max)
				return f;
		}
		if (string.split(" ").length > 1){
			Float f = parse(string.split(" ")[1]);
			if (f!=null && f >= min && f <= max)
				return f;
		}
		return null;
	}
	
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
	
}
