<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/user/sign_up.css" type="text/css">
</head>
<body>
<%-- 찾은 아이디를 확인하는 페이지. --%>
<jsp:include page="../include/top.jsp" />
	<div class="container">
		
			<h2>아이디확인</h2>
			<hr width="70%">
			<h3>회원님의 아이디는 ${mem_id } 입니다.</h3>
		
	</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>