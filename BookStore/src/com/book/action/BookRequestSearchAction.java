package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookRequestDAO;
import com.book.model.BookRequestDTO;

public class BookRequestSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String search_label = request.getParameter("search_label").trim();
		String search_txt = request.getParameter("search_txt").trim();
		
		int rowsize = 10;
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
		
		BookRequestDAO dao = BookRequestDAO.getInstance();
		
		totalRecord = dao.getRequestCount(search_label, search_txt);
		
		allPage = (int)Math.ceil(totalRecord / (double)rowsize);
		
		if(endBlock > allPage) {
			endBlock = allPage;
		}
		
		List<BookRequestDTO> list = dao.searchBookRequest(page, rowsize, search_label, search_txt);
		

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
		request.setAttribute("search_label", search_label);
		request.setAttribute("search_txt", search_txt);
		
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("requestbook/book_request_search_list.jsp");
		
		return forward;
	}

}
