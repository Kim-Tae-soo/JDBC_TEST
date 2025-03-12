package kr.or.ddit.basic.singleton;

/*
 
	singleton 패턴 ==> 객체가 1개만 만들어지게 하는 방법
		(외부에서 new명령을 사용하지 못하게 한다.)
		
		
	- singleton 패턴의 클래스를 만드는 방법 ( 필수 구성 요소 )
	1.	자신 class의 참조값이 저장될 변수를 'private static'으로 선언한다.
	
	2.	생성자의 접근 제한자를 'private'으로 설정한다.
	
	3.	자신 class의 인스턴스를 생성하고 이 인스턴스를 반환하는 메서드를  
		' public static ' 으로 작성한다.
		( 이 때 메서드명으로는 'getInstance'를 주로 사용한다. )
 	
 */

public class Mysingleton {
	// 1번
	private static Mysingleton single;
	
	// 2번
	private Mysingleton() {
		System.out.println("싱글톤의 생성자 입니다.");
	}
	
	// 3번
	public static Mysingleton getInstance() {
		// 1번의 변수가 null이면 객체를 생성해서 1번의 변수에 저장한다.
		if(single == null) single = new Mysingleton();
		return single;
	}

	// -----------------------------------------------------------
	// 기타 이 클래스가 처리할 내용들을 작성한다.
	public void testPrint() {
		System.out.println("싱글톤 클래스의 메서드 호출 입니다.");
	}

}
