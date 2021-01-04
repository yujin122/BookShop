<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "../css/book_sale/book_qna_form.css">
<script type="text/javascript" src="../js/jquery-3.5.1.js"></script>
<link rel = "stylesheet" href = "../css/common.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">

	function insert_QnA(){
		
		if($("#qna_txt").val() == ""){
			alert("내용을 입력해주세요.");
			$("#qna_txt").focus();
			return;
		}else{
			var txt = $("#qna_txt").val();
			var s_num = opener.document.getElementById("s_num").value;
			var page = "1";
			
			$.ajax({
				url : "${pageContext.request.contextPath }/qna_insert.do",
				type : "post",
				dataType : "text",
				data : {
					"qna_txt" : txt,
					"s_num" : s_num
				},
				success : function(data){
					if(data > 0){
						alert("문의글이 등록되었습니다.");
						
						opener.location.href='javascript:insertQnA('+page+')';
						window.close();
					}else {
						alert("문의글 등록에 실패했습니다.");
					}
				},
				error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});	
		}
	}
	
</script>
</head>
<body>

	<div class = "book_qna_box">
		<h3>상품 문의 폼</h3>
		<hr>
		<div class="form-group" id = "qna_table">
		<table>
			<tr>
				<td><input type = "text" class="form-control" value = "문의합니다." size = "50" readonly></td>
			</tr>
			<tr>
				<td><textarea class="form-control" rows="15" cols="50" placeholder="상품문의 내용을 입력해주세요." name = "qna_txt" id = "qna_txt"></textarea></td>
			<tr>
			<tr>
				<td colspan = "2" align = "center"> 
					<input type = "button" class = "btn_blue" value = "제출" onclick = "insert_QnA()">
				</td>
			</tr>
		</table>
		</div>
	</div>
</body>
</html>