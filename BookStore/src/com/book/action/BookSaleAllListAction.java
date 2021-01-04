package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookSaleDAO;
import com.book.model.CategoryDAO;
import com.book.model.CategoryDTO;
import com.book.model.ListDTO;

public class BookSaleAllListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 페이징 작업
		int rowsize = 5;		// 한 페이지당 보여질 게시물의 수
		int block = 3;			// 아래에 보여질 페이지의 최대 수 - 예) [1][2][3] / [4][5][6]
		int totalRecord = 0;	// DB상의 레코드 게시물 전체 수
		int allPage = 0;		// 전체 페이지 수
		int page = 0;			// 현재 페이지 변수
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}else {	// 파라미터로 넘어온 값이 없다면
			page = 1;			// 처음으로 "게시물 전체 목록" 태그를 클릭한 경우	
		}
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);
		
		// 해당 페이지의 끝 번호
		int endNo = (page * rowsize);
		
		// 해당 페이지의 시작 블럭
		int startBlock = (((page - 1) / block) * block) + 1;
		
		// 해당 페이지의 마지막 블럭
		int endBlock = (((page - 1) / block) * block) + block;
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		totalRecord = bs_dao.getListCount();		// DB상의 전체 게시물의 수를 확인하는 메서드
		
		allPage = (int)Math.ceil(totalRecord / (double)rowsize); 	// ceil반환타입이 double이라 한쪽을 형변환시킴
		
		if(endBlock > allPage) {	// 끝 블럭 번호가 전체페이지수보다 크면 전체페이지
			endBlock = allPage;
		}
		
		// 페이지에 해당하는 게시물을 가져오는 메서드 호출
		//List<ListDTO> pageList = bs_dao.prodList(page, rowsize);
		
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endNo", endNo);
		request.setAttribute("endBlock", endBlock);
		//request.setAttribute("pageList", pageList);
		
		CategoryDAO dao = CategoryDAO.getInstance();
		
		// 상단 바 카테고리
		List<CategoryDTO> list = dao.cateAllList();
		request.setAttribute("cateAllList", list);
		
		// 사이드 바 카테고리
		List<CategoryDTO> sideList = dao.categoryList();
		request.setAttribute("List", sideList);
		
		// 전체 리스트
		List<ListDTO> all_list = bs_dao.prodList(page, rowsize);
		request.setAttribute("prodList", all_list);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("book_sale/book_category_main.jsp");
		
		return forward;
	}

}
