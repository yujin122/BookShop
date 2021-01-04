<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class = "side_bar">
	<div class = "side_tit">
		<h3>고객센터</h3>
	</div>
	<ul>
		<li><a href="${pageContext.request.contextPath }/help_list.do">공지사항</a></li>
		<li><a href = "${pageContext.request.contextPath }/faq_list.do">FAQ(자주하는 질문)</a></li>
		<li><a href = "${pageContext.request.contextPath }/qna_list.do">Q&A</a></li>
	</ul>
</div>
