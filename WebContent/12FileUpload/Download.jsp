<%@ page import="utils.JSFunction" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String saveDirectory = application.getRealPath("/Uploads"); //Uploads 폴더의 물리적 경로 받아옵니다.
String saveFilename = request.getParameter("sName");
String originalFilename = request.getParameter("oName");

try{
	//파일을 찾아 입력 스트립 생성
	File file = new File(saveDirectory, saveFilename);
	InputStream inStream = new FileInputStream(file);
	
	//한글 파일명 깨짐 방지
	String client = request.getHeader("User-Agent");
	/*User-Agent를 통해서 브라우저 정보를 가져오고 
	브라우져 별로 한글 처리를 다르게 해서 한글 파일명이 깨지지 않게 합니다.
	인터넷 익스플로러가 아니면 UTF-8로 처리
	인터넷 익스플로러이면 KSC5601로 처리 합니다.
	*/
	if(client.indexOf("WOW64") == -1){
		originalFilename = new String(originalFilename.getBytes("UTF-8"),"ISO-8859-1");
	}else{
		originalFilename = new String(originalFilename.getBytes("KSC5601"),"ISO-8859-1");
	}
	
	//파일 다운로드용 응답 헤더 설정
	response.reset(); //응답헤더 초기화
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Disposition","attachment; filename=\""+originalFilename+"\"");//웹 브라우져에서 파일 다운로드를 할 때 원복 파일 명이 나오게 처리
	response.setHeader("Content-Length", "" + file.length());
	
	//출력 스트립 초기화
	out.clear();
	
	//response 내장 객체로부터 새로운 출력 스트림 생성
	OutputStream outStream = response.getOutputStream();
	
	//출력 스트립에 파일 내용 출력
	byte b[] = new byte[(int)file.length()];
	int readBuffer = 0;
	while((readBuffer = inStream.read(b)) >0){
		outStream.write(b, 0, readBuffer);
	}
	
	//입/출력 스트림 닫음
	inStream.close();
	outStream.close();
	
}catch(FileNotFoundException e){
	JSFunction.alertBack("파일을 찾을 수 없습니다.", out);
	
}catch (Exception e){
	JSFunction.alertBack("예외가 발생했습니다.", out);
}
%>