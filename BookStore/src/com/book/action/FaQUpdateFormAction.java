package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.FaQDAO;
import com.book.model.FaQDTO;

public class FaQUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		if(request.getParameter("cate") != null) {
			String cate = request.getParameter("cate");
			request.setAttribute("cate", cate);
		}
		FaQDAO dao = FaQDAO.getInstance();
		
		FaQDTO dto = dao.getFaQ(num);
		
		request.setAttribute("page", page);
		request.setAttribute("faq", dto);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("qna/faq_update_form.jsp");
		
		return forward;
	}

}
