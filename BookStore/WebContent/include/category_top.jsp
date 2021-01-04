<%@page import="com.book.model.CategoryDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.book.model.CategoryDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/book_sale/book_main.css">
</head>
<body>
	<%-- 왼쪽 대분류 카테고리 리스트 --%>
	<div class="side_bar">
		<div class = "side_tit">
			<h3>카테고리</h3>
		</div>
		<ul>
			<li><a href="book_sale_all_list.do"> 전체 </a></li>
			<c:set var="list" value="${List }" />
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="dto">
					<li><a href="category_list.do?code=${dto.getCate_code() }">${dto.getCate_name() }</a></li>
				</c:forEach>
			</c:if>
		</ul>
		<%-- <table>
			<tr>
				<td class="side_bar_td">카테고리</td>
			</tr>
			<tr>
				<td><a href="book_sale_all_list.do"> 전체 </a></td>
			</tr>

			<c:set var="list" value="${List }" />
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="dto">
					<tr>
						<td><a href="category_list.do?code=${dto.getCate_code() }">${dto.getCate_name() }</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</table> --%>
	</div>
				