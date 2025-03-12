package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReadTest {

	public static void main(String[] args) {
		
		Properties prop = new Properties();
		
		File file = new File("res/kr/or/ddit/config/memo.properties");
		FileInputStream fin = null;
		
		
		try {
			// 파일 내용을 읽어올 입력용 스트림 객체 생성
			fin = new FileInputStream(file);
			// 입력용 스트림 객체를 이용하여 파일 내용을 읽어와 Properties 객체에 저장하기 ==> loda() 메서드 이용
			//		==> ㅠㅏ일 내용을 읽어와 key값과  value값을 분류하여 properties객체에 추가해 준다.
			
			prop.load(fin);
			
			// 읽어온 정보 출력해보기
			
			System.out.println();
			System.out.println("name : " + prop.getProperty("name"));
			System.out.println("age : " + prop.getProperty("age"));
			System.out.println("addr : " + prop.getProperty("addr"));
			System.out.println("tel : " + prop.getProperty("tel"));
			System.out.println("--------------------------------");
			System.out.println("출력 끝..");
			
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fin!=null)try{fin.close();} catch(IOException e){}
		}
	}

}
