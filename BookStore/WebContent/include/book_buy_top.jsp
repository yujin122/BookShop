<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id = "menu_box">
	<ul>
		<li><a href = "${pageContext.request.contextPath }/book_buy_list.do">전체</a></li>
		<li><a href = "${pageContext.request.contextPath }/buy_list_menu.do?menu=신청">구매 신청</a></li>
		<li><a href = "${pageContext.request.contextPath }/buy_list_menu.do?menu=취소">구매 취소</a></li>
		<li><a href = "${pageContext.request.contextPath }/buy_list_menu.do?menu=완료">구매 완료</a></li>
	</ul>
</div>