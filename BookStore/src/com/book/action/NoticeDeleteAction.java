package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookNoticeDAO;

public class NoticeDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 공지사항 삭제
		int num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page"));
		
		BookNoticeDAO dao = BookNoticeDAO.getInstance();
		int res = dao.deleteNotice(num);
		
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("notice_admin.do?page="+page);
		}else {
			out.println("<script>");
			out.println("alert('공지사항 글 삭제 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
