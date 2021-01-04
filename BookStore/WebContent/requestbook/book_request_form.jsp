<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "css/requestbook/requestbook.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	function check(){
		if($("#book_title").val() == ""){
			alert("도서명을 입력해주세요");
			$("#book_title").focus();
			return false;
		}
		if($("#book_price").val() == ""){
			alert("희망가격을 입력해주세요");
			$("#book_price").focus();
			return false;
		}
		if($("#book_contact").val() == ""){
			alert("연락처를 입력해주세요");
			$("#book_contact").focus();
			return false;
		}
		if($("#request_title").val() == ""){
			alert("제목을 입력해주세요");
			$("#request_title").focus();
			return false;
		}
		if($("#request_cont").val() == ""){
			alert("내용을 입력해주세요");
			$("#request_cont").focus();
			return false;
		}
	}
</script>
</head>
<body>
	<jsp:include page="../include/top.jsp" />
	
	<div class="container">

			<div class = "cont_box">
	
				<jsp:include page="../include/book_request_side.jsp" />
				
				<div class = "main_box">
				<h3>도서 요청</h3>
				
						<form method = "post" action = "book_request.do" onsubmit="return check()">
							<div id = "request_book_info_tb">
								<table>
									<tr>
										<th>도서명 <span class = "star">*</span></th>
										<td colspan = "3"><input type = "text" class="form-control" name = "book_title" id = "book_title" size = "30">
									</tr>
									<tr>
										<th>출판사</th>
										<td><input type = "text" class="form-control" name = "book_pub_company" size = "12"></td>
										<th>저자</th>
										<td><input type = "text" class="form-control" name = "book_author" size = "12"></td>
									</tr>
									<tr>
										<th>희망가격<span class = "star">*</span></th>
										<td><input type = "text" class="form-control" name = "book_price" id = "book_price" size = "5"> </td>
										<th>연락처<span class = "star">*</span></th>
										<td><input type = "text" class="form-control" name = "book_contact" id = "book_contact" size = "20" placeholder="이메일 또는 핸드폰 번호"></td>
									</tr>
								</table>
							</div>
							<div class = "reqeust_form_tb">
								<table>
									<tr>
										<th>제목 <span class = "star">*</span></th>
										<td colspan = "3"><input type = "text" class="form-control" name = "request_title" id = "request_title" size = "30"></td>
									</tr>
									<tr>
										<th >내용 <span class = "star">*</span></th>
										<td colspan = "3">
											<textarea rows="20" cols="60" class="form-control" name = "request_cont" id = "request_cont" placeholder="책 요청 내용을 입력해주세요"></textarea>
										</td>
									</tr>
								</table>
							</div>
							<div class = "request_form_btn_box">
								<input type = "submit" class="btn_blue" value = "요청하기">
							</div>	
					</form>
					</div>
				</div>
			</div>

<jsp:include page="../include/bottom.jsp" />
</body>
</html>