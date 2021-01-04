<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div id = "menu_box">
	<ul>
		<li><a href = "${pageContext.request.contextPath }/book_sale_list.do">판매 목록</a></li>
		<li><a href = "${pageContext.request.contextPath }/sale_list_menu.do?menu=신청">구매 요청</a></li>
		<li><a href = "${pageContext.request.contextPath }/sale_list_menu.do?menu=취소">구매 취소</a></li>
		<li><a href = "${pageContext.request.contextPath }/sale_list_menu.do?menu=완료">판매 완료</a></li>
	</ul>
</div>