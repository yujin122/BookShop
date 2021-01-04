<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/user/find_id.css?after" type="text/css">
</head>
<body>
<jsp:include page="../include/top.jsp" />
<%-- 아이디 찾기 폼 페이지(이름과 생년월일 이용) --%>
	<div id="find_id_wrap">
		<div class="container">
			<div id="find_id_box">
				<br/>
				<h2>아이디찾기</h2>
				<hr width="70%">
					<form action="<%=request.getContextPath() %>/member_find_id_ok.do" method="post" class="form-horizontal">
					<table>
						<tr>
							<td>
								<label for="mem_name" class="control-label">이름</label>
							</td>
							<td>
								<input name = "mem_name" id="mem_name" class="form-control">
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_email" class="control-label">이메일</label>
							</td>
							<td>
								<input name = "mem_email" id="mem_email" class="form-control">
							</td>
						</tr>
					</table>
						<button type="submit" class="btn btn_white">확인</button>
					</form>
			</div>
		</div>
	</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>