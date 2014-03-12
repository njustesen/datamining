package questionnaire.cleaners;

import java.util.Calendar;
import java.util.Date;

public class AgeFixer {
	
	int min;
	int max;
	
	public AgeFixer(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}

	public int getAge(Date current, Date birth){
		
		Calendar birthday = getCalendar(birth);
		int birthdays = 0;
		
		birthday.add(Calendar.YEAR, 1);
		
		while(birthday.getTime().getTime() <= current.getTime()){
			birthdays++;
			birthday.add(Calendar.YEAR, 1);
		}
		
		return birthdays;
	}
	
	public Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal;
	}
	
}
