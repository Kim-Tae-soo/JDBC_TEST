package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

/*
 	
 	- Statment 객체를 사용하면 SQL Injection 해킹을 당할 수 있는 예제
 	
 	1) 정상 검색 ==> (계좌번호) 입력
 	2) 전체 검색 해킹 ==> ( ' or 1=1 -- ' ) 입력
 	3) 전체 회원 정보 해킹 ==> (' union select mem_id, mem_name, mem_password, null from member --' ) 입력
 	 
 */

public class jdbcTest06 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			conn = DBUtil.getConnection();
			
			System.out.println("계좌 정보 검색하기");
			System.out.println();
			
			System.out.println("검색할 계좌번호 입력 >> ");
			
			String bankNo = scan.nextLine();
			
			/*
			// Statement 객체를 이용하는 경우
			String sql = "SELECT * FROM BANKINFO WHERE BANK_NO = '" + bankNo + "'";
			System.out.println("SQL ==> " +sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// --------------------------------------------
			*/
			
			// PreparedStatement 객체를 이용하는 경우 -----------------------
				
			//---------------------------------------------
			
			
			System.out.println();
			System.out.println("\t--검 색 결 과--\t");
			System.out.println("\t계좌번호\t은행명\t예금주명\t개설날짜");
			while(rs.next()) {
				String bNo = rs.getString("bank_no");
				String bName = rs.getString("bank_name");
				String bUserName = rs.getString("bank_user_name");
				String bDate = rs.getString("bank_date");
				System.out.println(bNo + "\t" + bName + "\t" + bUserName + "\t" + bDate);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (SQLException e) { }
			if(stmt != null) try { stmt.close(); } catch (SQLException e) { }
	        if(pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
	        if(conn != null) try { conn.close(); } catch (SQLException e) { }
			
		}

	}

}
