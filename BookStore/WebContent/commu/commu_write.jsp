<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel = "stylesheet" href = "./css/requestbook/requestbook.css">
<link rel = "stylesheet" href = "./css/commu/commu_write.css">
<body>
<jsp:include page="../include/top.jsp" />
<div class="container">
	<div class="cont_box">
			<form method="post" action="<%=request.getContextPath() %>/commu_write_ok.do">
			<input type="hidden" name="c_id_nickname" value="${session_mem_nickname }(${session_mem_id })">
				<div id = "request_book_info_tb">
					<table>
				         <tr>
				         	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				            <th>글제목</th>
				            <td> <input name="c_title" class="form-control" size="20"> </td>
				            <th>비밀번호</th>
				            <td> <input type="password" name="c_pwd" class="form-control input-sm" size="20"> </td>
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