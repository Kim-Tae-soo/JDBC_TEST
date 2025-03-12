package kr.or.ddit.basic;

import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class jdbcTest07_teaVer {

	private Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		new jdbcTest07_teaVer().startMember();

	}
	
	// 시작 메서드
	public void startMember() {
		while(true) {
			int choice = displayMenu();
			
			switch (choice) {
			case 1:
				InsertMember();
				break;
			case 2:
				DeleteMember();
				break;
			case 3:
				UpdateMember();
				break;
			case 4:
				EditMemberDetails();
				break;
			case 5:
				SelectMember();
				break;
			case 0:
				System.out.println("프로그램 종료");
				return;
			default :
				System.out.println("잘못된 입력입니다. \n 다시 입력해주세요.");
				
			}

		}
	}
	
	// 회원 정보를 추가하는 메서드
	private void InsertMember() {
		System.out.println();
		System.out.println("Add Member Info");
		
		int count = 0;			// 회원ID의 개수가 저장될 변수
		String memId = null;	// 회원ID가 저장될 변수
		
		do {
			System.out.print("Member ID >>");
			memId = sc.next();
			count = getMemberIdCount(memId);
			
			if (count>0) {
				System.out.println("The ID you entered is an already registered MemberID : " + memId);
				System.out.println("Please enter another MemberID");
			}
		} while(count>0);
		
		System.out.print("Member PassWord >> ");
		String pass = sc.next();
		
		System.out.print("Member Name >> ");
		String name = sc.next();
		
		System.out.print("Member Tel >> ");
		String tel = sc.next();
		
		sc.nextLine(); // 입력된 버퍼 비우기
		
		System.out.print("Member Addr >> ");
		String addr = sc.nextLine();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			String InsertSQL = "INSERT INTO MYMEMBER "
						+ "(MEM_ID, MEM_PASS, MEM_NAME, MEM_TEL, MEM_ADDR)"
						+ "VALUES(?,?,?,?,?)";
			pstmt = conn.prepareStatement(InsertSQL);
			pstmt.setString(1, memId);
			pstmt.setString(2, pass);
			pstmt.setString(3, name);
			pstmt.setString(4, tel);
			pstmt.setString(5, addr);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt>0) {
				System.out.println("Succeeded in adding data");
			} else {
				System.out.println("Failed to add data");
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
		
	}
	
	// 회원 ID를 매개변수로 받아서 해당 회원 ID의 개수를 반환하는 메서드
	private int getMemberIdCount(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0; // 반환값이 저장될 변수
		
		try {
			conn = DBUtil.getConnection();
			String CountSQL = "SELECT COUNT(*) CNT FROM MYMEMBER WHERE MEM_ID = ?";
			pstmt = conn.prepareStatement(CountSQL);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("CNT");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
		return count;
	}
	
	// 회원 정보를 삭제하는 메서드
	private void DeleteMember() {
		System.out.println();
		System.out.println("Please enter member information to be deleted");
		System.out.println("Member ID to be deleted >> ");
		String memId = sc.next();
		
		int count = getMemberIdCount(memId);
		if(count ==0) {
			System.out.println("There is no member with member ID : " + memId);
			System.out.println();
			return;
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			String DeleteSQL = "DELETE FROM MYMEMBER WHERE MEM_ID = ?";
			pstmt = conn.prepareStatement(DeleteSQL);
			pstmt.setString(1, memId);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("Delete successful");
			}else {
				System.out.println("Deletion failed");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
	}
	
	// 회원 정보를 수정하는 메서드 - 전체 항목 수정하기
	private void UpdateMember() {
		System.out.println();
		System.out.println("Please enter member information to be update");
		System.out.println("Member ID to be update >> ");
		String memId = sc.next();
		
		int count = getMemberIdCount(memId);
		if(count ==0) {
			System.out.println("There is no member with member ID : " + memId);
			System.out.println();
			return;
		}
		
		System.out.println();
		System.out.print("New Password >> ");
		String newPass = sc.next();
		
		System.out.print("New Name >> ");
		String newName = sc.next();
		
		System.out.print("New Tel >> ");
		String newTel = sc.next();
		
		
		sc.nextLine(); // 버퍼비우기
		System.out.print("New Addr >> ");
		String newAddr = sc.next();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			String UpdateSQL = "UPDATE MYMEMBER SET "
							+ "MEM_PASS = ?, MEM_NAME = ?, MEM_TEL = ?, MEM_ADDR = ? "
							+ "WHERE MEM_ID = ?";
			
			pstmt = conn.prepareStatement(UpdateSQL);
			pstmt.setString(1, newPass);
			pstmt.setString(2, newName);
			pstmt.setString(3, newTel);
			pstmt.setString(4, newAddr);
			pstmt.setString(5, memId);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt>0) {
				System.out.println("Update Success");
			} else {
				System.out.println("Update Failed");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
	}
	
	// 회원 정보를 수정한다 // 원하는 항목만 수정한다
	private void EditMemberDetails() {
		System.out.println();
		System.out.println("Please enter member information to be update");
		System.out.println("Member ID to be update >> ");
		String memId = sc.next();
		
		int count = getMemberIdCount(memId);
		if(count ==0) {
			System.out.println("There is no member with member ID : " + memId);
			System.out.println();
			return;
		}
		
		String UpdateField = null;	// 수정할 항목의 컬럼명이 저장될 변수
		String UpdateTitle = null;	// 수정할 항목의 제목이 저장될 변수
		int UpdateNum; 				// 수정할 항목의 번호가 저장될 변수
		
		do {
			System.out.println();
			System.out.println("Select the item you want to edit");
			System.out.println("1.Member Password\n2.Member Name\n3.Member Tel\n4.Member Addr");
			System.out.println("-----------------------------------");
			System.out.print("Select item to edit >> ");
			UpdateNum = sc.nextInt();
			
			switch(UpdateNum) {
				case 1: UpdateTitle = "Member Password"; UpdateField="MEM_PASS"; break;
				case 2: UpdateTitle = "Member Name"; UpdateField="MEM_NAME"; break;
				case 3: UpdateTitle = "Member Tel"; UpdateField="MEM_TEL"; break;
				case 4: UpdateTitle = "Member Addr"; UpdateField="MEM_ADDR"; break;
				default:
					System.out.println("The item to be modified was selected incorrectly. Please select again.");
			
			}
			
		}while(UpdateNum<1 || UpdateNum>4);
		sc.nextLine(); // 버퍼 비우기
		System.out.println();
		System.out.println("New " + UpdateTitle + " >> ");
		String UpdateData = sc.nextLine();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			String DetailUpdateSQL = "UPDATE MYMEMBER SET " + UpdateField + " = ?"
								+ "WHERE MEM_ID = ? ";
			
			pstmt = conn.prepareStatement(DetailUpdateSQL);
			
			pstmt.setString(1, UpdateData);
			pstmt.setString(2, memId);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt>0) {
				System.out.println("Update Success");
			} else {
				System.out.println("Update Failed");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
		
	}
	
	// 회원 정보를 전체 조회하는 메서드
	private void SelectMember() {
		
		System.out.println();
		System.out.println("\t--MEMBER LIST--\t");
		System.out.println("------------------------------------");
		System.out.println("ID\tPASSWORD\tNAME\tTEL\t\tADDR");
		System.out.println();
		
		Connection conn = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			String SelectSQL = "SELECT * FROM MYMEMBER ORDER BY 1";
			pstmt = conn.prepareStatement(SelectSQL);
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				
				/*
				컬럼 위치도 가능 
				String MEM_ID = rs.getString(1);
				String MEM_PASS = rs.getString(2);
				String MEM_NAME = rs.getString(3);
				String MEM_TEL = rs.getString(4);
				String MEM_ADDR = rs.getString(5);
				 */
				
				
				String MEM_ID = rs.getString("MEM_ID");
				String MEM_PASS = rs.getString("MEM_PASS");
				String MEM_NAME = rs.getString("MEM_NAME");
				String MEM_TEL = rs.getString("MEM_TEL");
				String MEM_ADDR = rs.getString("MEM_ADDR");
				
				System.out.println(MEM_ID + "\t" + MEM_PASS + "\t\t" + MEM_NAME + "\t" + MEM_TEL + "\t" + MEM_ADDR);
				
			}
			
			if(cnt==0) {
				System.out.println("There are no registered members");
			}
			System.out.println("------------------------------------");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
		
	}
	
	// 메뉴를 출력하고 작업 번호를 입력 받아 반환한ㄴ 메서드
	private int displayMenu() {
		System.out.println();
		System.out.println("Member PG");
		System.out.println("1. Data Insert");
		System.out.println("2. Data Delete");
		System.out.println("3. Data Update");
		System.out.println("4. Data EditMemberDetails");
		System.out.println("5. Data Select");
		System.out.println("0. EXIT");
		System.out.print("Choice menu num >> ");

		return sc.nextInt();
	}

}
