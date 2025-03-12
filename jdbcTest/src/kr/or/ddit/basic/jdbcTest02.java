package kr.or.ddit.basic;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
 	문제) 사용자로부터 LPROD_ID 값을 입력 받아 입력한 값보다 LPROD_ID가 큰 자료들을 출력하시오.
 */

public class jdbcTest02 {


	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		Scanner scan = new Scanner(System.in);
		
		
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XEPDB1",
					"KTS98",
					"java"
					);
			System.out.println("LPROD 값을 입력해 주세요 >> ");
			int inputLPROD = scan.nextInt();
			
//			String sql = "SELECT * FROM LPROD WHERE LPROD_ID > " + inputLPROD
//					+"order by LPROD_ID";
			String sql = "SELECT * FROM LPROD";
								
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			System.out.println("결과");
			
			while(rs.next()) {
				if(inputLPROD < rs.getInt("LPROD_ID")) {
					
					System.out.println("LPROD_ID : " + rs.getInt(1));
					System.out.println("LPROD_GU : " + rs.getString(2));	
					System.out.println("LPROD_NAME : " + rs.getString(3));
					System.out.println("----------------------------------------------------");
					}
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// 5. 사용했던 자원 반납
			if(rs!=null)
				try {rs.close();} catch(SQLException e) {}
				try {stmt.close();} catch(SQLException e) {}
				try {conn.close();} catch(SQLException e) {}
			
		
			
		}
	}

}
