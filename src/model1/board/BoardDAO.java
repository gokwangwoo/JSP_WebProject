package model1.board;

import javax.servlet.ServletContext;
import common.JDBConnect;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class BoardDAO extends JDBConnect{
	public BoardDAO(ServletContext application) {
		super(application);
	}
	//검색 조건에 맞는 게시물의 개수를 반환합니다.
	public int selectCount(Map<String, Object>map) {
		int totalCount = 0; //결과(게시물 수)를 담을 변수
		
		//게시물 수를 얻어오는 쿼리문 작성
		String query = "SELECT COUNT(*) FROM board";
		if(map.get("searchWord") != null) {
			//if문을 써서 검색어가 있을 경우 아래의 쿼리문을 동작시키게 했습니다.
			query += " WHERE " + map.get("searchField")+ " " + " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try {
			stmt = con.createStatement(); //쿼리문 생성
			rs = stmt.executeQuery(query); //쿼리문 실행
			rs.next();//커서를 첫 번째 행으로 이동
			totalCount = rs.getInt(1);//첫번째 컬럼 값을 가져옴, SELECT COUNT(*) 결과로 반환하는 값이 정수 임으로 getInt를 사용
			//숫자 1은 select 절에 명시도니 컬럼의 인덱스를 의미, DB에서 인덱스는 1부터 시작
		}catch(Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount; //마지막으로 추출한 값을 반환하면 이값이 JSP로 반환되게 됩니다.
	}
	
	//검색 조건에 맞는 게시물 목록을 반환하는 기능 함수
	public List<BoardDTO> selectList(Map<String, Object> map){
		List<BoardDTO> bbs = new Vector<BoardDTO>(); //결과(게시물 목록)을 담을 변수
		
		String query = "SELECT * FROM board ";
		if(map.get("searchWord") != null) {
			//if문을 사용하여 검색하는 검색어가 있을 경우 아래의 쿼리문을 사용한다.
			query += " WHERE " + map.get("searchField") + " " + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY num DESC "; //위의 select count(*)와 다르게 order by로 정렬하는 기능을 추가
		
		try {
			stmt = con.createStatement(); //쿼리문 생성
			rs = stmt.executeQuery(query); //쿼리 실행
			
			while(rs.next()) { //결과를 순화하며....더이상 행이 존재하지 않을 때까지 중괄호 내용을 실행
				//한행(게시물 하나)의 내용을 DTO에 저장
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getNString("num"));
				
			}
		}catch(Exception e) {
			
		}
		
		return bbs;
	}

}
