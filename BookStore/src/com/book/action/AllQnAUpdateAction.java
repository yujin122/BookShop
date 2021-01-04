package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.AllQnADTO;

public class AllQnAUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		
		String writer, check;
		String pwd = null;
		
		if(session.getAttribute("session_mem_id") != null) {
			writer = session.getAttribute("session_mem_id").toString();
		}else {
			writer = request.getParameter("name");
		}
		
		if(request.getParameter("pwd") != null) {
			pwd = request.getParameter("pwd");
		}
		
		if(request.getParameter("check") != null) {
			check = "1";
		}else {
			check = "0";
		}
		
		int page = Integer.parseInt(request.getParameter("page"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String cont = request.getParameter("cont");
		
		AllQnADTO dto = new AllQnADTO();
		
		dto.setAq_num(num);
		dto.setAq_category(category);
		dto.setAq_title(title);
		dto.setAq_content(cont);
		dto.setAq_pwd(pwd);
		dto.setAq_lock(check);
		dto.setAq_writer(writer);
		
		AllQnADAO dao = AllQnADAO.getInstance();
		
		int res = dao.updateAllQnA(dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			if(request.getParameter("cate") != null) {
				String cate = request.getParameter("cate");
				
				String categ = URLEncoder.encode(cate, "utf-8");
				
				forward.setRedirect(true);
				forward.setPath("all_qna_cont.do?num="+num+"&page="+page+"&cate="+categ);
				
			}else {
				if(request.getParameter("search_cate") != null && request.getParameter("search_label") != null && request.getParameter("search_txt") != null) {
					String search_cate = request.getParameter("search_cate");
					String search_label = request.getParameter("search_label");
					String search_txt = request.getParameter("search_txt");
					
					search_cate = URLEncoder.encode(search_cate, "utf-8");
					search_label = URLEncoder.encode(search_label, "utf-8");
					search_txt = URLEncoder.encode(search_txt, "utf-8");
					
					forward.setRedirect(true);
					forward.setPath("all_qna_cont.do?num="+num+"&page="+page+"&search_cate="+search_cate+"&search_label="+search_label+"&search_txt="+search_txt);
				}else {
					forward.setRedirect(true);
					forward.setPath("all_qna_cont.do?num="+num+"&page="+page);
				}
			}
			
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
