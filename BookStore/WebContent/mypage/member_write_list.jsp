<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/mypage/member_write_list.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	
	$(function(){
		$("#allCheck").click(function(){
			if($("#allCheck").prop("checked")){
				$("input:checkbox").prop("checked", true);
			}else{
				$("input:checkbox").prop("checked", false);
			}
		});
	});
	
	function delcheck(){
		
		if(!$("input:checkbox").is(":checked")){
			alert("삭제하실 글을 선택해주세요.");
			return false;
		}
		
		if(confirm("삭제하시겠습니까?")){
			return true;
		}else{
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
					<h3 id = "main_title">내가 쓴 글 확인</h3>
					<form method = "post" action = "member_write_del.do" onsubmit="return delcheck()">
						<div id = "cart_del_box">
							<input type = "submit" class = "btn_white" value = "삭제">
						</div>
					<table class = "write_tb">
						<tr id = "write_list_th">
							<th><input type = "checkbox" id = "allCheck"></th><th>번호</th> <th class = "write_tit_td">제목</th> <th>조회수</th>
							<th>작성일</th> <th>닉네임(아이디)</th> <th>추천</th>
						</tr>
						 <c:set var="list" value="${List }" />					
							<c:if test="${!empty list }">
				         <c:forEach items="${list }" var="dto">
							<tr>
								<td><input type = "checkbox" name = "delCheck" value = "${dto.getC_num() }"></td>
								<td>${dto.getC_num() }</td>
								<td class = "write_tit_td">
								 <a href="commu_cont.do?c_num=${dto.getC_num() }&page=${page }" > ${dto.getC_title() } </a>
				               	</td>	
							   	<td>${dto.getC_hit() } </td>
				               	<td>${dto.getC_date().substring(0,10) } </td>
				               	<td>${dto.getC_id_nickname() } </td>
				               	<td>${dto.getC_like() } </td>
									
							</tr>
							</c:forEach>
							</c:if>
							 <c:if test="${empty list }">
						        <tr>
						           <td colspan="6">
						              <h3>검색된 글이 없습니다.</h3>
						           </td>
						        </tr>
						      </c:if>
						</table>
					</form>
					
				<div class = "page_box">
					<c:if test="${page > block }">
			     <a href="member_write_list.do?page=1">◀◀</a>
			     <a href="member_write_list.do?page=${startBlock - 1 }">◀</a>
			   </c:if>
			   
			   <c:forEach begin="${startBlock }" end="${endBlock }" var="i">
			      <c:if test="${i == page }">
			      	<b><a href="member_write_list.do?page=${i }">[${i }]</a></b>
			      </c:if>
			      
			      <c:if test="${i != page }">
			         <a href="member_write_list.do?page=${i }">[${i }]</a>
			      </c:if>
			   </c:forEach>
			   		<c:if test="${endBlock < allPage }">
			     <a href="member_write_list.do?page=${endBlock + 1 }">▶</a>
			     <a href="member_write_list.do?page=${allPage }">▶▶</a>
			   </c:if>
				</div>
				
				</div>
			</div>
		</div>
	</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>