package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CommuDAO;


public class MemberWriteDelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		String[] num = request.getParameterValues("delCheck");
		
		CommuDAO dao = CommuDAO.getInstance();
		int check = dao.deleteCommu(num);
		
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("member_write_list.do");
		}else {
			out.println("<script>");
			out.println("alert('삭제되지 않았습니다 ')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
