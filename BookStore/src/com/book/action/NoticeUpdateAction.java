package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookNoticeDAO;
import com.book.model.BookNoticeDTO;

public class NoticeUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 수정 상세 내용
		int num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page"));
		
		BookNoticeDAO dao = BookNoticeDAO.getInstance();
		BookNoticeDTO dto = dao.getNoticeCont(num);
		
		request.setAttribute("dto", dto);
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("notice/notice_update.jsp");
		
		return forward;
	}

}
