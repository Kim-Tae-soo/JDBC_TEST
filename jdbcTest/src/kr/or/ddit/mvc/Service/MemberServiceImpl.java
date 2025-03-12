package kr.or.ddit.mvc.Service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.mvc.DAO.IMemberDAO;
import kr.or.ddit.mvc.DAO.MemberDAOImpl;
import kr.or.ddit.mvc.VO.MemberVO;


public class MemberServiceImpl implements IMemberService {
	
	private IMemberDAO DAO;	//DAO 객체가 저장될 변수 선언
	
	private MemberServiceImpl() { // 원래 public 이였음
//		DAO = new MemberDAOImpl();	// DAO 객체 생성
		DAO = MemberDAOImpl.getInstance();	// DAO 객체 생성
	}
	
	// 1번
	private static MemberServiceImpl service;
	
	// 2번 --> 접근 제한자만 바꿔주면된다 15번라인 public -> prviate으로 변경
	
	// 3번 
	public static MemberServiceImpl getInstance() {
		if(service == null) service = new MemberServiceImpl();
		return service;
	}
	@Override
	public int insertMember(MemberVO VO) {
		return DAO.insertMember(VO);
	}

	@Override
	public int deleteMember(String memId) {
		return DAO.deleteMember(memId);
	}

	@Override
	public int updateMember(MemberVO VO) {
		return DAO.updateMember(VO);
	}

	@Override
	public List<MemberVO> getAllMember() {
		return DAO.getAllMember();
	}

	@Override
	public int getMemberIdCount(String memId) {
		return DAO.getMemberIdCount(memId);
	}

	@Override
	public int EditMemberDetails(Map<String, String> paramMap) {
		return DAO.EditMemberDetails(paramMap);
	}
	

}
