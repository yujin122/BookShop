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
<script type="text/javascript">
	function date_check(){
		if($("#date1").val() == "" || $("#date2").val() == ""){
			alert("날짜를 입력해주세요");
			return false;
		}else{
			if($("#date1").val() > $("#date2").val()){
				alert("날짜를 순서대로 입력해주세요");
				return false;
			}
		}
	}
</script>
</head>
<body>

	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class = "cont_box">
				<jsp:include page="../include/mypage_side.jsp" />
				
				<div class = "main_box">
						<h3 id = "main_title">전체 주문 내역</h3>
						<jsp:include page="../include/book_buy_top.jsp" />				
						<div id = "search_box">
							<p>날짜별 검색<p>
							<form method = "post" action = "book_buy_search.do" class="form-inline" onsubmit = "return date_check();">
								<input type = "date" class="form-control" name = "date1" id = "date1"> ~ <input type = "date" class="form-control" name = "date2" id = "date2">
								<input type = "submit" value = "검색" class = "btn_blue">
								<input type = "button" value = "전체" class = "btn_white" onclick = "location = '${pageContext.request.contextPath}/book_buy_list.do'">
							</form>
						</div>
					
						<div id="buy_list_box">
							<table class = "my_tb">
								<tr>
									<th>번호</th>
									<th colspan="2" class = "cont_td">도서 정보</th>
									<th>수량</th>
									<th>총 금액<span>(배송비)</span></th>
									<th>판매자</th>
									<th>처리상태</th>
								</tr>
								<c:set var="list" value="${list }" />
								<c:if test="${!empty list }">
									<c:forEach items="${list }" var="buy">
										<tr>
											<td colspan="7" class = "date_td">${buy.getBuy_date().substring(0,10) }</td>
										</tr>
										<tr>
											<td rowspan="3">${buy.getBuy_num() }</td>
											<td rowspan="3">
												<c:if test="${empty buy.getB_image() }">
													<!-- 도서 정보 이미지가 없을 때  -->
													<c:if test="${!empty buy.getS_image() }">
														<!-- 판매자가 올린 이미지가 있을때 대체 -->
														<img
															src="${pageContext.request.contextPath }/upload/book${buy.getS_image() } "
															class = "book_img">
													</c:if>
													<c:if test="${empty buy.getS_image() }">
														<!-- 판매자가 올린 이미지가 없을때 대체 -->
														<img src="./img/nophoto.png" class = "book_img">
													</c:if>
												</c:if> <c:if test="${!empty buy.getB_image() }">
													<img src="${buy.getB_image() }"  class = "book_img">
												</c:if>
											</td>
											<td class = "cont_td">
												<a href="${pageContext.request.contextPath }/book_buy_cont.do?num=${buy.getBuy_num() }&page=${page }">${buy.getB_name() }</a>
											</td>
											<td rowspan="3">${buy.getBuy_qty() }</td>
											<td rowspan="3">
												<c:set var="price" value="${buy.getS_price() }" />
												<c:set var="qty" value="${buy.getBuy_qty() }" />
												<fmt:formatNumber value="${price * qty }" />원<br> (${buy.getS_charge() })
											</td>
											<td rowspan="3">
												${buy.getMem_nickname() }<br>(${buy.getS_member() })
											</td>
											<td rowspan="3">${buy.getBuy_state() }</td>
										</tr>
										<tr>
											<td class = "cont_td">${buy.getB_author() }/ ${buy.getB_pub_company() } / ${buy.getB_pub_date().substring(0,10) }</td>
										</tr>
										<tr>
											<td class = "cont_td">
											<fmt:formatNumber var = "b_price" value = "${buy.getB_price() }" />
											<fmt:formatNumber var = "s_price" value = "${buy.getS_price() }" />
											<span>${b_price }원</span><br>
											${s_price }원	
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty list }">
									<tr>
										<td colspan="7">
											<h3>도서가 존재하지 않습니다.</h3>
										</td>
									</tr>
								</c:if>
							</table>
							</div>
							<div class="page_box">
				
								<c:if test="${page > block }">
									<a href="book_buy_list.do?page=1"> << </a>
									<a href="book_buy_list.do?page=${startBlock-1 }"> < </a>
								</c:if>
				
								<c:forEach begin="${startBlock }" end="${endBlock }" step="1"
									var="i">
									<c:if test="${i == page }">
										<b><a href="book_buy_list.do?page=${i }">[${i }]</a></b>
									</c:if>
									<c:if test="${i != page }">
										<a href="book_buy_list.do?page=${i }">[${i }]</a>
									</c:if>
								</c:forEach>
				
								<c:if test="${endBlock < allPage }">
									<a href="book_buy_list.do?page=${endBlock + 1 }"> > </a>
									<a href="book_buy_list.do?page=${allPage }"> >> </a>
								</c:if>
				
							</div>
						</div>
					</div>
				</div>
	
	<jsp:include page="../include/bottom.jsp" />
					