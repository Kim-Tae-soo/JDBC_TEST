package kr.or.ddit.mvc.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import kr.or.ddit.mvc.Service.IMemberService;
import kr.or.ddit.mvc.Service.MemberServiceImpl;
import kr.or.ddit.mvc.VO.MemberVO;

public class MemberController {

	private Scanner sc;
	private IMemberService service;		// Service 객체가 저장될 변수 선언
	
	public MemberController() {
		sc = new Scanner(System.in);
//		service = new MemberServiceImpl();
		service = MemberServiceImpl.getInstance();
	}
	
	public static void main(String[] args) { // 메인 메서드
		new MemberController().startMember();
		
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
			count = service.getMemberIdCount(memId);
			
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
		
		// 입력 받은 자료를 VO객체에 저장한다.
		MemberVO memberVO = new MemberVO();
		memberVO.setMem_id(memId);
		memberVO.setMem_pass(pass);
		memberVO.setMem_name(name);
		memberVO.setMem_tel(tel);
		memberVO.setMem_addr(addr);
		
		int cnt = service.insertMember(memberVO);
		
		if(cnt>0) {
			System.out.println("Succeeded in adding data");
		} else {
			System.out.println("Failed to add data");
		}
	}
		
	
	// 메뉴를 출력하고 작업 번호를 입력 받아 변환하는 메서드
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
	
	// 회원 정보 삭제 메서드
	private void DeleteMember() {
		System.out.println();
		System.out.println("Please enter member information to be deleted");
		System.out.println("Member ID to be deleted >> ");
		String memId = sc.next();
		
		int count = service.getMemberIdCount(memId);
		if(count ==0) {
			System.out.println("There is no member with member ID : " + memId);
			System.out.println();
			return;
		}
		int cnt = service.deleteMember(memId);
		
		if(cnt > 0) {
			System.out.println("Delete successful");
		}else {
			System.out.println("Deletion failed");
		}
		
	}
	
	private void UpdateMember() {
		System.out.println();
		System.out.println("Please enter member information to be update");
		System.out.println("Member ID to be update >> ");
		String memId = sc.next();
		
		int count = service.getMemberIdCount(memId);
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
		
		// 입력한 자료를 VO에 저장한다.
		MemberVO memVO = new MemberVO();
		memVO.setMem_id(memId);
		memVO.setMem_pass(newPass);
		memVO.setMem_name(newName);
		memVO.setMem_tel(newTel);
		memVO.setMem_addr(newAddr);
		
		int cnt = service.updateMember(memVO);
		
		if(cnt>0) {
			System.out.println("Update Success");
		} else {
			System.out.println("Update Failed");
		}
		
	}
	// 회원의 원하는 항목만 수정하려 출력
	private void EditMemberDetails() {
		System.out.println();
		System.out.println("Please enter member information to be update");
		System.out.println("Member ID to be update >> ");
		String memId = sc.next();
		
		int count = service.getMemberIdCount(memId);
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
		
		// 입력 받은 자료들을 Map객체에 저장한다.
		// Map의 key값 정보 ==> 회원ID(MEM_ID), 수정할 컬럼명(FIELD), 변경될데이터(DATA)
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("MEMID", memId);
		paramMap.put("FIELD", UpdateField);
		paramMap.put("DATA", UpdateData);
		
		int cnt = service.EditMemberDetails(paramMap);
		
		if(cnt>0) {
			System.out.println("Update Success");
		} else {
			System.out.println("Update Failed");
		}
	
	}
	
	// 전체 회원 목록을 조회
	private void SelectMember() {
		List<MemberVO> memList = service.getAllMember();
		
		System.out.println();
		System.out.println("\t--MEMBER LIST--\t");
		System.out.println("------------------------------------");
		System.out.println("ID\tPASSWORD\tNAME\tTEL\t\tADDR");
		System.out.println();
		
		if(memList==null || memList.size() == 0) {
			System.out.println("There are no registered members");
		} else {
			for(MemberVO memVO : memList) {
				String memId = memVO.getMem_id();
				String memPass = memVO.getMem_pass();
				String memName = memVO.getMem_name();
				String memTel = memVO.getMem_tel();
				String memAddr = memVO.getMem_addr();
				
				System.out.println(memId + "\t" + memPass + "\t\t" + memName + "\t" + memTel + "\t" + memAddr);
			}
		}
		
		System.out.println("------------------------------------");
			
			
		
	}
	
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
				System.out.println("Program OFF");
				return;
			default :
				System.out.println("Sorry retry Input Number");
				
			}

		}
	}

}
