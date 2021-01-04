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
			
			<%--------------- 상단 중분류 카테고리 -----------------%>
			<div class="main_box" align="center">
				<table>
					<tr>
						<td class="top_cate">
							<%-- [전체] 카테고리 클릭 시 보여지는 중분류 카테고리 --%> <c:set var="a_list" value="${cateAllList }" /> <c:if test="${!empty a_list }">
								<table>
									<%-- [전체] 중분류 카테고리를 6개씩 나누어 보여줌 --%>
									<tr>
										<c:forEach items="${a_list }" var="a_dto">
											<c:set var="cnt" value="${cnt + 1 }" />
											<td><a href="category_list_product.do?code=${a_dto.getCate_code() }&menu=all">
													${a_dto.getCate_name() }</a></td>
											<c:if test="${cnt%6 == 0 }">
									</tr>
									<tr>
										</c:if>
										</c:forEach>
									</tr>
								</table>
							</c:if>
						</td>
					</tr>
					<%------------- 상단 카테고리 END ----------------%>


					<%--------------- 대분류 카테고리 해당하는 전체 도서 리스트 ---------------%>
					<%-- [전체] 카테고리 클릭시 보여지는 도서 리스트 --%>
					<tr>
						<td class="main_list">
							<table>
								<c:set var="p_list" value="${prodList }" />
								<c:if test="${!empty p_list }">
									<c:forEach items="${p_list }" var="p_dto">
										<tr>
											<td rowspan="4">
													<c:if test="${empty p_dto.getB_image() }"> <!-- 도서 정보 이미지가 없을 때  -->
													<c:if test="${!empty p_dto.getS_image() }"> <!-- 판매자가 올린 이미지가 있을때 대체 -->
														<img src="${pageContext.request.contextPath }/upload/book${p_dto.getS_image() }" onclick="location.href='book_cont.do?s_num=${p_dto.getS_num() }&page=${page }'">
													</c:if>
													<c:if test="${empty p_dto.getS_image() }"> <!-- 판매자가 올린 이미지가 없을때 대체 -->
														<img src="./img/nophoto.png" onclick="location.href='book_cont.do?s_num=${p_dto.getS_num() }&page=${page }'">
													</c:if>
												</c:if> 
												<c:if test="${!empty p_dto.getB_image() }">
													<img src="${p_dto.getB_image() }" onclick="location.href='book_cont.do?s_num=${p_dto.getS_num() }&page=${page }'">
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
						</td>
					</tr>
					
				</table>
				

				<div class="page_box">
					<c:if test="${page > block }">
						<a href="book_sale_all_list.do?page=1"> << </a>
						<a href="book_sale_all_list.do?page=${startBlock - 1 }"> < </a>
					</c:if>

					<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
						<c:if test="${i == page }">
							<b><a href="book_sale_all_list.do?page=${i }">[${i }]</a></b>
						</c:if>

						<c:if test="${i != page }">
							<a href="book_sale_all_list.do?page=${i }">[${i }]</a>
						</c:if>
					</c:forEach>

					<c:if test="${endBlock < allPage }">
						<a href="book_sale_all_list.do?page=${endBlock + 1 }"> > </a>
						<a href="book_sale_all_list.do?page=${allPage }"> >> </a>
					</c:if>
				</div>
				
				<div class="search_box">
					<form method="post" class="search_form" action="${pageContext.request.contextPath }/book_sale_search_main.do">
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