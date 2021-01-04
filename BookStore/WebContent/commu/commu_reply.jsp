<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<form method="post" action="<%=request.getContextPath() %>/commu_reply_ok.do">
	<input type="hidden" name="c_num" value="${c_num }">
	<input type="hidden" name="page" value="${page }">
	<input type="hidden" name="c_group" value="${c_group }">
	<input type="hidden" name="c_step" value="${c_step }">
	<input type="hidden" name="c_indent" value="${c_indent }">
	<input type="hidden" name="c_id_nickname" value="${session_mem_nickname }(${session_mem_id })">
	      <div id = "request_book_info_tb">
	      <table>
	      
	         <tr>
	         	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	            <th>글제목</th>
	            <td> <input name="c_title" class="form-control"> </td>
	            <th>비밀번호</th>
	            <td> <input type="password" name="c_pwd" class="form-control"> </td>
	            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	         </tr>
	        </table>
	        </div>
	        <div class = "reqeust_form_tb">
	        <table>
	         <tr>
	            <th>글내용</th>
	            <td>
	               <textarea rows="7" cols="30" name="c_content" class="form-control"></textarea>
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