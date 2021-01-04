<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<link rel="stylesheet" href="css/help_qna/faq_form.css">
<script type="text/javascript">
	
	function check(){
		if($("#question").val() == ""){
			alert("질문을 입력해주세요");
			$("#question").focus();
			return false;
		}
		
		if($("#answer").val() == ""){
			alert("답변을 입력해주세요");
			$("#answer").focus();
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
				<h3 id = "main_title">FAQ 수정</h3>	
				<form method = "post" action = "faq_update.do" class="form-inline" onsubmit="return check()">
				<div id = "faq_form">
					<c:set var = "faq" value = "${faq }" />
					<input type = "hidden" value = "${faq.getF_num() }" name = "num">
					<input type = "hidden" value = "${page }" name = "page">
					<c:if test="${!empty cate }">
					<input type = "hidden" value = "${cate }" name = "cate">
					</c:if> 
					<table>
						<tr>
							<td>
								<select class="form-control" name = "category">
									<option value = "회원"
									<c:if test = "${faq.getF_category() == '회원' }">selected</c:if>>회원</option>
									<option value = "판매"
									<c:if test = "${faq.getF_category() == '판매' }">selected</c:if>>판매</option>
									<option value = "취소반품교환"
									<c:if test = "${faq.getF_category() == '취소반품교환' }">selected</c:if>>취소&반품&교환</option>
									<option value = "주문결제"
									<c:if test = "${faq.getF_category() == '주문결제' }">selected</c:if>>주문&결제</option>
									<option value = "배송"
									<c:if test = "${faq.getF_category() == '배송' }">selected</c:if>>배송</option>								
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<input type = "text" class="form-control" name = "question" id = "question" value = "${faq.getF_question() }">
							</td>
						</tr>
						<tr>
							<td>
								<textarea rows="25" class="form-control" name = "answer" id = "answer">${faq.getF_answer() }</textarea>
							</td>
						</tr>
						</table>
					</div>
					<div id = "faq_write_btn">
						<input type = "submit" class = "btn_blue" value = "수정">
						<input type = "button" value = "취소" class = "btn_white" onclick = "javascript:history.back()">
					</div>
					</form>	
				</div>	
			</div>
		</div>
		<jsp:include page="../include/bottom.jsp" />
</body>
</html>