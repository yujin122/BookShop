<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "css/mypage/member_delete.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	
	function dele_check(){
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
		<div id="main">
			<div class = "cont_box">
				<jsp:include page="../include/mypage_side.jsp" />

				<div class = "main_box">
					<h3 id = "main_title">회원 탈퇴</h3>
					<form method = "post" action = "memder_delete.do" class="form-inline" onsubmit="return dele_check()">
					<div id = "mem_del_box">
						<table>
							<tr>
								<td colspan = "2">
								<p>정보를 안전하게 보호하기 위해</p>
								<p>비밀번호를 다시 한 번 확인합니다.</p>
								</td>
							</tr>	
							<tr>
								<th>아이디</th>
								<td>${session_mem_id }</td>
							</tr>
							<tr>
								<th>비밀번호</th>
								<td><input type = "password" class="form-control" name = "pwd" id = "pwd"></td>
							</tr>
						</table>
						<div id = "mem_del_btn">
							<input type = "submit" class = "btn_blue" value = "회원탈퇴">
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../include/bottom.jsp" />