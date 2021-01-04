<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "./css/requestbook/requestbook.css">
<link rel = "stylesheet" href = "./css/commu/commu_write.css">
</head>
<body>
<jsp:include page="../include/top.jsp" />
<div class="container">
	<div class="cont_box">
	<form method="post" action="<%=request.getContextPath() %>/commu_edit_ok.do">
	<c:set value="${dto }" var="dto" />
	<input type="hidden" name="c_id_nickname" value="${session_mem_nickname }(${session_mem_id })">
	<input type="hidden" name="c_num" value="${c_num }">
	<input type="hidden" name="page" value="${page }">
		<div id = "request_book_info_tb">
	      <table>
	         <tr>
	         <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	            <th>글제목</th>
	            <td> <input name="c_title" class="form-control" value="${dto.getC_title() }" > </td>
	         
	            <th>비밀번호</th>
	            <td> <input type="password" class="form-control" name="c_pwd"> </td>
	            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	         </tr>
	        </table>
	     </div>
	     <div class = "reqeust_form_tb">
	        <table>
	         <tr>
	            <th>글내용</th>
	            <td>
	               <textarea rows="7" cols="30" class="form-control" name="c_content">${dto.getC_content() }</textarea>
	            </td>
	         </tr>
	         <tr>
	            <td colspan="2" id="commu_write_submit_td">
	               <input type="submit" class="btn btn_white" value="글쓰기">

	            </td>
	         </tr>
	      </table>
	      </div>
	   </form>
	</div>
</div>
<jsp:include page="../include/bottom.jsp" />
</body>
</html>