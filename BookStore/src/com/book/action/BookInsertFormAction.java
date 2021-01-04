package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CategoryDAO;
import com.book.model.CategoryDTO;

public class BookInsertFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 중고도서 등록 폼 (카테고리 가져오기)
		
		CategoryDAO cate_dao = CategoryDAO.getInstance();
		
		List<CategoryDTO> cate_list = cate_dao.categoryList();
		
		request.setAttribute("cate_list", cate_list);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("book_sale/book_insert.jsp");
		
		return forward;
	}

}
