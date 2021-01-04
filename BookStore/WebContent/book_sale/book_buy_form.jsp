<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/book_sale/book_buy_form.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	
	function buy_form_check(){
		if($("#name").val() == ""){
			alert("이름을 입력해주세요");
			$("#name").focus();
			return false;
		}
		if($("#phone").val() == ""){
			alert("연락처를 입력해주세요");
			$("#phone").focus();
			return false;
		}
		if(!$("#direct").is(":checked") && !$("#delivery").is(":checked")){
			alert("거래 방식을 입력해주세요");
			return false;
		} 
	}
	
</script>
</head>
<body>
	
	<jsp:include page="../include/top.jsp" />
	
	<div class="container">
	
	<div id = "book_buy_form_box">
	
		<h3>중고 도서 구매 신청</h3>
		<p>구매 도서 정보</p>
		<div id = "book_buy_info_box">
			<c:set var = "bs" value = "${bs_dto }" />
			<c:set var = "bi" value = "${bi_dto }" />
			<c:set var = "mem" value = "${mem_dto }" />
			<table>
				<tr>
					<th>번호</th>
					<th colspan = "2">도서 정보</th>
					<th>구매자</th>
					<th>판매자</th>
				</tr>
				<tr>
					<td rowspan = "4">${bs.getS_num() }</td>
					<td rowspan = "4" id = "book_buy_form_img_td">
						<c:if test="${empty bi.getB_image() }">
							<!-- 도서 정보 이미지가 없을 때  -->
							<c:if test="${!empty bs.getS_image() }">
								<!-- 판매자가 올린 이미지가 있을때 대체 -->
								<img
									src="${pageContext.request.contextPath }/upload/book${bs.getS_image() } "
									class = "book_img">
							</c:if>
							<c:if test="${empty bs.getS_image() }">
								<!-- 판매자가 올린 이미지가 없을때 대체 -->
								<img src="./img/nophoto.png" class = "book_img">
							</c:if>
						</c:if> 
						<c:if test="${!empty bi.getB_image() }">
							<img src="${bi.getB_image() }"  class = "book_img">
						</c:if>
					</td>
					<td class = "buy_form_td"><b>${bi.getB_name() }</b></td>
					<td rowspan = "4">${session_mem_nickname }<br>(${session_mem_id })</td>
					<td rowspan = "4">${mem.getMem_nickname() }<br>(${bs.getS_member() })</td>
				</tr>
				<tr>
					<td class = "buy_form_td">${bi.getB_author() } / ${bi.getB_pub_company() } / ${bi.getB_pub_date().substring(0,10) }</td>
				</tr>
				<tr></tr>
				<tr>
					<td class = "buy_form_td">
						<fmt:formatNumber var = "b_price" value = "${bi.getB_price() }" />
						<fmt:formatNumber var = "s_price" value = "${bs.getS_price() }" />
						<span>${b_price }원</span><br>
						${s_price }원
					</td>
				</tr>
				<tr class = "buy_tr">
					<td colspan="5"><hr>수량 : ${cnt }</td>
				</tr>
				<tr class = "buy_tr">
					<td colspan="5">배송비 : <fmt:formatNumber value = "${bs.getS_charge() }" /> 원</td>
				</tr>
				<tr class = "buy_tr">
					<c:set var = "cnt" value = "${cnt }"/>
					<c:set var = "price" value = "${bs.getS_price() }" />
					<c:set var = "charge" value = "${bs.getS_charge() }"></c:set>
					<td colspan="5">
					직거래 시 총 금액 : <fmt:formatNumber value = "${cnt * price }" /> 원 <br>
					</td>
				</tr>
				<tr class = "buy_tr">
					<td colspan="5">
					배송 시 총 금액 : <fmt:formatNumber value = "${cnt * price + charge}" /> 원
					</td>
				</tr>
			</table>
		</div>
		<br>
		<p>구매자 정보 입력</p>
		<form method = "post" action="${pageContext.request.contextPath }/book_buy.do" class="form-inline" onsubmit="return buy_form_check()">
		<div id = "buy_mem_info_box">
				<input type = "hidden" name = "s_num" value = "${bs.getS_num() }">
				<c:if test="${!empty c_num }">
					<input type = "hidden" name = "c_num" value = "${c_num }">
				</c:if>
				<table>
					<tr>
						<th>입금자명</th>
						<td><input type = "text" class="form-control" name = "name" id = "name" value = "${session_mem_name }"></td>
					</tr>
					<tr>
						<th>핸드폰 번호</th>
						<td><input type = "text" class="form-control" name = "phone" id = "phone"></td>
					</tr>
					<tr>
						<th>수량</th>
						<td><input type = "text" class="form-control" name = "cnt" readonly value = "${cnt }"></td>
					</tr>
					<tr>
						<th>거래 방식</th>
						<td>
						<label for = "transaction"></label>
						<input type = "radio" class="form-control" name = "transaction" id = "direct" value = "direct">직거래
						<input type = "radio" class="form-control" name = "transaction" id = "delivery" value = "delivery">배송						
						</td>						
					</tr>
					<tr>
						<th>요청사항</th>
						<td>
							<textarea rows="10" cols="65" class="form-control" name = "request_txt" id = "request_txt"></textarea>
						</td>
					</tr>
				</table>				
		</div>
		<div id = "book_buy_form_btn_box">
			<button type = "submit" class = "btn_blue">구매신청</button>
			<button type = "button" class = "btn_white" onclick = "javascript:history.back()">구매취소</button>
		</div>
		</form>
	</div>
	</div>

<jsp:include page="../include/bottom.jsp" />
	
</body>
</html>