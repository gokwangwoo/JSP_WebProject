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
				
				dto.setNum(rs.getString("num"));		//일련번호
				dto.setTitle(rs.getString("title"));	//제목
				dto.setContent(rs.getString("content"));//내용
				dto.setPostdate(rs.getDate("postdate"));//작성일
				dto.setId(rs.getString("id")); 			//작성자 아이디
				dto.setVisitcount(rs.getString("visitcount"));//조회수
				
				bbs.add(dto);
				
			}
		}catch(Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
			
		}
		
		return bbs;
	}
	//검색 조건에 맞는 게시물 목록을 반환합니다.(페이징 기능 지원).
	public List<BoardDTO> selectListPage(Map<String, Object> map){
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		//쿼리문 템플릿
		String query = "SELECT * FROM ( "
				+ "			SELECT Tb.*,ROWNUM rNum FROM (	"
				+ " 			SELECT * FROM board";
		//검색 조건 추가
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
				  + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		query += "		ORDER BY num DESC "
			   + "		) Tb "
			   + " ) "
			   + " WHERE rNum BETWEEN ? AND ?";
		
		try {
			//쿼리문 완성
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			
			//쿼리문 실행
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				//한 행(게시물 하나)의 데이터를 DTO에 저장
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				//반환할 결과 목록에 게시물 추가
				bbs.add(dto);
			}
			
		}catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		//목록반환
		return bbs;
	}
	
	//게시글 데이터를 받아 DB에 추가합니다.
	public int insertWrite(BoardDTO dto){
		int result = 0;
		
		try {
			//INSERT 쿼리문 작성
			String query = "INSERT INTO board ( "
					+ "num,title,content,id,visitcount) "
					+ "VALUES ( "
					+ "seq_board_num.NEXTVAL, ?, ?, ?, 0)";
			
			psmt = con.prepareStatement(query); //동적 쿼리
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
		
	}
	
	//지정한 게시물을 찾아 내용을 반환합니다.
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		//쿼리문 준비
		String query = "SELECT B.*, M.name "
				+ " FROM member M INNER JOIN board B "
				+ " ON M.id = B.id "
				+ " WHERE num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num); // 인파리미터를 일련번호로 설정
			rs = psmt.executeQuery(); //쿼리 실행
			
			//결과 처리
			if(rs.next()) {
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString(6));
				dto.setName(rs.getString("name"));
			}
		}
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	//지정한 게시물의 조회수를 1 증가시킵니다.
	public void updateVisitCount(String num) {
		//쿼리문 준비
		String query = " UPDATE board SET "
					   +" visitcount = visitcount+1 "
					   + " WHERE num=? ";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num); //인파라미터를 일련번호로 설정
			psmt.executeQuery(); //쿼리 실행
		}
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	//지정한 게시물을 수정합니다.
	public int updateEdit(BoardDTO dto) {
		int result = 0;
		
		try {
			//쿼리문 템플릿
			String query = "UPDATE board SET "
					+ " title=?, content=? "
					+ " WHERE num=?";
			//쿼리문 완성
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
			
			//쿼리문 실행
			result = psmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
	
		return result;
	}
	
	//지정한 게시물을 삭제합니다.
	// 지정한 게시물을 삭제합니다.
    public int deletePost(BoardDTO dto) { 
        int result = 0;

        try {
            // 쿼리문 템플릿
            String query = "DELETE FROM board WHERE num=?"; 

            // 쿼리문 완성
            psmt = con.prepareStatement(query); 
            psmt.setString(1, dto.getNum()); 

            // 쿼리문 실행
            result = psmt.executeUpdate(); 
        } 
        catch (Exception e) {
            System.out.println("게시물 삭제 중 예외 발생");
            e.printStackTrace();
        }
        
        return result; // 결과 반환
    }

}
