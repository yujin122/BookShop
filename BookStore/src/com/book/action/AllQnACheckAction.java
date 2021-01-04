package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.AllQnADTO;

public class AllQnACheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		int page = Integer.parseInt(request.getParameter("page"));
		String pwd = request.getParameter("pwd");
		String btn = request.getParameter("btn");
		
		AllQnADAO dao = AllQnADAO.getInstance();
		
		int res = dao.checkPwd(num,pwd);
		
		ActionForward forward = new ActionForward();
		
		if(res == 1) {
			AllQnADTO dto = dao.getAllQna(num);
			
			request.setAttribute("dto", dto);
			
			if(request.getParameter("cate") != null) {
				String cate = request.getParameter("cate");
				cate = URLEncoder.encode(cate, "utf-8");
				
				forward.setRedirect(true);
				
				if(btn.equals("delete")) {
					forward.setPath("all_qna_delete.do?num="+num);
				}else if(btn.equals("cont")) {
					forward.setPath("all_qna_cont.do?num="+num+"&page="+page+"&cate="+cate);
				}else if(btn.equals("update")) {
					forward.setPath("all_qna_update_form.do?num="+num+"&page="+page+"&cate="+cate);
				}else if(btn.equals("non_update")) {
					forward.setPath("all_qna_non_update_form.do?num="+num+"&page="+page+"&cate="+cate);
				}
			}else {
				forward.setRedirect(true);
				
				if(btn.equals("delete")) {
					forward.setPath("all_qna_delete.do?num="+num);
				}else if(btn.equals("cont")) {
					if(request.getParameter("search_cate") != null) {
						System.out.println(1);
						String cate = request.getParameter("search_cate");
						String label = request.getParameter("search_label");
						String txt = request.getParameter("search_txt");
						
						cate = URLEncoder.encode(cate, "utf-8");
						
						txt = URLEncoder.encode(txt, "utf-8");
						
						forward.setPath("all_qna_cont.do?num="+num+"&page="+page+"&search_cate="+cate+"&search_label="+label+"&search_txt="+txt);
					}else {
						System.out.println(2);
						forward.setPath("all_qna_cont.do?num="+num+"&page="+page);
					}
					
				}else if(btn.equals("update")) {
					if(request.getParameter("search_cate") != null) {
						System.out.println(1);
						String cate = request.getParameter("search_cate");
						String label = request.getParameter("search_label");
						String txt = request.getParameter("search_txt");
						
						cate = URLEncoder.encode(cate, "utf-8");
						
						txt = URLEncoder.encode(txt, "utf-8");
						
						forward.setPath("all_qna_update_form.do?num="+num+"&page="+page+"&search_cate="+cate+"&search_label="+label+"&search_txt="+txt);
					}else {
						forward.setPath("all_qna_update_form.do?num="+num+"&page="+page);
					}
				}else if(btn.equals("non_update")) {
					if(request.getParameter("search_cate") != null) {
						System.out.println(1);
						String cate = request.getParameter("search_cate");
						String label = request.getParameter("search_label");
						String txt = request.getParameter("search_txt");
						
						cate = URLEncoder.encode(cate, "utf-8");
						
						txt = URLEncoder.encode(txt, "utf-8");
						
						forward.setPath("all_qna_non_update_form.do?num="+num+"&page="+page+"&search_cate="+cate+"&search_label="+label+"&search_txt="+txt);
					}else {
						forward.setPath("all_qna_non_update_form.do?num="+num+"&page="+page);
					}
					
				}	
			}
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('비밀번호가 틀렸습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
