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

public class BookSaleSearchCateAction implements Action {
	//검색된 searchPage.jsp에서 카테고리 클릭 시 해당 카테고리만 보여주는 메서드. BookSaleSEarchMainAction과 대부분 같음.
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String book_sale_search_category = request.getParameter("book_sale_search_category");
		String book_sale_search_item = request.getParameter("book_sale_search_item");
		int cate_code = Integer.parseInt(request.getParameter("cate_code"));
		//카테고리 코드를 포함해서 리스트를 검색, 리스트를 리턴하는 메서드.
		BookSaleDAO dao = BookSaleDAO.getInstance();
		List<ListDTO> list = dao.searchBook_Main(book_sale_search_category,book_sale_search_item, cate_code);
		request.setAttribute("List1", list);
		request.setAttribute("book_sale_search_category", book_sale_search_category);
		request.setAttribute("book_sale_search_item", book_sale_search_item);
		
		//카테고리를 반환하는 메서드.
		CategoryDAO dao_cate = CategoryDAO.getInstance();
		List<CategoryDTO> list_cate = dao_cate.getCategory();
		request.setAttribute("list_cate", list_cate);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("main/searchPage.jsp");
		
		return forward;
	}

}
