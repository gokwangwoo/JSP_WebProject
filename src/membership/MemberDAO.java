package membership;

//DB에 연결한 후 , CRUD에 해당하는 SQL 쿼리문을 실행한 후, 얻어온 결과를 앞서 만든 DTO 객체에 담아 반환

import common.JDBConnect;

public class MemberDAO extends JDBConnect{
	//명시한 데이터베이스로의 연결이 완료된 MemberDAO 객체를 새성합니다.
	public MemberDAO(String drv, String url, String id, String pw) {
		super(drv, url, id, pw);
	}
	
	//명시한 아이디/패스워드와 일치하는 회원 정보를 반환합니다.
	public MemberDTO getMemberDTO(String uid, String upass) {
		MemberDTO dto = new MemberDTO(); //회원 정보 DTO 객체 생성
		String query = "SELECT * FROM member WHERE id=? AND pass=?"; //쿼리문 템플릿
		
		try {
			//쿼리 실행
			psmt = con.prepareStatement(query); //동적 쿼리문 준비
			psmt.setString(1,  uid); //쿼리문 첫번째 인파라미터에 값 설정
			psmt.setString(2, upass); //쿼리문의 두 번째 인파라미터에 값 설정
			rs = psmt.executeQuery(); //쿼리문 실행
			
			//결과 처리
			if(rs.next()) {
				//쿼리 결과로 얻은 회원 ㅈ어보를 DTO 객체에 저장
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getString(4));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return dto; //DTO 객체 반환
	}

}
