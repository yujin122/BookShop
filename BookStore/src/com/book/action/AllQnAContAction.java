package com.book.action;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.AllQnADTO;

public class AllQnAContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int page = Integer.parseInt(request.getParameter("page"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		AllQnADAO dao = AllQnADAO.getInstance();
		
		dao.allQnAHit(num);
		
		AllQnADTO dto = dao.getAllQna(num);
		
		request.setAttribute("dto", dto);
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		
		if(request.getParameter("cate") != null) {
			String cate = request.getParameter("cate");
			request.setAttribute("cate", cate);
			
			forward.setRedirect(false);
			forward.setPath("qna/all_qna_cont.jsp?page="+page+"&cate="+cate);
		}else {
			forward.setRedirect(false);
			
			if(request.getParameter("search_cate") != null) {
				String cate = request.getParameter("search_cate");
				request.setAttribute("search_cate", cate);
				String label = request.getParameter("search_label");
				request.setAttribute("search_label", label);
				String txt = request.getParameter("search_txt");
				request.setAttribute("search_txt", txt);
				
				forward.setPath("qna/all_qna_search_cont.jsp");
			}else {
				forward.setPath("qna/all_qna_cont.jsp?");
			}
		}
		
		return forward;
	}

}
