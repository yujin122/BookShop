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
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		  $(".noticeCont").hide();
		
		  $(".noticeList").click(function()  {  
		    $(".noticeCont").not($(this).next(".noticeCont").slideToggle(500)).slideUp(); // noticeCont 클래스를 가진 div를 표시/숨김(토글)
		  });
		});
</script>
</head>
<body>

	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class="cont_box">
			<jsp:include page="../include/help_side.jsp" />
			<div class="main_box">
				<h3 id = "main_title">공지사항</h3>
				<table>
					<tr>
						<td class="notice_td">
						<c:set var="list" value="${List }" />
							<c:if test="${!empty list }">
								<c:forEach items="${list }" var="notice">
									<div class="noticeList"> 
										${notice.getN_title() } <em>${notice.getN_date().substring(0,10) }</em>
									</div>
									<div class="noticeCont" style="white-space: pre-line;">
										${notice.getN_cont() }
									</div>
								</c:forEach>
							</c:if> 
							<c:if test="${empty list }">
								<h3>등록된 공지사항 없습니다.</h3>
							</c:if>
						</td>
					</tr>

					<tr align="right">
						<c:if test="${session_mem_id == 'admin' }">
							<td><input type="button" class="btn_blue" onclick="location='notice_admin.do'" value="관리자모드"></td>
						</c:if>
					</tr>
				</table>


				<div class="page_box">
					<c:if test="${page > block }">
						<a href="help_list.do?page=1"> << </a>
						<a href="help_list.do?page=${startBlock-1 }"> < </a>
					</c:if>

					<c:forEach begin="${startBlock }" end="${endBlock }" step="1" var="i">
						<c:if test="${i == page }">
							<b><a href="help_list.do?page=${i }">[${i }]</a></b>
						</c:if>
						<c:if test="${i != page }">
							<a href="help_list.do?page=${i }">[${i }]</a>
						</c:if>
					</c:forEach>

					<c:if test="${endBlock < allPage }">
						<a href="help_list.do?page=${endBlock + 1 }"> > </a>
						<a href="help_list.do?page=${allPage }"> >> </a>
					</c:if>
				</div>

				<div class="search_box">
					<form method="post" action="notice_search.do">
						<select name="search_list" class="search_select">
							<option value="title">제목</option>
							<option value="cont">내용</option>
							<option value="title_cont">제목+내용</option>
						</select> 
						<input type="text" name="search_word" id="search_txt" size="20" placeholder="검색어를 입력하세요."> 
						<input type="submit" class="btn_blue" value="검색">
					</form>
				</div>
				
			</div>
		</div>

	</div>

	<jsp:include page="../include/bottom.jsp" />
</body>
</html>