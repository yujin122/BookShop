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

public class CategoryListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 코드에 해당하는 카테고리 리스트를 조회하는 컨트롤러
		int cate_code = Integer.parseInt(request.getParameter("code"));
		
		// 페이징 작업
		int rowsize = 5;		// 한 페이지당 보여질 게시물의 수
		int block = 3;			// 아래에 보여질 페이지의 최대 수 - 예) [1][2][3] / [4][5][6]
		int totalRecord = 0;	// DB상의 레코드 게시물 전체 수
		int allPage = 0;		// 전체 페이지 수
		int page = 0;			// 현재 페이지 변수

		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}else {	
			page = 1;			
		}

		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		int startBlock = (((page - 1) / block) * block) + 1;
		int endBlock = (((page - 1) / block) * block) + block;

		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		totalRecord = bs_dao.sideListCount(cate_code);		

		allPage = (int)Math.ceil(totalRecord / (double)rowsize); 	

		if(endBlock > allPage) {	
			endBlock = allPage;
		}
		
		// 페이지에 해당하는 게시물을 가져오는 메서드 호출
		//List<ListDTO> pageList = bs_dao.prodListOther(cate_code, page, rowsize);

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
		request.setAttribute("cate_code", cate_code);
		
		CategoryDAO daoCate = CategoryDAO.getInstance();
		List<CategoryDTO> cateList = daoCate.SubCateList(cate_code);
		request.setAttribute("cateList", cateList);
		
		BookSaleDAO daoList = BookSaleDAO.getInstance();
		List<ListDTO> prodList = daoList.prodListOther(cate_code, page, rowsize);
		request.setAttribute("prodListOther", prodList);
		
		// 사이드 바 카테고리
		List<CategoryDTO> sideList = daoCate.categoryList();
		request.setAttribute("List", sideList);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("book_sale/book_category_side.jsp");
		
		return forward;
	}

}
