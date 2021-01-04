<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<link rel = "stylesheet" href = "css/mypage/book_buy_list.css">
<title>Insert title here</title>
</head>
<body>
	
	<jsp:include page="../include/top.jsp" />
	
	<div class="container">
		<div id="main">
			<div class = "cont_box">
				<jsp:include page="../include/mypage_side.jsp" />
				
				<div class = "main_box">
					<div id = "info_box">
						<table>
							<tr>
								<c:set var = "mem" value = "${mem_dto }" />
								<td colspan = "3"><p><span>${mem.getMem_nickname() }(${mem.getMem_id() })</span>님 안녕하세요!</p><hr></td>
							</tr>
							<tr>
								<th>장바구니</th>
								<th>구매</th>
								<th>판매</th>
							</tr>
							<tr>
								<td class = "cnt_td"><a href = "book_cart_list.do">${cart_cnt }</a></td>
								<td class = "cnt_td"><a href = "book_buy_list.do">${buy_cnt }</a></td>
								<td class = "cnt_td"><a href = "book_sale_list.do">${sale_cnt }</a></td>
							</tr>
						</table>
					</div>
					<div id = "cart_box">
						<table class = "my_tb">
							<tr>
							<th>번호</th>
							<th colspan = "2" class = "cont_td">도서정보</th>
							<th>수량</th>
							<th>판매자</th>
							<th>구매</th>
							</tr>
							<c:set var = "list" value = "${list }" />
							<c:if test="${!empty list }">
								<c:forEach items="${list }" var="cart">
								<tr>
									<td colspan = "6" class = "date_td">${cart.getC_date().substring(0,10) }</td>
								</tr>
								<tr>
									<td rowspan="3">${cart.getC_num() }</td>
									<td rowspan="3">
									<c:if test="${empty cart.getB_image() }"> <!-- 도서 정보 이미지가 없을 때  -->
										<c:if test="${!empty cart.getS_image() }"> <!-- 판매자가 올린 이미지가 있을때 대체 -->
											<img src="${pageContext.request.contextPath }/upload/book${cart.getS_image() } " class = "book_img">
										</c:if>
										<c:if test="${empty cart.getS_image() }">	<!-- 판매자가 올린 이미지가 없을때 대체 -->
											<img src="./img/nophoto.png" class = "book_img">
										</c:if>
										</c:if>
										<c:if test="${!empty cart.getB_image() }">
											<img src="${cart.getB_image() }" class = "book_img">
										</c:if>	
									</td>
									<td class = "cont_td"><a href = "${pageContext.request.contextPath }/book_cont.do?s_num=${cart.getS_num() }&cart=cart">${cart.getB_name() }</a></td>
									<td rowspan="3">${cart.getC_qty() }</td>
									<td rowspan="3">${cart.getMem_nickname() }<br>(${cart.getS_member() })</td> <%-- 회원 닉네임 넣기 --%>
									<td rowspan="3" id = "sale_btn_td">
									<input type="button" value="구매" class = "btn_blue" onclick = "location = '${pageContext.request.contextPath}/book_buy_form.do?s_num=${cart.getS_num() }&count=${cart.getC_qty() }&c_num=${cart.getC_num() }'">
									</td>
								</tr>
								<tr>
									<td class = "cont_td">${cart.getB_author() } / ${cart.getB_pub_company() } / ${cart.getB_pub_date().substring(0,10) }</td>
								</tr>
								<tr>
									<td class = "cont_td">
										<fmt:formatNumber var = "b_price" value = "${cart.getB_price() }" />
										<fmt:formatNumber var = "s_price" value = "${cart.getS_price() }" />
										<span>${b_price }원</span><br>
										${s_price }원	
									</td>
								</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty list }">
								<tr>
									<td colspan = "6">
										<h3>장바구니가 비어있습니다.</h3>
									</td>
								</tr>	
							</c:if>
							
						</table>
					</div>
					
					<div class = "page_box">
						<c:if test="${page > block }">
						<a href = "mypage_main.do?page=1"> << </a>
						<a href = "mypage_main.do?page=${startBlock-1 }"> < </a>
						</c:if>
						
						<c:forEach begin = "${startBlock }" end = "${endBlock }" step="1" var = "i">
							<c:if test="${i == page }">
								<b><a href = "mypage_main.do?page=${i }">[${i }]</a></b>
							</c:if>
							<c:if test="${i != page }">
								<a href = "mypage_main.do?page=${i }">[${i }]</a>
							</c:if>
						</c:forEach>
						
						<c:if test="${endBlock < allPage }">
							<a href = "mypage_main.do?page=${endBlock + 1 }"> > </a>
							<a href = "mypage_main.do?page=${allPage }"> >> </a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>	
		
	<jsp:include page="../include/bottom.jsp" />