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
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class BookContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 중고도서 상세페이지
	
		int s_num = Integer.parseInt(request.getParameter("s_num").trim());
		int page = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}else {
			page = 1;
		}
		
		if(request.getParameter("code") != null) {
			int code = Integer.parseInt(request.getParameter("code"));
			request.setAttribute("code", code);
		}
		
		if(request.getParameter("menu") != null) {
			String menu = request.getParameter("menu");
			request.setAttribute("menu", menu);
		}
		
		if(request.getParameter("cart") != null) {
			String cart = request.getParameter("cart");
			request.setAttribute("cart", cart);
		}
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		bs_dao.hit_bookSale(s_num);
		BookSaleDTO bs_dto = bs_dao.getBookSale(s_num);
		
		BookInfoDAO bi_dao = BookInfoDAO.getInstance();
		BookInfoDTO bi_dto = bi_dao.getBookInfo(bs_dto.getS_bnum());
		
		MemberDAO mem_dao = MemberDAO.getInstance();
		MemberDTO mem_dto = mem_dao.getMemberInfo(bs_dto.getS_member());
		
		CategoryDAO cate_dao = CategoryDAO.getInstance();
		List<CategoryDTO> cate_list = cate_dao.getBookCategory(bi_dto.getB_cate_fk());
		
		request.setAttribute("bs_dto", bs_dto);
		request.setAttribute("bi_dto", bi_dto);
		request.setAttribute("mem_dto", mem_dto);
		request.setAttribute("cate_list", cate_list);		
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("book_sale/book_cont.jsp");
		
		return forward;
	}

}
