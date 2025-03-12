package kr.or.ddit.basic;

import java.util.ResourceBundle;

/*
 	ResourceBundle 객체 ==> 	파일의 확장자가 '.properties'인 파일의 내용을 읽어와 
 							key값과 value값을 분리해서 정보를 갖고 있는 객체
 	
 	==>	읽어올 파일의 내용은 'key값=value' 형태로 되어 있어야 한다.
 	
 	 
 */

public class ResourceBundleTest {
	
	//ResourceBundle 객체를 이용하여 파일 내용 읽어오는 예제
	public static void main(String[] args) {
		// ResourceBundle 객체 생성
		//		==> 읽어올 파일을 지정할 때 '패키지명.파일명' 형태로 지정하고 확장자는 지정하지 않는다.
		//		==> 확장자를 지정하지 않는 이유는 항상 확장자의 파일은 '.properties'이기 때문이다. 
		//		==> 객체 생성이 완료되면 데이터가 읽혀져서 저장되어 있다.
		ResourceBundle bundle = ResourceBundle.getBundle("kr.or.ddit.config.db");
		
		System.out.println("읽어온 내용들...");
		System.out.println("driver : " + bundle.getString("driver"));
		System.out.println("url : " + bundle.getString("url"));
		System.out.println("user : " + bundle.getString("user"));
		System.out.println("pass : " + bundle.getString("pass"));
		

	}

}
