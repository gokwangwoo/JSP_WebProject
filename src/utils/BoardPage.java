//모델2 방식에도 같이 활용할 코드라서 utils에 제작하였습니다.
/**이 java 코드의 역할은 목록에 출력한 게시물을 List.jsp에서 가져왔으니 
 * 페이지 바로가기 영역을 HTML 문자열로 출력해주는 메서드 입니다.
 * **/
package utils;

public class BoardPage {
	public static String pagingStr(int totalCount, int pageSize, int blockPage, int pageNum, String reqUrl) {
		String pagingStr = "";
		
		//단계 3 : 전체 페이지 수 계산
		int totalPages = (int)(Math.ceil(((double) totalCount / pageSize)));
		
		//단계 4 : '이전 페이지 블록 바로가기' 출력
		int pageTemp = ((((pageNum) - 1)/blockPage) * blockPage) + 1;
		if(pageTemp != 1) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=1'>[첫 페이지]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp - 1) + "'>[이전 블록]</a>";
		
		}
		//단계 5 : 각 페이지 번호 출력
		int blockCount = 1;
		while(blockCount <= blockPage && pageTemp <= totalPages) {
			if(pageTemp == pageNum) {
				//현재 페이지는 링크를 걸지 않음
				pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp + "'>" + pageTemp + "</a>&nbsp;";
			}
			pageTemp++;
			blockCount++; //여기를 통해서 현재 페이지 말고 나머지 페이지 숫자를 키우면서 링크를 거는 것이다.
		}
		
		//단계 6 : '다음 페이지 블록 바로가기' 출력
		if(pageTemp <= totalPages) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + pageTemp + "'>[다음블록]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPages + "'>[마지막 페이지]</a>";
		}
		
		
		return pagingStr;
	}

}
