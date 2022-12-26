package chapter04;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(now);
		
		printDate01(now);
		printDate02(now);
	}
	private static void printDate01(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(d);
		
		System.out.println(date);
	}
	private static void printDate02(Date d) {
		//년도에 (+1900) 해줘야함 년도를 두자리로 표시했었어서 <= 캘린더 써라
		int year = d.getYear();
		//월은 0~11이 나옴 그러니 +1 해줘야 함 세팅할 때도 -1 <= 캘린더에서도 마찬가지임
		int month = d.getMonth();
		//일
		int day = d.getDate();
		//시
		int hour = d.getHours();
		//분
		int minute=d.getMinutes();
		//초
		int second = d.getSeconds();
		
		System.out.println((year+1900)+"-"+(month+1)+"-"+day+" "+hour+":"+minute+":"+second);
	}
}
