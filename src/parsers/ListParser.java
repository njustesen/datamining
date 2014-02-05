package parsers;

import java.util.Arrays;
import java.util.List;


public class ListParser implements TypeParser<List<String>> {

	@Override
	public List<String> parse(String string) {
		return (List<String>) Arrays.asList(string.split(","));
	}


}
