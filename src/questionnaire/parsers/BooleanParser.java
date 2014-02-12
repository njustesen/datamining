package questionnaire.parsers;


public class BooleanParser implements TypeParser<Boolean>{

	@Override
	public Boolean parse(String string) {
		String str = new StringParser().parse(string);
		if (str.contains("yes"))
			return true;
		else if (str.contains("no"))
			return false;
		return null;
	}

}
