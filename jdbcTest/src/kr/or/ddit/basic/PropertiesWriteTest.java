package kr.or.ddit.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesWriteTest {

	public static void main(String[] args) {
		
		Properties prop = new Properties();
		
		File file = new File("res/kr/or/ddit/config/memo.properties");
		FileOutputStream fout = null;
		
		try {
			// 저장할 내용을 Properties 객체에 추가하기..
			prop.setProperty("name", "홍길동");
			prop.setProperty("age", String.valueOf(30));
			prop.setProperty("addr", "대전시 중구 오류동");
			prop.setProperty("tel", "010-1234-5678");
			
			// Properties 객체를 저장할 출력용 스트림 객체 생성
			fout = new FileOutputStream(file);
			
			// 파일로 저장하기 ==> store() 메서드 이용
			prop.store(fout, "주석입니다");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fout!=null) try {fout.close();} catch(IOException e) {}
		}
		
		
		
	}

}
