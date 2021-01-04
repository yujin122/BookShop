<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/book_sale/book_main.css">
<script type="text/javascript">
	/* 카테고리 버튼을 클릭 시 frm 폼을 실행하는 메서드. */
	function Cate(cate_code) {
		var form1 = document.forms["frm"];
		var input   = document.createElement('input');
		var cate = cate_code;
		input.type   = 'hidden';

		input.name  = 'cate_code';

		input.value  = cate;

		form1.appendChild(input);
		form1.submit();
	}
	
	function book_cart_main(num, qty) {
		
		var count = 1;
		var s_num = num;
		
		if(qty == '0'){
			alert("재고가 없는 상품입니다.");
		}else{
			$.ajax({
				url : "book_cart_insert.do",
				type : "post",
				data : {
					"count" : count,
					"num" : s_num
				},
				dataType : "text",
				success : function(data) {
					console.log(data);
					if (data > 0) {
						if (confirm("장바구니로 이동하시겠습니까?")) {
							location.href = "book_cart_list.do";
						} else {
							$("#s_count").val("");
							return;
						}
					} else if (data == -1) {
						alert("이미 장바구니에 담은 도서입니다.");
					} else {
						alert("장바구니 담기 실패");
					}
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:"
							+ request.responseText + "\n" + "error:" + error);
				}

			});
		}
		
	}

	function book_buy_main(num, qty) {
		var count = 1;
		var s_num = num;
		
		if(qty == '0'){
			alert("재고가 없는 상품입니다.");
		}else{
			location.href = "book_buy_form.do?s_num="+ num +"&count="+count;
		}
		
	}
</script>

</head>
<body>
<%-- 탑 메뉴 바에서 검색 혹은 검색 후 카테고리 버튼을 클릭 시 이동되는 페이지. --%>
<jsp:include page="../include/top.jsp" />
<div class="container">
		<div class="cont_box">
		<c:set value="${list_cate }" var="list_cate" />
		<c:if test="${!empty list_cate }">
<%-- 카테고리 클릭 시 검색 결과에서 해당 카테고리에 해당되는 데이터만 가져오는 메서드로 가는 히든 폼. --%>
		<form method="post" action="<%=request.getContextPath() %>/book_sale_search_cate.do" name = "frm">
			<input type="hidden" value="${book_sale_search_category }" name = "book_sale_search_category">
			<input type="hidden" value="${book_sale_search_item }" name = "book_sale_search_item">
		</form>
			<div class="side_bar" id = "search_side">
				<table>
					<tr>
						<td class="side_bar_td"><b>카테고리</b></td>
					</tr>
					<c:forEach items = "${list_cate }" var = "dto">
					<%-- 100, 200 번 등 대분류일 경우 bold체함. 아닐 경우 앞에 ㄴ붙임. --%>
					<c:if test="${dto.getCate_code_ref() == 0 }">
						<tr>
							<td>
								<a href="#" onclick="Cate(${dto.getCate_code() })"><b>${dto.getCate_name() }</b></a>
								<br/>
							</td>
						</tr>
					</c:if>
					<c:if test="${dto.getCate_code_ref() != 0 }">
						<tr>
							<td>
								ㄴ<a href="#" onclick="Cate(${dto.getCate_code() })"><font size = "2em">${dto.getCate_name() }</font></a>
								<br/>
							</td>
						</tr>
					</c:if>
					</c:forEach>
				</c:if>
				</table>
			</div>
<%-- 하늘씨가 넘겨준 코드. getB_company()와 getB_date()로 되어 있던 코드를 getB_pub_company와 getB_pub_date로 바꾼 걸 제외하면 변경사항 없음. --%>
		<div id = "book_search_info">
			<p><span>${book_sale_search_item }</span>에 대한 검색 결과입니다.</p>
		</div>
		<div class="main_list">
			<table>
				<c:set var="p_list" value="${List1 }" />
				<c:if test="${!empty p_list }">
					<c:forEach items="${p_list }" var="p_dto">
						<tr>
							<td rowspan="4">
									<c:if test="${empty p_dto.getB_image() }"> <!-- 도서 정보 이미지가 없을 때  -->
									<c:if test="${!empty p_dto.getS_image() }"> <!-- 판매자가 올린 이미지가 있을때 대체 -->
										<img width = "100" height = "170" src="${pageContext.request.contextPath }/upload/book${p_dto.getS_image() }" onclick="location.href='book_cont.do?s_num=${p_dto.getS_num() }&page=${page }'">
									</c:if>
									<c:if test="${empty p_dto.getS_image() }"> <!-- 판매자가 올린 이미지가 없을때 대체 -->
										<img width = "100" height = "170" src="./img/nophoto.png" onclick="location.href='book_cont.do?s_num=${p_dto.getS_num() }&page=${page }'">
									</c:if>
								</c:if> 
								<c:if test="${!empty p_dto.getB_image() }">
									<img width = "100" height = "170" src="${p_dto.getB_image() }" onclick="location.href='book_cont.do?s_num=${p_dto.getS_num() }&page=${page }'">
								</c:if>
							</td>
							<td class="state">[${p_dto.getS_state() }]</td>
							<td class="hit" rowspan="4">조회수 : ${p_dto.getS_hit() }</td>
							<td class="nickname" rowspan="3">${p_dto.getMem_nickname() }(${p_dto.getS_member() })</td>
						</tr>
						<tr>
							<td class="name"><a href="${pageContext.request.contextPath }/book_cont.do?s_num=${p_dto.getS_num() }&page=${page }">${p_dto.getB_name() }</a></td>
						</tr>
						<tr>
							<td>${p_dto.getB_author() } / ${p_dto.getB_pub_company() } / ${p_dto.getB_pub_date().substring(0,7) }</td>
						</tr>
						<tr>
							<td>
								<fmt:formatNumber var="b_price" value="${p_dto.getB_price() }" /> 
								<fmt:formatNumber var="s_price" value="${p_dto.getS_price() }" /> 
								<span>${b_price }원</span><br>
										 ${s_price }원 
							</td>
							<td>
								<c:if test="${!empty session_mem_id }">
									<input type="button" class="btn_blue" value="장바구니" onclick="book_cart_main('${p_dto.getS_num() }','${p_dto.getS_qty() }')">
									<input type="button" class="btn_white" value="구매" onclick="book_buy_main('${p_dto.getS_num() }','${p_dto.getS_qty() }')">
								</c:if> 
								<c:if test="${empty session_mem_id }">
									<input type="button" class="btn_blue" value="장바구니" onclick="javascript:alert('로그인 후 이용가능합니다.')">
									<input type="button" class="btn_white" value="구매" onclick="javascript:alert('로그인 후 이용가능합니다.')">
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="5"><hr></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty p_list }">
					<tr>
						<td colspan="4">
							<h3>등록된 도서가 없습니다.</h3>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>
</div>
<jsp:include page="../include/bottom.jsp" />
