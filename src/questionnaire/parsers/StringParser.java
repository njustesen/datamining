package questionnaire.parsers;

public class StringParser implements TypeParser<String>{

	public String parse(String string) {
		string = string.trim();
		string = string.toLowerCase();
		return string;
	}

}
