<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/help_qna/faq.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	$(function(){
		$(".answer_tr").hide();
		
		$(".question_td").click(function(){
			if($(this).parent().next().css("display") == "none"){
				$(".answer_tr").hide();
				$(this).parent().next().show();
			}else {
				$(this).parent().next().hide();
					
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class = "cont_box">
			<jsp:include page="../include/help_side.jsp" />
			
			<div class = "main_box">	
			<h3 id = "main_title">FAQ 게시판</h3>	
			<jsp:include page="../include/faq_middle.jsp" />
				<div id = "faq_tb_box">
					<table>
						<c:set var = "list" value = "${list }" />
						<c:if test="${!empty list }">
						<c:forEach items="${list }" var = "faq">
						<tr class = "q_tr">
							<td><span>Q.</span></td>
							<td class = "cate_td">${faq.getF_category() }</td>
							<td class = "question_td">${faq.getF_question() }</td>
							<td>
								<c:if test="${session_mem_id == 'admin' }">
								<input type = "button" class = "btn_white" onclick = "location = '${pageContext.request.contextPath }/faq_update_from.do?num=${faq.getF_num() }&page=${page }'" value = "수정"><br>
								<input type = "button" class = "btn_white" onclick = "if(confirm('삭제하시겠습니까?')){
									location = '${pageContext.request.contextPath }/faq_delete.do?num=${faq.getF_num() }&page=${page }';
									}else{ return; };" value = "삭제">
								</c:if>
							</td>
						</tr>
						<tr class = "answer_tr">
							<td><span>A.</span></td>
							<td colspan = "3" class = "answer_td" style="white-space:pre-wrap">${faq.getF_answer() }</td>
						</tr>
						</c:forEach>
						</c:if>
						<c:if test="${empty list }">
							<tr>
								<td colspan = "4">
									<h3>FAQ가 존재하지 않습니다.</h3>
								</td>
							</tr>
						</c:if>
					</table>
					<div id = "faq_btn">
						<c:if test="${session_mem_id == 'admin' }">
						<input type = "button" class = "btn_blue" value = "FAQ등록" 
								onclick = "location = '${pageContext.request.contextPath }/faq_write_form.do'">
						</c:if> 
					</div>
				</div>
		
				<div class = "page_box">
					<c:if test="${page > block }">
						<a href = "faq_list_cate.do?cate=${list[0].getF_category() }&page=1"> << </a>
						<a href = "faq_list_cate.do?cate=${list[0].getF_category() }&page=${startBlock-1 }"> < </a>
					</c:if>
					
					<c:forEach begin = "${startBlock }" end = "${endBlock }" step="1" var = "i">
						<c:if test="${i == page }">
							<b><a href = "faq_list_cate.do?cate=${list[0].getF_category() }&page=${i }">[${i }]</a></b>
						</c:if>
						<c:if test="${i != page }">
							<a href = "faq_list_cate.do?cate=${list[0].getF_category() }&page=${i }">[${i }]</a>
						</c:if>
					</c:forEach>
					
					<c:if test="${endBlock < allPage }">
						<a href = "faq_list_cate.do?cate=${list[0].getF_category() }&page=${endBlock + 1 }"> > </a>
						<a href = "faq_list_cate.do?cate=${list[0].getF_category() }&page=${allPage }"> >> </a>
					</c:if>	
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../include/bottom.jsp" />
</body>
</html>