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

public class BookSaleSearchMainAction implements Action {
	//탑 메뉴 바  검색 창에서 검색하는 메서드.
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String book_sale_search_category = request.getParameter("book_sale_search_category");
		String book_sale_search_item = request.getParameter("book_sale_search_item").trim();
		
		//검색된 리스트를 리턴함.
		BookSaleDAO dao = BookSaleDAO.getInstance();
		List<ListDTO> list = dao.searchBook_Main(book_sale_search_category,book_sale_search_item);
		
		request.setAttribute("List1", list);
		request.setAttribute("book_sale_search_category", book_sale_search_category);
		request.setAttribute("book_sale_search_item", book_sale_search_item);
		
		//사이드 바에 필요한 카테고리 리스트를 반환함.
		CategoryDAO dao_cate = CategoryDAO.getInstance();
		List<CategoryDTO> list_cate = dao_cate.getCategory();
		request.setAttribute("list_cate", list_cate);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("main/searchPage.jsp");
		
		return forward;
	}

}
