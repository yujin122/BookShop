$(function() {
	var page = 1;
	insertQnA(page);
});

// 세션 존재 확인 후 장바구니로
function book_cart(qty) {
	
	if(qty == '0'){
		alert("재고가 없는 상품입니다.");
	}else if ($("#s_count").val() == "") {
		alert("수량을 입력해주세요");
	} else {
		var count = $("#s_count").val();
		var s_num = $("#s_num").val();

		$.ajax({
			url : "book_cart_insert.do",
			type : "post",
			data : {
				"count" : count,
				"num" : s_num
			},
			dataType : "text",
			success : function(data) {
				if (data > 0) {
					if (confirm("장바구니로 이동하시겠습니까?")) {
						location.href = "book_cart_list.do";
					} else {
						$("#s_count").val("");
						return;
					}
				} else if (data == -1) {
					alert("이미 장바구니에 담은 도서입니다.");
				} else {
					alert("장바구니 담기 실패");
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}

		});

	}
}

// 구매
function book_buy(qty) {
	
	if(qty == '0'){
		alert("재고가 없는 상품입니다.");
	}else if ($("#s_count").val() == "") {
		alert("수량을 입력해주세요");
	} else {
		var cnt = $("#s_count").val();
		var num = $("#s_num").val();
		
		location.href = "book_buy_form.do?s_num="+ num +"&count="+cnt;
	}
}

// 문의글 리스트
function insertQnA(no) {
	var s_num = $("#s_num").val();

	$.ajax({
				url : "get_qna.do",
				dataType : "json",
				data : {
					"snum" : s_num,
					"page" : no
				},
				success : function(result) {

					var data = result.data;
					var paging = result.paging;
					var s_member = result.s_mem;
					$("#sale_qna_table tr:gt(0)").remove();

					var tr = "";

					if (result.isData) {
						for (i in result.data) {
							
							tr += "<tr>";
							tr += "<td>" + data[i].q_num + "</td>";
							tr += "<td class = 'tit_td'>";
							for (var j = 0; j < data[i].q_indent; j++) {
								tr += "&nbsp;&nbsp;&nbsp;";
								if (j == data[i].q_indent - 1) {
									tr += "<img src = './img/re.png' width = '30' height = '20'>";
								}
							}
							if(data[i].q_mem == '-'){
								tr += "삭제된 문의글입니다.</td>";
								tr += "<td>" + data[i].q_mem +"</td>";
							}else{
								tr += "<a href = 'javascript:open_cont("
									+ data[i].q_num + ")'>문의합니다.</a></td>";
								tr += "<td>" + data[i].q_mem_nickname+"("+data[i].q_mem+") </td>";
							}
							tr += "<td>" + data[i].q_date.substring(0, 10)
									+ "</td>";
							tr += "<td id = 'hit" + data[i].q_num + "'>"
									+ data[i].q_hit + "</td>";
							tr += "</tr>";
							tr += "<tr style = 'display : none' id = 'td"
									+ data[i].q_num
									+ "'>";
							tr += "<td></td>";
							tr += "<td colspan = '4'>"
									+ "<textarea rows = '5' cols = '60' class = 'book_sale_txtarea' id = 'cont"
									+ data[i].q_num
									+ "' readonly>"
									+ data[i].q_contents + "</textarea><br>";
							tr += "<div class = 'book_sale_option_box' id = 'option_box" + data[i].q_num
									+ "'>";
							if ($("#session").val() != null) {
								if (s_member == $("#session").val()) { // 현재중고도서작성자 ==세션아이디
									tr += "<a href = 'javascript:replyQnA("
											+ data[i].q_num + ")'>답글</a>";
								}
								if (data[i].q_mem == $("#session").val()) { // 문의작성자==세션아이디
									tr += " <a href = 'javascript:updateQnAForm("
											+ data[i].q_num + ")'>수정</a>";
									tr += " <a href = 'javascript:deleteQnA("
											+ data[i].q_num + ")'>삭제</a>";
								}
							}
							tr += "</div>";
							tr += "<div id = 'update_btn" + data[i].q_num
									+ "' style = 'display : none'>";
							tr += "<input type = 'button' value = '수정' class = 'btn_blue' onclick = 'updateQnA("
									+ data[i].q_num + ")'>";
							tr += "<input type = 'button' value = '취소' class = 'btn_white' onclick = 'updateQnAForm("
									+ data[i].q_num + ")'>";
							tr += "</div>";
							tr += "</td>";
							tr += "</tr>";
						}
					} else {
						tr += "<tr>";
						tr += "<td colspan = '7' align = 'center'>";
						tr += "<h3>문의글이 존재하지 않습니다.</h3>";
						tr += "</td>";
						tr += "</tr>";
					}

					$("#tit").after(tr);

					$(".page_box").empty();

					if (paging.page > paging.block) {
						$(".page_box").append(
								"<a href = 'javascript:insertQnA(1)'> << </a>");
						$(".page_box").append(
								"<a href = 'javascript:insertQnA("
										+ (paging.startBlock - 1)
										+ ")'> < </a>");
					}

					for (var i = paging.startBlock - 1; i < paging.endBlock; i++) {
						if (paging.page == (i + 1)) {
							$(".page_box").append(
									"<b><a href = 'javascript:insertQnA("
											+ (i + 1) + ")'>[" + (i + 1)
											+ "]</a></b>");
						} else {
							$(".page_box").append(
									"<a href = 'javascript:insertQnA("
											+ (i + 1) + ")'>[" + (i + 1)
											+ "]</a>");
						}
					}

					if (paging.endBlock < paging.allPage) {
						$(".page_box").append(
								"<a href = 'javascript:insertQnA("
										+ (paging.endBlock + 1) + ")'> > </a>");
						$(".page_box").append(
								"<a href = 'javascript:insertQnA("
										+ paging.allPage + ")'> >> </a>");
					}

				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:"
							+ request.responseText + "\n" + "error:" + error);
				}
			});
}

