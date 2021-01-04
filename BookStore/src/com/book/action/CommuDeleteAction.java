package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;

public class CommuDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setAttribute("c_num",Integer.parseInt(request.getParameter("c_num")));
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("commu/commu_delete.jsp");
		return forward;
	}

}
