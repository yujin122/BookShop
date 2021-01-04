<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/book_sale/book_insert.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript" src = "./js/book_insert.js"></script>
</head>
<body>

	<jsp:include page="../include/top.jsp" />
	<div id = "book_insert_wrap">
		<div class="container">
		<div id = "book_insert_box">
			<h3>중고 도서 등록</h3>
			<div id = "book_search_box">
				<p>등록 도서 검색</p>
				<div id = "book_search_cont">
					<select class="form-control" name = "search_label" id = "search_label">
						<option value = "도서명">도서명</option>
						<option value = "ISBN">ISBN</option>
					</select>
					<input type = "text"  class="form-control" name = "search_txt" id = "book_search_txt">
					<button type = "submit" class= "btn_blue" id = "search_btn" onclick = "search_book()" >검색</button>
				</div>
			</div>
			<div id = "book_form_box">
				<form method = "post" action = "book_insert.do" enctype = "multipart/form-data" onsubmit = "return check()">
				<input type = "hidden" name = "book_cont" id = "book_cont">
				<input type = "hidden" name = "book_img" id = "book_img">
				<hr>
				<p>도서 정보 입력</p>
				<div class = "book_form_table_box">
					<table>
					<tr id = "cate">
						<th>도서 분야<span class = "star">*</span></th>
						<td>
						<c:set var = "list" value = "${cate_list }"/>
						<c:if test = "${!empty list }">
						<select class="form-control" name = "category1" id = "category1" onchange = "cateSelect()">
						<option value ="">---- 카테고리 1 ----</option>
						<c:forEach items="${list }" var = "category">
							<option value = "${category.getCate_code() }">${category.getCate_name() }</option>
						</c:forEach>
						</select>
						</c:if>
						 > 
						<select class="form-control" name = "category2" id = "category2">
							<option value = "">---- 카테고리 2 ----</option>
						</select>  
						</td>
					</tr>
					<tr>
						<th>ISBN</th>
						<td><input type = "text" class="form-control" name = "book_isbn" id = "book_isbn"></td>
					</tr>
					<tr>
						<th>도서명<span class = "star">*</span></th>
						<td><input type = "text" class="form-control" name = "book_title" id = "book_title" size = "40"></td>
					</tr>
					<tr>
						<th>저자명<span class = "star">*</span></th>
						<td><input type = "text" class="form-control" name = "book_writer" id = "book_writer"></td>
					</tr>
					<tr>
						<th>옮긴이</th>
						<td><input type = "text" class="form-control" name = "book_translator" id = "book_translator"></td>
					</tr>
					<tr>
						<th>출판사<span class = "star">*</span></th>
						<td><input type = "text" class="form-control" name = "book_publisher" id = "book_publisher"></td>
					</tr>
					<tr>
						<th>출간일<span class = "star">*</span></th>
						<td><input type = "date" class="form-control" name = "book_date" id = "book_date"></td>
					</tr>
					<tr>
						<th>정가<span class = "star">*</span></th>
						<td><input type = "text" class="form-control" name = "book_price" id = "book_price" placeholder="ex) 5000"></td>
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
								<option value = "최상">최상</option>
								<option value = "상">상</option>
								<option value = "중">중</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>판매가<span class = "star">*</span></th>
						<td><input type = "text" class="form-control" name = "book_sell" id = "book_sell" placeholder="ex) 5000"></td>
					</tr>
					<tr>
						<th>판매상태<span class = "star">*</span></th>
						<td>
							<select class="form-control" name = "books_status">
								<option value = "판매중">판매중</option>
								<option value = "판매완료">판매완료</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>재고수량<span class = "star">*</span></th>
						<td><input type = "number" class="form-control" name = "book_qty" id = "book_qty" min = "1" max= "30"></td>
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
							<select class="form-control" name = "book_direct" id = "book_direct">
								<option value = "">--- 직거래 ---</option>
								<option value = "가능">가능</option>
								<option value = "불가능">불가능</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>배송비<span class = "star">*</span></th>
						<td><input type = "text" class="form-control" name = "ship_charge" id = "ship_charge" placeholder="ex) 5000"></td>
					</tr>
					</table>
				</div>
				<hr>
				<p>상품 소개/구매 유의사항/이미지 등록</p>
				<div class = "book_form_table_box">
					<table>
					<tr>
						<th>상품 설명</th>
						<td>
						<textarea class="form-control" rows="20" cols="55" name = "book_user_cont" id = "book_content"></textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
						<input type = "file" name = "book_user_img" id = "user_book_img">
						</td>
					</tr>
					</table>
				</div>
				<div id = "book_insert_btn_box">
					<input type = "submit" class = "btn_blue" value = "등록">
					<input type = "button" class = "btn_white" value = "취소" onclick = "javascript:history.back()">
				</div>		
			</form>
			</div>
		</div>
	</div>
	</div>
	<jsp:include page="../include/bottom.jsp" />
</body>
</html>