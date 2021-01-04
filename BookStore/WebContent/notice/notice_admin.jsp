<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/help_notice/notice.css">
<script type="text/javascript">
	
	function check(num, page) {
		var del = confirm("정말로 삭제하시겠습니까?");
		if(del) {
			location.href="book_notice_delete.do?num="+num+"&page="+page;
		}
	}

</script>
</head>
<body>

	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class="cont_box">
			<jsp:include page="../include/help_side.jsp" />
			<div class="main_box">
				<h3>공지사항 - 관리자 모드</h3>
				<div class="notice_box">
					<table>
						<tr class="noticeTh">
							<th class="no">번호</th>
							<th class="title">제목</th>
							<th class="cont">내용</th>
							<th class="date">작성일</th>
							<th class="bt"></th>
						</tr>
						<c:set var="list" value="${List }" />
						<c:if test="${!empty list }">
							<c:forEach items="${list }" var="notice">
								<tr class="noticeCont">
									<td align="center">${notice.getN_num() }</td>
									<td>${notice.getN_title() }</td>
									<td style="white-space: pre-line;">${notice.getN_cont() }</td>
									<td>${notice.getN_date().substring(0,10) }</td>
									<td>
										<input type="button" class="btn_white" value="수정" onclick="location='notice_update.do?num=${notice.getN_num() }&page=${page }'"> <br /> 
										<input type="button" class="btn_white" value="삭제" onclick="check(${notice.getN_num() },${page })">
									</td>
								</tr>
							</c:forEach>
						</c:if>

						<c:if test="${empty list }">
							<td colspan="6">
								<h2>등록된 공지사항이 없습니다.</h2>
							</td>
						</c:if>
						<tr>
							<td colspan="6" class="submit">
								<input type="button" class="btn_blue" value="등록" onclick="location='notice_insert.do?num=${notice.getN_num() }&page=${page }'"><br />
							</td>
						</tr>
					</table>
				</div>

				<div class="page_box">
					<c:if test="${page > block }">
						<a href="notice_admin.do?page=1"> << </a>
						<a href="notice_admin.do?page=${startBlock-1 }"> < </a>
					</c:if>

					<c:forEach begin="${startBlock }" end="${endBlock }" step="1" var="i">
						<c:if test="${i == page }">
							<b><a href="notice_admin.do?page=${i }">[${i }]</a></b>
						</c:if>
						<c:if test="${i != page }">
							<a href="notice_admin.do?page=${i }">[${i }]</a>
						</c:if>
					</c:forEach>

					<c:if test="${endBlock < allPage }">
						<a href="notice_admin.do?page=${endBlock + 1 }"> > </a>
						<a href="notice_admin.do?page=${allPage }"> >> </a>
					</c:if>
				</div>

			</div>

		</div>
	</div>

	<jsp:include page="../include/bottom.jsp" />

</body>
</html>