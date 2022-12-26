package chapter04;

import java.util.Calendar;

public class CalendarTest {

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2021);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DATE, 25);
		
		cal.set(1999,03,15);
		cal.add(Calendar.DATE, 10000);
		
		printDate(cal);
	}
	//Calendar 내부에 필드있음 사용해서 값 넣어서 get 해오기
	private static void printDate(Calendar cal) {
		final String d = "일월화수목금토";
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH); //+1 해줘야함
		int date = cal.get(Calendar.DATE);
		int day = cal.get(Calendar.DAY_OF_WEEK);// 1(일) ~ 7(토)
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(cal.SECOND);
		
		System.out.println(year+"-"+(month+1)+"-"+date+"-"+d.charAt(day-1)+"요일 "+hour+":"+minute+":"+second);
	}

}
