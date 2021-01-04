<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%
	//네이버로 로그인 시 콜백 정보(회원 정보를 얻기 위해 필요)를 받아오는 스크립트릿(회원 정보는 memberProfileAction에서 얻음)
    String clientId = "x0V8yJaViGR2CeWlz_Uz";//애플리케이션 클라이언트 아이디값";	(중요)변경되면 안됨. 노출되면 안됨.
    String clientSecret = "alsVx0WCM6";//애플리케이션 클라이언트 시크릿값";			(중요)변경되면 안됨. 노출되면 안됨.
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String redirectURI = URLEncoder.encode("http://localhost:8585/BookShop/user/callback.jsp", "UTF-8");	//현 콜백 주소. 변경 시 네이버와 로그인에서도 변경 필요.
    String apiURL;
    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";	//콜백 정보를 얻어오는 네이버 주소
    apiURL += "client_id=" + clientId;
    apiURL += "&client_secret=" + clientSecret;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&code=" + code;
    apiURL += "&state=" + state;
    String access_token = "";	//회원 정보 획득 시 필요한 문자열.
    String refresh_token = "";	//로그인 유지 시 필요한 문자열.
    try {
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      BufferedReader br;
      if(responseCode==200) { // 정상 호출
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer res = new StringBuffer();	//res = accesstoken과 refresh 토큰이 담긴 json문자열.
      while ((inputLine = br.readLine()) != null) {
        res.append(inputLine);
      }
      br.close();
      if(responseCode==200) {
    	  //json문자열에서 access token만 추출. WEB-INF/lib에 json-simple-1.1.1.jar 필요.
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject)parser.parse(res.toString());
      	access_token = (String)object.get("access_token");
      }
    } catch (Exception e) {
      System.out.println(e);
    }
%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- 로딩 시 회원 정보를 얻는 데 필요한 access token을 member_profile.do로 이동 --%>
	<script type="text/javascript">
		window.onload = function() {
			document.frm.action="<%=request.getContextPath()%>/member_profile.do";
			document.frm.method="post"
			document.frm.submit();
		}
	</script>
</head>
<body>
<%-- 네이버로 로그인 시 값을 받는 페이지. 사용자에게 노출되어서는 안됨. 로딩 시 member_profile.do로 이동 --%>
	<form name="frm">
		<input type="hidden" value="<%=access_token %>" name="accesstoken">
	</form>
</body>
</html>