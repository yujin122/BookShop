<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/help_qna/all_qna.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
</head>
<body>
	
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class = "cont_box">
		<jsp:include page="../include/help_side.jsp" />
			
			<div class = "main_box">
		
				<div class = "qna_search_info_box">
					<p><span>${search_txt }</span>에 대한 검색 결과입니다.</p>
					<form method = "post" action = "all_qna_search.do">
						<input type = "hidden" name = "page" value = "${page }">
						<select class = "search_select" name = "search_cate">
							<option value = "전체">전체</option>
							<option value = "회원">회원</option>
							<option value = "판매">판매</option>
							<option value = "취소반품교환">취소&반품&교환</option>
							<option value = "주문결제">주문&결제</option>
							<option value = "배송">배송</option>
						</select>
						<select class = "search_select" name = "search_label">
							<option value = "title">제목</option>
							<option value = "writer">작성자</option>
						</select>
						<input type = "text" name = "search_txt" id = "search_txt" placeholder="검색어를 입력하세요">
						<input type = "submit" class = "btn_blue" value = "검색">
					</form>
				</div>
				<div id = "qna_box">
					<table class = "book_tb">
						<tr>
							<th>번호</th>
							<th>카테고리</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
						<c:set var = "list" value = "${list }" />
						<c:if test="${!empty list }">
						<c:forEach items="${list }" var = "qna">
							<tr>
								<td>${qna.getAq_num() }</td>
								<td>${qna.getAq_category() }</td>
								<td class = "qna_tit_td">
									<c:forEach begin="1" end="${qna.getAq_indent() }">
										&nbsp;&nbsp;&nbsp;
									</c:forEach>
									<c:if test="${qna.getAq_lock() == '1' }">
										<c:if test="${session_mem_id == 'admin' }">
											<a href = "${pageContext.request.contextPath }/all_qna_cont.do?num=${qna.getAq_num() }&page=${page }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }">${qna.getAq_title() }</a>
											<img id = "lock_img" src="img/lock.png">
										</c:if>
										<c:if test="${session_mem_id != 'admin' }">
											<a href = "${pageContext.request.contextPath }/all_qna_check_form.do?num=${qna.getAq_num() }&page=${page }&btn=cont&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }">${qna.getAq_title() }</a>
											<img id = "lock_img" src="img/lock.png">
										</c:if>
									</c:if>
									<c:if test="${qna.getAq_lock() == '0' }">
										<a href = "${pageContext.request.contextPath }/all_qna_cont.do?num=${qna.getAq_num() }&page=${page }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }">${qna.getAq_title() }</a>
									</c:if>
								</td>
								<td id = "nick_td">
									<c:if test="${!empty qna.getAq_mem_nickname() }">
										${qna.getAq_mem_nickname() }(${qna.getAq_writer() })
									</c:if>
									<c:if test="${empty qna.getAq_mem_nickname() }">
										${qna.getAq_writer() }
									</c:if>
								</td>
								<td>${qna.getAq_date().substring(0,10) }</td>
								<td>${qna.getAq_hit() }</td>
							</tr>
						</c:forEach>
						</c:if>
						<c:if test = "${empty list }">
							<tr>
								<td colspan = "6">
									<h3>문의글이 존재하지 않습니다.</h3>
								</td>
							</tr>
						</c:if>
					</table>
				</div>
				<div id = "all_qna_btn">
					<c:if test="${!empty session_mem_id }">
						<input type = "button" value = "문의하기" class = "btn_blue" onclick = "location = '${pageContext.request.contextPath }/all_qna_form.do'">
					</c:if>
					<c:if test="${empty session_mem_id }">
						<input type = "button" value = "문의하기" class = "btn_blue" onclick = "location = '${pageContext.request.contextPath }/all_qna_non_form.do'">
					</c:if>
				</div>
				<div class = "page_box">
					<c:if test="${page > block }">
						<a href = "all_qna_search.do?page=1&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }"> << </a>
						<a href = "all_qna_search.do?page=${startBlock-1 }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }"> < </a>
					</c:if>
					
					<c:forEach begin = "${startBlock }" end = "${endBlock }" step="1" var = "i">
						<c:if test="${i == page }">
							<b><a href = "all_qna_search.do?page=${i }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }">[${i }]</a></b>
						</c:if>
						<c:if test="${i != page }">
							<a href = "all_qna_search.do?page=${i }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }">[${i }]</a>
						</c:if>
					</c:forEach>
					
					<c:if test="${endBlock < allPage }">
						<a href = "all_qna_search.do?page=${endBlock + 1 }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }"> > </a>
						<a href = "all_qna_search.do?page=${allPage }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }"> >> </a>
					</c:if>	
				</div>	
			</div>	
		</div>
	</div>
	<jsp:include page="../include/bottom.jsp" />
	
</body>
</html>