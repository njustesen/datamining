package mushrooms.data;

public enum Ring_Number {
	
	none(0),one(1),two(2);
	int value;

	Ring_Number(int value){
		this.value = value;
	}

	public float distance(Ring_Number other){
		//return Math.max(value, other.value) - Math.min(value, other.value);
		return (float) Math.sqrt(value * value - other.value * other.value);
	}

}
