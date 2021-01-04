package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookBuyDAO;
import com.book.model.BookBuyDTO;
import com.book.model.BookCartDAO;
import com.book.model.BookSaleDAO;


public class BookBuyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 구매 신청 추가
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		int s_num = Integer.parseInt(request.getParameter("s_num").trim());
		String name = request.getParameter("name").trim();
		int qty = Integer.parseInt(request.getParameter("cnt").trim());
		String phone = request.getParameter("phone").trim();
		String trans = request.getParameter("transaction");
		String request_txt = request.getParameter("request_txt").trim();
		
		BookBuyDTO bb_dto = new BookBuyDTO();
		
		bb_dto.setBuy_snum(s_num);
		bb_dto.setBuy_member(mem_id);
		bb_dto.setBuy_name(name);
		bb_dto.setBuy_qty(qty);
		bb_dto.setBuy_trans(trans);
		bb_dto.setBuy_phone(phone);
		bb_dto.setBuy_request(request_txt);
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();		
		BookBuyDAO bb_dao = BookBuyDAO.getInstance();
		BookCartDAO bc_dao = BookCartDAO.getInstance();
		
		// 구매신청
		int res = bb_dao.insertBookBuy(bb_dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			// 구매 수량 차감
			bs_dao.buySaleQty(s_num, qty);
			
			// 장바구니에서 구매시 장바구니 삭제
			if(request.getParameter("c_num") != null) {
				int c_num = Integer.parseInt(request.getParameter("c_num").trim());
				bc_dao.deleteCartBuy(c_num);
			}
			
			forward.setRedirect(true);
			forward.setPath("book_buy_list.do");
			
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('도서 구매 신청 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
