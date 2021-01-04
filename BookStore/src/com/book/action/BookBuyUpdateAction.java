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

public class BookBuyUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 구매 신청 수정 업데이트
		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name").trim();
		String phone = request.getParameter("phone").trim();
		String trans = request.getParameter("transaction");
		String request_txt = request.getParameter("request_txt").trim();
		int page = Integer.parseInt(request.getParameter("page"));
		
		BookBuyDTO dto = new BookBuyDTO();
		dto.setBuy_num(num);
		dto.setBuy_name(name);
		dto.setBuy_phone(phone);
		dto.setBuy_trans(trans);
		dto.setBuy_request(request_txt);
		
		BookBuyDAO dao = BookBuyDAO.getInstance();
		
		int res = dao.updateBookBuy(dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			
			if(request.getParameter("menu") != null) {
				String menu = request.getParameter("menu").trim();
				menu = URLEncoder.encode(menu, "utf-8");
				
				forward.setPath("book_buy_cont.do?num="+num+"&menu="+menu+"&page="+page);
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
			out.println("alert('수정 실패')");
			out.println("history.back()");
			out.println("</script>");
			
		}
		return forward;
	}

}
