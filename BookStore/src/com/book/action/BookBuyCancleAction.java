package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookBuyDAO;
import com.book.model.BookBuyDTO;
import com.book.model.BookSaleDAO;

public class BookBuyCancleAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num").trim());
		int qty = Integer.parseInt(request.getParameter("qty").trim());
		int page = Integer.parseInt(request.getParameter("page"));
		
		BookBuyDAO bb_dao = BookBuyDAO.getInstance();
		BookBuyDTO bb_dto =  bb_dao.getBookBuy(num);
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		bs_dao.cancleSaleQty(bb_dto.getBuy_snum(), qty);
		
		int res = bb_dao.updateBookBuyCancle(num);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			
			if(request.getParameter("menu") != null) {
				String menu = request.getParameter("menu").trim();
				menu = URLEncoder.encode(menu, "utf-8");
				
				forward.setPath("book_buy_cont.do?num="+num+"&page="+page+"&menu="+menu);
			}else {
				if(request.getParameter("date1") != null) {
					String date1 = request.getParameter("date1");
					String date2 = request.getParameter("date2");
					
					forward.setPath("book_buy_cont.do?num="+num+"&page="+page+"&date1="+date1+"&date2="+date2);
				}else {
					forward.setPath("book_buy_cont.do?num="+num+"&page="+page);
				}
			}
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('구매 신청 취소 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
