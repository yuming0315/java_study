package prob04;

public class Person {
	private static int numberOfPerson; // 전체 인구수
	private int age;
	private String name;
	
	/* 코드 작성 */
	public Person() {
		this(12,"");
	}
	
	public Person(String name) {
		this(12,name);
	}

	public Person(int age, String name) {
		this.age=age;
		this.name = name;
		numberOfPerson++;
	}

	public static int getPopulation() {
		return numberOfPerson;
	}

	public void selfIntroduce() {
		System.out.printf("내 이름은 %s이며, 나이는 %d입니다.\n",name,age);
	}
	
	
}
