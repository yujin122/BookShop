<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/main/main.css" type="text/css">
<script>
window.onload = function() {
	var pwdOk = "<%=session.getAttribute("pwdok")%>";
	if(pwdOk == 'pwdok'){
		alert('새로운 비밀번호를 입력된 이메일로 보냈습니다. 비밀번호를 수정해주세요.');
		<% session.setAttribute("pwdok", null); %>
	}
}
</script>
<script type="text/javascript">
setInterval(function () {
	var image = '';
	$.ajaxSetup({
		ContentType:'application/x-www-form-urlencoded;charset=UTF-8',
		type: "post"});
	$.ajax({
		url: "<%=request.getContextPath() %>/main_hit_image.do",
		dataType: "json",
		data: null,
		success: function(data) {
			$("#hit_image_div").empty();
			$.each(data, function(index, arrjson) {
				if(arrjson.b_image != null){
					image = "<a href = '${pageContext.request.contextPath }/book_cont.do?s_num="+arrjson.s_num+"'><img src='"+arrjson.b_image+"' alt='"+arrjson.b_name+"' class='main_book_img'></a>";
				}else if(arrjson.s_image != null){
					image = "<a href = '${pageContext.request.contextPath }/book_cont.do?s_num="+arrjson.s_num+"'><img src="+"'<%=request.getContextPath() %>/upload/book"+arrjson.s_image+"' alt='"+arrjson.b_name+"' class='main_book_img'></a>";
				}else{
					image = "<a href = '${pageContext.request.contextPath }/book_cont.do?s_num="+arrjson.s_num+"'><img src="+"'<%=request.getContextPath() %>/img/nophoto.png' alt='nophoto' class='main_book_img'></a>";
				}
				$("#hit_image_div").append(image);
				if((index+1)%3 == 0){
					$("#hit_image_div").append("<div class = 'mini_list'><br/></div>");
				}
				if((index+1)%6 == 0){
					$("#hit_image_div").append("<br/>");
				}
			});
		},
		error: function() {
		}
	}).done(function() {
		$.ajax({
			url: "<%=request.getContextPath() %>/main_num_image.do",
			dataType: "json",
			data: null,
			success: function(data) {
				$("#new_image_div").empty();
				$.each(data, function(index, arrjson) {
					if(arrjson.b_image != null){
						image = "<a href = '${pageContext.request.contextPath }/book_cont.do?s_num="+arrjson.s_num+"'><img src='"+arrjson.b_image+"' alt='"+arrjson.b_name+"'class='main_book_img'></a></div>";
					}else if(arrjson.s_image != null){
						image = "<a href = '${pageContext.request.contextPath }/book_cont.do?s_num="+arrjson.s_num+"'><img src="+"'${pageContext.request.contextPath }/upload/book"+arrjson.s_image+"' alt='"+arrjson.b_name+"' class='main_book_img'></a>";
					}else{
						image = "<a href = '${pageContext.request.contextPath }/book_cont.do?s_num="+arrjson.s_num+"'><img src="+"'${pageContext.request.contextPath }/img/nophoto.png' alt='nophoto' class='main_book_img'></a>";
					}
					$("#new_image_div").append(image);
					if((index+1)%3 == 0){
						$("#new_image_div").append("<div class = 'mini_list'><br/></div>");
					}
					if((index+1)%6 == 0){
						$("#new_image_div").append("<br/>");
					}
				});
			},
			error: function() {
			}
		});
	});
}, 50000);
</script>
</head>
<body>
	<%-- 메인 페이지 상단과 하단은 include로 얻어오고 있고 가운데 (베스트 중고도서, 최신 중고도서) 삽입 예정 --%>
	<jsp:include page="../include/top.jsp" />
	<br/>
<div id="b_image_main">
	<div class="container">
		<div id = "best_book">
			<c:set var="list_Hit" value="${list_Hit }" />
			<c:set var="list_Num" value="${list_Num }" />
			<h3>베스트 중고도서</h3>
			<hr>
			<div class = "book_list" id = "hit_image_div">
				<c:forEach items="${list_Hit }" var="dto" varStatus="status">
					<a href = "${pageContext.request.contextPath }/book_cont.do?s_num=${dto.getS_num() }">
					<c:if test="${!empty dto.getB_image() }">
						<img src="${dto.getB_image() }" alt="${dto.getB_name() }" class = "main_book_img">
					</c:if>
					<c:if test="${empty dto.getB_image() }">
					<c:if test="${!empty dto.getS_image() }">
						<img src ="${pageContext.request.contextPath }/upload/book${dto.getS_image() }" alt="${dto.getS_num() }" class = "main_book_img">
					</c:if>
					<c:if test="${empty dto.getS_image() }">
						<img src ="${pageContext.request.contextPath }/img/nophoto.png" alt="${dto.getB_name() }" class = "main_book_img">
					</c:if>
					</c:if>
					</a>
					<c:if test="${status.count%3 == 0 }">
						<div class = "mini_list">
							<br>
						</div>
					</c:if>
					<c:if test="${status.count%6 == 0 }">
						<br>
					</c:if>
				</c:forEach>
			</div>
		</div>	
		<div id ="new_book">
			<h3>최신 중고도서</h3>
				<hr>
				<div class = "book_list" id = "new_image_div">
					
					<c:forEach items="${list_Num }" var="dto" varStatus="status">
						<a href = "${pageContext.request.contextPath }/book_cont.do?s_num=${dto.getS_num() }">
						<c:if test="${!empty dto.getB_image() }">
							<img src="${dto.getB_image() }" alt="${dto.getB_name() }" class = "main_book_img">
						</c:if>
						<c:if test="${empty dto.getB_image() }">
						<c:if test="${!empty dto.getS_image() }">
							<img src ="${pageContext.request.contextPath }/upload/book${dto.getS_image() }" alt="${dto.getS_num() }" class = "main_book_img">
						</c:if>
						<c:if test="${empty dto.getS_image() }">
							<img src ="${pageContext.request.contextPath }/img/nophoto.png" alt="${dto.getB_name() }" class = "main_book_img">
						</c:if>
						</c:if>
						</a>
						<c:if test="${status.count%3 == 0 }">
							<div class= "mini_list">
								<br>
							</div>
						</c:if>
						<c:if test="${status.count%6 == 0 }">
							<br>
						</c:if>
					</c:forEach>
				</div>
			</div>	
		</div>
	</div>

	<jsp:include page="../include/bottom.jsp" />
</body>
</html>
