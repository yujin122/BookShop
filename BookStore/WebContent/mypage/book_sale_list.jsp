<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<link rel = "stylesheet" href = "css/mypage/book_buy_list.css">
</head>
<body>

	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div id="main">
			<div class = "cont_box">
				<jsp:include page="../include/mypage_side.jsp" />

				<div class = "main_box">
					
					<h3 id = "main_title">전체 판매 내역</h3>
					<jsp:include page="../include/book_sale_top.jsp" />
						<div id="buy_list_box">
							<table class = "my_tb">
								<tr>
									<th>번호</th>
									<th>상태</th>
									<th colspan = "2" class = "sale_cont_td">도서 정보</th>
									<th>재고</th>
									<th>수정/삭제</th>
								</tr>
								<c:set var = "list" value = "${list }" />
								<c:if test="${!empty list }">
									<c:forEach items="${list }" var = "sale">
									<tr>
										<td colspan="6" class = "date_td">${sale.getS_date().substring(0,10) }</td>
									</tr>
									<tr>
										<td rowspan = "3">${sale.getS_num() }</td>
										<td rowspan = "3">${sale.getS_state() }</td>
										<td rowspan = "3">
											<c:if test="${empty sale.getB_image() }"> <!-- 도서 정보 이미지가 없을 때  -->
												<c:if test="${!empty sale.getS_image() }"> <!-- 판매자가 올린 이미지가 있을때 대체 -->
													<img src="${pageContext.request.contextPath }/upload/book${sale.getS_image() } " class = "book_img">
												</c:if>
												<c:if test="${empty sale.getS_image() }">	<!-- 판매자가 올린 이미지가 없을때 대체 -->
													<img src="./img/nophoto.png" class = "book_img">
												</c:if>
											</c:if>
											<c:if test="${!empty sale.getB_image()}">
												<img src = "${sale.getB_image() }" class = "book_img">
											</c:if>
										</td>
										<td class = "sale_cont_td">
											<a href = "${pageContext.request.contextPath }/book_cont.do?s_num=${sale.getS_num() }&cart=cart">${sale.getB_name() }</a> 
										</td>
										<td rowspan = "3">${sale.getS_qty() }</td>
										<td rowspan = "3" id = "sale_btn_td">
											<input type = "button" value = "수정" class = "btn_white" onclick = "location = 'book_sale_update_form.do?num=${sale.getS_num() }&page=${page }'"><br>
											<input type = "button" value = "삭제" class = "btn_white" onclick = "if(confirm('삭제하시겠습니까?')){
												location = 'book_sale_delete.do?num=${sale.getS_num() }&page=${page }'
												}else{ return; }">
										</td>
									<tr>
										<td class = "sale_cont_td">${sale.getB_author() }/ ${sale.getB_pub_company() } / ${sale.getB_pub_date().substring(0,7) }</td>
									</tr>
									<tr>
										<td class = "sale_cont_td">
										<fmt:formatNumber var = "b_price" value = "${sale.getB_price() }" />
										<fmt:formatNumber var = "s_price" value = "${sale.getS_price() }" />
										<span>${b_price }원</span><br>
										${s_price }원
										</td>
									</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty list }">
									<tr>
										<td colspan = "7">
											<h3>도서가 존재하지 않습니다.</h3>
										</td>
									</tr>
								</c:if>
							</table>
							
							<div class = "page_box">
		
									<c:if test="${page > block }">
									<a href = "book_sale_list.do?page=1"> << </a>
									<a href = "book_sale_list.do?page=${startBlock-1 }"> < </a>
									</c:if>
									
									<c:forEach begin = "${startBlock }" end = "${endBlock }" step="1" var = "i">
										<c:if test="${i == page }">
											<b><a href = "book_sale_list.do?page=${i }">[${i }]</a></b>
										</c:if>
										<c:if test="${i != page }">
											<a href = "book_sale_list.do?page=${i }">[${i }]</a>
										</c:if>
									</c:forEach>
									
									<c:if test="${endBlock < allPage }">
										<a href = "book_sale_list.do?page=${endBlock + 1 }"> > </a>
										<a href = "book_sale_list.do?page=${allPage }"> >> </a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
<jsp:include page="../include/bottom.jsp" />
				