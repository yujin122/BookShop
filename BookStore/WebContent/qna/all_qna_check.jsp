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
<script type="text/javascript">
	function pwd_check(){
		
		if($("#pwd").val() == ""){
			alert("비밀번호를 입력해주세요");
			$("#pwd").focus();
			return false;
		}	
	}
</script>
</head>
<body>

	<jsp:include page="../include/top.jsp" />
		<div class="container">
			<div class = "cont_box">
			<jsp:include page="../include/help_side.jsp" />
				
				<div class = "main_box">
				<h3 id = "main_title">Q&A 게시판</h3>
				<form method="post" action="${pageContext.request.contextPath }/all_qna_check.do" class="form-inline" onsubmit="return pwd_check()">
					<div id="pwd_box">
						<p>비밀번호 확인</p>
							<input type="hidden" name="page" value="${param.page }">
							<input type="hidden" name="num" value="${param.num }"> 
							<input type="hidden" name="btn" value="${param.btn }">
							<c:if test="${!empty param.cate }">
								<input type="hidden" name="cate" value="${param.cate }">
							</c:if>
							<c:if test="${!empty param.search_cate && !empty param.search_label && !empty param.search_txt }">
								<input type="hidden" name="search_cate" value="${param.search_cate }">
								<input type="hidden" name="search_label" value="${param.search_label }">
								<input type="hidden" name="search_txt" value="${param.search_txt }">
							</c:if>
							비밀번호 : <input type="password" class="form-control" name="pwd" id="pwd">
							<div id = "qna_check_btn">
							<input type="submit" class = "btn_blue" value="확인">
							<input type = "button" class = "btn_white" value = "취소" onclick = "javascript:history.back()">
						</div>
					</div>
				</form>
				</div>
			</div>
		</div>
	<jsp:include page="../include/bottom.jsp" />
</body>
</html>