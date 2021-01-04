<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "css/requestbook/requestbook.css">
</head>
<body>
	
	<jsp:include page="../include/top.jsp" />
	<div class="container">

			<div class = "cont_box">
				<jsp:include page="../include/book_request_side.jsp" />
				
				<div class = "main_box">
					<div class = "request_search_box">
						<p><span>${search_txt }</span> 에 대한 검색 결과입니다.</p>
						<form method = "post" action = "book_request_search.do">
						<select class = "search_select" name = "search_label">
							<option value = "title">제목</option>
							<option value = "cont">내용</option>
							<option value = "writer">작성자</option>
							<option value = "title_cont">제목+내용</option>
						</select>
						<input type = "text" id = "search_txt" name = "search_txt" size = "30" placeholder="검색어를 입력하세요">
						<input type = "submit" class = "btn_blue" value = "검색">
					</form>
					</div>
					<table class = "book_tb">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>희망가격</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
						<c:set var = "list" value = "${list }" />
						<c:if test="${!empty list }">
							<c:forEach items="${list }" var = "request">
								<tr>
									<td>${request.getR_num() }</td>
									<td class = "tit_td"><a href = "book_request_cont.do?num=${request.getR_num() }&page=${page }&search_label=${search_label }&search_txt=${search_txt }">${request.getR_title() }</a></td>
									<td><fmt:formatNumber value = "${request.getR_price() }" /></td>
									<td>${request.getR_mem_nickname() }(${request.getR_member() })</td>
									<td>${request.getR_date().substring(0,10) }</td>
									<td>${request.getR_hit() }</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list }">
							<tr>
								<td colspan = "6">
									<h3>검색 결과에 해당하는 게시물이 존재하지 않습니다.</h3>
								</td>
							</tr>
						</c:if>
					</table>
					<div class = "page_box">
						<c:if test="${page > block }">
							<a href = "book_request_search.do?page=1&search_label=${search_label }&search_txt=${search_txt }"> << </a>
							<a href = "book_request_search.do?page=${startBlock-1 }&search_label=${search_label }&search_txt=${search_txt }"> < </a>
						</c:if>
						
						<c:forEach begin = "${startBlock }" end = "${endBlock }" step="1" var = "i">
							<c:if test="${i == page }">
								<b><a href = "book_request_search.do?page=${i }&search_label=${search_label }&search_txt=${search_txt }">[${i }]</a></b>
							</c:if>
							<c:if test="${i != page }">
								<a href = "book_request_search.do?page=${i }&search_label=${search_label }&search_txt=${search_txt }">[${i }]</a>
							</c:if>
						</c:forEach>
						
						<c:if test="${endBlock < allPage }">
							<a href = "book_request_search.do?page=${endBlock + 1 }&search_label=${search_label }&search_txt=${search_txt }"> > </a>
							<a href = "book_request_search.do?page=${allPage }&search_label=${search_label }&search_txt=${search_txt }"> >> </a>
						</c:if>	
					</div>							
				</div>
			</div>
		</div>

<jsp:include page="../include/bottom.jsp" />
</body>
</html>