package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReadTest02 {

	public static void main(String[] args) {
		
		Properties prop = new Properties();
		File f = new File("res/kr/or/ddit/config/db.properties");
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream(f);
			
			prop.load(fin);
			
			System.out.println();
			System.out.println("driver : " + prop.getProperty("driver"));
			System.out.println("url : " + prop.getProperty("url"));
			System.out.println("user : " + prop.getProperty("user"));
			System.out.println("pass : " + prop.getProperty("pass"));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(fin!=null) try{fin.close();} catch(IOException e) {}
		}

	}

}
