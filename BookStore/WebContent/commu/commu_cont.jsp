<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "./css/requestbook/requestbook.css">
<script
  src="https://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous"></script>
<%-- 댓글 본문 클릭 시 대댓글 입력 창이 열리는 스크립트 --%>
<script type="text/javascript">
	$(function() {
		$('.ment_write').hide();
		$('.reply_content').click(function () {
			let no = $(this).attr('id');
			$('#ment_num').val(no);
			$('.ment_write').hide();
			$('.ment_reply_frm').append($('<input/>', {type: 'hidden', name: 'ment_num', value: no }));
			$('#ment_write_'+no).toggle();
		});
	});

</script>

</head>
<body>
<jsp:include page="../include/top.jsp" />
<link rel = "stylesheet" href = "./css/commu/commu_cont.css?after">
<div class="container">
	<div class="cont_box">
		<div class="main_box">
			<div id="request_cont_box">
				<div id="request_tb_box">
					<table id="request_tb">
				      <c:set var="dto" value="${dto }" />
				      <c:if test="${!empty dto }">
				         <tr>
				            <th>작성자</th>
				            <td> ${dto.getC_id_nickname() } </td>
				         </tr>
				         <tr>
				            <th>글제목</th>
				            <td> ${dto.getC_title() } </td>
				         </tr>
				         
				         <tr>
				            <th>조회수</th>
				            <td> ${dto.getC_hit() } </td>
				         </tr>
				         <tr>
				            <th>추천수</th>
				            <td> ${dto.getC_like() } </td>
				         </tr>
				         
				         <tr>
				            <th>작성일자</th>
				            <td> ${dto.getC_date() } </td>
				         </tr>
				         </c:if>
				         <c:if test="${empty dto }">
				         <tr>
				            <td colspan="2" align="center">
				               <h3>검색된 레코드가 없습니다.</h3>
				         	</td>
				         </tr>
				      </c:if>
			        </table>
		    	</div>
            <textarea id = "request_cont" rows="7" cols="30" readonly>${dto.getC_content() }</textarea>
		</div>
<%--댓글 테이블 --%>
<br/><br/><br/>


	<table class="book_tb">
		<c:set value="${list_ment }" var="list_ment" />
		 <c:if test="${!empty list_ment }">
	         <c:forEach items="${list_ment }" var="dto">
	         	<input type="hidden" value="${dto.getMent_pwd() }" id="ment_delete_check_${dto.getMent_num() }">
	            <tr>
            		<td> 
		            	<c:forEach begin="1" end="${dto.getMent_indent() }">
		                       	&nbsp;&nbsp;&nbsp;
		                </c:forEach> 
		               ${dto.getMent_id_nickname() } </td>
	               <td id="${dto.getMent_num() }" class="reply_content" width="250"> ${dto.getMent_cont() }</td>
	               <td> ${dto.getMent_date().substring(0,10) } </td>
	               <td>
	               	<form action="<%=request.getContextPath() %>/ment_delete.do" method="post">
		               	<input type="password" name = "delete_pwd" placeholder="PWD" width="30px" id="ment_pwd">
		               	<input type="hidden" value="${page }" name="delete_page">
	            		<input type="hidden" value="${c_num }" name="delete_c_num">
	            		<input type="hidden" value="${dto.getMent_num() }" name="delete_ment_num">
		               	<input type="submit"  class="btn_white" value="삭제">
	               	</form>
	               </td>
	               
	            </tr>
	            <%-- 대댓글 입력창 --%>
	            <tr id="ment_write_${dto.getMent_num() }" class="ment_write">
	            	<td colspan="4" class="ment_reply_td">
	            		<form action="<%=request.getContextPath() %>/ment_write_reply.do" method="post" class="ment_reply_frm">
		            		<input name="ment_id_nickname_${dto.getMent_num() }" <c:if test= "${!empty session_mem_id }" >value="${session_mem_nickname }(${session_mem_id })" </c:if>>
		            		<textarea class="ment_reply_cont" rows="1" cols="120" name="ment_cont_${dto.getMent_num() }"></textarea>
		            		<input type="password" name="ment_pwd_${dto.getMent_num() }" placeholder="비밀번호" class="ment_reply_pwd">
		            		<input type="hidden" value="${dto.getMent_group() }" name = "ment_group_${dto.getMent_num() }">
		            		<input type="hidden" value="${dto.getMent_step() }" name="ment_step_${dto.getMent_num() }">
		            		<input type="hidden" value="${dto.getMent_indent() }" name="ment_indent_${dto.getMent_num() }">
		            		<input type="hidden" value="${page }" name="page">
		            		<input type="hidden" value="${c_num }" name="c_num">
		            		<input class="btn_white" type="submit" value="입력">
		            		</form>
	            	</td>
	            </tr>
	         </c:forEach>
	      </c:if>
	      <%-- 댓글이 비어있을때. --%>
	       <c:if test="${empty list_ment }">
	       <h2>댓글이 없습니다.</h2>
	      </c:if>
	      
	</table>
	<input type="hidden" name="ment_num" id="ment_num">
					
