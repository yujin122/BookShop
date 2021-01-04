package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MentDAO;

public class MentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String delete_pwd = request.getParameter("delete_pwd");
		int delete_page = Integer.parseInt(request.getParameter("delete_page"));
		int delete_c_num = Integer.parseInt(request.getParameter("delete_c_num"));
		int delete_ment_num = Integer.parseInt(request.getParameter("delete_ment_num"));
		MentDAO dao = MentDAO.getInstance();
		int res = dao.deleteMent(delete_ment_num, delete_pwd);
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("commu_cont.do?c_num="+delete_c_num+"&page="+delete_page);
		}else if(res == -1) {  // 게시글 추가 실패
			out.println("<script>");
			out.println("alert('비밀번호가 틀렸습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('댓글 삭제 실패')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}
		return forward;
	}

}
