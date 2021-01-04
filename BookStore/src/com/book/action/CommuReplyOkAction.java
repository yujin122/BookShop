package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CommuDAO;
import com.book.model.CommuDTO;

public class CommuReplyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String c_title = request.getParameter("c_title").trim();
		String c_content = request.getParameter("c_content").trim();
		String c_pwd = request.getParameter("c_pwd").trim();
		String c_id_nickname = request.getParameter("c_id_nickname");
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		int page = Integer.parseInt(request.getParameter("page"));
		int c_group = Integer.parseInt(request.getParameter("c_group"));
		int c_step = Integer.parseInt(request.getParameter("c_step"));
		int c_indent = Integer.parseInt(request.getParameter("c_indent"));
		
		CommuDAO dao = CommuDAO.getInstance();
		CommuDTO dto = new CommuDTO();
		
		dto.setC_title(c_title);
		dto.setC_content(c_content);
		dto.setC_pwd(c_pwd);
		dto.setC_id_nickname(c_id_nickname);
		dto.setC_group(c_group);
		dto.setC_step(c_step);
		dto.setC_indent(c_indent);
		int max = dao.maxCommu()+1;
		dto.setC_num(max);
		
		dao.updateMentStep(c_group,c_step);
		
		int res = dao.insertReplyCommu(dto);
		ActionForward forward = new ActionForward();
		
		if(res>0) {
			forward.setRedirect(true);
			forward.setPath("commu_list.do?page="+page);
		}else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('답글 삽입 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