<%-- 댓글 입력창 --%>
<form action="ment_write.do" method="post">
<table class="book_tb"  id="ment_tb">
	<tr>
       	<td id="ment_wirte_id_td"><input id="ment_write_id" name="ment_id_nickname" placeholder="닉네임" <c:if test= "${!empty session_mem_id }" >value="${session_mem_nickname }(${session_mem_id })" </c:if>>
    	<input type="password" name="ment_pwd" placeholder="비밀번호" id="ment_write_pwd"></td>
    	<td><textarea rows="1" cols="30" name="ment_cont"></textarea></td>
    	<td>
    		<input type="hidden" value="${page }" name="page">
    		<input type="hidden" value="${c_num }" name="c_num">
    		<input class="btn_white" type="submit" value="입력">
    	</td>
    </tr>
</table>
</form>
			<div id = "request_btn_box">
		      	<button class="btn_white" type="button" value="글추천"	onclick="location.href='commu_like.do?c_num=${dto.getC_num() }&page=${page }'">추천</button>
               	<button class="btn_white" type="button" value="글수정"	onclick="location.href='commu_edit.do?c_num=${dto.getC_num() }&page=${page }'">수정</button>
        	    <button class="btn_white" type="button" value="글삭제"	onclick="location.href='commu_delete.do?c_num=${dto.getC_num() }'">삭제</button>
            	<button class="btn_white" type="button" value="글답변"	onclick="location.href='commu_reply.do?c_num=${dto.getC_num() }&page=${page }&c_group=${dto.getC_group() }&c_step=${dto.getC_step() }&c_indent=${dto.getC_indent() }'">답글</button>
	            <button class="btn_white" type="button" value="전체목록"	onclick="location.href='commu_list.do'">목록</button>
			</div>
<br/><br/><br/>

<%--댓글 아래 전체 게시물 보여주는 테이블 --%>
		<table class="book_tb">
	      <tr id="list_tr">
	         <th>글번호</th> <th>글제목</th> <th>조회수 <th>작성일자</th><th>닉네임(아이디)</th> <th>추천</th>
	      </tr>
	      <c:set var="list" value="${list }" />
	      <c:if test="${!empty list }">
	         <c:forEach items="${list }" var="dto">
	            <tr>
	            	<td>
	            	<c:forEach begin="1" end="${dto.getC_indent() }">
	                       &nbsp;&nbsp;
	                </c:forEach>
	                
	               ${dto.getC_num() } </td>
	               <td> <a href="commu_cont.do?c_num=${dto.getC_num() }&page=${page }">${dto.getC_title() } </a>
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
	      <tr>
	         <td colspan="6" align="right">
	            <input type="button"  class="btn_white" value="글쓰기" onclick="location.href='commu_write.do'" <c:if test= "${empty session_mem_id }">disabled</c:if> >
	         </td>
	      </tr>
	   </table>
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