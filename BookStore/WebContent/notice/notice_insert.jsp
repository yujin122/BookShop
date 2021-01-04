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
				<h3>공지사항 등록</h3>

				<form method="post" action="notice_insert_ok.do">
					<div class="notice_insert_box">
						<table>
							<tr>
								<th>제목</th>
								<td><input type="text" class="form-control" name="n_title" id="n_title" size="30"></td>
							</tr>
							<tr>
								<th>내용</th>
								<td><textarea rows="20" class="form-control" cols="60" name="n_cont" id="n_cont" placeholder="내용을 입력해주세요."></textarea></td>
							</tr>
							<tr>
								<td class="submit" colspan="2">
									<input type="submit" class="btn_blue" value="등록">
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