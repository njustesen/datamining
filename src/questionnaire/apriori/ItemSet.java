package questionnaire.apriori;

import java.util.List;

public class ItemSet {
	
	final List<String> set;

    public ItemSet( List<String> set ) {
        this.set = set;
    }

    public List<String> getSet() {
		return set;
	}

	@Override
	public String toString() {
		String str = "[";
		int count = 0;
		for(String item : set){
			str += item;
			count++;
			if (count < set.size())
				str += ";";
		}
		return str + "]";
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSet other = (ItemSet) obj;
		if (set == null) {
			if (other.set != null)
				return false;
		} else {
			for(String str : set){
				if (!other.getSet().contains(str))
					return false;
			}
		}
			
		return true;
	}

    
    
}
