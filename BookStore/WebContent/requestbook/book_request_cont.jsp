<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "css/requestbook/requestbook.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
</head>
<body>
	<jsp:include page="../include/top.jsp" />
	
	<div class="container">
		
			<div class = "cont_box">
				<jsp:include page="../include/book_request_side.jsp" />
				
				<div class = "main_box">
					<div id = "request_cont_box">
					<c:set var= "request" value = "${dto }" />
						<c:if test="${!empty request }">
							<h3>${request.getR_title() }</h3>
							<p><span>${request.getR_mem_nickname() }(${request.getR_member() })</span><span>${request.getR_date().substring(0,16) }</span><span>조회수 : ${request.getR_hit() }</span></p>
							<div id = "request_tb_box">
								<table id = "request_tb">
									<tr>
										<th>도서명</th>
										<td colspan = "3">${request.getR_book_title() }</td>
									</tr>
									<tr>
										<th>출판사</th>
										<td>${request.getR_pub_company() }</td>
										<th>저자</th>
										<td>${request.getR_author() }</td>
									</tr>
									<tr>
										<th>희망가격</th>
										<td><fmt:formatNumber value = "${request.getR_price() }" /> 원</td>
										<th>연락처</th>
										<td>${request.getR_contact() }</td>
									</tr>
								</table>
							</div>
							<textarea rows="20" cols="60" id = "request_cont" readonly>${request.getR_contents() }</textarea>
						</c:if>
						
					</div>
					<div id = "request_btn_box">
						<c:if test="${session_mem_id == request.getR_member() }">
							<button class="btn_white" onclick = "location = 'book_request_update_form.do?num=${request.getR_num() }&page=${page }'">수정</button>
							<button class="btn_white" onclick = "if(confirm('삭제하시겠습니까?')){
								location.href = '${pageContext.request.contextPath }/book_request_delete.do?num=${request.getR_num() }&page=${page }';
							}else{ return; }">삭제</button>
						</c:if>
						<c:if test="${empty search_label and empty search_txt }">
							<button class="btn_white" onclick = "location = 'book_request_list.do?page=${page }'">목록</button>
						</c:if>
						<c:if test="${not empty search_label and not empty search_txt }">
							<button class="btn_white" onclick = "location = 'book_request_search.do?page=${page }&search_label=${search_label }&search_txt=${search_txt }'">목록</button>
						</c:if>
						
					</div>
				</div>
			</div>
		</div>

<jsp:include page="../include/bottom.jsp" />
</body>
</html>