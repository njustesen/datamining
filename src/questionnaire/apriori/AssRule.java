package questionnaire.apriori;

public class AssRule {
	
	ItemSet a;
	ItemSet b;
	
	public AssRule(ItemSet a, ItemSet b) {
		super();
		this.a = a;
		this.b = b;
	}

	public ItemSet getA() {
		return a;
	}

	public void setA(ItemSet a) {
		this.a = a;
	}

	public ItemSet getB() {
		return b;
	}

	public void setB(ItemSet b) {
		this.b = b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssRule other = (AssRule) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AssRule [a=" + a + ", b=" + b + "]";
	}
	
}
