package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CommuDAO;
import com.book.model.CommuDTO;

public class CommuEditOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String c_title = request.getParameter("c_title").trim();
		String c_content = request.getParameter("c_content").trim();
		String c_pwd = request.getParameter("c_pwd").trim();
		String c_id_nickname = request.getParameter("c_id_nickname");
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		CommuDAO dao = CommuDAO.getInstance();
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		if(dao.getPwd(c_num).equals(c_pwd)) {
			CommuDTO dto = new CommuDTO();
			dto.setC_title(c_title);
			dto.setC_content(c_content);
			dto.setC_id_nickname(c_id_nickname);
			dto.setC_num(c_num);
			int res = dao.updateCont(dto);
			if(res>0) {
				forward.setRedirect(true);
				forward.setPath("commu_cont.do?c_num="+c_num+"&page="+page);
			}else {
				out.println("<script>");
				out.println("alert('게시물 수정 실패')");
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
