package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import membership.MemberDAO;
import membership.MemberDTO;

public class MemberAuth extends HttpServlet{
	MemberDAO dao; //JDBC프로그램을 위해 dao가져온다.
	
	@Override
	public void init() throws ServletException {//서블릿을 초기화 하는 함수
		// TODO Auto-generated method stub
		//application 내장 객체 얻기
		ServletContext application = this.getServletContext();
		
		//web.xml에서 DB연결 정보 얻기(web.xml에 등록한 컨텍스트 초기화 매개변수 중 DB연결 정보를 읽는다)
		String driver = application.getInitParameter("OracleDriver");
		String connectUrl = application.getInitParameter("OracleURL");
		String oId = application.getInitParameter("OracleId");
		String oPass = application.getInitParameter("OraclePwd");
		
		//DAO 생성
		dao = new MemberDAO(driver, connectUrl, oId, oPass);
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 클라이언트 요청을 처리하는 서비스 객체
		
		//서블릿 초기화 배개변수에서 관리자 ID받기
		//web.xml에서 <init-param>admin_id</init-param> <param-value>nakja</param-value>값을 받아오는 것이다.
		String admin_id = this.getInitParameter("admin_id");
		
		//인증을 요청한 ID/패스워드
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		
		//회원 테이블에서 인증 요청한 ID/패스워드에 해당하는 회원 찾기
		MemberDTO memberDTO = dao.getMemberDTO(id, pass);
		
		//차즌 회원의 이름에 따른 처리
		String memberName = memberDTO.getName();
		if(memberName != null) { //일치하는 회원 찾은 경우
			req.setAttribute("authMessage", memberName + "회원님 방가방가^^*");
		}
		else { //일치하는 회원 없음
			if(admin_id.equals(id))//관리자
				req.setAttribute("authMessage", admin_id + "는 최고 관리자 입니다.");
			else
				req.setAttribute("authMessage", "귀하는 회우너이 아닙니다.");
		}
		req.getRequestDispatcher("/13Servlet/MemberAuth.jsp").forward(req, resp);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		dao.close();
	}

}
