package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.FaQDAO;

public class FaQDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int page = Integer.parseInt(request.getParameter("page"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		FaQDAO dao = FaQDAO.getInstance();
		
		int res = dao.deleteFaQ(num);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			if(request.getParameter("cate") != null) {	// 카테고리 리스트에서 수정한경우
				String category = request.getParameter("cate");
				String cateName = URLEncoder.encode(category, "utf-8");
				
				forward.setRedirect(true);
				forward.setPath("faq_list_cate.do?page="+page+"&cate="+cateName);
			}else { // 전체 리스트에서 수정한 경우
				forward.setRedirect(true);
				forward.setPath("faq_list.do?page="+page);
			}
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('삭제 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
