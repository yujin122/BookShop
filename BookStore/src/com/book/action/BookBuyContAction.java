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

public class BookBuyContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 도서 구매 상세페이지
		int num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page"));
		
		if(request.getParameter("menu") != null) {
			String menu = request.getParameter("menu").trim();
			request.setAttribute("menu", menu);
		}
		
		if(request.getParameter("date1") != null && request.getParameter("date2") != null) {
			String date1 = request.getParameter("date1");
			String date2 = request.getParameter("date2");
			
			request.setAttribute("date1", date1);
			request.setAttribute("date2", date2);
		}
		
		BookBuyDAO bb_dao = BookBuyDAO.getInstance();
		BookBuyDTO bb_dto = bb_dao.getBookBuy(num);
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		BookSaleDTO bs_dto = bs_dao.getBookSale(bb_dto.getBuy_snum());
		
		BookInfoDAO bi_dao = BookInfoDAO.getInstance();
		BookInfoDTO bi_dto = bi_dao.getBookInfo(bs_dto.getS_bnum());
		
		MemberDAO mem_dao = MemberDAO.getInstance();
		MemberDTO mem_dto = mem_dao.getMemberInfo(bs_dto.getS_member());
		
		request.setAttribute("bb_dto", bb_dto);
		request.setAttribute("bs_dto", bs_dto);
		request.setAttribute("bi_dto", bi_dto);
		request.setAttribute("mem_dto", mem_dto);
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("mypage/book_buy_cont.jsp");
		
		return forward;
	}

}
