package questionnaire.parsers;

import questionnaire.models.Animal;

public class AnimalParser implements TypeParser<Animal>{

	public Animal parse(String string){
		
		String str = new StringParser().parse(string);
		
		if(str.contains(Animal.ASPARAGUS.name))
			return Animal.ASPARAGUS;
		
		if(str.contains(Animal.ELEPHANT.name))
			return Animal.ELEPHANT;
		
		if(str.contains(Animal.ZEBRA.name))
			return Animal.ZEBRA;
		
		int i = 0;
		
		return null;
	}
	
}
