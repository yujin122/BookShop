<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/help_notice/notice.css">
</head>
<body>
	
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class="cont_box">
			<jsp:include page="../include/help_side.jsp" />
			<div class="main_box">
				<h3>공지사항 수정</h3>

				<c:set var="dto" value="${dto }" />
				<form method="post" action="notice_update_ok.do">
					<div class="notice_insert_box">
						<input type="hidden" value="${dto.getN_num() }" name="num">
						<input type="hidden" value="${page }" name="page">
						<table>
							<tr>
								<th>작성일</th>
								<td><input type="text" class="form-control" name="n_date" id="n_date" value="${dto.getN_date().substring(0,10) }" readonly></td>
							</tr>
							<tr>
								<th>제목</th>
								<td><input type="text" class="form-control" name="n_title" id="n_title" size="20" value="${dto.getN_title() }"></td>
							</tr>
							<tr>
								<th valign="top">내용</th>
								<td><textarea rows="20" class="form-control" cols="50" name="n_cont" id="n_cont">${dto.getN_cont() }</textarea></td>
							</tr>
							<tr>
								<td align="center" colspan="2" class="submit"> 
									<input type="submit" class="btn_blue" value="수정">
									<input type="button" class="btn_white" value="취소" onclick="history.back();">
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../include/bottom.jsp" />
	
</body>
</html>