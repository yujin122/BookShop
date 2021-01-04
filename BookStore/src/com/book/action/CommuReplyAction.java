package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;

public class CommuReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		request.setAttribute("c_num",c_num);
		request.setAttribute("page",Integer.parseInt(request.getParameter("page")));
		request.setAttribute("c_group",Integer.parseInt(request.getParameter("c_group")));
		request.setAttribute("c_step",Integer.parseInt(request.getParameter("c_step")));
		request.setAttribute("c_indent",Integer.parseInt(request.getParameter("c_indent")));
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("commu/commu_reply.jsp");
		return forward;
	}

}
