<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/book_sale/book_buy_form.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	
	function buy_update_check(){
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
		<div id="main">
			<div class = "cont_box">
				<jsp:include page="../include/mypage_side.jsp" />

				<div class = "main_box">

					<h3>신청 도서 정보</h3>
					<div id = "book_buy_info_box">
						<c:set var = "bs" value = "${bs_dto }" />
						<c:set var = "bi" value = "${bi_dto }" />
						<c:set var = "bb" value = "${bb_dto }" />
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
								<td class = "buy_form_td"><b><a href = "${pageContext.request.contextPath }/book_cont.do?s_num=${bs.getS_num() }">${bi.getB_name() }</a></b></td>
								<td rowspan = "4"><fmt:formatNumber value = "${bs.getS_price() }" /> 원</td>
								<td rowspan = "4">${session_mem_nickname }<br>(${session_mem_id })</td>
								<td rowspan = "4">${mem.getMem_nickname() }<br>(${bs.getS_member() })</td>
							</tr>
							<tr>
								<td class = "buy_form_td">${bi.getB_author() } / ${bi.getB_pub_company() } / ${bi.getB_pub_date().substring(0,7) }</td>
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
							<tr>
								<td colspan="6" class = "buy_tr"><hr>수량 : ${bb.getBuy_qty() }</td>
							</tr>
							<tr class = "buy_tr">
								<td colspan="6">배송비 : <fmt:formatNumber value = "${bs.getS_charge() }" /> 원</td>
							</tr>
							<tr class = "buy_tr">
								<c:set var = "cnt" value = "${bb.getBuy_qty() }" />
								<c:set var = "price" value = "${bs.getS_price() }" />
								<c:set var = "charge" value = "${bs.getS_charge() }"></c:set>
								<td colspan="6">
								직거래 시 총 금액 : <fmt:formatNumber value = "${cnt * price }" /> 원 <br>
								</td>
							</tr>
							<tr class = "buy_tr">
								<td colspan="6">
								배송 시 총 금액 : <fmt:formatNumber value = "${cnt * price + charge}" /> 원
								</td>
							</tr>
						</table>
					</div>
					<br>
					<h3>구매 신청 정보</h3>
					<form method = "post" class="form-inline" action = "book_buy_update.do" onsubmit="return buy_update_check()">
						<div id = "buy_mem_info_box">
						<input type = "hidden" value = "${bb.getBuy_num() }" name = "num">
						<input type = "hidden" value = "${page }" name = "page">
						<c:if test="${!empty menu }">
							<input type = "hidden" value = "${menu }" name = "menu">
						</c:if>
						<c:if test="${!empty date1 }">
							<input type = "hidden" value = "${date1 }" name = "date1">
							<input type = "hidden" value = "${date2 }" name = "date2">
						</c:if>
							<table>
								<tr>
									<th>신청 상태</th>
									<td>구매 ${bb.getBuy_state() }</td>
								</tr>
								<tr>
									<th>신청 일자</th>
									<td>${bb.getBuy_date().substring(0,10) }</td>
								</tr>
								<tr>
									<th>입금자명</th>
									<td><input name = "name"  class="form-control" id = "name" value = "${bb.getBuy_name() }"></td>
								</tr>
								<tr>
									<th>핸드폰 번호</th>
									<td><input name = "phone" class="form-control" id = "phone" value = "${bb.getBuy_phone() }"></td>
								</tr>
								<tr>
									<th>수량</th>
									<td>${bb.getBuy_qty() }</td>
								</tr>
								<tr>
									<th>거래 방식</th>
									<td>
										<label for = "transaction"></label>
										<input type = "radio" class="form-control" name = "transaction" id = "direct" value = "direct"
										<c:if test="${bb.getBuy_trans() == 'direct' }">
										checked="checked"
										</c:if>>직거래
										<input type = "radio" class="form-control" name = "transaction" id = "delivery" value = "delivery"
										<c:if test="${bb.getBuy_trans() == 'delivery' }">
										checked="checked"
										</c:if>>배송	
									</td>						
								</tr>
								<tr>
									<th>요청사항</th>
									<td>
										<textarea rows="10" name = "request_txt" id = "request_txt">${bb.getBuy_request() }</textarea>
									</td>
								</tr>
						
							</table>
							</div>
							<div id = "book_buy_form_btn_box">
								<button type = "submit" class = "btn_blue">수정</button>
								<button type = "button" class = "btn_white" onclick = "javascript:history.back()">수정취소</button>
							</div>
						</form>
						</div>
					</div>
				</div>
			</div>

<jsp:include page="../include/bottom.jsp" />
		