package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import kr.or.ddit.util.*;

/*
 	회원을 관리하는 프로그램을 작성하시오
 	(DB의 MYMEMBER 테이블 이용)
 	
 	아래 메뉴의 기능을 모두 구현하시오 (CRUD 기능 구현 연습)
 	메뉴 예시)
 		1. 자료 추가		--> CREATE (INSERT)
 		2. 자료 삭제		--> DELETE
 		3. 자료 수정		--> UPDATE
 		4. 전체 자료 출력	--> READ (SELECT)
 		0. 프로그램 종료
 	-------------------------------------------
 	
 	조건)
 	
 	1) 자료 추가에서 '회원ID'는 중복되지 않는다. (중복되면 다시 입력을 받는다.)
 	2) 자료 삭제는 '회원ID'를 입력 받아서 처리한다.
 	3) 자료 수정에서 '회원ID'는 변경되지 않는다.
 	
 	
 */

public class jdbcTest7DBUTIL {
	Scanner scan = new Scanner(System.in);
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public static void main(String[] args) {

		new jdbcTest7DBUTIL().memberStart();

	}

	public void memberStart() {

		while (true) {
			int choice = memberdisplay();
		
			switch (choice) {
			case 1:
				Insert();
				break;
			case 2:
				Delete();
				break;
			case 3:
				Update();
				break;
			case 4:
				Select();
				break;
			case 0:
				System.out.println("프로그램 종료");
				return;
			default :
				System.out.println("잘못된 입력입니다.");
				
			}

		}
	}

	public int memberdisplay() {
		System.out.println("회원 관리 프로그램");
		System.out.println("1. 자료 추가");
		System.out.println("2. 자료 삭제");
		System.out.println("3. 자료 수정");
		System.out.println("4. 전체 자료 출력");
		System.out.println("0. 프로그램 종료");
		System.out.print("번호 입력 >> ");

		return scan.nextInt();
	}

	private void Insert() {

		try {
			conn = DBUtil3.getConnection();

			System.out.print("회원 ID 입력 >> ");
			String MEM_ID = scan.next();

			String checkSQL = "SELECT COUNT(*) FROM MYMEMBER WHERE MEM_ID = ?";
			pstmt = conn.prepareStatement(checkSQL);
			pstmt.setString(1, MEM_ID);
			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {
				System.out.println("회원 ID가 중복됩니다. 다시 입력해주세요.");
				return;
			}

			System.out.print("비밀번호 입력 >> ");
			String MEM_PASS = scan.next();
			System.out.print("이름 입력 >> ");
			String MEM_NAME = scan.next();
			System.out.print("전화번호 입력 >> ");
			String MEM_TEL = scan.next();
			System.out.print("주소 입력 >> ");
			String MEM_ADDR = scan.next();

			String insertSQL = "INSERT INTO MYMEMBER (MEM_ID, MEM_PASS, MEM_NAME, MEM_TEL, MEM_ADDR) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, MEM_ID);
			pstmt.setString(2, MEM_PASS);
			pstmt.setString(3, MEM_NAME);
			pstmt.setString(4, MEM_TEL);
			pstmt.setString(5, MEM_ADDR);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				System.out.println("회원 추가 완료");
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

	private void Delete() {
		try {
			conn = DBUtil3.getConnection();

			System.out.print("삭제할 회원 ID 입력 >> ");
			String MEM_ID = scan.next();

			String deleteSQL = "DELETE FROM MYMEMBER WHERE MEM_ID = ?";
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setString(1, MEM_ID);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				System.out.println("회원 삭제 완료");
			} else {
				System.out.println("해당 회원 ID가 없습니다.");
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

	private void Update() {
		try {
			conn = DBUtil3.getConnection();

			System.out.print("수정할 회원 ID 입력 >> ");
			String MEM_ID = scan.next();

			System.out.print("비밀번호 입력 >> ");
			String MEM_PASS = scan.next();
			System.out.print("이름 입력 >> ");
			String MEM_NAME = scan.next();
			System.out.print("전화번호 입력 >> ");
			String MEM_TEL = scan.next();
			System.out.print("주소 입력 >> ");
			String MEM_ADDR = scan.next();

			String updateSQL = "UPDATE MYMEMBER SET MEM_PASS = ?, MEM_NAME = ?, MEM_TEL = ?, MEM_ADDR = ? WHERE MEM_ID = ?";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, MEM_PASS);
			pstmt.setString(2, MEM_NAME);
			pstmt.setString(3, MEM_TEL);
			pstmt.setString(4, MEM_ADDR);
			pstmt.setString(5, MEM_ID);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				System.out.println("회원 정보 수정 완료");
			} else {
				System.out.println("해당 회원 ID가 없습니다.");
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

	private void Select() {
		try {
			conn = DBUtil3.getConnection();

			System.out.println("멤버 정보 검색");

			String SelectSQL = "SELECT * FROM MYMEMBER";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SelectSQL);

			System.out.println();
			System.out.println("\t--멤버자료 출력 결과--\t");
			while (rs.next()) {
				String MEM_ID = rs.getNString(1);
				String MEM_PASS = rs.getNString(2);
				String MEM_NAME = rs.getNString(3);
				String MEM_TEL = rs.getNString(4);
				String MEM_ADDR = rs.getNString(5);

				System.out.println(MEM_ID + "\t" + MEM_PASS + "\t" + MEM_NAME + "\t" + MEM_TEL + "\t" + MEM_ADDR);
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
