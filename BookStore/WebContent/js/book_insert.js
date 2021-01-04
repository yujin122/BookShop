	
	// 도서 검색 창 열기
	function search_book(){
		window.name = "inser_book";
		window.open("book_sale/book_search.jsp", "search_book", "width=550, height=600");		
	}
	
	// 자식창에서 isbn 가져오기
	function sendISBN(isbn){
		getSearchISBN(isbn);
	}
	
	// isbn 도서 검색
	function getSearchISBN(isbn){
		var key = "key";
		
		$.ajax({
			url : "https://dapi.kakao.com/v3/search/book",
			headers : {'Authorization' : 'KakaoAK ' + key},
			type : "get",
			dataType : "json",
			data : {
				'query' : isbn,
				'target' : 'isbn'
			},
			success : function(result){
				
				var authors = "";
				for(var i in result.documents[0].authors){
					authors = result.documents[0].authors[i] + " ";
				}
				$("#book_writer").val(authors);
				$("#book_writer").attr("readonly",true);
				
				$("#book_cont").val(result.documents[0].contents);
				$("#book_cont").attr("readonly",true);
				
				var book_isbn = result.documents[0].isbn.split(" ")
				var isbn_t = result.documents[i].isbn.trim();
				 
				isbn_t = isbn_t.replace(" ", "-")
				
				
				console.log(isbn_t);
				
				var pattern_spc = /[-]/;

				if(pattern_spc.test(isbn_t)){
					var isbn = isbn_t.split("-");
					isbn_t = isbn[0]; 
				}
				
				$("#book_isbn").val(isbn_t);
				$("#book_isbn").attr("readonly",true);
				
				$("#book_title").val(result.documents[0].title);
				$("#book_title").attr("readonly",true);
				
				var translator = "";
				for(var i in result.documents[0].translators){
					translator = result.documents[0].translators[i] + " ";
				}
				$("#book_translator").val(translator);
				$("#book_translator").attr("readonly",true);
				
				$("#book_publisher").val(result.documents[0].publisher);
				$("#book_publisher").attr("readonly",true);
				
				var date = result.documents[0].datetime.split("T")
				$("#book_date").val(date[0]);
				$("#book_date").attr("readonly",true);
				
				$("#book_price").val(result.documents[0].price);
				$("#book_price").attr("readonly",true);
				
				$(".img").remove();
				
				var tr = "";
				
				tr += "<tr class = 'img'><td colspan = '2'>";
				if(result.documents[0].thumbnail == ""){
					tr += "<img src = './img/nophoto.png' class = 'book_insert_img'></td></tr>"
				}else{
					tr += "<img src = '"+result.documents[0].thumbnail+"' class = 'book_insert_img'></td></tr>"
				}
				$("#cate").after(tr);
				
				$("#book_img").val(result.documents[0].thumbnail);
			
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	// 카테고리 가져오기
	function cateSelect(){
		var select = $("#category1 option:selected").val();
		console.log(select);
		
		$.ajax({
			url : "get_sub_category.do",
			type : "get",
			data : {
				"select" : select
			},
			dataType : "json",
			success : function(data){
				
				$("#category2").empty();
				
				var option = "";
				
				for(i in data){
					option += "<option value = "+data[i].cate_code+">"+data[i].cate_name+"</option>"	
				}
				
				$("#category2").append(option);
				
			}
		});
	}
	
	function check(){
		if($("#category1").val() == ""){
			alert("도서 분야를 선택해주세요.");
			$("#category1").focus();
			return false;
		}
		if($("#book_title").val() == ""){
			alert("도서명을 입력해주세요.");
			$("#book_title").focus();
			return false;
		}
		if($("#book_writer").val() == ""){
			alert("저자명을 입력해주세요.");
			$("#book_writer").focus();
			return false;
		}
		if($("#book_publisher").val() == ""){
			alert("출판사를 입력해주세요.");
			$("#book_publisher").focus();
			return false;
		}
		if($("#book_date").val() == ""){
			alert("출간일을 입력해주세요.");
			$("#book_date").focus();
			return false;
		}
		if($("#book_price").val() == ""){
			alert("정가를 입력해주세요.");
			$("#book_price").focus();
			return false;
		}
		if($("#book_quality").val() == ""){
			alert("상품품질을 선택해주세요.");
			$("#book_quality").focus();
			return false;
		}
		if($("#book_sell").val() == ""){
			alert("판매가를 입력해주세요.");
			$("#book_sell").focus();
			return false;
		}
		if($("#books_status").val() == ""){
			alert("판매상태를 선택해주세요.");
			$("#books_status").focus();
			return false;
		}
		if($("#book_qty").val() == ""){
			alert("재고 수량을 입력해주세요.");
			$("#book_qty").focus();
			return false;
		}
		if($("#book_direct").val() == ""){
			alert("직거래 가능을 선택해주세요.");
			$("#book_direct").focus();
			return false;
		}
		if($("#ship_charge").val() == ""){
			alert("배송비를 입력해주세요.");
			$("#ship_charge").focus();
			return false;
		}
		
	}