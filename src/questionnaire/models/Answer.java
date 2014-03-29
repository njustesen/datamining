package questionnaire.models;
import java.util.Date;
import java.util.List;

import questionnaire.id3.attributes.AGE;
import questionnaire.id3.attributes.CPLUSPLUS;
import questionnaire.id3.attributes.CSHARP;
import questionnaire.id3.attributes.ENGLISH_LEVEL;
import questionnaire.id3.attributes.JAVA;
import questionnaire.id3.attributes.PYTHON;
import questionnaire.id3.attributes.YEARS_OF_STUDY;

import mushrooms.data.Class_Label;

/**
 * The representation of a student answer.
 * 
 * @author Niels
 *
 */
public class Answer {

	Integer age;
	Date birthday;
	Integer skill;
	Integer yearsStudy;
	String os;
	List<String> programmingLanguage;
	List<ProgrammingLanguage> proLanParsed;
	Integer englishLevel;
	Animal animal;
	Boolean mountains;
	Boolean winterTired;
	Float random1_10;
	Float randomReal0_1;
	Float randomReal0_1Second;
	String canteen;
	String color;
	Boolean nnAndSVM;
	Boolean sql;
	String sqlServer;
	Boolean priori;
	Float sqrt;
	String homeTown;
	String therbForttGlag;
	Integer planets;
	Integer nextNumber;
	String sequence;

	public Answer() {
		
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSkill() {
		return skill;
	}

	public void setSkill(Integer skill) {
		this.skill = skill;
	}

	public Integer getYearsStudy() {
		return yearsStudy;
	}

	public void setYearsStudy(Integer yearsStudy) {
		this.yearsStudy = yearsStudy;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public List<String> getProgrammingLanguage() {
		return programmingLanguage;
	}

	public void setProgrammingLanguage(List<String> programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}
	
	public List<ProgrammingLanguage> getProLanParsed() {
		return proLanParsed;
	}

	public void setProLanParsed(List<ProgrammingLanguage> proLanParsed) {
		this.proLanParsed = proLanParsed;
	}

	public Integer getEnglishLevel() {
		return englishLevel;
	}

	public void setEnglishLevel(Integer englishLevel) {
		this.englishLevel = englishLevel;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Boolean getMountains() {
		return mountains;
	}

	public void setMountains(Boolean mountains) {
		this.mountains = mountains;
	}

	public Boolean getWinterTired() {
		return winterTired;
	}

	public void setWinterTired(Boolean winterTired) {
		this.winterTired = winterTired;
	}

	public Float getRandom1_10() {
		return random1_10;
	}

	public void setRandom1_10(Float random1_10) {
		this.random1_10 = random1_10;
	}

	public Float getRandomReal0_1() {
		return randomReal0_1;
	}

	public void setRandomReal0_1(Float randomReal0_1) {
		this.randomReal0_1 = randomReal0_1;
	}

	public Float getRandomReal0_1Second() {
		return randomReal0_1Second;
	}

	public void setRandomReal0_1Second(Float randomReal0_1Second) {
		this.randomReal0_1Second = randomReal0_1Second;
	}

	public String getCanteen() {
		return canteen;
	}

	public void setCanteen(String canteen) {
		this.canteen = canteen;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean getNNAndSVM() {
		return nnAndSVM;
	}

	public void setNNAndSVM(Boolean nNAndSVM) {
		nnAndSVM = nNAndSVM;
	}

	public Boolean getSql() {
		return sql;
	}

	public void setSql(Boolean sql) {
		this.sql = sql;
	}

	public String getSqlServer() {
		return sqlServer;
	}

	public void setSqlServer(String sqlServer) {
		this.sqlServer = sqlServer;
	}

	public Boolean getPriori() {
		return priori;
	}

	public void setPriori(Boolean priori) {
		this.priori = priori;
	}

	public Float getSqrt() {
		return sqrt;
	}

	public void setSqrt(Float sqrt) {
		this.sqrt = sqrt;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public String getTherbForttGlag() {
		return therbForttGlag;
	}

	public void setTherbForttGlag(String therbForttGlag) {
		this.therbForttGlag = therbForttGlag;
	}

	public Integer getPlanets() {
		return planets;
	}

	public void setPlanets(Integer planets) {
		this.planets = planets;
	}

	public Integer getNextNumber() {
		return nextNumber;
	}

	public void setNextNumber(Integer nextNumber) {
		this.nextNumber = nextNumber;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "Answer [age=" + age + ", birthday=" + birthday + ", skill="
				+ skill + ", yearsStudy=" + yearsStudy + ", os=" + os
				+ ", programmingLanguage=" + programmingLanguage
				+ ", englishLevel=" + englishLevel + ", animal=" + animal
				+ ", mountains=" + mountains + ", winterTired=" + winterTired
				+ ", random1_10=" + random1_10 + ", randomReal0_1="
				+ randomReal0_1 + ", randomReal0_1Second="
				+ randomReal0_1Second + ", canteen=" + canteen + ", color="
				+ color + ", nnAndSVM=" + nnAndSVM + ", sql=" + sql
				+ ", sqlServer=" + sqlServer + ", priori=" + priori + ", sqrt="
				+ sqrt + ", homeTown=" + homeTown + ", therbForttGlag="
				+ therbForttGlag + ", planets=" + planets + ", nextNumber="
				+ nextNumber + ", sequence=" + sequence + "]";
	}

	public Object getAttributeValue(Object attr) {
		if(attr.equals(AGE.class)){
			if (age == null)
				return AGE.none;
			if (age < 20)
				return AGE.younger_than_20;
			else if (age > 30)
				return AGE.older_than_30;
			else
				return AGE.between_20_and_30;
		}
		
		if(attr.equals(ENGLISH_LEVEL.class)){
			if (englishLevel == null)
				return ENGLISH_LEVEL.none;
			if (englishLevel < 50)
				return ENGLISH_LEVEL.below_50;
			else if (englishLevel > 70)
				return ENGLISH_LEVEL.higher_than_70;
			else if (englishLevel > 60)
				return ENGLISH_LEVEL.between_60_and_70;
			return ENGLISH_LEVEL.between_50_and_60;
		}
		
		if(attr.equals(YEARS_OF_STUDY.class)){
			if (yearsStudy == null)
				return YEARS_OF_STUDY.none;
			if (yearsStudy < 5)
				return YEARS_OF_STUDY.below_five;
			else if (yearsStudy > 10)
				return YEARS_OF_STUDY.five_to_nine;
			return YEARS_OF_STUDY.ten_or_more;
		}
		
		if(attr.equals(CPLUSPLUS.class)){
			if (proLanParsed.contains(ProgrammingLanguage.CPLUSPLUS))
				return CPLUSPLUS.yes;
			return CPLUSPLUS.no;
		}
		
		if(attr.equals(JAVA.class)){
			if (proLanParsed.contains(ProgrammingLanguage.JAVA))
				return JAVA.yes;
			return JAVA.no;
		}
		
		if(attr.equals(CSHARP.class)){
			if (proLanParsed.contains(ProgrammingLanguage.CSHARP))
				return CSHARP.yes;
			return CSHARP.no;
		}
		
		if(attr.equals(PYTHON.class)){
			if (proLanParsed.contains(ProgrammingLanguage.PYTHON))
				return PYTHON.yes;
			return PYTHON.no;
		}
		
		return null;
	}
	
}
