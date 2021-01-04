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
	
	$(function(){
		if($("#check").is(":checked") == false){
			$("#pwd").attr("disabled", true);
		}
		
		$("#check").click(function(){
			if($("#check").is(":checked") == false){
				$("#pwd").val("");
				$("#pwd").attr("disabled", true);
			}else if($("#check").is(":checked") == true){
				$("#pwd").attr("disabled", false);
			}
		});
	});	

	function update_check(){
		if($("#category").val == "카테고리"){
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
		if($("#check").is(":checked") == true){
			if($("#pwd").val() == ""){
				alert("비밀번호를 입력해주세요");
				$("#pwd").focus();
				return false;
			}
		}
	}
	
</script>
<body>

	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class = "cont_box">
		<jsp:include page="../include/help_side.jsp" />
			
			<div class = "main_box">
				<h3 id = "main_title">Q&A 수정</h3>
				<c:set var="qna" value="${dto }" />
				<form method="post" action="all_qna_update.do" class="form-inline" onsubmit="return update_check()">
				<input type="hidden" name="num" value="${qna.getAq_num() }">
				<input type="hidden" name="page" value="${page }">
				<c:if test="${!empty cate}">
					<input type="hidden" name="cate" value="${cate }">
				</c:if>
				<c:if test="${!empty search_label }">
					<input type="hidden" name="search_cate" value="${search_cate }">
					<input type="hidden" name="search_label" value="${search_label }">
					<input type="hidden" name="search_txt" value="${search_txt }">
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
							<td><input type="text" class="form-control" name="title" id="title" placeholder="제목을 입력해주세요" value="${qna.getAq_title() }"></td>
						</tr>
						<tr>
							<td><textarea rows="30" class="form-control" name="cont" id="cont" placeholder="문의할 내용을 입력해주세요">${qna.getAq_content() }</textarea>
							</td>
						</tr>
						<tr>
							<td><span>비밀글</span> <input type="checkbox" class="form-control" name="check" id="check" value="true" <c:if test = "${qna.getAq_lock() == '1' }">checked</c:if>></td>
						</tr>
						<tr>
							<td>
							<span>비밀번호</span> <input type="password" class="form-control" name="pwd" id="pwd" value = "${qna.getAq_pwd() }">
							</td>
						</tr>
					</table>
					<hr>
						<div id = "all_qna_form_btn">
							<input type="submit" class = "btn_blue" value="수정">
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