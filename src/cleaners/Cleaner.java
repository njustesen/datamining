package cleaners;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import parsers.AnimalParser;
import parsers.BooleanParser;
import parsers.DateParser;
import parsers.FloatParser;
import parsers.IntegerParser;
import parsers.ListParser;
import parsers.StringParser;

import models.Answer;


public class Cleaner {

	public List<Answer> clean(List<List<String>> data, int rows) {
		
		List<Answer> answers = new ArrayList<Answer>();
		
		for (List<String> row : data) {
			
			if (row.size() != rows){
				continue;
			}
			
			Answer answer = new Answer();
			
			answer.setAge(new IntegerParser(18,80).parse(row.get(0)));
			answer.setBirthday(new DateParser(18,80).parse(row.get(1)));
			answer.setSkill(new IntegerParser(1,10).parse(row.get(2)));
			answer.setYearsStudy(new IntegerParser(0,30).parse(row.get(3)));
			answer.setOs(new StringParser().parse(row.get(4)));
			answer.setProgrammingLanguage(new ListParser().parse(row.get(5)));
			answer.setEnglishLevel(new IntegerParser(45,69).parse(row.get(6)));
			answer.setAnimal(new AnimalParser().parse(row.get(7)));
			answer.setMountains(new BooleanParser().parse(row.get(8)));
			answer.setWinterTired(new BooleanParser().parse(row.get(9)));
			answer.setRandom1_10(new FloatParser(1,10).parse(row.get(10)));
			answer.setRandomReal0_1(new FloatParser(0,1).parse(row.get(11)));
			answer.setRandomReal0_1Second(new FloatParser(0,1).parse(row.get(12)));
			answer.setCanteen(new StringParser().parse(row.get(13)));
			answer.setColor(new StringParser().parse(row.get(14)));
			answer.setNNAndSVM(new BooleanParser().parse(row.get(15)));
			answer.setSql(new BooleanParser().parse(row.get(16)));
			answer.setSqlServer(new StringParser().parse(row.get(17)));
			answer.setPriori(new BooleanParser().parse(row.get(18)));
			answer.setSqrt(new FloatParser(1,10).parse(row.get(19)));
			answer.setHomeTown(new StringParser().parse(row.get(20)));
			answer.setTherbForttGlag(new StringParser().parse(row.get(21)));
			answer.setPlanets(new IntegerParser(1,10).parse(row.get(22)));
			answer.setNextNumber(new IntegerParser(45,69).parse(row.get(23)));
			answer.setSequence(new StringParser().parse(row.get(24)));
			
			answers.add(answer);
			System.out.println("---");
			System.out.println(Arrays.toString(row.toArray()));
			System.out.println(answer);
			
		}
		
		return answers;
		
	}

}
