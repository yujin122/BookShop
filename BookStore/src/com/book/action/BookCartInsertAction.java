package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookCartDAO;
import com.book.model.BookCartDTO;
public class BookCartInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 장바구니 넣기
		
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		int s_num = Integer.parseInt(request.getParameter("num").trim());
		int count = Integer.parseInt(request.getParameter("count").trim());
		
		BookCartDTO bc_dto = new BookCartDTO();
		
		bc_dto.setC_member(mem_id);
		bc_dto.setC_sale_num(s_num);
		bc_dto.setC_qty(count);
		
		BookCartDAO bc_dao = BookCartDAO.getInstance();
		
		int res = bc_dao.insertBookCart(bc_dto);
		
		PrintWriter out = response.getWriter();
		
		out.println(res);
		out.close();
		
		return null;
	}

}
