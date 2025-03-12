package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

/*
 	LPROD 테이블에 새로운 데이터 추가하기
 	
 	1. LPROD_GU와 LPROD_NAME은 직접 입력 받아서 처리한다.
 	2. LPROD_ID는 현재의 LPROD_ID 값들 중에 제일 큰 값보다 1크게한다.
 	
 	입력 받은 LPROD_GU가 이미 등록되어 있으면 다시 입력 받아서 처리한다.
 	
 	// JAVA PreparedStatement 객체 이용하여 처리
 */

public class jdbcTest05 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			  conn = DBUtil.getConnection();
			  
		      String sql = "SELECT NVL(MAX(LPROD_ID),0) MAXID FROM LPROD";
		      pstmt = conn.prepareStatement(sql);
		      
		      rs = pstmt.executeQuery();
		      int maxId = 0;
		      if(rs.next()) {	// 검색한 레코드(row) 개수가 1개일 경우에는 if문으로 비교 가능
		    	  // maxId = rs.getInt(1);	// 컬럼 번호 이용	
		    	  // maxId = rs.getInt("max(LPROD_ID)"); // 식 자체를 이용 (AS가 없을 시 )
		    	  maxId = rs.getInt("maxId");		// 컬럼의 AS (ALIAS)이용 
		    	  
		      }
		      maxId++;
		      // --------------------------------------------------------------
		      
		      // 입력 받은 LPROD_GU가 이미 등록되어 있으면 다시 입력 받아서 처리한다.
		      String gu;
		      int count = 0;
		    
		      String sql2 = "SELECT COUNT(*) cnt FROM LPROD WHERE LPROD_GU = ?";
		      pstmt = conn.prepareStatement(sql2);
		      
		      do {
		    	  System.out.println("상품 분류 코드(LPROD_GU) 입력 >> ");
		    	  gu = scan.next();
		    	  
		    	  pstmt.setString(1, gu); // 조건 데이터 셋팅
		    	  
		    	  rs = pstmt.executeQuery();
		    	  
		    	  if(rs.next()) {
//		    		  count = rs.getInt(1);
		    		  count = rs.getInt(1);
		    	  }
		    	  if(count >0) {
		    		  System.out.println("입력한 상품 분류 코드 " + gu + "는 이미 등록된 코드입니다.");
		    		  System.out.println("다른 코드로 다시 입력하세요...");
		    		  System.out.println();
		    	  }
		    	  
		      } while(count>0);
		      
		      System.out.println("상품 분명 (LPROD_NAME) 입력 >> ");
		      String name = scan.next();
		      
		      String sql3 = "INSERT INTO LPROD (LPROD_ID,LPROD_GU,LPROD_NAME)" + "VALUES(?, ?, ?)";
		      pstmt = conn.prepareStatement(sql3);
		      
		      pstmt.setInt(1, maxId);
		      pstmt.setString(2, gu);
		      pstmt.setString(3, name);
		      
		      int cnt = pstmt.executeUpdate();
		      
		      if(cnt>0){
		    	  System.out.println("등록 성공!!!");
		      } else {
		    	  System.out.println("등록 실패!!!");
		      }
		      
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {         
	         if(rs != null) try { rs.close(); } catch (SQLException e) { }
	         if(pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
	         if(conn != null) try { conn.close(); } catch (SQLException e) { }
	      }

		
		
	}

}
