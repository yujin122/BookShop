package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MentDAO;
import com.book.model.MentDTO;

public class MentWriteReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int ment_num = Integer.parseInt(request.getParameter("ment_num"));
		String ment_id_nickname = request.getParameter("ment_id_nickname_"+ment_num).trim();
		String ment_cont = request.getParameter("ment_cont_"+ment_num);
		String ment_pwd = request.getParameter("ment_pwd_"+ment_num).trim();
		
		int ment_group = Integer.parseInt(request.getParameter("ment_group_"+ment_num));
		int ment_step = Integer.parseInt(request.getParameter("ment_step_"+ment_num));
		int ment_indent = Integer.parseInt(request.getParameter("ment_indent_"+ment_num));
		int page = Integer.parseInt(request.getParameter("page"));
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		request.setAttribute("page", page);
		request.setAttribute("c_num", c_num);
		
		MentDAO dao = MentDAO.getInstance();
		
		
		MentDTO dto = new MentDTO();
		int max = dao.getMaxMent()+1;
		dto.setC_num_fk(c_num);
		dto.setMent_cont(ment_cont);
		dto.setMent_id_nickname(ment_id_nickname);
		dto.setMent_pwd(ment_pwd);
		dto.setMent_num(max);
		dto.setMent_group(ment_group);
		dto.setMent_step(ment_step);
		dto.setMent_indent(ment_indent);
		
		dao.updateMentStep(ment_group, ment_step);
		
		int res = dao.writeMentReply(dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("commu_cont.do?c_num="+c_num+"&page="+page);
		}else {  // 게시글 추가 실패
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('댓글 추가 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
