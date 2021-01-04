<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel = "stylesheet" href = "css/common.css">
<link rel = "stylesheet" href = "css/top.css">
</head>
<body>
	<div class = "header_container">
		<div class="header_login">
			<div class="mini_logo">
				<a href="main.do"><img src="<%=request.getContextPath()%>/img/logo.png" alt="로고" class="header_logo_img"></a>
			</div>
			<c:if test="${session_mem_id == null }">
				<ol class="breadcrumb">
					<li><a href="${pageContext.request.contextPath }/login.do">로그인</a></li>
					<li><a href="${pageContext.request.contextPath }/sign_up.do">회원가입</a></li>
				</ol>
			</c:if> <c:if test="${session_mem_id != null }">
				<ol class="breadcrumb">
					<li><a href="${pageContext.request.contextPath }/member_logout.do">로그아웃</a></li>
					<li><a href="${pageContext.request.contextPath }/mypage_main.do">마이페이지</a></li>
				</ol>
			</c:if>
		</div>
		<div class="header_search_logo">
			<%-- 도서 번호, 책이름, 작가, 출판사를 검색하는 검색창. --%>
			<div class="header_logo">
				<a href="main.do"><img src="<%=request.getContextPath()%>/img/logo.png" alt="로고" class="header_logo_img"></a>
			</div>
			<div id = "main_search_box">
				<form method="post" action="${pageContext.request.contextPath}/book_sale_search_main.do" class="form-inline ">
					<select name= "book_sale_search_category">
						<option value="b_isbn">도서 번호</option>
						<option value="b_name">책이름</option>
						<option value="b_author">작가</option>
						<option value="b_pub_company">출판사</option>
					</select><input name="book_sale_search_item" placeholder="검색"><button type="submit" class = "btn_blue" value="검색">검색</button>
				</form>
			</div>
		</div>
		<div class="header_menubar">
					<ul  class="nav nav-tabs nav-justified">
						<li><a href="${pageContext.request.contextPath }/book_sale_all_list.do">중고도서</a></li>
						<li><a href="${pageContext.request.contextPath }/book_request_list.do">도서요청</a></li>
						<li><a href="${pageContext.request.contextPath }/commu_list.do">커뮤니티</a></li>
						<li><a href="${pageContext.request.contextPath }/help_list.do">고객센터</a></li>
					</ul>
		</div>
	</div>