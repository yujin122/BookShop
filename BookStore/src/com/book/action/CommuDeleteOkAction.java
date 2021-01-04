package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CommuDAO;

public class CommuDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		String c_pwd = request.getParameter("c_pwd").trim();
		
		CommuDAO dao = CommuDAO.getInstance();
		
		PrintWriter out = response.getWriter();
		ActionForward forward = new ActionForward();
		
		if(c_pwd.equals(dao.getPwd(c_num))) {
			int res = dao.deleteCont(c_num);
			if(res>0) {
				forward.setRedirect(true);
				forward.setPath("commu_list.do");
			}else {
				out.println("<script>");
				out.println("alert('게시물 삭제 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
		} else {
			out.println("<script>");
			out.println("alert('비밀번호가 틀렸습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
