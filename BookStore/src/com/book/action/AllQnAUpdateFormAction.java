package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.AllQnADTO;

public class AllQnAUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		if(request.getParameter("cate") != null) {
			String cate = request.getParameter("cate");
			request.setAttribute("cate", cate);	
		}

		if(request.getParameter("search_cate") != null && request.getParameter("search_label") != null && request.getParameter("search_txt") != null) {
			String search_cate = request.getParameter("search_cate");
			String search_label = request.getParameter("search_label");
			String search_txt = request.getParameter("search_txt");
			
			request.setAttribute("search_cate", search_cate);
			request.setAttribute("search_label", search_label);
			request.setAttribute("search_txt", search_txt);
		}
		
		AllQnADAO dao = AllQnADAO.getInstance();
		
		AllQnADTO dto = dao.getAllQna(num);
		
		request.setAttribute("dto", dto);
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("qna/all_qna_update_form.jsp");
		
		return forward;
	}

}
