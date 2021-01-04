<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/mypage/member_edit.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="./js/sign_up.js"></script>
<script>
	$(function(){
		//중복 이메일 체크 ajax
		var mem_email = $('#mem_email').val();
		
		$("#mem_email").keyup(function() {
			$.ajax({
				url: "${pageContext.request.contextPath }/edit_email_check.do",
				dataType: "text",
				data: {
					"mem_email" : $('#mem_email').val()
				},
				success: function(data) {
					$("#email_check").text(data);
				},
				error: function() {
					alert('에러 발생');
				}
			});
		});
	});
	
	function edit_check() {
		if($("#pwd").val() == ""){
			alert("비밀번호를 입력해주세요.");
			$("#pwd").focus();
			return false;
		}
		if($("#mem_name").val() == ""){
			alert("이름을 입력해주세요.");
			$("#mem_name").focus();
			return false;
		}
		if($("#mem_nickname").val() == ""){
			alert("닉네임을 입력해주세요.");
			$("#mem_nickname").focus();
			return false;
		}
		if($("#mem_email").val() == ""){
			alert("이메일을 입력해주세요.");
			$("#mem_email").focus();
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
					<h2>회원정보 수정</h2>
					<hr width = "65%">
				
				<div id = "member_info_up_box">
					<form method = "post" action = "member_edit_ok.do" class="form-inline" onsubmit = "return edit_check()">
						<c:set var = "dto" value = "${edit }"/>
						<table>
							<tr>
								<th>아이디</th>
								<td colspan = "2"><input type = "text" class = "form-control" name = "mem_id" readonly value = "${dto.getMem_id() }"></td>
							</tr>
							<tr>
								<th>비밀번호</th>
								<td colspan = "2"><input type = "password" class = "form-control" name = "mem_pwd" id = "pwd"> </td>
							</tr>
							<tr>
								<th>이름</th>
								<td colspan = "2"><input type = "text" class = "form-control" id = "mem_name" name = "mem_name" value = "${dto.getMem_name() }">	</td>
							</tr>
							<tr>
								<th>나이</th>
								<td colspan = "2"><input type = "text" min = "1" max ="999" class = "form-control" name = "mem_age" value ="${dto.getMem_age() }"></td>
							</tr>
							<tr>
								<th>닉네임</th>
								<td colspan = "2"><input type = "text" class = "form-control" name = "mem_nickname" id = "mem_nickname" value = "${dto.getMem_nickname() }"></td>
							</tr>
							<tr>
								<th>성별</th>
								<td colspan = "2">
									<select class = "form-control" name="mem_gender">
										<option value="M"
										<c:if test="${dto.getMem_gender() == 'M' }">selected</c:if>>남자</option>
										<option value="F"
										<c:if test="${dto.getMem_gender() == 'F' }">selected</c:if>>여자</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>생일</th>
								<td colspan = "2">
									<c:set var = "birth" value = "${dto.getMem_birth() }" />
									<c:set var = "birthArr" value = "${fn:split(birth, '-')}"></c:set>
									<select id = "mem_birth_morth" name="mem_birth_month" class ="form-control" >
										<c:forEach begin="1" end="12" var="i">
											<option value='<fmt:formatNumber value="${i }" pattern="00"/>'
											<c:if test = "${birthArr[0] == i}">selected</c:if>><fmt:formatNumber value="${i }" pattern="00월"/></option>
										</c:forEach>
									</select>
									-
									<c:set var ="birth" value = "${dto.getMem_birth() }"/>
									<c:set var = "birthArr" value = "${fn:split(birth,'-')}"></c:set>
									<select id ="mem_birth_day" name="mem_birth_day" class = "form-control">
										<c:forEach begin="1" end="31" var="i">
											<option value='<fmt:formatNumber value="${i }" pattern="00"/>'
											<c:if test = "${birthArr[1] == i}">selected</c:if>><fmt:formatNumber value="${i }" pattern="00일"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>주소</th>
								<td>
									<c:set var = "addr" value = "${dto.getMem_addr() }" />
									<c:set var = "addrArr" value = "${fn:split(addr, '|')}" />
									<input type="text" class = "form-control" id="sample4_postcode" placeholder="우편번호" name="mem_addr_1" value = "${addrArr[0] }">
								</td>
								<td><button type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" class ="btn btn-info">우편번호 찾기</button></td>
							</tr>
							<tr>
								<th></th>
								<td colspan = "2"><input type="text" class = "form-control" id="sample4_roadAddress" placeholder="도로명주소"><br>
									</td>
							</tr>
							<tr>
								<th></th>
								<td colspan = "2"><input type="text" class = "form-control" id="sample4_jibunAddress" placeholder="지번주소" name="mem_addr_2" value = "${addrArr[1] }"></td>
							</tr>
							<tr>
								<th></th>
								<td colspan = "2"><input type="text" class = "form-control" id="sample4_detailAddress" placeholder="상세주소" name="mem_addr_3" value = "${addrArr[2] }"></td>
							</tr>
							<tr>
								<th></th>
								<td colspan = "2"><input type="text" class = "form-control" id="sample4_extraAddress" placeholder="참고항목" name="mem_addr_4"></td>
							</tr>
							<tr>
								<th>전화번호</th>
								<td colspan = "2"><input type = "text" class = "form-control" name = "mem_number" value = "${dto.getMem_number() }"></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td colspan = "2">
									<input name = "mem_email" id="mem_email" class="form-control" value = "${dto.getMem_email() }">
									<span id="email_check">중복확인</span>
								</td>
							</tr>
						</table>
						<div id = "mem_info_up_btn">
							<input type ="submit" class ="btn_blue" value = "수정">
							<input type = "button" class = "btn_white" onclick="javascript:history.back()" value = "취소">
						</div>
					</form>				
				</div>
				</div>
			</div>
		</div>
	</div>

<jsp:include page="../include/bottom.jsp" />