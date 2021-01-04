package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookInfoDAO;
import com.book.model.BookInfoDTO;
import com.book.model.BookSaleDAO;
import com.book.model.BookSaleDTO;
import com.book.model.CategoryDAO;
import com.book.model.CategoryDTO;

public class BookSaleUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page"));
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		BookSaleDTO bs_dto = bs_dao.getBookSale(num);
		
		BookInfoDAO bi_dao = BookInfoDAO.getInstance();
		BookInfoDTO bi_dto = bi_dao.getBookInfo(bs_dto.getS_bnum());
		
		CategoryDAO cate_dao = CategoryDAO.getInstance();
		List<CategoryDTO> cate_list = cate_dao.getBookCategory(bi_dto.getB_cate_fk());
		
		request.setAttribute("bs_dto", bs_dto);
		request.setAttribute("bi_dto", bi_dto);
		request.setAttribute("cate_list", cate_list);
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("mypage/book_sale_update_form.jsp");
		
		return forward;
	}

}
