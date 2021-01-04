<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/user/find_pwd.css" type="text/css">
</head>
<body>
<jsp:include page="../include/top.jsp" />
<%-- 비밀번호 찾기 폼. --%>
	<div id="find_pwd_wrap">
		<div class="container">
			<div id="find_pwd_box">
				<br/>
				<h2>비밀번호찾기</h2>
				<hr width="70%">
				<form action="<%=request.getContextPath()%>/member_find_pwd_ok.do" method="post" class="form-horizontal">
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
								<label for="mem_id" class="control-label">아이디</label>
							</td>
							<td>
								<input type="text" name="mem_id" id="mem_id" class="form-control">
							</td>
						</tr>
					</table>
						<button type="submit" class="btn btn_white">Confirm</button>
				</form>
			</div>
		</div>
	</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>