<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../include/top.jsp" />
<div class="container" align="center">
	<div class="cont_box">
	<form action="<%=request.getContextPath()%>/commu_delete_ok.do" method="post">
		<input type="hidden" name="c_num" value="${c_num }">
		<div class="form-group">
		 <label for="c_pwd">글 비밀번호 입력</label>
		 <input type="password" name="c_pwd" class="form-control">
		<br/>
		<input type="submit" class="btn btn_white" value="확인">
		</div>
	</form>
	</div>
</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>