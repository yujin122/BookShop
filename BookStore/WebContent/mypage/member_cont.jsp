<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/mypage/member_edit.css" type="text/css">
</head>
<body>
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div id="main">
			<div class = "cont_box">
				<jsp:include page="../include/mypage_side.jsp" />

				<div class = "main_box">
					
					<h3>회원 상세정보</h3>
					
					<div id = "mem_info_box">
						<table>
							<c:set var = "dto" value ="${cont }"/>
							<c:if test ="${!empty dto }">
							<tr>
								<th>아이디</th>
								<td>${dto.getMem_id() }</td>
							</tr>
							<tr>
								<th>비밀번호</th>
								<td>${dto.getMem_pwd() }</td>
							</tr>
							<tr>
								<th>나이</th>
								<td>${dto.getMem_age() }</td>
							</tr>
							<tr>
								<th>닉네임</th>
								<td>${dto.getMem_nickname() }</td>
							</tr>
							<tr>
								<th>성별</th>
								<td>${dto.getMem_gender()  }</td>
							</tr>
							<tr>
								<th>생일</th>
								<td>${dto.getMem_birth() }</td>
							</tr>
							<tr>
								<th>주소</th>
								<td>${dto.getMem_addr() }</td>
							</tr>
							<tr>
								<th>전화번호</th>
								<td>${dto.getMem_number() }</td>
							</tr>
							<tr>
								<th>이메일</th>
								<td>${dto.getMem_email() }</td>
							</tr>
							</c:if>
						</table>
					</div>
					<div id = "mem_info_btn">
						<button onclick = "location.href = 'member_edit.do'" class ="btn_blue">수정</button>
					</div>
					
				</div>
			</div>
		</div>
	</div>
<jsp:include page="../include/bottom.jsp" />