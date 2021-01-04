<%@page import="com.book.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="./css/user/sign_up.css?after" type="text/css">
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="./js/sign_up.js"></script>
<script type="text/javascript">
function birthmonth() {
		var month = document.getElementById("mem_birth_month").value;
		var child = document.getElementById("mem_birth_day");
		var parent = document.getElementById("mem_birth_day_p");
		var td_p = document.getElementById("mem_birth");
		if(month == 4 || month == 6 || month ==9 || month ==11){
			parent.parentNode.removeChild(parent);
			var sel = document.createElement("select");
			sel.name = "mem_birth_day";
			sel.id = "mem_birth_day_p";
			sel.classList.add('form-control');
 			for(var i = 1; i <=30; i++){
 				var day = document.createElement("option");
 				if(i<=9){
	 				i= "0"+i;
					day.value = i;
					day.text = i+"일";
 				}else{
					day.value = i;
					day.text = i+"일";
 				}
				sel.appendChild(day);
			}
			td_p.appendChild(sel);
		}else if(month == 2){
			parent.parentNode.removeChild(parent);
			var sel = document.createElement("select");
			sel.name = "mem_birth_day";
			sel.id = "mem_birth_day_p";
			sel.classList.add('form-control');
			for(var i = 1; i <=28; i++){
				var day = document.createElement("option");
				if(i<=9){
		 			i= "0"+i;
					day.value = i;
					day.text = i+"일";
				}else{
					day.value = i;
					day.text = i+"일";
 				}
				sel.appendChild(day);
				
			}
			
			td_p.appendChild(sel);
 		}else {
			parent.parentNode.removeChild(parent);
			var sel = document.createElement("select");
			sel.name = "mem_birth_day";
			sel.id = "mem_birth_day_p";
			sel.classList.add('form-control');
			for(var i = 1; i <=31; i++){
				var day = document.createElement("option");
				if(i<=9){
		 			i= "0"+i;
					day.value = i;
					day.text = i+"일";
				}else{
					day.value = i;
					day.text = i+"일";
 				}
				sel.appendChild(day);
				
			}
			
			td_p.appendChild(sel);
 		}
}
</script>
<script>
//아이디 중복체크 ajax 메서드.
$(function(){
	$.ajaxSetup({
		ContentType:'application/x-www-form-urlencoded;charset=UTF-8',
		type: "post"});
// id 중복 여부 체크하기
var mem_id = $('#mem_id').val();
$("#mem_id").keyup(function() {
	$.ajax({
		url: "<%=request.getContextPath()%>/member_id_check.do",
		dataType: "text",
		data: {mem_id : $('#mem_id').val()},
		success: function(data) {
			$("#id_check").remove();
			var param = '<span class="input-group-addon" id = "id_check">'+data+'</span>';
			$("#idDiv").append(param);
		},
		error: function() {
			alert('에러 발생');
		}
	});
});
//중복 이메일 체크 ajax
var mem_email = $('#mem_email').val();
$("#mem_email").keyup(function() {
	$.ajax({
		url: "<%=request.getContextPath()%>/member_email_check.do",
		dataType: "text",
		data: {mem_email : $('#mem_email').val()},
		success: function(data) {
			$("#email_check").remove();
			var param = '<span class="input-group-addon" id = "email_check">'+data+'</span>';
			$("#email_td").append(param);
		},
		error: function() {
			alert('에러 발생');
		}
	});
});

});

</script>
<body>
<%-- 회원가입 폼 페이지 --%>
<jsp:include page="../include/top.jsp" />
	<div id="sign_up_wrap">
		<div class="container">
			<div id="sign_up_box">
				<br/>
				<h2>회원가입</h2>
				<hr width="70%">
				<form method="post" action="<%=request.getContextPath()%>/member_sign_up_ok.do" enctype="multipart/form-data" class="form-horizontal">
					<table>
						<tr>
							<td>
								<label for="mem_id" class="control-label">아이디</label>
							</td>
							<td>
								<div  id="idDiv" class="input-group">
									<input name="mem_id" id="mem_id" class="form-control" aria-describedby="id_check">
									<span class="input-group-addon" id="id_check">중복여부확인</span>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_pwd" class="control-label">비밀번호</label>
							</td>
						<td>
							<input type="password" id="mem_pwd" name="mem_pwd" class="form-control input_length">
						</td>
						</tr>
						<tr>
							<td>
								<label for="mem_name" class="control-label">이름</label>
							</td>
							<td>
								<input name = "mem_name" id="mem_name" class="form-control input_length">
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_age" class="control-label">나이</label>
							</td>
							<td>
								<input type="number" name = "mem_age" id="mem_age" class="form-control input_length">
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_nickname" class="control-label">별명</label>
							</td>
							<td>
								<input name = "mem_nickname" id="mem_nickname" class="form-control input_length">
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_gender" class="control-label">성별</label>
							</td>
							<td>
								<select name="mem_gender" id="mem_gender" class="form-control">
									<option value="M">남자</option>
									<option value="F">여자</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_birth" class="control-label">생일</label>
							</td>
							<td id="mem_birth">
								<select id="mem_birth_month" name="mem_birth_month" onchange="birthmonth()" class="form-control">
									<c:forEach begin="1" end="12" var="i">
										<option value='<fmt:formatNumber value="${i }" pattern="00"></fmt:formatNumber>'>
										<fmt:formatNumber value="${i }" pattern="00월"></fmt:formatNumber>
										</option>
									</c:forEach>
								</select>
								-
								<select id = "mem_birth_day_p" name="mem_birth_day"  class="form-control">
									<c:forEach begin="1" end="31" var="i">
										<option id="mem_birth_day" value='<fmt:formatNumber value="${i }" pattern="00"></fmt:formatNumber>'>
										<fmt:formatNumber value="${i }" pattern="00일"></fmt:formatNumber>
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<%-- 주소 수정 api 우편번호 찾기 클릭 시 다음 주소찾기가 팝업으로 뜸 해당 주소 저장함. --%>
						<tr>
							<td>
								<label for="mem_addr" class="control-label">주소</label>
							</td>
							<td>
								<div class="input-group">
									<input type="text" id="sample4_postcode" placeholder="우편번호" name="mem_addr_1"  class="form-control">
									<span class="input-group-btn">
									<button type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" class="btn btn-info" >우편 찾기</button>
								</span>
								</div>
								<input type="text" id="sample4_roadAddress" placeholder="도로명주소" name="mem_addr_2" class="form-control">
								<input type="text" id="sample4_jibunAddress" placeholder="지번주소" name="mem_addr_2" class="form-control">
								<span id="guide" style="color:#999;display:none"></span>
								<input type="text" id="sample4_detailAddress" placeholder="상세주소" name="mem_addr_3" class="form-control">
								<input type="text" id="sample4_extraAddress" placeholder="참고항목" name="mem_addr_4" class="form-control">
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_number" class="control-label">전화번호</label>
							</td>
							<td>
								<input name = "mem_number" id="mem_number" class="form-control input_length">
							</td>
						</tr>
						<tr>
							<td>
								<label for="mem_email" class="control-label">이메일</label>
							</td>
							<td>
								<div id="email_td" class="input-group">
									<input name = "mem_email" id="mem_email" class="form-control" aria-describedby="email_check">
									<span class="input-group-addon" id="email_check">중복여부확인</span>
								</div>
							</td>
						</tr>
					</table>
					<div id="btn_box">
						<button type="submit" class="btn btn-lg btn_white">회원가입</button>
					</div>
				</form>
			</div>			
		</div>
	</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>