package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookNoticeDAO;
import com.book.model.BookNoticeDTO;

public class NoticeSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 공지사항 검색
		String search_list = request.getParameter("search_list").trim();
		String search_word = request.getParameter("search_word").trim();
		
		int rowsize = 15;
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
		
		BookNoticeDAO dao = BookNoticeDAO.getInstance();
		
		totalRecord = dao.getNoticeSearchCount(search_list, search_word);
		
		allPage = (int)Math.ceil(totalRecord / (double)rowsize);
		
		if(endBlock > allPage) {
			endBlock = allPage;
		}
		
		List<BookNoticeDTO> list = dao.searchNotice(page, rowsize, search_list, search_word);
		
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);
		request.setAttribute("List", list);
		request.setAttribute("search_list", search_list);
		request.setAttribute("search_word", search_word);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("notice/notice_search_list.jsp");
		
		return forward;
	}

}
