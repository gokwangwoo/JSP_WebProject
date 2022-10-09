<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="common.JDBConnect" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>JDBC</title></head>
<meta charset="UTF-8">
<body>
	<h2>회원 추가 테스트(executeUpdate() 사용)</h2>
	<%
	//DB에 연결
	JDBConnect jdbc = new JDBConnect();
	
	//테스트용 입력값 준비
	String id = "test1";
	String pass = "1111";
	String name = "테스트1회원";
	
	//쿼리문 생성
	String sql = "INSERT INTO member VALUES (?, ?, ?, sysdate)";
	out.println("jdbc 연결 유무: "+jdbc +"<br />");
	out.println("jdbc.con 연결유무: " + jdbc.con);
	PreparedStatement psmt = jdbc.con.prepareStatement(sql);
	psmt.setString(1, id);
	psmt.setString(2, pass);
	psmt.setString(3, name);
	
	//쿼리 수행
	int inResult = psmt.executeUpdate();
	out.println(inResult + "행이 입력 되었습니다.");
	
	jdbc.close();
	
	%>
	
	

</body>
</html>