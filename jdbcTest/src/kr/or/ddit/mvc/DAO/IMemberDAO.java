package kr.or.ddit.mvc.DAO;

import java.util.List;
import java.util.Map;

import kr.or.ddit.mvc.VO.MemberVO;

/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성해서 Service에 전달하는 DAO의 Interface 이다.
 *
 * @author KTS_98
 */

public interface IMemberDAO {

	/**
	 * MemberVO객체에 담겨진 자료를 DB에 Insert하는 메서드
	 * 
	 * @param vo insert할 자료가 저장된 MemberVO 객체
	 * @return insert 작업 성공 : 1, 실패 : 0
	 */
	public int insertMember(MemberVO VO);

	/**
	 * 회원 ID를 매개변수로 받아서 해당 회원 정보를 삭제하는 메서드
	 * 
	 * @param memId 삭제할 회원ID
	 * @return 작업 성공 : 1 , 실패 : 0
	 */
	public int deleteMember(String memId);

	/**
	 * 변경할 자료가 저장된 MemberVO객체를 받아서 DB에 Update하는 메서드
	 * 
	 * @param VO Update할 정보가 저장된 MemberVO 객체
	 * @return 작업 성공 : 1 , 실패 : 0
	 */
	public int updateMember(MemberVO VO);

	/**
	 * DB의 전체 회원 정보를 가져와 List에 담아서 반환하는 메서드
	 * 
	 * @return 검색한 결과가 저장된 List객체
	 */
	public List<MemberVO> getAllMember();

	/**
	 * 회원ID를 매개변수로 받아서 해당 회원ID의 개수를 반환하는 메서드
	 * 
	 * @param memId 검색할 회원 ID
	 * @return 검색된 회원ID의 갯수
	 */
	public int getMemberIdCount(String memId);

	/**
	 * 매개변수로 받은 Map의 정보를 이용하여 회원 정보 중 원하는 컬럼을 수정하는 메서드
	 * 		Map의 key값 정보 ==> 회원ID(MEM_ID), 수정할 컬럼명(FIELD), 변경될데이터(DATA)
	 * 
	 * @param paramMap 회원ID, 수정할 컬럼명, 변경될 데이터가 저장된 Map객체
	 * @return 작업 성공 : 1 , 작업 실패 : 0 
	 */
	public int EditMemberDetails(Map<String,String> paramMap);

}
