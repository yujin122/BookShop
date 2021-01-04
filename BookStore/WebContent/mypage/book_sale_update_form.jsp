<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/book_sale/book_insert.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script>
	function check(){
		if($("#book_quality").val() == ""){
			alert("상품품질을 선택해주세요.");
			$("#book_quality").focus();
			return false;
		}
		if($("#book_sell").val() == ""){
			alert("판매가를 입력해주세요.");
			$("#book_sell").focus();
			return false;
		}
		if($("#books_status").val() == ""){
			alert("판매상태를 선택해주세요.");
			$("#books_status").focus();
			return false;
		}
		if($("#book_qty").val() == ""){
			alert("재고 수량을 입력해주세요.");
			$("#book_qty").focus();
			return false;
		}
		if($("#book_direct").val() == ""){
			alert("직거래 가능을 선택해주세요.");
			$("#book_direct").focus();
			return false;
		}
		if($("#ship_charge").val() == ""){
			alert("배송비를 입력해주세요.");
			$("#ship_charge").focus();
			return false;
		}
	}
</script>
</head>
<body>
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div id="main">
			<div class = "cont_box">
				<jsp:include page="../include/mypage_side.jsp" />

				<div class = "main_box">
					<h3>판매 도서 수정</h3>
					<hr>
					<div id = "book_update_form_box">
						<c:set var = "book_info" value = "${bi_dto }" />
						<c:set var = "book_sale" value = "${bs_dto }" />
						<c:set var = "cate_list" value = "${cate_list }" />
						<form method = "post" action = "book_sale_update.do" enctype = "multipart/form-data" onsubmit = "return check()">
						<input type = "hidden" value = "${book_sale.getS_num() }" name = "num">
						<input type = "hidden" value = "${page }" name = "page">
						<p>도서 정보 입력</p>
						<div class = "book_form_table_box">
							<table>
								<tr id = "cate">
									<th>도서 분야</th>
									<td>
										${cate_list.get(0).getCate_name() } > ${cate_list.get(1).getCate_name() }
									</td>
								</tr>
								<tr>
									<td colspan = '2'>
										<c:if test="${empty book_info.getB_image() }">
										<img src = './img/nophoto.png' class = 'book_insert_img'>
										</c:if>
										<c:if test="${!empty book_info.getB_image() }">
										<img src = '${book_info.getB_image() }' class = 'book_insert_img'>
										</c:if>
									</td>
								</tr>
								<tr>
									<th>ISBN</th>
									<td>${book_info.getB_isbn() }</td>
								</tr>
								<tr>
									<th>도서명</th>
									<td>${book_info.getB_name() }</td>
								</tr>
								<tr>
									<th>저자명</th>
									<td>${book_info.getB_author() }</td>
								</tr>
								<tr>
									<th>옮긴이</th>
									<td>${book_info.getB_translator() }</td>
								</tr>
								<tr>
									<th>출판사</th>
									<td>${book_info.getB_pub_company() }</td>
								</tr>
								<tr>
									<th>출간일</th>
									<td>${book_info.getB_pub_date().substring(0,7) }</td>
								</tr>
								<tr>
									<th>정가</th>
									<td><fmt:formatNumber value = "${book_info.getB_price() }" /></td>
								</tr>
								</table>
							</div>
							<hr>
							<p>상품 정보 입력</p>
							<div class = "book_form_table_box">
								<table>
								<tr>
									<th>상품품질<span class = "star">*</span></th>
									<td>
										<select class="form-control" name = "book_quality" id = "book_quality">
											<option value = "">--- 상품 품질 ---</option>
											<option value = "최상"
												<c:if test="${book_sale.getS_quality() == '최상' }"> selected</c:if>>최상</option>
											<option value = "상"
												<c:if test="${book_sale.getS_quality() == '상' }"> selected </c:if>>상</option>
											<option value = "중"
												<c:if test="${book_sale.getS_quality() == '중' }"> selected </c:if>>중</option>
										</select>
										
									</td>
								</tr>
								<tr>
									<th>판매가<span class = "star">*</span></th>
									<td><input type = "text" class="form-control" name = "book_sell" id = "book_sell" value = "${book_sale.getS_price() }" placeholder="ex) 5000"></td>
								</tr>
								<tr>
									<th>판매상태<span class = "star">*</span></th>
									<td>
										<select class="form-control" name = "books_status">
											<option value = "판매중" class="form-control"
											<c:if test="${book_sale.getS_state() == '판매중' }">selected</c:if>>판매중</option>
											<option value = "판매완료" class="form-control"
											<c:if test="${book_sale.getS_state() == '판매완료' }">selected</c:if>>판매완료</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>재고수량<span class = "star">*</span></th>
									<td><input type = "number" class="form-control" name = "book_qty" id = "book_qty" min = "0" max= "30" value = "${book_sale.getS_qty() }"></td>
								</tr>
								</table>
							</div>
							<hr>
							<p>거래 정보 입력</p>
							<div class = "book_form_table_box">
								<table>
								<tr>
									<th>직거래 가능<span class = "star">*</span></th>
									<td>
										<select name = "book_direct" class="form-control" id = "book_direct">
											<option value = "">--- 직거래 ---</option>
											<option value = "가능"
											<c:if test="${book_sale.getS_direct() == '가능' }"> selected</c:if>>가능</option>
											<option value = "불가능"
											<c:if test="${book_sale.getS_direct() == '불가능' }">selected</c:if>>불가능</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>배송비<span class = "star">*</span></th>
									<td><input type = "text" class="form-control" name = "ship_charge" id = "ship_charge" value = "${book_sale.getS_charge() }" placeholder="ex) 5000"></td>
								</tr>
								<tr>
									<th>상품 설명</th>
									<td>
									<textarea rows="20" class="form-control" name = "book_user_cont" id = "book_content">${book_sale.getS_contents() }</textarea>
									</td>
								<tr>
								<tr>
									<td></td>
									<td>
									<input type = "file" name = "book_user_img_new" id = "user_book_img">
									<input type = "hidden" name = "book_user_img_old" value = "${book_sale.getS_image() }">
									</td>
								</tr>
							</table>
						</div>
						<div id = "book_insert_btn_box">
							<input type = "submit" class = "btn_blue" value = "수정">
							<input type = "button" class = "btn_white" onclick = "javascript:history.back()" value = "취소">
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="../include/bottom.jsp" />
				