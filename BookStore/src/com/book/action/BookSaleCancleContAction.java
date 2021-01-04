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

public class BookSaleCancleContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int num = Integer.parseInt(request.getParameter("num").trim());
		int qty = Integer.parseInt(request.getParameter("qty").trim());
		int page = Integer.parseInt(request.getParameter("page"));
		String menu = request.getParameter("menu").trim();
		menu = URLEncoder.encode(menu, "utf-8");
		
		BookBuyDAO bb_dao = BookBuyDAO.getInstance();
		BookBuyDTO bb_dto =  bb_dao.getBookBuy(num);
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		bs_dao.cancleSaleQty(bb_dto.getBuy_snum(), qty);
		
		int res = bb_dao.updateBookBuyCancle(num);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("book_sale_cont.do?num="+num+"&page="+page+"&menu="+menu);
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
