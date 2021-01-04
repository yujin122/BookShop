<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id = "menu_box">
	<ul>
		<li><a href = "${pageContext.request.contextPath }/faq_list.do">전체</a>
		<li><a href = "${pageContext.request.contextPath }/faq_list_cate.do?cate=회원">회원</a>
		<li><a href = "${pageContext.request.contextPath }/faq_list_cate.do?cate=판매">판매</a>
		<li><a href = "${pageContext.request.contextPath }/faq_list_cate.do?cate=취소반품교환">취소&반품&교환</a>
		<li><a href = "${pageContext.request.contextPath }/faq_list_cate.do?cate=주문결제">주문&결제</a>
		<li><a href = "${pageContext.request.contextPath }/faq_list_cate.do?cate=배송">배송</a>
	</ul>
</div>
