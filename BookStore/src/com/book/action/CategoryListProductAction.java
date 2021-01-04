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

public class CategoryListProductAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 중분류 코드에 해당하는 상품 리스트 조회 컨트롤러
		int cate_code = Integer.parseInt(request.getParameter("code"));
		String menu = request.getParameter("menu");
		
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
		totalRecord = bs_dao.topListCount(cate_code);		

		allPage = (int)Math.ceil(totalRecord / (double)rowsize); 	

		if(endBlock > allPage) {	
			endBlock = allPage;
		}
		
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endNo", endNo);
		request.setAttribute("endBlock", endBlock);
		request.setAttribute("cate_code", cate_code);
		request.setAttribute("menu", menu);
		
		CategoryDAO daoCate = CategoryDAO.getInstance();
		BookSaleDAO daoList = BookSaleDAO.getInstance();
		
		if(menu.equals("all")) {
			// 상단 바 카테고리
			List<CategoryDTO> list = daoCate.cateAllList();
			request.setAttribute("cateAllList", list);
		}else if(menu.equals("sub")) {
			int cate_code_fk = daoCate.getSubCate(cate_code);
			
			List<CategoryDTO> cateList = daoCate.SubCateList(cate_code_fk);
			request.setAttribute("cateList", cateList);
		}
		
		// 사이드 바 카테고리
		List<CategoryDTO> sideList = daoCate.categoryList();
		request.setAttribute("List", sideList);
		
		// 해당 카테고리 리스트
		List<ListDTO> prodList = daoList.middleProdList(cate_code, page, rowsize);
		request.setAttribute("prodList", prodList);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("book_sale/book_category_top.jsp");
		
		return forward;
	}

}
