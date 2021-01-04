<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/help_qna/all_qna_form.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	
	function aq_non_check(){
		if($("#category").val == "카테고리"){
			alert("카테고리를 선택해주세요");
			$("#category").focus();
			return false;
		}
		if($("#name").val == ""){
			alert("이름을 입력해주세요");
			$("#name").focus();
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
				<h3 id = "main_title">Q&A 등록</h3>
				<form method = "post" action = "all_qna_insert.do" class="form-inline" onsubmit="return aq_non_check()">
				<div id = "all_qna_form_box">
					<hr>
					<table>
						<tr>
							<td>
								<select class="form-control" name = "category" id = "category">
									<option value = "카테고리">---- 카테고리 ----</option>
									<option value = "회원">회원</option>
									<option value = "판매">판매</option>
									<option value = "취소반품교환">취소&반품&교환</option>
									<option value = "주문결제">주문&결제</option>
									<option value = "배송">배송</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><span>이름</span>  <input type = "text" class="form-control" name = "name" id = "name" size = "20"></td>
						</tr>
						<tr>
							<td><input type = "text" class="form-control" name = "title" id = "title" placeholder="제목을 입력해주세요"></td>
						</tr>
						<tr>
							<td>
								<textarea rows="30" class="form-control" name = "cont" id = "cont" placeholder="문의할 내용을 입력해주세요"></textarea>
							</td>
						</tr>
						<tr>
							<td><span>비밀글 </span> <input type = "checkbox" class="form-control" name = "check" id = "check" value = "true"></td>
						</tr>
						<tr>
							<td><span>비밀번호</span> <input type = "password" class="form-control" name = "pwd" id = "pwd"></td>
						</tr>
					</table>
				</div>
				<hr>
				<div id = "all_qna_form_btn">
					<input type = "submit" class = "btn_blue" value = "등록">
					<input type = "button" class = "btn_white" value = "취소" onclick = "location = 'javascript:history.back()'">
				</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../include/bottom.jsp" />
</body>
</html>