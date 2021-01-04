<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="css/help_qna/all_qna_form.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	
	function check(){
		if(!$("#category").val == "카테고리"){
			alert("카테고리를 선택해주세요");
			$("#category").focus();
			return false;
		}
		if($("#title").val() == ""){
			alert("제목을 입력해주세요");
			$("#title").focus();
			return false;
		}
		if($("#cont").val() == ""){
			alert("내용을 입력해주세요");
			$("#cont").focus();
			return false;
		} 
	}
	
</script>
<body>
	
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class = "cont_box">
		<jsp:include page="../include/help_side.jsp" />
			
			<div class = "main_box">
				<h3 id = "main_title">Q&A 답글</h3>
					
					<c:set var="qna" value="${dto }" />
					<form method="post" action="all_qna_reply.do" class="form-inline" onsubmit="return check()">
					<input type="hidden" name="num" value="${qna.getAq_num() }">
					<input type="hidden" name="page" value="${page }">
					<c:if test="${!empty cate}">
						<input type="hidden" name="cate" value="${cate }">
					</c:if>
					<div id = "all_qna_form_box">
						<hr>
						<table>
							<tr>
								<td>
								<select class="form-control" name="category" id="category">
										<option value="카테고리">---- 카테고리 ----</option>
										<option value="회원"
											<c:if test = "${qna.getAq_category() == '회원' }">selected</c:if>>회원</option>
										<option value="판매"
											<c:if test = "${qna.getAq_category() == '판매' }">selected</c:if>>판매</option>
										<option value="취소반품교환"
											<c:if test = "${qna.getAq_category() == '취소반품교환' }">selected</c:if>>취소&반품&교환</option>
										<option value="주문결제"
											<c:if test = "${qna.getAq_category() == '주문결제' }">selected</c:if>>주문&결제</option>
										<option value="배송"
											<c:if test = "${qna.getAq_category() == '배송' }">selected</c:if>>배송</option>
								</select>
								</td>
							</tr>
							<tr>
								<td><input type="text" class="form-control" name="title" id="title" readonly value="RE: ${qna.getAq_title() }"></td>
							</tr>
							<tr>
								<td><textarea rows="30" class="form-control" name="cont" id="cont"placeholder="문의할 내용을 입력해주세요">${qna.getAq_content() }</textarea>
								</td>
							</tr>
						</table>
						<hr>
						<div id = "all_qna_form_btn">
							<input type="submit" class = "btn_blue" value="등록">
							<input type="button" class = "btn_white" value="취소" onclick="location = 'javascript:history.back()'">
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	<jsp:include page="../include/bottom.jsp" />
</body>
</html>