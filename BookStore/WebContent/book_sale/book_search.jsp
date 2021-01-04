<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "../css/book_sale/book_search.css">
<script type="text/javascript" src="../js/jquery-3.5.1.js"></script>
<link rel = "stylesheet" href = "../css/common.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
	
	$(function(){
		getVal();	
	}); 
	
	function getVal(){
		var label = opener.document.getElementById("search_label").value;
		var txt = opener.document.getElementById("book_search_txt").value;
		var page = 1;
		
		if(label == "도서명"){
			document.getElementById("title_txt").value = txt;
			getSearchTitle(txt, page);
		}else if(label == "ISBN"){
			document.getElementById("isbn_txt").value = txt;
			getSearchISBN(txt, page);
		}
	} 
	
	 // 제목으로 검색
	function getSearchTitle(title, page){
		var key = "key";
		
		if($("#title_txt").val() == ""){
			alert("검색어를 입력해주세요");
			$("#title_txt").focus();
			return;
		}else{
			$.ajax({
				url : "https://dapi.kakao.com/v3/search/book",
				headers : {'Authorization' : 'KakaoAK ' + key},
				type : "get",
				dataType : "json",
				data : {
					'query' : title,
					'target' : 'title',
					'page' : page
				},
				success : function(result){
					$(".page_box").remove();
					
					if(page == 1){
						$("#book_search_table").empty();	
					}

					var a = "";
					
					 for(var i in result.documents){
						var isbn_t = result.documents[i].isbn.trim();
						 
						isbn_t = isbn_t.replace(" ", "-")
						
						var pattern_spc = /[-]/;

						if(pattern_spc.test(isbn_t)){
							var isbn = isbn_t.split("-");
							isbn_t = isbn[0]; 
						}
						
						a += "<a href = 'javascript:sendISBN(\""+ isbn_t +"\")'>";
						a += "<table>";
						a += "<tr><th rowspan = '3'><img src = '"+result.documents[i].thumbnail
							+"' class = 'book_search_img'></th>";
						a += "<td>"+result.documents[i].title + "</td>";
						a += "</tr><tr><td>"+result.documents[i].authors + "/" + result.documents[i].publisher +"</tr><tr>";
						a += "<td>정가 : "+ result.documents[i].price +"원 </td></tr></table>"
					} 
					 
					 if(!result.meta.is_end){
							a += "<div class = 'page_box'>";
							a += "<a href = 'javascript:search_title("+(page+1)+")'>더보기</a>";
							a += "</div>";
					}
					
					$("#book_search_table").append(a);
				},
				error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}
	}
	
	 // isbn으로 검색
	function getSearchISBN(isbn, page){
		var key = "key";
		
		if($("#isbn_txt").val() == ""){
			alert("검색어를 입력해주세요");
			$("#isbn_txt").focus();
			return;
		}else{
			$.ajax({
				url : "https://dapi.kakao.com/v3/search/book",
				headers : {'Authorization' : 'KakaoAK ' + key},
				type : "get",
				dataType : "json",
				data : {
					'query' : isbn,
					'target' : 'isbn',
					'page' : page
				},
				success : function(result){
					
					$(".page_box").empty();
					
					if(page == 1){
						$("#book_search_table").empty();	
					}
					
					var a = "";
					
					for(var i in result.documents){
						var isbn_t = result.documents[i].isbn.trim();
						 
						isbn_t = isbn_t.replace(" ", "-")
					
						var pattern_spc = /[-]/;

						if(pattern_spc.test(isbn_t)){
							var isbn = isbn_t.split("-");
							isbn_t = isbn[0]; 
						}
						
						a += "<a href = 'javascript:sendISBN(\""+isbn_t +"\")'>";
						a += "<table>";
						a += "<tr><th rowspan = '3'><img src = '"+result.documents[i].thumbnail
							+"' class = 'book_search_img'></th>";
						a += "<td>"+result.documents[i].title + "</td>";
						a += "</tr><tr><td>"+result.documents[i].authors + "/" + result.documents[i].publisher +"</tr><tr>";
						a += "<td>정가 : "+ result.documents[i].price +"원 </td></tr></table>"
					} 
					
					if(!result.meta.is_end){
						a += "<div align = 'center' class = 'page_box'>";
						a += "<a href = 'javascript:search_isbn("+(page+1)+")'>더보기</a>";
						a += "</div>";
					}
					
					$("#book_search_table").append(a);
					
				},
				error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});	
		}
	} 
	
	function sendISBN(isbn){
		console.log(isbn);
		opener.sendISBN(isbn);
		window.close();
	} 
	
 	function search_isbn(page){
 		var txt = $("#isbn_txt").val();
		getSearchISBN(txt, page);
	}
	 
	function search_title(page){
		
		var txt = $("#title_txt").val();
		getSearchTitle(txt, page);
	}

	
</script>
</head>
<body>
	<!-- 도서 등록시 도서 검색 폼 -->
	<div id = "book_search_box">
		<h3>도서 검색</h3>
		<hr>
		<div id = "search_box">
		<c:set var = "page" value = "1" />
			<table>
				<tr>
					<th>ISBN</th>
					<td><input class="form-control" type = "text" id = "isbn_txt"></td>
					<td><button class= "btn_blue" onclick = "search_isbn(${page })">검색</button></td>
				</tr>
				<tr>
					<th>책이름</th>
					<td><input class="form-control" type = "text" id = "title_txt"></td>
					<td><button class= "btn_blue" onclick = "search_title(${page })">검색</button></td>
				</tr>
			</table>
		</div>
		<hr>
		<div id = "book_search_table">
		</div>
	</div>
</body>
</html>