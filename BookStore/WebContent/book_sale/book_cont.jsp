<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "css/book_sale/booksale_cont.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="./js/book_cont.js"></script>
<script type="text/javascript">
	$(function(){
		
		if("${cart }" == 'cart'){
			$("#list_btn").hide();
		}
		
		var loc = "";
		
		if("${page }" != ""){
			loc = "${pageContext.request.contextPath }/book_sale_all_list.do?page=${page }";
			if("${code }" != ""){
				loc = "${pageContext.request.contextPath }/category_list.do?page=${page }&code=${code }";
				if("${menu }" != ""){
					loc = "${pageContext.request.contextPath }/category_list_product.do?page=${page }&code=${code }&menu=${menu }";
				}
			}
		}
		
		$("#list_btn").attr("onclick", "location = '"+loc+"'");
		
	});
</script>
</head>
<body>
	<jsp:include page="../include/top.jsp" />
	<div class="container">
			<div class = "cont_box">
				<div id = "book_sale_cont_box" align = "left">
					<c:set var = "book_info" value = "${bi_dto }" />
					<c:set var = "book_sale" value = "${bs_dto }" />
					<c:set var = "member" value = "${mem_dto }" />
					<c:set var = "cate_list" value = "${cate_list }" />
					<p>[${book_sale.getS_state() }]</p>
					<h2>${book_info.getB_name() }</h2>
					<span>${book_info.getB_author() }</span>|<span>${book_info.getB_pub_company() }</span>|<span>${book_info.getB_pub_date().substring(0,10) }</span>|<span>조회수 : ${book_sale.getS_hit() }</span>
					<input type = "hidden" id = "no"><!--답변폼 넘버 넘겨줌 -->
					<input type = "hidden" value = "${book_sale.getS_num() }" id = "s_num">
					<div id = "book_sale_info_box">
						<table>
							<tr>
								<td rowspan = "7" id = "sale_cont_bimg_td">
								<c:if test="${empty book_info.getB_image() }"> <!-- 도서 정보 이미지가 없을 때  -->
									<c:if test="${!empty book_sale.getS_image() }"> <!-- 판매자가 올린 이미지가 있을때 대체 -->
										<img src="${pageContext.request.contextPath }/upload/book${book_sale.getS_image() } " class = "sale_cont_bimg">
									</c:if>
									<c:if test="${empty book_sale.getS_image() }">	<!-- 판매자가 올린 이미지가 없을때 대체 -->
										<img src="./img/nophoto.png" class = "sale_cont_bimg">
									</c:if>
								</c:if>
								<c:if test="${!empty book_info.getB_image() }">
									<img src="${book_info.getB_image() }" class = "sale_cont_bimg">
								</c:if>					
								</td>
								<th>정가  </th>
								<td><fmt:formatNumber value = "${book_info.getB_price() }" /> 원 </td>
							</tr>
							<tr>
								<th>판매가 </th>
								<td><fmt:formatNumber value = "${book_sale.getS_price() }" /> 원 </td> 
							</tr>
							<tr>
								<th>상태</th>
								<td>${book_sale.getS_quality() }</td> 
							</tr>
							<tr>
								<th>분야 </th>
								<td>${cate_list.get(0).getCate_name() } > ${cate_list.get(1).getCate_name() } </td> 
							</tr>
							<tr>
								<th>재고량</th>
								<td>${book_sale.getS_qty() } 권 </td> 
							</tr>
							<tr>
								<th>등록일</th>
								<td>${book_sale.getS_date() }</td> 
							</tr>
							<tr>
								<th>수량 </th>
								<td><input type = "number" min = "1" max = "${book_sale.getS_qty() }" name = "s_count" id = "s_count"></td>
							</tr>			
						</table>			
						<div id = "book_sale_cont_btn_box">
							<c:if test="${!empty session_mem_id }">
								<button class="btn_white" onclick = "book_cart('${book_sale.getS_qty() }')">장바구니</button>
								<button class="btn_blue" onclick = "book_buy('${book_sale.getS_qty() }')">구매</button>
							</c:if>
							<c:if test="${empty session_mem_id }">
								<button class="btn_white" onclick = "javascript:alert('로그인 후 이용가능합니다.')">장바구니</button>
								<button class="btn_blue" onclick = "javascript:alert('로그인 후 이용가능합니다.')">구매</button>
							</c:if>
						</div>
					</div>
				</div>
				<div id = "sale_middle_box">
					<table id = "sale_cont_info_tb">
						<tr>
							<th><h3>책 소개</h3></th>
							<td> 
								<b>옮긴이</b> : ${book_info.getB_translator() }
								<c:if test="${empty book_info.getB_translator() }">
								(없음)
								</c:if>
								<p>${book_info.getB_contents() }...</p>
							</td>
						</tr>
						<tr>
							<th><h3>판매자 정보</h3></th>
							<td>
								<table id = "sale_info_tb">
									<tr>
										<th>판매자</th>
										<td>${member.getMem_nickname() }(${member.getMem_id() })</td>
									</tr>
									<tr>
										<th>직거래 </th>
										<td>${book_sale.getS_direct() }</td>
									</tr>
									<tr>
										<th>배송비</th>
										<td><fmt:formatNumber value = "${book_sale.getS_charge() }" /> 원 </td>
									</tr>
								
								</table>
							</td>
						</tr>
						<tr>
							<th><h3>상품 설명</h3></th>
							<td>
								<c:if test="${!empty book_sale.getS_contents() }">
								${book_sale.getS_contents() } <br>
								</c:if>
								<c:if test="${!empty book_sale.getS_image() }">
								<img src="${pageContext.request.contextPath }/upload/book${book_sale.getS_image() }" id = "book_cont_img">
								</c:if>
							</td>
						</tr>
					</table>
				</div>
				<div id = "sale_list_btn_box">
					<input type = "button" class = "btn_white" id = "list_btn" value = "목록">	
				</div>
				<div id = "qna_box">
					<h3>상품문의</h3>
					<table class = "book_tb" id = "sale_qna_table">
						<tr id = "tit">
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</table>
					<div class = "page_box"></div>
					<div id = "sale_qna_btn">
						<c:if test="${!empty session_mem_id }">
						<input type = "hidden" value = "${session_mem_id }" id = "session">
						<button onclick = "open_write()" class = "btn_blue" id = "bbbbb">문의하기</button>
					</c:if>
					</div>	
				</div>
			</div>
		</div>

<jsp:include page="../include/bottom.jsp" />
