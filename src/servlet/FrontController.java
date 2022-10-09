package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.one") //URL패턴이 "*.one"에 해당하는 요청은 모두 이 서블릿과 매핑된다.

public class FrontController extends HttpServlet{
	 @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = req.getRequestURI(); //request내장 객체로 부터 현재 경로에서 호스트명을 제외한 나머지 부분을 알아낸 다음
		int lastSlash = uri.lastIndexOf("/");//마지막 슬래쉬(/)인덱스를 구합니다.
		String commandStr = uri.substring(lastSlash); //그리고 인덱스 경로의 마지막 문자열을 얻어옵니다.
		
		if(commandStr.equals("/regist.one")) //<--인덱스 경로의 마지막 문자열이 괄호안의 내용과 같으면 어떻게 처리할건지 if else문으로 구성
			registFunc(req);
		else if(commandStr.equals("/login.one"))
			loginFunc(req);
		else if(commandStr.equals("/freeboard.one"))
			freeboardFunc(req);
		
		req.setAttribute("uri", uri);
		req.setAttribute("commandStr", commandStr);
		req.getRequestDispatcher("/13Servlet/FrontController.jsp").forward(req, resp);//전달된 값을 FrontController에게 전송한다

	}
	 //페이지별 처리 매서드
	 void registFunc(HttpServletRequest req) {
		 req.setAttribute("resultValue", "<h4>회원가입<h4>");
	 }
	 
	 void loginFunc(HttpServletRequest req) {
		 req.setAttribute("resultValue", "<h4>로그인</h4>");
	 }
	 
	 void freeboardFunc(HttpServletRequest req) {
		 req.setAttribute("resultValue", "<h4>자유게시판</h4>");
	 }

}
