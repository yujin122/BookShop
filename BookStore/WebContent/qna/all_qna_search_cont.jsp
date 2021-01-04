<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/help_qna/all_qna.css">
<script type="text/javascript" src="./js/jquery-3.5.1.js"></script>
<script type="text/javascript">
	$(function(){
		var mem_id = "${session_mem_id }";
		var cate = "${cate }";
		var num = $("#num").val();
		
		if(mem_id != ""){ // 로그인
			if($("#nickname").val() != ""){	// 회원글
				if(mem_id == $("#writer").val()){	//작성자
					$("#delete_btn").attr("onclick", "del("+num+")");
					if(cate == ""){		// 전체리스트
						$("#update_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_update_form.do?num="+num+"&page=1&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }'");
						$("#list_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_search.do?page=${page }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }'");
					}	
				}else{		// 작성자 X
					$("#delete_btn").hide();
					if(cate == ""){		// 전체리스트
						$("#update_btn").hide();
						$("#list_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_search.do?page=${page }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }'");
					}
				}
			}else{	// 비회원글
				$("#delete_btn").hide();
				if(cate == ""){		// 전체리스트
					$("#update_btn").hide();
					$("#list_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_search.do?page=${page }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }'");
				}
			}
		}else{	// 로그인 X
			if($("#nickname").val() != ""){	// 회원글
				$("#delete_btn").hide();
				if(cate == ""){		// 전체리스트
					$("#update_btn").hide();
					$("#list_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_search.do?page=${page }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }'");
				}
			}else{	// 비회원글
				$("#delete_btn").attr("onclick", "location = 'all_qna_check_form.do?num="+num+"&page=1&btn=delete'");
				if(cate == ""){		// 전체리스트
					$("#update_btn").attr("onclick", "location = 'all_qna_check_form.do?num="+num+"&page=1&btn=non_update&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }'");
					$("#list_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_search.do?page=${page }&search_cate=${search_cate }&search_label=${search_label }&search_txt=${search_txt }'");
				}
			}
		} 
		
		if(mem_id == 'admin'){
			if(cate == ""){
				$("#reply_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_reply_form.do?num="+num+"&page=${page }'")
			}else{
				$("#reply_btn").attr("onclick", "location = '${pageContext.request.contextPath }/all_qna_reply_form.do?num="+num+"&page=${page }&cate=${cate }'")
			}
		}else{
			$("#reply_btn").hide();
		}
		
	});
	
	function del(num){
		
		if(confirm('삭제하시겠습니까?')){
			location = "${pageContext.request.contextPath }/all_qna_delete.do?num="+num;
		}else{ 
			return; 
		}
	}
</script>
</head>
<body>
	<jsp:include page="../include/top.jsp" />
	<div class="container">
		<div class = "cont_box">
		<jsp:include page="../include/help_side.jsp" />
			
			<div class = "main_box">
				<div id="qna_cont_box">
					<c:set var="qna" value="${dto }" />
					<p>[${qna.getAq_category() }]</p>
					<h3>${qna.getAq_title() }</h3>
					<c:if test="${!empty qna.getAq_mem_nickname() }">
						<span>${qna.getAq_mem_nickname() }(${qna.getAq_writer() })</span>
					</c:if> 
					<c:if test="${empty qna.getAq_mem_nickname() }">
						<span>${qna.getAq_writer() }</span>
					</c:if> <span>${qna.getAq_date().substring(0,10) }</span> <span>조회수 : ${qna.getAq_hit() }</span>
					<hr>
					<textarea rows="25" name="cont" id="cont" readonly>${qna.getAq_content() }</textarea>
				</div>
				<div id="opetion_box">
					<input type="hidden" id="writer" value="${qna.getAq_writer() }">
					<input type="hidden" id="nickname" value="${qna.getAq_mem_nickname() }">
					<input type="hidden" id="num" value="${qna.getAq_num() }"> 
					
					<input type="button" class = "btn_white" id="update_btn" value="수정"> 
					<input type="button" class = "btn_white" id="delete_btn" value="삭제">
					<input type="button" class = "btn_blue" id="reply_btn" value="답글"> 
					<input type="button" class = "btn_white" id="list_btn" value="목록"> 
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../include/bottom.jsp" />
</body>
</html>