// 문의글 쓰기
function open_write() {

	window.name = "qna_write";
	window.open("book_sale/book_qna_form.jsp", "pna_form",
			"width=490, height=564");

}

// 문의글 내용
function open_cont(no) {

	if ($("#td" + no).css("display") == "none") {
		$("#td" + no).show();
		qna_hit(no);
	} else {
		$("#td" + no).hide();
	}
}

// 답변 폼 열기
function replyQnA(no) {

	$("#no").val(no);

	window.name = "qna_reply_write";
	window.open("book_sale/book_qna_reply_form.jsp", "pna_form",
			"width=490, height=564");
}

// 수정 폼 열기
var txt = "";

function updateQnAForm(no) {

	if ($("#update_btn" + no).css("display") == "none") {
		$("#update_btn" + no).show();
		$("#option_box" + no).hide();
		$("#cont" + no).attr("readonly", false);
		$("#cont" + no).css("border", "1px solid gray");
		txt = $("#cont" + no).val();
	} else {
		$("#update_btn" + no).hide();
		$("#option_box" + no).show();
		$("#cont" + no).val(txt);
		$("#cont" + no).attr("readonly", true);
		$("#cont" + no).css("border", "0");
	}
}

// 답변 수정
function updateQnA(no) {

	if ($("#cont" + no).val() == "") {
		alert("내용을 입력해주세요.");
		$("#cont" + no).focus();
		return;
	} else {
		var qna_txt = $("#cont" + no).val();

		$.ajax({
			url : "update_qna.do",
			type : "post",
			data : {
				"qna_txt" : qna_txt,
				"num" : no
			},
			dataType : "text",
			success : function(data) {
				if (data > 0) {
					alert("수정 완료");

					$("#update_btn" + no).hide();
					$("#option_box" + no).show();
					$("#cont" + no).val(qna_txt);
					$("#cont" + no).attr("readonly", true);
					$("#cont" + no).css("border", "0");
				} else {
					alert("수정 실패");
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		});
	}

}

// 답변 삭제
function deleteQnA(no) {
	var page = 1;
	if (confirm('삭제하시겠습니까?')) {

		$.ajax({
			url : "delete_qna.do",
			type : "post",
			data : {
				"num" : no
			},
			dataType : "text",
			success : function(data) {
				if (data > 0) {
					alert('삭제 완료');
					insertQnA(page);
				} else {
					alert('삭제 실패');
				}
			},
			error : function(request, status, error) {
				alert("code:" + request.status + "\n" + "message:"
						+ request.responseText + "\n" + "error:" + error);
			}
		});

	} else {
		return;
	}
}

// 조회수
function qna_hit(no) {
	$.ajax({
		url : "qna_hit.do",
		data : {
			"num" : no
		},
		dataType : "text",
		success : function(data) {
			$("#hit" + no).empty();

			$("#hit" + no).append(data);

		},
		error : function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	});
}
