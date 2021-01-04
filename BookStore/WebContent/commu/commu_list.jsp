<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "./css/commu/commu.css">
</head>
<body>
<jsp:include page="../include/top.jsp" />
<div class="container">
		<div class = "cont_box">
			<div id = "commu_box">
			
			<h3>커뮤니티</h3>
				
				<table class="book_tb">
					<tr>
			         	<th>글번호</th> <th>글제목</th> <th>조회수 <th>작성일자</th><th>닉네임(아이디)</th> <th>추천</th>
			      	</tr>
				    <c:set var="list" value="${list }" />
				    <c:if test="${!empty list }">
				    <c:forEach items="${list }" var="dto">
		            	<tr>
		               		<td>${dto.getC_num() } </td>
		               		<td class = "tit_td">
			               		<c:forEach begin="1" end="${dto.getC_indent() }">
			               		<c:set var ="cnt" value ="${cnt+1 }"/>
			               			&nbsp;&nbsp;&nbsp;
			               		</c:forEach> 
			               		<a href="commu_cont.do?c_num=${dto.getC_num() }&page=${page }">${dto.getC_title() } </a>
		               		</td>
		               		<td> ${dto.getC_hit() } </td>
		               		<td> ${dto.getC_date().substring(0,10) } </td>
		               		<td> ${dto.getC_id_nickname() } </td>
		               		<td> ${dto.getC_like() } </td>
		            	</tr>
		         	</c:forEach>
		      		</c:if>
		      		<c:if test="${empty list }">
		        	<tr>
		           		<td colspan="6" align="center">
		              		<h3>검색된 레코드가 없습니다.</h3>
		           		</td>
		        	</tr>
		      		</c:if>
		  		 </table>
	   			<div id = "commu_btn_box">
	   				<c:if test= "${!empty session_mem_id }">
	   					<button class="btn_white" type="button" value="글쓰기" onclick="location.href='commu_write.do'">글쓰기</button>
	   				</c:if>
	   			</div>
				<div class = "page_box">
					<c:if test="${page > block }">
				    	<a href="commu_list.do?page=1">◀◀</a>
				    	<a href="commu_list.do?page=${startBlock - 1 }">◀</a>
				   	</c:if>
				   
				   	<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
				    <c:if test="${i == page }">
				    	<b><a href="commu_list.do?page=${i }">[${i }]</a></b>
				    </c:if>
				      
				    <c:if test="${i != page }">
				    	<a href="commu_list.do?page=${i }">[${i }]</a>
				    </c:if>
				   	</c:forEach>
				   
				   	<c:if test="${endBlock < allPage }">
				    	<a href="commu_list.do?page=${endBlock + 1 }">▶</a>
						<a href="commu_list.do?page=${allPage }">▶▶</a>
				   	</c:if>
				</div>
			</div>
	  	</div>
	  </div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>