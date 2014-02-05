package parsers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateParser implements TypeParser<Date>{

	private List<String> patterns;
	private int youngest;
	private int oldest;
	
	public DateParser(int youngest, int oldest){
		super();
		this.youngest = youngest;
		this.oldest = oldest;
		initPatterns();
	}

	private void initPatterns() {
		patterns = new ArrayList<String>();
		// Danish
		patterns.add("ddMMyy");
		patterns.add("dd/MM/yy");
		patterns.add("dd/MM/yyyy");
		patterns.add("dd/MM yy");
		patterns.add("dd/MM yyyy");
		patterns.add("dd/MM-yy");
		patterns.add("dd/MM-yyyy");
		patterns.add("dd/MM - yy");
		patterns.add("dd/MM - yyyy");
		patterns.add("dd-MM - yy");
		patterns.add("dd-MM - yyyy");
		patterns.add("dd-MM-yy");
		patterns.add("dd-MM-yyyy");
		patterns.add("dd.MM.yy");
		patterns.add("dd.MM.yyyy");
		
		patterns.add("dd MM yy");
		patterns.add("dd MM yyyy");
		patterns.add("dd. MM yy");
		patterns.add("dd. MM yyyy");
		patterns.add("dd MM. yy");
		patterns.add("dd MM. yyyy");
		patterns.add("dd. MM. yy");
		patterns.add("dd. MM. yyyy");
		
		patterns.add("dd MMM yy");
		patterns.add("dd MMM yyyy");
		patterns.add("dd. MMM yy");
		patterns.add("dd. MMM yyyy");
		patterns.add("dd MMM. yy");
		patterns.add("dd MMM. yyyy");
		patterns.add("dd. MMM. yy");
		patterns.add("dd. MMM. yyyy");
		
		// American
		patterns.add("yyMMdd");
		patterns.add("yy/MM/dd");
		patterns.add("yyyy/MM/dd");
		patterns.add("yy MM dd");
		patterns.add("yyyy MM dd");
		patterns.add("yy-MM-dd");
		patterns.add("yyyy-MM-dd");
		patterns.add("yy.MM.dd");
		patterns.add("yyyy.MM.dd");
		patterns.add("yy MMM dd");
		patterns.add("yyyy MMM. dd");
	}

	public Date parse(String string) {
		
		for(String pattern : patterns){
			Date date = parseDate(new SimpleDateFormat(pattern), string);
			if (verify(date))
				return date;
		}
		
		return null;
		
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
		
		/*
		if (date.getTime() <= 0)
			return false;
		*/
		
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
	
}
