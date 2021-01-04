package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;

public class AllQnADeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		AllQnADAO dao = AllQnADAO.getInstance();
		
		int res = dao.deleteAllQnA(num);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("qna_list.do");
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('삭제 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
