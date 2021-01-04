package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.BookBuyDAO;
import com.book.model.BookCartDAO;
import com.book.model.BookCartListDTO;
import com.book.model.BookSaleDAO;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class MypageMainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		MemberDAO mem_dao = MemberDAO.getInstance();
		MemberDTO mem_dto = mem_dao.getMemberInfo(mem_id);
		request.setAttribute("mem_dto", mem_dto);
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		BookBuyDAO bb_dao = BookBuyDAO.getInstance();
		BookCartDAO bc_dao = BookCartDAO.getInstance();
		
		int cart_cnt = bc_dao.cartCount(mem_id);
		request.setAttribute("cart_cnt", cart_cnt);
		
		int buy_cnt = bb_dao.getmyBuyCount(mem_id);
		request.setAttribute("buy_cnt", buy_cnt);
		
		int sale_cnt = bs_dao.getBookSaleCount(mem_id,"판매중");
		request.setAttribute("sale_cnt", sale_cnt);
		
		int rowsize = 5;
		int block = 5;
		int totalRecord = 0;
		int allPage = 0;
		
		int page = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}else {
			page = 1;
		}
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		int startBlock = (((page - 1) / block) * block) + 1;
		int endBlock = (((page - 1) / block) * block) + block;
		
		totalRecord = bc_dao.cartCount(mem_id);
		
		allPage = (int)Math.ceil(totalRecord / (double)rowsize);
		
		if(endBlock > allPage) {
			endBlock = allPage;
		}
		
		List<BookCartListDTO> list = bc_dao.getBookCart(mem_id, page, rowsize);
		
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);
		request.setAttribute("list", list);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("mypage/mypage_main.jsp");
		
		return forward;
	}

}
