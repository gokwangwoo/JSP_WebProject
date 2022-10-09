<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LifeCycle.jsp</title>
</head>
<body>
	<script>
	function requestAction(frm, met){
		if(met == 1){
			frm.method = 'get';
		}
		else{
			frm.method = 'post';
		}
		frm.submit();
	}
	</script>
	
	<h2>서블릿 생명주기(LifeCycle) 메서드</h2>
	<form action="./LifeCycle.do"> <!-- form태그를 정희합니다. action 속성을 제외한 나머지는 자바스크립트에서 설정하도록 했다 -->
		<input type="button" value="Get 방식 요청하기" onclick="requestAction(this.form, 1);" />
		<input type="button" value="Post 방식 요청하기" onclick="requestAction(this.form, 2);" />
	
	</form>

</body>
</html>