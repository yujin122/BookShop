<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class = "side_bar">
	<div class = "side_tit">
		<h3>도서 요청</h3>
	</div>
	<ul>
		<!-- <li class = "tit_li"><h3>도서 요청</h3></li> -->
		<li><a href = "${pageContext.request.contextPath }/book_request_list.do">도서 요청 게시판</a></li>
		<c:if test="${empty session_mem_id }">
			<li><a href = "javascript:alert('로그인 후 이용가능합니다.')">도서 요청</a></li>
		</c:if>
		<c:if test="${!empty session_mem_id }">
			<li><a href = "${pageContext.request.contextPath }/book_request_form.do">도서 요청</a></li>
		</c:if>
	</ul>
</div>