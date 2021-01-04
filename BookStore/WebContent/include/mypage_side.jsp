<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class = "side_bar">
	<div class = "side_tit">
		<h3>마이페이지</h3>
	</div>
		<ul>
			<li><a href = "${pageContext.request.contextPath }/book_cart_list.do">장바구니</a></li>
			<li><a href = "${pageContext.request.contextPath }/book_buy_list.do">도서 구매 관리</a></li>
			<li><a href = "${pageContext.request.contextPath }/book_sale_list.do">도서 판매 관리</a></li>
			<li><a href = "${pageContext.request.contextPath }/member_write_list.do">내가쓴글</a></li>
			<li><a href = "${pageContext.request.contextPath }/member_cont.do">회원 정보 수정 </a></li>
			<li><a href = "${pageContext.request.contextPath }/member_delete_form.do">회원탈퇴</a></li>
		</ul>
	</div>
					