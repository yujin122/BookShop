<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<link rel="stylesheet" href="css/help_qna/faq_form.css">
<script type="text/javascript">
	
	function faq_check(){
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
				<h3 id = "main_title">FAQ 등록</h3>
				<form method = "post" action = "faq_insert.do" class="form-inline" onsubmit="return faq_check()">
					<div id = "faq_form">
						<table>
							<tr>
								<td>
									<select class="form-control" name = "category">
										<option value = "회원">회원</option>
										<option value = "판매">판매</option>
										<option value = "취소반품교환">취소&반품&교환</option>
										<option value = "주문결제">주문&결제</option>
										<option value = "배송">배송</option>								
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<input type = "text" class="form-control" name = "question" id = "question" placeholder="질문을 입력해주세요">
								</td>
							</tr>
							<tr>
								<td>
									<textarea rows="25" class="form-control" name = "answer" id = "answer" placeholder="답변을 입력해주세요"></textarea>
								</td>
							</tr>
							</table>
						</div>
						<div id = "faq_write_btn">
							<input type = "submit" class = "btn_blue" value = "등록">
							<input type = "button" class = "btn_white" value = "취소" onclick = "javascript:history.back()">
						</div>
						</form>
					</div>	
				</div>
			</div>
	<jsp:include page="../include/bottom.jsp" />
</body>
</html>