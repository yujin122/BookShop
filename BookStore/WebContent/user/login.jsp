<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/user/login.css?after" type="text/css">
<%
	//네이버 로그인 스크립트릿(현재 네이버에 4조 중고도서로 어플 등록함.])
    String clientId = "x0V8yJaViGR2CeWlz_Uz";//애플리케이션 클라이언트 아이디값(4조 중고도서 어플), 공개되면 안됨.
    String redirectURI = URLEncoder.encode("http://localhost:8585/BookShop/user/callback.jsp", "UTF-8"); //로그인하고 다시 돌려 받을 주소 위치. 변경 시 네이버와 콜백.jsp에서도 변경해야 함.
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
</head>
<%-- 로그인 페이지 --%>
<body>
<jsp:include page="../include/top.jsp" />
<div id="login_wrap">
	
		<div class="container">
			<div id="login_box">
				<br>
				<h2>로그인</h2>
				<hr width="70%">
				<form action="${pageContext.request.contextPath}/member_login.do" method="post" class="form-horizontal">
						<table>
							<tr>
							<td>
						<label for="inputId">아이디</label>
							</td>
							<td>
						<input name="mem_id" class="form-control" id="inputId" placeholder="ID">
							</td>
							</tr>
							<tr>
							<td>
						<label for="inputPwd" class="control-label">비밀번호</label>
							</td>
							<td>
						<input type="password" name = "mem_pwd" id="inputPwd" class="form-control" placeholder="PASSWORD">
							</td>
							</tr>
						
						</table>
						<br>
						<button type="submit" class="btn btn_white">Login</button>
									<%-- <a>태그는 apiURL에 저장된 네이버 주소로 가서 네이버 로그인 처리 --%>
							<a href="<%=apiURL%>">
								<img width="100" src="http://static.nid.naver.com/oauth/small_g_in.PNG">
							</a>
				</form>
			<br/><br/>
			<div class="btn-group" role="group">
				<button type="button" class="btn btn_white" onclick = "location.href = '${pageContext.request.contextPath}/member_find_id.do'">Find Id</button>
				<button type="button" class="btn btn_white" onclick = "location.href = '${pageContext.request.contextPath}/member_find_pwd.do'">Find Pwd</button>
				<button type="button" class="btn btn_white" onclick = "location.href = '${pageContext.request.contextPath}/sign_up.do'">Sign Up</button>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>