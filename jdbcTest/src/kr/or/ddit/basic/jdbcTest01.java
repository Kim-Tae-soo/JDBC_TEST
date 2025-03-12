package kr.or.ddit.basic;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbcTest01 {
	/*
	 	JDBC(Java DataBase Connectivity)
	 		==> Java에서 DB를 이용한 자료를 처리하기 위한 라이브러리
	 		
	 		- JDBC를 이용한 데이터베이스 처리 순서
	 		
	 		1. 드라이버 로딩	==> 라이브러리를 사용할 수 있게 메모리로 읽어 들이는 작업
 					Class.forName("oracle.jdbc.driver.OracleDriver")
	 		
	 		2. DB에 접속하기 ==> DB에 접속이 완료되면 Connection객체가 반환된다.
	 				DriverManager.getConnection() 메서드를 사용한다.
	 		
	 		3. 질의 ==> SQL문장을 DB서버로 보내서 실행 결과를 얻어온다.
	 				(Statement 객체 또는 PreparedStatement 객체를 사용한다.)
	 		
	 		4. 결과 처리 ==> 질의 결과를 받아서 원하는 작업을 수행한다.
	 			1) 실행한 SQL문이 'SELECT'문일 겨우에는 'SELECT'한 결과가 
	 			   ResultSet 객체에 저장되어 반환한다.
	 			2) 실행한 SQL문이 'SELECT'문이 아닐 경우 (INSERT, UPDATE, DELETE 등)
	 			   실행 결과가 '정수값'으로 반환된다.('정수값'은 보통 실행에 성공한 레코드 수를 말한다.)
	 		5. 사용한 자원을 반납한다. ==> close() 메서드 이용
	 			   
	 */
	public static void main(String[] args) {
		// DB 작업에 필요한 변수 선언
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// 1 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. DB 연결 ==> Connection 객체 생성
			// DirverManager 클래스 ==> DB에 접속하기 위한 드라이버를 관리하는 클래스
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/XEPDB1", //url
//					"jdbc:oracle:thin:@localhost:1521:xe", //url
					"KTS98",
					"java"
					);
			
			// 3. 질의
			// 3-1. SQL문 작성
			String sql = "SELECT * FROM LPROD ";
			
			// 3-2 Statement 객체 생성 ==> 질의를 서버에 전송하고 결과를 얻어오는 객체
			stmt = conn.createStatement();
			
			// 3-3 SQL문을 DB서버로 보내서  결과를 얻어온다.
			// 	   (실행할 SQL문이 'SELECT'문이기 때문에 결과가 Result 객체에 저장되어 반환된다.)
			rs = stmt.executeQuery(sql);
			
			// 4. 결과 처리하기 ==> 한 레코드씩 화면에 출력하기
			// 	  (ResultSet 객체에 저장된 데이터를 차례로 꺼내오려면 반복문과 next()메서드를 이용하여 처리한다.)
			System.out.println("== 쿼리문 처리 결과 ==");
			
			// rs.next() ==> Result객체의 데이터를 가리키는 포인터를 다음번째의 레코드로 이동시키고 그 곳에 데이터가
//							 있으면 true, 없으면 false를 나타낸다
			
			while(rs.next()) {
				// 포인터가 가리키는 곳의 자료를 가져오는 방법
				// 형식1) rs.get자료형이름("컬럼명" 또는 "alias명") 
				// 형식2) rs.get자료형이름( 컬럼번호 ); ==> 컬럼 번호는 1부터 시작...
				System.out.println("LPROD_ID : " + rs.getInt("LPROD_ID"));
				System.out.println("LPROD_GU : " + rs.getString(2));	// 2번째 컬럼을 의미함
				System.out.println("LPROD_NAME : " + rs.getString(3));
				System.out.println("----------------------------------------------------");
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
