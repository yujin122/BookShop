package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookBuyDAO;
import com.book.model.BookBuyDTO;
import com.book.model.BookInfoDAO;
import com.book.model.BookInfoDTO;
import com.book.model.BookSaleDAO;
import com.book.model.BookSaleDTO;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class BookSaleContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		int page = Integer.parseInt(request.getParameter("page"));
		String menu = request.getParameter("menu");
		
		BookBuyDAO bb_dao = BookBuyDAO.getInstance();
		BookBuyDTO bb_dto = bb_dao.getBookBuy(num);
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		BookSaleDTO bs_dto = bs_dao.getBookSale(bb_dto.getBuy_snum());
		
		BookInfoDAO bi_dao = BookInfoDAO.getInstance();
		BookInfoDTO bi_dto = bi_dao.getBookInfo(bs_dto.getS_bnum());
		
		MemberDAO mem_dao = MemberDAO.getInstance();
		MemberDTO mem_dto = mem_dao.getMemberInfo(bb_dto.getBuy_member());
		
		request.setAttribute("mem_dto", mem_dto);
		request.setAttribute("bb_dto", bb_dto);
		request.setAttribute("bs_dto", bs_dto);
		request.setAttribute("bi_dto", bi_dto);
		request.setAttribute("page", page);
		request.setAttribute("menu", menu);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("mypage/book_sale_cont.jsp");
		
		return forward;
	}

}
