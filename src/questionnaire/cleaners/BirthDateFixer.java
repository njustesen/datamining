package questionnaire.cleaners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BirthDateFixer {
	
	private List<String> patterns;
	private int youngest;
	private int oldest;

	public Date getBirthDate(int age, String birthday, Date dateOfData){
		
		initPatterns();
		
		Calendar data = Calendar.getInstance();
		data.setTime(dateOfData);
		
		Calendar birthdate = Calendar.getInstance();
		Date parsedBirthday = parse(birthday);
		if (parsedBirthday==null)
			return null;
		birthdate.setTime(parsedBirthday);
		
		Calendar cal = Calendar.getInstance();
		
		
		int year = data.get(Calendar.YEAR) - age;
		if (birthdate.get(Calendar.MONTH) < data.get(Calendar.MONTH) || 
				(birthdate.get(Calendar.MONTH) == data.get(Calendar.MONTH) &&
				birthdate.get(Calendar.DAY_OF_MONTH) <= data.get(Calendar.DAY_OF_MONTH))){
			year++;
		}
		int month = birthdate.get(Calendar.MONTH);
		int date = birthdate.get(Calendar.DAY_OF_MONTH);
		
		cal.set(year, month, date, 0, 0, 0);
		
		return cal.getTime();
		
	}
	
	public Date parse(String string) {
		
		for(String pattern : patterns){
			Date date = parseDate(new SimpleDateFormat(pattern), string);
			if (date != null)
				return date;
		}
		
		return null;
		
	}
	
	private void initPatterns() {
		patterns = new ArrayList<String>();
		// Danish
		patterns.add("dd/MM");
		patterns.add("dd-MM");
		patterns.add("dd.MM.");
		
		patterns.add("dd MM");
		patterns.add("dd. MM");
		patterns.add("dd MM.");
		patterns.add("dd. MM.");
		
		patterns.add("dd MMM");
		patterns.add("dd. MMM");
		patterns.add("dd MMM.");
		patterns.add("dd. MMM.");
		
		// American
		patterns.add("MM/dd");
		patterns.add("MM dd");
		patterns.add("MM-dd");
		patterns.add("MM.dd");
		patterns.add("MMM dd");
		patterns.add("MMM. dd");
	}
	
	private Date parseDate(SimpleDateFormat format, String string) {
		try {
			return format.parse(string);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return null;
	}

	private boolean verify(Date date) {
		if (date == null)
			return false;
		
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -oldest);
		if (cal.after(dateCal))
			return false;
		
		cal.add(Calendar.YEAR, oldest);
		cal.add(Calendar.YEAR, -youngest);
		if (cal.before(dateCal))
			return false;
		
		return true;
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}
	
}
