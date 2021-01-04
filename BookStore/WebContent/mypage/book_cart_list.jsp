<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<link rel = "stylesheet" href = "css/mypage/book_buy_list.css">
<script type="text/javascript">
	$(function(){
		$("#allCheck").click(function(){
			if($("#allCheck").prop("checked")){
				$("input:checkbox").prop("checked", true);
			}else{
				$("input:checkbox").prop("checked", false);
			}
		});
	});
	
	function del_check(){
		if(!$("input:checkbox").is(":checked")){
			alert("삭제하실 중고도서를 선택해주세요.");
			return false;
		}
		
		if(confirm("삭제하시겠습니까?")){
			return true;
		}else{
			return false;
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
					<h3 id = "main_title">장바구니</h3>
					<form method = "post" action = "book_cart_del.do" class="form-inline" onsubmit="return del_check();">
					<div id = "book_cart_box">
						<div id = "cart_del_box">
							<input type = "submit" class = "btn_white" value = "삭제">
						</div>
						<table class = "my_tb">
						<c:set var = "list" value = "${list }" />
						<tr>
							<th><input type = "checkbox" id = "allCheck"></th>
							<th>번호</th>
							<th colspan = "2" class = "cont_td">도서정보</th>
							<th>수량</th>
							<th>판매자</th>
							<th>구매</th>
						</tr>
							<c:if test="${!empty list }">
							<c:forEach items="${list }" var="cart">
							<tr>
								<td colspan = "7" class = "date_td">${cart.getC_date().substring(0,10) }</td>
							</tr>
							<tr>
								<td rowspan="3">
									<input type = "checkbox" name = "delChecks" class="form-control" id = "delChecks" value = "${cart.getC_num() }">
								</td>
								<td rowspan="3">${cart.getC_num() }</td>
								<td rowspan="3">
								<c:if test="${empty cart.getB_image() }"> <!-- 도서 정보 이미지가 없을 때  -->
									<c:if test="${!empty cart.getS_image() }"> <!-- 판매자가 올린 이미지가 있을때 대체 -->
										<img src="${pageContext.request.contextPath }/upload/book${cart.getS_image() }" class = "book_img">
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
								<td rowspan="3">수량 : ${cart.getC_qty() }</td>
								<td rowspan="3">${cart.getMem_nickname() }<br>(${cart.getS_member() })</td> <%-- 회원 닉네임 넣기 --%>
								<td rowspan="3" id = "sale_btn_td">
								<input type="button" value="구매" class = "btn_blue" onclick = "location = '${pageContext.request.contextPath}/book_buy_form.do?s_num=${cart.getS_num() }&count=${cart.getC_qty() }&c_num=${cart.getC_num() }'">
								</td>
							</tr>
							<tr>
								<td class = "cont_td">${cart.getB_author() } / ${cart.getB_pub_company() } / ${cart.getB_pub_date().substring(0,7) }</td>
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
								<td colspan = "7">
									<h3>장바구니가 비어있습니다.</h3>
								</td>
							</tr>	
						</c:if>
						
						</table>
					</div>
					</form>
					<div class = "page_box">
						<c:if test="${page > block }">
						<a href = "book_cart_list.do?page=1"> << </a>
						<a href = "book_cart_list.do?page=${startBlock-1 }"> < </a>
						</c:if>
						
						<c:forEach begin = "${startBlock }" end = "${endBlock }" step="1" var = "i">
							<c:if test="${i == page }">
								<b><a href = "book_cart_list.do?page=${i }">[${i }]</a></b>
							</c:if>
							<c:if test="${i != page }">
								<a href = "book_cart_list.do?page=${i }">[${i }]</a>
							</c:if>
						</c:forEach>
						
						<c:if test="${endBlock < allPage }">
							<a href = "book_cart_list.do?page=${endBlock + 1 }"> > </a>
							<a href = "book_cart_list.do?page=${allPage }"> >> </a>
						</c:if>
					</div>
				</div>
			</div>
		</div>

<jsp:include page="../include/bottom.jsp" />