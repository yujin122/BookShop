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
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
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
	<%-- 전체 중고도서 리스트 --%>
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class="cont_box">
			<jsp:include page="../include/category_top.jsp" />

			<div class="main_box" align="center">
				<table>
					<tr class="top_cate_tr"> 
						<td class="top_cate">
							<%-- [전체]를 제외한 나머지 대분류 카테고리 클릭 시 보여지는 중분류 카테고리 --%> <c:set
								var="c_list" value="${cateList }" /> <c:if
								test="${!empty c_list }">
								<c:forEach items="${c_list }" var="c_dto">
									<a href="category_list_product.do?code=${c_dto.getCate_code() }&menu=sub">${c_dto.getCate_name() }&nbsp;&nbsp;&nbsp;</a>
								</c:forEach>
							</c:if>

						</td>
					</tr>
					<%------------- 상단 카테고리 END ----------------%>



					<%-- 나머지 대분류 카테고리 클릭시 보여지는 도서 리스트 --%>
					<tr>
						<td class="main_list">
							<table>
								<c:set var="p_list_other" value="${prodListOther }" />
								<c:if test="${!empty p_list_other }">
									<c:forEach items="${p_list_other }" var="p_list_dto">
										<tr>
											<td rowspan="4">
												<c:if test="${empty p_list_dto.getB_image() }"> <!-- 도서 정보 이미지가 없을 때  -->
													<c:if test="${!empty p_list_dto.getS_image() }"> <!-- 판매자가 올린 이미지가 있을때 대체 -->
														<img src="${pageContext.request.contextPath }/upload/book${p_list_dto.getS_image() } ">
													</c:if>
													<c:if test="${empty p_list_dto.getS_image() }"> <!-- 판매자가 올린 이미지가 없을때 대체 -->
														<img src="./img/nophoto.png">
													</c:if>
												</c:if> 
												<c:if test="${!empty p_list_dto.getB_image() }">
													<img src="${p_list_dto.getB_image() }" width="100" height="auto">
												</c:if>
											</td>
											<td class="state">[${p_list_dto.getS_state() }]</td>
											<td class="hit" rowspan="4">조회수 : ${p_list_dto.getS_hit() }</td>
											<td class="nickname" rowspan="3">${p_list_dto.getMem_nickname() }(${p_list_dto.getS_member() })</td>
										</tr>
										<tr>
											<td><a href="${pageContext.request.contextPath }/book_cont.do?s_num=${p_list_dto.getS_num() }&page=${page }&code=${cate_code }">${p_list_dto.getB_name() }</a></td>
										</tr>
										<tr>
											<td height="20">${p_list_dto.getB_author() } / ${p_list_dto.getB_pub_company() } / ${p_list_dto.getB_pub_date().substring(0,7) }</td>
										</tr>
										<tr>
											<td><fmt:formatNumber var="b_price" value="${p_list_dto.getB_price() }" /> 
												<fmt:formatNumber var="s_price" value="${p_list_dto.getS_price() }" /> 
												<span>${b_price }원</span><br>
												${s_price }원
											<td>
												<c:if test="${!empty session_mem_id }">
													<input type="button" class="btn_blue" value="장바구니" onclick="book_cart_main('${p_list_dto.getS_num() }','${p_list_dto.getS_qty() }')">
													<input type="button" class="btn_white" value="구매" onclick="book_buy_main('${p_list_dto.getS_num() }','${p_list_dto.getS_qty() }')">
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

								<c:if test="${empty p_list_other }">
									<tr>
										<td>
											<h3>등록된 도서가 없습니다.</h3>
										</td>
									</tr>
								</c:if>
							</table>
						</td>
					</tr>
				</table>

				<div class="page_box">
					<c:if test="${page > block }">
						<a href="category_list.do?page=1&code=${cate_code }"> << </a>
						<a href="category_list.do?page=${startBlock - 1 }&code=${cate_code }"> < </a>
					</c:if>

					<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
						<c:if test="${i == page }">
							<b><a href="category_list.do?page=${i }&code=${cate_code }">[${i }]</a></b>
						</c:if>
						<c:if test="${i != page }">
							<a href="category_list.do?page=${i }&code=${cate_code }">[${i }]</a>
						</c:if>
					</c:forEach>

					<c:if test="${endBlock < allPage }">
						<a href="category_list.do?page=${endBlock + 1 }&code=${cate_code }"> > </a>
						<a href="category_list.do?page=${allPage }&code=${cate_code }"> >> </a>
					</c:if>
				</div>

				<div class="search_box">
					<form method="post" class="search_form"
						action="${pageContext.request.contextPath }/book_sale_search_main.do">
						<select name="book_sale_search_category" class="search_select">
							<option value="b_num">도서 번호</option>
							<option value="b_name">책이름</option>
							<option value="b_author">작가</option>
							<option value="b_pub_company">출판사</option>
						</select> 
						<input type="text" name="book_sale_search_item" id="search_txt" size="30" placeholder="검색어를 입력하세요"> 
						<input type="submit" class="btn_blue" value="검색">
					</form>
					<c:if test="${!empty session_mem_id }">
						<input type="button" class="btn_blue" value="중고도서 등록" onclick="location = '${pageContext.request.contextPath}/book_insert_form.do'">
					</c:if>
				</div>

			</div>
		</div>
	</div>

	<jsp:include page="../include/bottom.jsp" />
</body>
</html>